package net.deiffery.glometyper;

import com.github.kwhat.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Main extends JFrame {
    static ArrayList<Component> frameComponents = new ArrayList<>();
    static GlomeTyperBot bot;
    public static void main(String[] args) {
        JFrame frame = new JFrame("GlomeTyper");
        try {
            bot = new GlomeTyperBot("", 0, frame);
        } catch (NativeHookException | AWTException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try{
                    if(bot != null){
                        bot.shutdown();
                    }
                } catch (NativeHookException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JLabel label = new JLabel("Enter your text:");
        label.setBounds(30,20,150,25);
        frameComponents.add(label);
        JTextArea textField = new JTextArea();
        textField.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textField);
        scrollPane.setBounds(150,20,150,50);
        frameComponents.add(scrollPane);
        JLabel labelDelay = new JLabel("Enter your delay: (ms)");
        labelDelay.setBounds(25, 75, 150,25);
        frameComponents.add(labelDelay);
        JTextField textFieldDelay = new JTextField();
        textFieldDelay.setBounds(150, 75,150,25);
        textFieldDelay.setText("0");
        frameComponents.add(textFieldDelay);
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
        frameComponents.add(button);
        JButton buttonStop = new JButton("Stop");
        buttonStop.addActionListener(action->{
            if(bot.isEnabled()){
                bot.stop();
            }
        });
        buttonStop.setBounds(170,120,150, 25);
        frameComponents.add(buttonStop);
        frame.setVisible(true);
        frame.setResizable(false);
        renderFrameComponents(frame);
        Image icon = new ImageIcon(Main.class.getResource("/icon/icon.png")).getImage();
        frame.setIconImage(icon);
    }

    public static void renderFrameComponents(JFrame frame){
        for(int i = 0; i < frameComponents.size(); i++){
            Component component = frameComponents.get(i);
            frame.add(component);
        }
    }
}