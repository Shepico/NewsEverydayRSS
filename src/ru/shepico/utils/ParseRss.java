/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.shepico.object.ChannelList;

public class ParseRss {

    //private static Logger log = Logger.getLogger(ParseRss.class.getName()); //логер

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
                    //
                    String guidBrutto = entry.getElementsByTagName("guid").item(0).getTextContent();
                    String guid = "";
                    //Если GUID содержит параметры еще. Чистим от них
                    int index = guidBrutto.indexOf("?");
                    if (index > 0) {
                        guid =  guidBrutto.substring(0,index);
                    }else guid = guidBrutto;
                    /*System.out.println(guidBrutto);
                    System.out.println(guid);*/
                    //Пишем все новости в базу
                    db.addNewsDB(guid, title, link, pubDate, description);
                }

            }
        } catch (MalformedURLException em){
            LoggerMy.exLog(em);
        } catch (IOException eio) {
            LoggerMy.exLog(eio);
        }
    }

    private static Document parseXML(InputStream stream){
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(stream);
        } catch (SAXException es) {
            LoggerMy.exLog(es);
        } catch (ParserConfigurationException ep) {
            LoggerMy.exLog(ep);
        } catch (IOException eio) {
            LoggerMy.exLog(eio);
        }
        return doc;
    }
}