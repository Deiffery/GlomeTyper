package net.deiffery.glometyper;

import com.github.kwhat.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;

public class Main{
    static GlomeTyperBot bot;
    public static void main(String[] args) {
        JFrame frame = new JFrame("GlomeTyper");
        try {
            bot = new GlomeTyperBot("", 0, frame);
        } catch (NativeHookException | AWTException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        JLabel label = new JLabel("Enter your text:");
        label.setBounds(30,20,150,25);
        JTextArea textField = new JTextArea();
        textField.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textField);
        scrollPane.setBounds(150,20,150,50);
        JLabel labelDelay = new JLabel("Enter your delay: (ms)");
        labelDelay.setBounds(25, 75, 150,25);
        JTextField textFieldDelay = new JTextField();
        textFieldDelay.setBounds(150, 75,150,25);
        textFieldDelay.setText("0");
        JButton button = new JButton("Submit");
        button.addActionListener(action->{
            String name = textField.getText();
            Integer delay = Integer.valueOf(textFieldDelay.getText());
            if(!bot.isEnabled()){
                bot.start();
            }
            if(!name.isEmpty()){
                bot.setTextToPaste(name);
            } else {
                JOptionPane.showMessageDialog(frame, "Input your text!", "Error", JOptionPane.WARNING_MESSAGE);
                bot.stop();
                return;
            }
            bot.setDelay(delay);
        });
        button.setBounds(13,120,150, 25);
        JButton buttonStop = new JButton("Stop");
        buttonStop.addActionListener(action->{
            if(bot.isEnabled()){
                bot.stop();
            }
        });
        buttonStop.setBounds(170,120,150, 25);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(label);
        frame.add(button);
        frame.add(buttonStop);
        frame.add(labelDelay);
        frame.add(textFieldDelay);
        frame.add(scrollPane);
        Image icon = new ImageIcon("src/main/resources/icon/icon.png").getImage();
        frame.setIconImage(icon);
    }
}