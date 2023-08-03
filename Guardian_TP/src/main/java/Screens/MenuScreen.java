/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Screens;


import Game.Player.Team;
import Screens.GameScreen;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import framework.*;
import java.awt.Font;
import java.awt.event.MouseEvent;

/**
 *
 * @author fokin
 */
public class MenuScreen implements Screen{

    private Rectangle singlePlayerRect;
    private Rectangle multiPlayerRect;
    private Rectangle exitRect;
    
    private final Color  background = Color.decode("#cfeebe");
    private final Color  buttons = Color.decode("#f89345");
    
    @Override
    public void create() {
        singlePlayerRect = new Rectangle(200, 150, 500, 70);
        multiPlayerRect = new Rectangle(200, 300, 500, 70);
        exitRect = new Rectangle(325, 500, 250, 50);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(background);
        g.fillRect(0, 0, Game.conf.getWidth(), Game.conf.getHeight());
        
        g.setColor(Color.decode("#ff642e"));
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 65));
        g.drawString("ОБЕРЕГ", 300, 80);
        
        g.setColor( buttons.brighter());
        if(singlePlayerRect.contains(Game.input.getMouseX(), Game.input.getMouseY())) {
            if (Game.input.isMouseDown(MouseEvent.BUTTON1)) Game.app.setScreen(new ChooseTeamScreen());
            g.fillRect(singlePlayerRect.x-10, singlePlayerRect.y-10, 520, 90);
        } else if(multiPlayerRect.contains(Game.input.getMouseX(), Game.input.getMouseY())) {
            if (Game.input.isMouseDown(MouseEvent.BUTTON1)) Game.app.setScreen(new GameScreen(2, Team.VARANGIANS, Team.VIKINGS));
            g.fillRect(multiPlayerRect.x-10, multiPlayerRect.y-10, 520, 90);
        } else if(exitRect.contains(Game.input.getMouseX(), Game.input.getMouseY())) {
            if (Game.input.isMouseDown(MouseEvent.BUTTON1)) System.exit(1);
            g.fillRect(exitRect.x-5, exitRect.y-5, 260, 60);
        }

        
        g.setColor( buttons);
        g.fill(singlePlayerRect);
        g.fill(multiPlayerRect);
        g.fill(exitRect);

        g.setColor(Color.WHITE);
        g.drawString("1 игрок", 300, 205);
        g.drawString("2 игрока", 300, 355);
        
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 42));
        g.drawString("Выйти", 380, 540);

    }
}
