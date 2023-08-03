/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Screens;

import Game.Player.Team;
import framework.Game;
import framework.Screen;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author fokin
 */
public class ChooseTeamScreen implements Screen {
    
    private Rectangle VarangOptionRect;
    private Rectangle VikingOptionRect;

    
    private final Color  background = Color.decode("#cfeebe");
    private final Color  buttons = Color.decode("#f89345");
    private BufferedImage tileset;
    private BufferedImage varangianImage, vikingImage;
    
    
    @Override
    public void create() {
        VarangOptionRect = new Rectangle(100, 400, 300, 70);
        VikingOptionRect = new Rectangle(500, 400, 300, 70);
        tileset = Game.files.loadImage("res/tileset2.png");
        varangianImage = tileset.getSubimage(0, 0,200, 200);
        vikingImage = tileset.getSubimage(200, 0, 200, 200);
    }

    @Override
    public void update() {
        if(Game.input.isKeyDown(KeyEvent.VK_ESCAPE))
            Game.app.setScreen(new MenuScreen());
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(background);
        g.fillRect(0, 0, Game.conf.getWidth(), Game.conf.getHeight());
        
        g.setColor(Color.decode("#ff642e"));
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 65));
        g.drawString("ВЫБЕРИТЕ СТОРОНУ", 100, 80);
         g.setColor( buttons.brighter());
        if(VarangOptionRect.contains(Game.input.getMouseX(), Game.input.getMouseY())) {
            if (Game.input.isMouseDown(MouseEvent.BUTTON1)) Game.app.setScreen(new GameScreen(1, Team.VARANGIANS, Team.VIKINGS));
            g.fillRect(VarangOptionRect.x-10, VarangOptionRect.y-10, 320, 90);
        } else if(VikingOptionRect.contains(Game.input.getMouseX(), Game.input.getMouseY())) {
            if (Game.input.isMouseDown(MouseEvent.BUTTON1)) Game.app.setScreen(new GameScreen(1, Team.VIKINGS,  Team.VARANGIANS));
            g.fillRect(VikingOptionRect.x-10, VikingOptionRect.y-10, 320, 90);
        } 
        
        g.setColor( buttons);
        g.fill(VarangOptionRect);
        g.fill(VikingOptionRect);

        g.setColor(Color.WHITE);
        g.drawString("Варяги", 135, 455);
        g.drawString("Викинги", 517, 455);
        
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        
        g.drawImage(varangianImage, 150, 150, null);
        g.drawImage(vikingImage, 550, 150, null);
 
    }

}
