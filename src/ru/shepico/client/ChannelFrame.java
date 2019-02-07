package ru.shepico.client;

import ru.shepico.object.Channel;
import ru.shepico.object.ChannelList;
import ru.shepico.utils.DBaccess;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ChannelFrame extends JFrame {
    JPanel mainPanel;
    JPanel editPanel;
    JTable tableChannel;
    DefaultTableModel modelTable;
    //
    JTextField titleField;
    JTextField linkField;
    JTextArea descArea;
    DBaccess db;
    ChannelList cl;

    public ChannelFrame(ChannelList cl, DBaccess db) {
        this.db = db;
        this.cl = cl;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initFrame();
            }
        });
    }

    private void initFrame() {
        setTitle("Channels");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        //
        modelTable = new DefaultTableModel();
        createHeaderTable();
        //
        writeInChannnels(); //Заполнить список каналов
        //
        tableChannel = new JTable(modelTable);
        tableChannel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                listenerTable();
            }
        });
        tableChannel.getColumn("Title").setPreferredWidth(150);
        tableChannel.getColumn("Link").setPreferredWidth(300);
        tableChannel.getColumn("Description").setPreferredWidth(350);
        //
        initEditPanel();
        //
        mainPanel = new JPanel();
        mainPanel.add(tableChannel);
        //
        add(mainPanel, BorderLayout.CENTER);
        add(editPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void createHeaderTable() {
        Object[] titleColumn = {"Title", "Link", "Description"};
        modelTable.setColumnIdentifiers(titleColumn);
    }


    private void writeInChannnels() {
        Channel channel;

        cl = db.selectChannelDB();
        for (int i = 0; i < cl.getSizeChannelList(); i++) {
            channel = cl.getChannel(i);
            modelTable.addRow(channel.getRowChannel());
        }
    }

    private void initEditPanel() {
        editPanel = new JPanel();
        editPanel.setLayout(new BorderLayout());

        JButton addBtn = new JButton("Добавить");
        JButton changeBtn = new JButton("Изменить");
        JButton removeBtn = new JButton("Удалить");
        titleField = new JTextField("title");
        linkField = new JTextField("link");
        descArea = new JTextArea("desc");
        descArea.setWrapStyleWord(true);
        //
        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new GridLayout(3, 1));
        panelBtn.add(addBtn);
        panelBtn.add(changeBtn);
        panelBtn.add(removeBtn);
        changeBtn.setVisible(false);//todo удалить после уточнения необходимости
        //
        JPanel panelField = new JPanel();
        panelField.setLayout(new BoxLayout(panelField, BoxLayout.Y_AXIS));
        panelField.add(titleField);
        panelField.add(linkField);
        panelField.add(descArea);
        //
        editPanel.add(panelBtn, BorderLayout.EAST);
        editPanel.add(panelField, BorderLayout.CENTER);
        //
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addChannel();
            }
        });
        changeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("изменяем");
            }
        });
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeChannel();
            }
        });
    }

    private void listenerTable() {
        int indexRow = tableChannel.getSelectedRow();
        titleField.setText((String) tableChannel.getValueAt(indexRow, 0));
        linkField.setText((String) tableChannel.getValueAt(indexRow, 1));
        descArea.setText((String) tableChannel.getValueAt(indexRow, 2));
    }

    //Работа с базой данных
    private Channel createChannelForActivity() {
        Channel newChannel = new Channel(titleField.getText(), linkField.getText(), descArea.getText());
        return newChannel;
    }

    private void addChannel() {
        Channel newChannel = createChannelForActivity();
        if (db.addChannelDB(newChannel)) {
            modelTable.addRow(newChannel.getRowChannel());
            System.out.println("Канал добавлен"); //todo выводить пользователю
        }
    }

    private void removeChannel() {
        Channel newChannel = createChannelForActivity();
        if (db.removeChannelDB(newChannel)) {
            //todo обновить список каналов
            System.out.println("Канал удален"); //todo выводить пользователю
        }
    }
}