/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package framework;

/**
 *
 * @author fokin
 */
public class Configuration {
    private int width, height;
    private int FPS;
    private String title;
    private boolean showFPS;
    
    public Configuration() {
        width = 320;
        height = 240;
        FPS = 60;
        title = "";
        showFPS = false;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getFPS() {
        return FPS;
    }
    
    public void setFPS(int FPS) {
        this.FPS = FPS;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public boolean isShowFPS() {
        return showFPS;
    }
    
    public void setShowFPS(boolean flag){
        this.showFPS = flag;
    }
}
