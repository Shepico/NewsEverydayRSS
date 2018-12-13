/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.client;

import javax.swing.*;

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
        add(panelLeft);
        add(panelRight);
        pack();
        setVisible(true);
        
    }
    
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
    }
}