/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Game.Figures.Figure;
import Game.Player.Player;
import Game.Player.Team;
import Game.Figures.King;
import Game.Figures.Regular;
import static Game.Player.Team.VARANGIANS;
import static Game.Player.Team.VIKINGS;
import java.awt.Graphics2D;

/**
 *
 * @author fokin
 */
public class Board {
    
    private Cell[][] cells;
    
    private Player player;
    private Player opponent;
    
    private GameManager manager;
    
    public Board(Player player, Player opponent, GameManager manager) {
        this.player = player;
        this.opponent = opponent;
        this.manager = manager;
        cells = new Cell[9][9];
        init();
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cells[i][j].getFigure() !=  null) {
                    if (cells[i][j].getFigure().getTeam() == player.getTeam())
                        player.getFigures().add(cells[i][j].getFigure());
                    else opponent.getFigures().add(cells[i][j].getFigure());
                }
            }
        }
        print();
    }

    private void init(){
        for (int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                cells[i][j] = new Cell(j, i, null);
            }
        }
        //расстановка викингов
        for (int i = 0; i < 2; i++) {
            int y = i == 0 ? 0 : 8;
            cells[y][3].setFigure(new Regular(Team.VIKINGS, cells));
            cells[y][4].setFigure(new Regular(Team.VIKINGS, cells));
            cells[y][5].setFigure(new Regular(Team.VIKINGS, cells));
            if(y == 0) cells[y+1][4].setFigure(new Regular(Team.VIKINGS, cells));
            else cells[y-1][4].setFigure(new Regular(Team.VIKINGS, cells));
        }
        for (int i = 0; i < 2; i++) {
            int x = i == 0 ? 0 : 8;
            cells[3][x].setFigure(new Regular(Team.VIKINGS, cells));
            cells[4][x].setFigure(new Regular(Team.VIKINGS, cells));
            cells[5][x].setFigure(new Regular(Team.VIKINGS, cells));
            if(x == 0) cells[4][x+1].setFigure(new Regular(Team.VIKINGS, cells));
            else cells[4][x-1].setFigure(new Regular(Team.VIKINGS, cells));
        }
        
        //расстановка варяг
        for(int i = 2; i <= 6; i++) {
            if (i == 4) continue;
            cells[i][4].setFigure(new Regular(Team.VARANGIANS, cells));
        }
        for(int i = 2; i <= 6; i++) {
            if (i == 4) continue;
            cells[4][i].setFigure(new Regular(Team.VARANGIANS, cells));
        }
        cells[4][4].setFigure(new King(cells));
    }
    
    //debug func
    public void print() {
        for (int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                
                try {
                    switch (cells[i][j].getFigure().getTeam()) {
                    case VARANGIANS -> System.out.print("1 ");
                    case VIKINGS -> System.out.print("2 ");
                }
                }  catch (NullPointerException e) {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }
    
    public Cell[][] getCells() {
        return this.cells;
    }
    
    public void draw(Graphics2D g){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cells[i][j].getFigure() != null) {
                    Figure figure = cells[i][j].getFigure();
                    g.drawImage(figure.getImage(), 
                            figure.getX()*60+30, figure.getY()*60+30, 60,60,null);
                }
            }
        }
    }
    
}
