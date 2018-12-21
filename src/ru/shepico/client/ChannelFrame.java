package ru.shepico.client;

import javax.swing.*;
import java.awt.*;

public class ChannelFrame extends JFrame {
    JPanel mainPanel;

    public static void main(String[] args) {
        new ChannelFrame();
    }

    public ChannelFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initFrame();
            }
        });
    }

    private void initFrame(){
        setTitle("Channels");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));


        pack();
        setVisible(true);
    }
}