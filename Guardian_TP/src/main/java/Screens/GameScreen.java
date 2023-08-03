/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Screens;

import Game.Board;
import Game.GameManager;
import Game.Player.Team;
import framework.Screen;
import java.awt.Color;
import java.awt.Graphics2D;
import framework.Game;
/**
 *
 * @author fokin
 */
public class GameScreen implements Screen{

    private Board board;
    private GameManager manager;
    private Team playerTeam, botTeam;
    
    private int gamemode;
    public GameScreen(int gamemode, Team playerTeam, Team botTeam)  {
        this.gamemode = gamemode;
        this.playerTeam = playerTeam;
        this.botTeam = botTeam;
    }
    
    @Override
    public void create() {
        manager = new GameManager(gamemode, playerTeam, botTeam);
        board = manager.getBoard();
    }

    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, Game.conf.getWidth(), Game.conf.getHeight());

        //Draw board

        Color light = Color.decode("#fbefe1");
        Color exit = Color.decode("#07a8a6");
        Color throne = Color.decode("#e6231c");//e6231c
        Color color = null;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                
                color = light;
                if (isExit(i, j)) color = exit;
                else if (i == 4 && j == 4) color = throne;
                
                g.setColor(color);
                g.fillRect(j*60 + 30, i*60 + 30, 60, 60);
                g.setColor(Color.black);
                g.drawRect(j*60 + 30, i*60 + 30, 60, 60);
                

            }
        }
        
        g.setColor(new Color(0, 0, 0, 60));
        if(Game.input.getMouseX() < 570 && Game.input.getMouseY() < 570)
            g.fillRect((Game.input.getMouseX()-30)/60*60+30,
                    (Game.input.getMouseY()-30)/60*60+30, 60, 60);
        
        g.setColor(new Color(0, 0, 255, 50));
        if(manager.activeCell != null)
            g.fillRect(manager.activeCell.getX()*60+30, manager.activeCell.getY()*60+30, 60, 60);

        board.draw(g);
        manager.draw(g);
    }
    
    private boolean isExit (int x, int y) {
        if ((x == 0 && y == 0) ||
            (x == 8 && y == 8) ||
            (x == 8 && y == 0)||
            (x == 0 && y == 8)  ) return true;
        else return false;
    }
    
}
