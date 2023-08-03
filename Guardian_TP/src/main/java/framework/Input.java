/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package framework;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


/**
 *
 * @author fokin
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener {

    private boolean[] keys = new boolean[256];
    private boolean[] releasedKeys = new boolean[256];
    
    private boolean[] buttons = new boolean[5];
    private boolean[] releasedButtons = new boolean[5];
    
    private int mouseX, mouseY;
    
    public void updateInput() {
        for (int i = 0; i <256; i++) 
            releasedKeys[i] = false;
        
        for (int i = 0; i <5; i++) 
            releasedButtons[i] = false;
    }
    
    public boolean isKeyDown(int code) {
        return keys[code];
    }
    
    public boolean isKeyUp(int code) {
        return releasedKeys[code];
    }
    
    public boolean isMouseDown(int code) {
        return buttons[code];
    }
    
    public boolean isMouseUp(int code) {
        return releasedButtons[code];
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        releasedKeys[e.getKeyCode()] = true;
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        releasedButtons[e.getButton()] = true;
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    public int getMouseX() {
        return mouseX;
    }
    
    public int getMouseY() {
        return mouseY;
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }


    
}
