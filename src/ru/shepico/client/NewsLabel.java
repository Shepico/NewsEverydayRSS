/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.client;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import javax.swing.JEditorPane;
import ru.shepico.object.News;
import ru.shepico.object.NewsList;
import ru.shepico.utils.DBaccess;
import ru.shepico.utils.MyDataChangedListener;

/**
 *
 * @author PS.Sheenkov
 */
public class NewsLabel extends JEditorPane{
    private DBaccess db;
    private NewsList newsList;
    private News news;
    private String title;
    private String link;
    private String linkFollow;
    private String desc;
    private String datePub;
    private String guid;
    //
    private ArrayList<MyDataChangedListener> listeners = new ArrayList<MyDataChangedListener>();

    public void addListener(MyDataChangedListener listener) {
        listeners.add(listener);
    }

    public void removeListener(MyDataChangedListener listener) {
        listeners.remove(listener);
    }

    private void onDataChangedListeners() {
        for(MyDataChangedListener listener : listeners) {
            listener.onDataChanged(this);
        }
    }

    //
    private final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR); //todo in const
    
    public NewsLabel(News news, DBaccess db, NewsList newsList){
        //super();
        setContentType("text/html");
        setEditable(false);
        setCursor(HAND_CURSOR); 
        //this.setAutoscrolls(true);
        //
        this.db = db;
        this.newsList = newsList;
        this.news = news;
        //
        setSize(new Dimension(10,100));
        //setMinimumSize(new Dimension(290,10));
        //setPreferredSize(new Dimension(290,250));        
        setMaximumSize(new Dimension(290,1250));
        
        
        title = "<b>" + news.getTitle() + "</b>";
        datePub = news.getDatePub();
        linkFollow = news.getLink();
        guid = news.getGuid();
        link = "<a href='" + linkFollow + "'>" + title + "</a>";         
        //desc = news.getDescription(); //отключили описание
        String br= "<br>";
        //setText("<html><p>" + datePub + " " + link + br + desc + "</p></html>");
        setText("<html><p>" + datePub + " " + link + "</p></html>");
                
        //this.setBorder(BorderFactory.createDashedBorder(Color.orange));
        goWebsite();
    }
    
    private void goWebsite() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (e.getButton() == MouseEvent.BUTTON1) { //левая кнопка мыши
                        Desktop.getDesktop().browse(new URI(linkFollow));
                    }
                    db.readNewsDB(guid);
                    /*}else if (e.getButton() == MouseEvent.BUTTON3) { //правая кнопка мыши
                        db.readNewsDB(guid);
                        System.out.println("Правая кнопка, прочли");
                    }*/
                    System.out.println("Любая кнопка, прочли");
                    newsList.removeNews(news);
                    onDataChangedListeners();
                } catch (Exception ex) {
                    //todo in logger
                    ex.printStackTrace();
                }
            }
        });

    }
    
}
