/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.client;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import ru.shepico.object.News;

/**
 *
 * @author PS.Sheenkov
 */
public class NewsLabel extends JLabel {
    private String title;
    private String link;
    private String linkFollow;
    private String desc;
    private String datePub;
    //
    private final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR); //todo in const
    
    public NewsLabel(News news){
        super();        
        title = "<b>" + news.getTitle() + "</b>";
        datePub = news.getDatePub();
        linkFollow = news.getLink();
        link = "<a href='" + linkFollow + "'>" + title + "</a>";         
        desc = news.getDescription();
        String br= "<br>";
        setText("<html><p>" + datePub + " " + link + br + desc + "</p></html>");        
        setCursor(HAND_CURSOR);          
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
