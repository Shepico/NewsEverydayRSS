/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.utils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ru.shepico.object.ChannelList;

/**
 * @author PS.Sheenkov
 */
public class ParseRss {

    public static void parse(ChannelList urlString, DBaccess db) {
        try {
            for (int j = 0; j < urlString.getSizeChannelList(); j++) {
                URL url = new URL(urlString.getChannel(j).getLink());
                URLConnection connection = url.openConnection();
                Document doc = parseXML(connection.getInputStream());

                //item
                NodeList nodeList = doc.getElementsByTagName("item");
                //News news;
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element entry = (Element) nodeList.item(i);
                    String title = entry.getElementsByTagName("title").item(0).getTextContent();
                    String link = entry.getElementsByTagName("link").item(0).getTextContent();
                    String pubDate = entry.getElementsByTagName("pubDate").item(0).getTextContent();
                    String description = "";
                    NodeList desc = entry.getElementsByTagName("description");
                    if (desc.getLength() > 0) {
                        description = desc.item(0).getTextContent();
                    }
                    String guid = entry.getElementsByTagName("guid").item(0).getTextContent();

                    //Пишем все новости в базу
                    db.addNewsDB(guid, title, link, pubDate, description);
                }

            }
        } catch (Exception e) {
            e.printStackTrace(); // todo разобратся с логированием и кокретным исключением
        }
    }

    private static Document parseXML(InputStream stream) throws Exception {
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(stream);
        } catch (Exception ex) {
            throw ex;
        }
        return doc;
    }
}