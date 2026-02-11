package net.deiffery.glometyper;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

import javax.tools.Tool;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
public class GlomeTyperBot implements NativeMouseInputListener {

    private final Robot robot;
    private String text;

    private int delay;

    private final AtomicBoolean enabled = new AtomicBoolean(false);

    public GlomeTyperBot(String text, int delay) throws AWTException, NativeHookException {
        this.robot = new Robot();
        this.text = text;
        this.robot.setAutoDelay(40);
        this.delay = delay;
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        if(!GlobalScreen.isNativeHookRegistered()){
            GlobalScreen.registerNativeHook();
        }
        GlobalScreen.addNativeMouseListener(this);
        System.out.println("Bot created. Hook registered: " + GlobalScreen.isNativeHookRegistered());
    }

    public void setTextToPaste(String text){
        this.text = text;
        System.out.println("Text updated: " + text);
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
        System.out.println("Delay updated: " + delay);
    }

    private void typeWithDelay(String text, int delay) throws  InterruptedException {
        for(char ch : text.toCharArray()){
            if(enabled.get()){
                typeChar(ch);
                Thread.sleep(delay);
            }
        }
    }

    private void typeChar(char ch) {
        StringSelection stringSelection = new StringSelection(String.valueOf(ch));
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    public void start(){
        enabled.set(true);
    }

    public void stop(){
        enabled.set(false);
    }

    public boolean isEnabled(){
        return enabled.get();
    }

    public void shutdown() throws NativeHookException {
        enabled.set(false);
        GlobalScreen.removeNativeMouseListener(this);
        if (GlobalScreen.isNativeHookRegistered()) {
            GlobalScreen.unregisterNativeHook();
        }
        System.out.println("Bot shutdown");
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent event){
        if(!enabled.get()) return;
        if(event.getButton() == NativeMouseEvent.BUTTON1){
            try{
                Thread.sleep(120);
                typeWithDelay(text, delay);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override public void nativeMousePressed(NativeMouseEvent e) {}
    @Override public void nativeMouseReleased(NativeMouseEvent e) {}
    @Override public void nativeMouseMoved(NativeMouseEvent e) {}
    @Override public void nativeMouseDragged(NativeMouseEvent e) {}
}
