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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;
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
    private final int HEIGHT = 300;
    
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
        //add(panelLeft);
        /*String link = "http://www.ya.ru";
        String text = "<html><h2>What is Google Labs?</h2>" +
                    "<font face=’verdana’ size = 2>" +
                    " <a href='" + link + "'>Google Labs is a playground</a> <br>" +
                    " where our more adventurous users can play around with <br>" +
                    " prototypes of some of our wild and crazy ideas and <br>" +
                    " offer feedback directly to the engineers who developed<br>" +
                    " them. Please note that Labs is the first phase in <br>" +
                    " a lengthy product development process and none of this <br>" +
                    " stuff is guaranteed to make it onto Google.com. <br>" +
                    " While some of our crazy ideas might grow into the <br>" +
                    " next Gmail or iGoogle, others might turn out to be, <br>" +
                    " well, just plain crazy.</html>";
         

        JLabel htmlLabel = new JLabel();
        htmlLabel.setText(text);        
        htmlLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //htmlLabel.addMouseListener(goWebsite);
        goWebsite(htmlLabel, link);*/
        NewsList newsList = ParseRss.parse("https://news.yandex.ru/finances.rss");
        for (int i=0; i<newsList.getSize(); i++ ){            
            //News news = newsList.getNews(i);
            NewsLabel nl = new NewsLabel(newsList.getNews(i));
            panelRight.add(nl); 
        }            
        add(panelRight);
        pack();
        setVisible(true);
        
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
        panelRight.setBorder(BorderFactory.createTitledBorder("NEWS"));        
    }
}