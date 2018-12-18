/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.client;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import ru.shepico.object.ChannelList;
import ru.shepico.object.News;
import ru.shepico.object.NewsList;
import ru.shepico.utils.DBaccess;
import ru.shepico.utils.ParseRss;

public class ReaderRSS_GUI extends JFrame{
    private JPanel panelLeft;
    private JPanel panelRight;
    private JScrollPane scrPane; 
    //button
    private JButton btnChannelPanelVisible;
    private JButton btnAddChannel;
    private JButton btnRemoveChannel;
    //const
    private final int WIDTH = 275;
    private final int HEIGHT = 700;
    
    public static void main (String[] args){
        SwingUtilities.invokeLater(new Runnable() {            
            public void run() {
                new ReaderRSS_GUI();
            }   
        });
    }    
    
    public ReaderRSS_GUI() {
        
        createAndShowGUI();   
    }
    
    private void createAndShowGUI(){
        initPanel();
        initButton();
        //
        setTitle(""); //todo установить заголовок программы
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH+50, HEIGHT);
        
        createLabelNews();
        
        //scrPane.setSize(WIDTH, HEIGHT);
        //scrPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        //add(btnChannelPanelVisible, BorderLayout.NORTH);
        add(panelLeft);
        add(scrPane);
        //add(panelRight);
        setLocationRelativeTo(null);
        setVisible(true);
        //pack();
    }
    //
    /*private void goWebsite(JLabel website, String link) {
        website.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(link));
                } catch (Exception ex) {
                    //todo in logger
                    ex.printStackTrace();
                }
            }
        });
    }*/

    //
    
    private void createLabelNews(){
        DBaccess db = new DBaccess();
        ChannelList cl = db.selectChannelDB();
        /*String[] arrayChannel = {"https://news.yandex.ru/finances.rss", 
                                        "https://www.vedomosti.ru/rss/news"};*/
        //NewsList newsList = ParseRss.parse("https://news.yandex.ru/finances.rss");
        NewsList newsList = ParseRss.parse(cl);
        for (int i=0; i<newsList.getSize(); i++ ){            
            //News news = newsList.getNews(i);
            NewsLabel nl = new NewsLabel(newsList.getNews(i));            
            panelRight.add(nl,0);            
        }           

        scrPane = new JScrollPane(panelRight);
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);   
    }
    
    private void initButton(){        
        btnAddChannel = new JButton("add"); //todo Добавить иконку
        btnRemoveChannel = new JButton("remove"); //todo Добавить иконку
        btnChannelPanelVisible = new JButton("Channel"); //todo add icon
        btnChannelPanelVisible.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelLeft.isVisible()) {
                    panelLeft.setVisible(false);
                    setSize(WIDTH+50, HEIGHT);
                }else
                    setSize(WIDTH*2+50, HEIGHT);
                    panelLeft.setVisible(true);
                }               
        });                
    }
    
    private void initPanel(){
        panelLeft = new JPanel();
        panelLeft.setName("Left");
        panelLeft.setAutoscrolls(true);
        panelLeft.setSize(WIDTH, HEIGHT); 
        panelLeft.setVisible(false);
        //
        panelRight = new JPanel();
        panelRight.setName("Right");
        panelRight.setAutoscrolls(true);
        panelRight.setSize(WIDTH, HEIGHT);        
        //panelRight.setPreferredSize(new Dimension(WIDTH, HEIGHT));        
        //panelRight.setMaximumSize(new Dimension(WIDTH, HEIGHT));        
        panelRight.setBorder(BorderFactory.createTitledBorder("NEWS"));
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
    }
}