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
import ru.shepico.object.News;

/**
 *
 * @author PS.Sheenkov
 */
public class ParseRss {       

    public void parse(URL url){
//        URL url = new URL("https://news.yandex.ru/finances.rss");
        try {
            URLConnection connection = url.openConnection();

            Document doc = parseXML(connection.getInputStream());
            NodeList nodeList = doc.getElementsByTagName("item");
            News news;    
            for(int i=0; i<nodeList.getLength();i++) {
                Element entry = (Element) nodeList.item(i);
                String title = entry.getElementsByTagName("title").item(0).getTextContent();
                String link = entry.getElementsByTagName("link").item(0).getTextContent();
                String pubDate = entry.getElementsByTagName("pubDate").item(0).getTextContent();
                String description = entry.getElementsByTagName("description").item(0).getTextContent();

                news = new News(title, link, description, pubDate);
                //System.out.println(descNodes.item(i).getTextContent());
                news.toString();
            }
        }catch (Exception e) {
            e.printStackTrace(); // todo разобратся с логированием и кокретным исключением
        }
    }
    

    private Document parseXML(InputStream stream) throws Exception {        
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(stream);
        } catch(Exception ex) {
            throw ex;
        }       
        return doc;
    }
}