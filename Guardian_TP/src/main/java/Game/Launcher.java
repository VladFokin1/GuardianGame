/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;



import Screens.MenuScreen;
import framework.Application;
import framework.Configuration;

/**
 *
 * @author fokin
 */
public class Launcher {
    public static void main(String args[]){
        Configuration conf = new Configuration();
        conf.setFPS(60);
        conf.setWidth(900);
        conf.setHeight(600);
        conf.setShowFPS(false);
        conf.setTitle("Оберег");
        new Application(conf).setScreen(new MenuScreen());
    }
}
