/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import DataStructures.LList;
import Game.Figures.Figure;
import Game.Figures.King;
import Game.Player.Move;
import Game.Player.Player;
import Game.Player.Team;
import Screens.MenuScreen;
import java.awt.Graphics2D;
import framework.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author fokin
 */
public class GameManager {
    private Board board;
    public Cell activeCell;
    private Cell[][] cells;
    private MoveController controller;
    private AIController aiController;
    
    public Player currentPlayer;
    private Player player;
    private Player opponent;
    private Team playerTeam, botTeam;
    
    private Player winner = null;
    
    private int gamemode;
    
    private int gameOverTimer;
    private int playerChangeTimer;
    
    private static final int SINGLE_PLAYER_MODE = 1;
    private static final int MULTI_PLAYER_MODE = 2;
    
    
    public GameManager(int gamemode, Team playerTeam, Team botTeam) {
        this.gamemode = gamemode;
        
        player = new Player(playerTeam);
        opponent = new Player(botTeam);
        
        board = new Board(player, opponent, this);
        cells = board.getCells();
        
        controller = new MoveController(this);
        
        activeCell = null;
        currentPlayer = (playerTeam == Team.VIKINGS) ? player : opponent;
        
        aiController = new AIController(this);
        
        gameOverTimer = -1;
        playerChangeTimer = 0;
        
    }
    
    public void update() {
        if(Game.input.isKeyDown(KeyEvent.VK_ESCAPE))
            Game.app.setScreen(new MenuScreen());
        if(playerChangeTimer != 0) {
            playerChangeTimer--;
            return;
        }

        if(gameOverTimer > 0) {
            gameOverTimer--;
            return;
        }

        if(gameOverTimer == 0) {
            if (Game.input.isMouseDown(MouseEvent.BUTTON1)) Game.app.setScreen(new MenuScreen());
            return;
        }
        
        switch(gamemode) {
            case (SINGLE_PLAYER_MODE): {
                if (currentPlayer.equals(player)) {
                    inputUpdate();
                } else {
                    Move move = aiController.getMove(currentPlayer);
                    
                    if (move != null) {
                        controller.move(move.from, move.to, currentPlayer);

                    } else winner = getOpponent(currentPlayer);
                    
                    
                    currentPlayer = getOpponent(currentPlayer);
                    playerChangeTimer = 10;
                }
                
            }
            case (MULTI_PLAYER_MODE): {
                inputUpdate();
            }
        }
        
        if (isWin(player)) winner = player;
        else if (isWin(opponent)) winner = opponent;
        if(winner != null && gameOverTimer == -1) gameOverTimer = 200;
        
        

    }
    
    public void draw(Graphics2D g) {
        
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        g.setColor(Color.BLACK);
        String s = currentPlayer.getTeam() == Team.VARANGIANS ? "ВАРЯГ" : "ВИКИНГОВ";
        if(gameOverTimer != 0) g.drawString("ХОД: " + s, 600, 50);
        else {
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            g.drawString("Нажмите для выхода", 600, 40);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        }
        
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        if(winner != null) g.drawString((winner.equals(player) ? "ВАРЯГИ" : "ВИКИНГИ") + " ПОБЕДИЛИ", 600, 200);
        
        if(getOpponent(currentPlayer).lastMoveTo != null) {
            g.setColor(new Color(0x66991702, true));
            g.fill(getOpponent(currentPlayer).lastMoveFrom.getRect());
            g.fill(getOpponent(currentPlayer).lastMoveTo.getRect());
        }
        if(activeCell == null) return;

        g.setColor(new Color(0, 0, 255, 50));
        g.fillRect(activeCell.getX()*60+30, activeCell.getY()*60+30, 60, 60);
        
        
        g.setColor(Color.GREEN);
        LList<Cell> list = activeCell.getFigure().getAvailableCells();
        for (Cell cell : list) {
            g.fillOval(cell.getX()*60+49, cell.getY()*60+49, 20, 20);
        }

    }
    
    private void inputUpdate() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(cells[i][j].getRect().contains(Game.input.getMouseX(), Game.input.getMouseY())
                        && Game.input.isMouseDown(MouseEvent.BUTTON1))
                    if(currentPlayer.getFigures().contains(
                            cells[i][j].getFigure())) activeCell = cells[i][j];
            }
        }
        
        if (activeCell != null && Game.input.isMouseDown(MouseEvent.BUTTON1)) {
           for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (cells[i][j].getRect().contains(Game.input.getMouseX(), Game.input.getMouseY())) {
                        LList<Cell> list = activeCell.getFigure().getAvailableCells();
                        if (list.contains(cells[i][j])) {
                            controller.move(activeCell, cells[i][j], currentPlayer);
                            currentPlayer = getOpponent(currentPlayer);
                            playerChangeTimer = 20;
                            activeCell = null;
                        }
                    } 
                }
           }         
        }
        
            
    }
    
    public Player getOpponent(Player player)  {
        if (player == this.player) return this.opponent;
        else return this.player;
    }
    
    public Board getBoard() {
        return this.board;
    }
    
    public MoveController getController() {
        return this.controller;
    }
    
    public boolean isWin(Player player) {
        Team team = player.getTeam();
        if (team == Team.VARANGIANS)  {

            for (int i =0; i < 9; i+=8) {
                for (int j =0; j < 9; j+=8) {
                    if (cells[i][j].getFigure() != null) {
                        if (cells[i][j].getFigure().getTeam() == Team.VARANGIANS) return true;
                    }
                }
            }
            return false;
        } else {
            return isLoss(getOpponent(player));
        }
    }
    
    public boolean isLoss(Player player) {
        Team team = player.getTeam();
        if (team == Team.VARANGIANS) {
            for (Figure figure: player.getFigures()) {
                if (figure instanceof King) return false;
            }
            return true;
        } else {
            return isWin(getOpponent(player));
        }
    }
}
