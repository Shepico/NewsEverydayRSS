/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.client;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import ru.shepico.object.News;
import ru.shepico.object.NewsList;
import ru.shepico.utils.ParseRss;

public class ReaderRSS_GUI extends JFrame{
    private JPanel panelLeft;
    private JPanel panelRight;
    //button
    private JButton btnAdd;
    private JButton btnRemove;
    //const
    private final int WIDTH = 300;
    private final int HEIGHT = 600;
    
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
        
        NewsList newsList = ParseRss.parse("https://news.yandex.ru/finances.rss");
        for (int i=0; i<newsList.getSize(); i++ ){            
            //News news = newsList.getNews(i);
            NewsLabel nl = new NewsLabel(newsList.getNews(i));
            panelRight.add(nl);
        }            
        
        /*JEditorPane test = new JEditorPane();
        test.setContentType("text/html");
        //test.setLineWrap(true);
        //test.setWrapStyleWord(true);
        //test.setMaximumSize(new Dimension(250,550));
        test.setPreferredSize(new Dimension(230,500));
       // test.setMinimumSize(new Dimension(10,10));
        test.setText("<html><div>dfsfsfdfsd dfsfsfdfsd dfsfsfdfdfsfsfdfsddf sfsfdfsdsddfsfsfdfsd</div><h2>Desktop</h2>.getDesktop().browse(new URI(link));</html>");*/
        //panelRight.add(scrPane);
        JScrollPane scrPane = new JScrollPane(panelRight);
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        //scrPane.setSize(WIDTH, HEIGHT);
        //scrPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        add(scrPane);
        //add(panelRight);
        
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
    private void initButton(){
        btnAdd = new JButton("add"); //todo Добавить иконку
        btnRemove = new JButton("remove"); //todo Добавить иконку
    }
    
    private void initPanel(){
        panelLeft = new JPanel();
        panelLeft.setName("Left");
        panelLeft.setAutoscrolls(true);
        panelLeft.setSize(WIDTH, HEIGHT);
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