/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import ru.shepico.object.News;

/**
 *
 * @author PS.Sheenkov
 */
public class NewsLabel extends JEditorPane {
    private String title;
    private String link;
    private String linkFollow;
    private String desc;
    private String datePub;
    //
    private final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR); //todo in const
    
    public NewsLabel(News news){
        //super();               
        setContentType("text/html");
        setEditable(false);
        setCursor(HAND_CURSOR);   
        setPreferredSize(new Dimension(290,590));        
        setMinimumSize(new Dimension(10,10));
        
        title = "<b>" + news.getTitle() + "</b>";
        datePub = news.getDatePub();
        linkFollow = news.getLink();
        link = "<a href='" + linkFollow + "'>" + title + "</a>";         
        desc = "<p>" + news.getDescription() + "</p>";
        String br= "<br>";
        setText("<html><p>" + datePub + " " + link + br + desc + "</p></html>");                 
        

        //this.setBorder(BorderFactory.createDashedBorder(Color.orange));
        goWebsite(); 
    }
    
    private void goWebsite() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(linkFollow));
                } catch (Exception ex) {
                    //todo in logger
                    ex.printStackTrace();
                }
            }
        });
    }
    
}
