package net.deiffery.glometyper;

import com.github.kwhat.jnativehook.NativeHookException;
import net.deiffery.glometyper.GlomeTyperBot;

import javax.swing.*;
import java.awt.*;

public class Main {

    static GlomeTyperBot bot;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GlomeTyper");
        try {
            bot = new GlomeTyperBot("", 0);
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        JLabel label = new JLabel("Enter your text:");
        label.setBounds(30,20,150,25);
        JTextArea textField = new JTextArea();
        textField.setBounds(150,20,150,50);
        JLabel labelDelay = new JLabel("Enter your delay:");
        labelDelay.setBounds(25, 75, 150,25);
        JTextField textFieldDelay = new JTextField();
        textFieldDelay.setBounds(150, 75,150,25);
        JButton button = new JButton("Submit");
        button.addActionListener(action->{
            String name = textField.getText();
            Integer delay = Integer.valueOf(textFieldDelay.getText());
            if(!bot.isEnabled()){
                bot.start();
            }
            bot.setTextToPaste(name);
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
        frame.add(textField);
        frame.add(button);
        frame.add(buttonStop);
        frame.add(labelDelay);
        frame.add(textFieldDelay);
    }
}