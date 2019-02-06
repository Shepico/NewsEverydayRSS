/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import ru.shepico.object.ChannelList;
import ru.shepico.object.NewsList;
import ru.shepico.utils.DBaccess;
import ru.shepico.utils.MyDataChangedListener;
import ru.shepico.utils.ParseRss;
import ru.shepico.utils.StaticUtils;

public class ReaderRSS_GUI extends JFrame implements MyDataChangedListener {
    private NewsList newsList;
    private JPanel panelRight;
    private JScrollPane scrPane;
    //button
    private JButton btnChannelPanelVisible;
    //
    DBaccess db;
    ChannelList cl;
    //const
    private final int WIDTH = 325;
    private final int HEIGHT = 700;

    public static void main(String[] args) {
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
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                createLabelNews();
            }
        };// конец перформера
        Timer tim = new Timer(900000, taskPerformer); //создаем объект таймер, 500 это время сработки в миллисекундах
        tim.start();//запускаем таймер
    }

    private void createAndShowGUI() {

        initPanel();
        initButton();
        scrPane = new JScrollPane(panelRight);
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //Общие данные
        setTitle(StaticUtils.NAME_PRG + " " + StaticUtils.VERSION); //Заголовок программы
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize(); //получаем разрешение desktopa
        int visibleHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height; //получаем высотц видимой области
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, visibleHeight);
        setLocation(sSize.width - WIDTH + 8, 0);
        setAlwaysOnTop(true); //всегда сверху       
        add(btnChannelPanelVisible, BorderLayout.NORTH);
        add(scrPane);
        //
        setVisible(true);

    }

    private void createLabelNews() {
        db = new DBaccess();
        cl = db.selectChannelDB();
        panelRight.removeAll();
        ParseRss.parse(cl, db);
        newsList = db.selectNewsDB();
        newsList.sortDatePub();
        for (int i = 0; i < newsList.getSize(); i++) {
            NewsLabel nl = new NewsLabel(newsList.getNews(i), db, newsList);
            nl.addListener(this);
            panelRight.add(nl, 0);
        }
        System.out.println("Прошли");
    }

    private void initButton() {

        btnChannelPanelVisible = new JButton("Channels"); //todo add icon
        btnChannelPanelVisible.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChannelFrame channelFrame = new ChannelFrame(cl, db);
            }
        });
    }

    private void initPanel() {
        panelRight = new JPanel();
        panelRight.setName("Right");
        panelRight.setAutoscrolls(true);
        panelRight.setSize(WIDTH, HEIGHT);
        panelRight.setBorder(BorderFactory.createTitledBorder("NEWS"));
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
    }

    @Override
    public void onDataChanged(NewsLabel nl) {
        panelRight.remove(nl);
        panelRight.repaint();
        panelRight.validate();
    }
}