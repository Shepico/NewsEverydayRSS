/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import ru.shepico.object.ChannelList;
import ru.shepico.object.NewsList;
import ru.shepico.utils.DBaccess;
import ru.shepico.utils.ParseRss;

public class ReaderRSS_GUI extends JFrame{
    private JPanel panelLeft;
    private JPanel panelRight;
    private JScrollPane scrPane; 
    //button
    private JButton btnChannelPanelVisible;
    //
    DBaccess db;
    ChannelList cl;
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
        createLabelNews();
        //
        /*Runnable taskRead = new Runnable(){
            public void run(){
                createLabelNews();
            }            
        };        
        //taskRead.run();*/
        ActionListener taskPerformer = new ActionListener() {
            @Override public void actionPerformed(ActionEvent evt) {
                createLabelNews();
            }
        };// конец перформера
        Timer tim=new Timer(900000, taskPerformer); //создаем объект таймер, 500 это время сработки в миллисекундах
        tim.start();//запускаем таймер


        /*ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(this::createLabelNews, 0L, 1L, TimeUnit.MINUTES);*/
    }
    
    private void createAndShowGUI(){
        initPanel();
        initButton();
        scrPane = new JScrollPane(panelRight);
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);   
        //
        setTitle(""); //todo установить заголовок программы
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH+50, HEIGHT);
        setAlwaysOnTop(true); //всегда сверху       
        //setExtendedState(JFrame.MAXIMIZED_VERT);          
        
        //scrPane.setSize(WIDTH, HEIGHT);
        //scrPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        add(btnChannelPanelVisible, BorderLayout.NORTH);
        //add(panelLeft);

        add(scrPane);
        //add(panelRight);
        //pack();
        setLocationRelativeTo(null);
        
        setVisible(true);
        
    }
    
    private void createLabelNews(){
        db = new DBaccess();
        cl = db.selectChannelDB();
        panelRight.removeAll();
        /*String[] arrayChannel = {"https://news.yandex.ru/finances.rss", 
                                        "https://www.vedomosti.ru/rss/news"};*/
        //NewsList newsList = ParseRss.parse("https://news.yandex.ru/finances.rss");
        NewsList newsList = ParseRss.parse(cl);        
        for (int i=0; i<newsList.getSize(); i++ ){            
            //News news = newsList.getNews(i);
            NewsLabel nl = new NewsLabel(newsList.getNews(i));            
            
            panelRight.add(nl,0);            
        }           
        System.out.println("Прошли");

        panelRight.revalidate();
        panelRight.repaint();
        //scrPane.repaint();
        //repaint();
    }   
    
    
    private void initButton(){        
        /*btnAddChannel = new JButton("add"); //todo Добавить иконку
        btnRemoveChannel = new JButton("remove"); //todo Добавить иконку*/
        btnChannelPanelVisible = new JButton("Channels"); //todo add icon
        btnChannelPanelVisible.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ChannelFrame channelFrame = new ChannelFrame(cl, db);
                /*if (panelLeft.isVisible()) {
                    panelLeft.setVisible(false);
                    setSize(WIDTH+50, HEIGHT);
                }else
                    setSize(WIDTH*2+50, HEIGHT);
                    panelLeft.setVisible(true);
                }*/
            }
        });                
    }
    
    private void initPanel(){
        /*panelLeft = new JPanel();
        panelLeft.setName("Left");
        panelLeft.setAutoscrolls(true);
        panelLeft.setSize(WIDTH, HEIGHT);
        panelLeft.setLayout(new GridLayout(1,2));
        panelLeft.add(new Label("тест1"));
        panelLeft.add(new Label("тест2"));
        panelLeft.setVisible(false);*/

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