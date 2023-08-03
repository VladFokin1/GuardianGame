/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import DataStructures.LList;
import Game.Figures.Figure;
import Game.Figures.King;
import Game.Figures.Regular;
import Game.Player.Player;

/**
 *
 * @author fokin
 */
public class MoveController {
    private GameManager manager;
    
    public MoveController(GameManager manager) {
        this.manager = manager;
    }
    public void moveBack(Cell from, Cell to, Player player) {
        if (from == null || to == null || from.getFigure() == null) return;
        
        if(from.getFigure().getTeam() == player.getTeam()) {
            
            //передвижение
            from.getFigure().setX(to.getX());
            from.getFigure().setY(to.getY());
            to.setFigure(from.getFigure());
            from.setFigure(null);
        }
    }
    
    public void move(Cell from, Cell to, Player player) {
        if (from == null || to == null || from.getFigure() == null) return;
        
        if(from.getFigure().getTeam() == player.getTeam()) {
            
            //передвижение
            from.getFigure().setX(to.getX());
            from.getFigure().setY(to.getY());
            to.setFigure(from.getFigure());
            from.setFigure(null);
            player.lastMoveFrom = from;
            player.lastMoveTo = to;
            
            //если зажали врага, съедаем
            LList<Cell> enemies = getEnemiesAround(to, player);
            for (Cell enemy : enemies) {
                int x = enemy.getX();
                int y = enemy.getY();
                Figure figure = enemy.getFigure();
                Cell[][] cells = manager.getBoard().getCells();
                try {
                    if (figure instanceof Regular) {
                        if (checkEnemiesHorizontal(x, y, figure, cells)) {
                            if (player.lastMoveTo == cells[y][x-1] || player.lastMoveTo == cells[y][x+1]) {
                                manager.getOpponent(player).getFigures().remove(enemy.getFigure());
                                enemy.setFigure(null);
                            }
                            
                        }
                        else if (checkEnemiesVertical(x, y, figure, cells)) {
                            if (player.lastMoveTo == cells[y-1][x] || player.lastMoveTo == cells[y+1][x]) {
                                manager.getOpponent(player).getFigures().remove(enemy.getFigure());
                                enemy.setFigure(null);
                            }
                        }          
                    } else {
                        if (isOnThrone(x, y)){
                            if (checkEnemyUp(x, y, figure, cells) &&
                                checkEnemyDown(x, y, figure, cells) &&
                                checkEnemyLeft(x, y, figure, cells) &&
                                checkEnemyRight(x, y, figure, cells)) 
                            {
                                manager.getOpponent(player).getFigures().remove(enemy.getFigure());
                                enemy.setFigure(null);
                            }
                        } else if (isNearThrone(x, y)){
                            if (y==3) {
                                if (checkEnemyUp(x, y, figure, cells) &&
                                    checkEnemyLeft(x, y, figure, cells) &&
                                    checkEnemyRight(x, y, figure, cells)) 
                                {
                                    manager.getOpponent(player).getFigures().remove(enemy.getFigure());
                                    enemy.setFigure(null);
                                }
                            } else if (y == 5) {
                                if (checkEnemyDown(x, y, figure, cells) &&
                                    checkEnemyLeft(x, y, figure, cells) &&
                                    checkEnemyRight(x, y, figure, cells))  
                                {
                                    manager.getOpponent(player).getFigures().remove(enemy.getFigure());
                                    enemy.setFigure(null);
                                }
                            } else if (x == 3) {
                                if (checkEnemyDown(x, y, figure, cells) &&
                                    checkEnemyUp(x, y, figure, cells) &&
                                    checkEnemyLeft(x, y, figure, cells)) 
                                {
                                    manager.getOpponent(player).getFigures().remove(enemy.getFigure());
                                    enemy.setFigure(null);
                                }
                            } else if (x == 5) {
                                if (checkEnemyDown(x, y, figure, cells) &&
                                    checkEnemyUp(x, y, figure, cells) &&
                                    checkEnemyRight(x, y, figure, cells)) 
                                {
                                    manager.getOpponent(player).getFigures().remove(enemy.getFigure());
                                    enemy.setFigure(null);
                                }
                            }
                        } else {
                            if (checkEnemiesHorizontal(x, y, figure, cells)) {
                                if (player.lastMoveTo == cells[y][x-1] || player.lastMoveTo == cells[y][x+1]) {
                                    manager.getOpponent(player).getFigures().remove(enemy.getFigure());
                                    enemy.setFigure(null);
                                }
                            }
                            else if (checkEnemiesVertical(x, y, figure, cells)) {
                                if (player.lastMoveTo == cells[y-1][x] || player.lastMoveTo == cells[y+1][x]) {
                                    manager.getOpponent(player).getFigures().remove(enemy.getFigure());
                                    enemy.setFigure(null);
                                }
                            } 
                        } 
                    }
                } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                    continue;
                }
                
            }
        }
    }
    
    private boolean checkEnemiesVertical(int x, int y, Figure figure, Cell[][] cells) {
        if ((checkEnemyUp(x, y, figure,  cells) || isExitOrThrone(x,y-1, cells)) 
            && (checkEnemyDown(x, y, figure,  cells) || isExitOrThrone(x,y+1, cells))) return true;                
        return false; 
    }
    private boolean checkEnemiesHorizontal(int x, int y, Figure figure, Cell[][] cells) {
        if ((checkEnemyLeft(x, y, figure,  cells) || isExitOrThrone(x-1,y, cells)) 
           && (checkEnemyRight(x, y, figure,  cells) || isExitOrThrone(x+1,y, cells))) return true;     
        return false; 
    }
    
    private boolean checkEnemyUp(int x, int y, Figure figure, Cell[][] cells)  {
        try {
            if (cells[y-1][x].getFigure().getTeam() != figure.getTeam()) return true;
        } catch(ArrayIndexOutOfBoundsException | NullPointerException e) {
            //System.out.println("oops Up");
        }
        return false;
    }
    
    private boolean checkEnemyDown(int x, int y, Figure figure, Cell[][] cells)  {
        try {
            if (cells[y+1][x].getFigure().getTeam() != figure.getTeam()) return true;
        } catch(ArrayIndexOutOfBoundsException | NullPointerException e) {
            //System.out.println("oops Down");
        }
        return false;
    }
    
    private boolean checkEnemyLeft(int x, int y, Figure figure, Cell[][] cells)  {
        try {
            if (cells[y][x-1].getFigure().getTeam() != figure.getTeam()) return true;
        } catch(ArrayIndexOutOfBoundsException | NullPointerException e) {
            //System.out.println("oops Left");
        }
        return false;
    }
    
    private boolean checkEnemyRight(int x, int y, Figure figure, Cell[][] cells)  {
        try {
            if (cells[y][x+1].getFigure().getTeam() != figure.getTeam()) return true;
        } catch(ArrayIndexOutOfBoundsException | NullPointerException e) {
            //System.out.println("oops Right");
        }
        return false;
    }        
    
    private boolean isExitOrThrone(int x, int  y, Cell[][] cells) {
        if (x == 4 && y == 4) { //трон
            if (cells[x][y].getFigure() == null) return true;
            return false;
        } 
        else if ((x==0 && y == 0) ||
            (x==8 && y == 8) ||
            (x==0 && y == 8) ||
            (x==8 && y == 0)) return true; //выходы
        else return false;
    }
    
    private  boolean isOnThrone(int x, int y) {
        if (x==4 && y == 4) {
            return true;
        }
        return false;
    }
    private boolean isNearThrone(int x, int y) {
        if (x==4) {
            if (y == 3 || y == 5) return true;
        } 
        if (y == 4) {
            if (x == 3 || x == 5) return true;
        }
        return false;
    }
    
    public Cell findCellByFigure(Figure figure, Cell[][] cells) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                if(cells[i][j].getFigure() != null &&
                        cells[i][j].getFigure().equals(figure)) return cells[i][j];
            }
        }
        return null;
    }
    
    public LList<Cell> getEnemiesAround(Cell cell, Player player) {
        Cell[][] cells = manager.getBoard().getCells();
        LList<Cell> list = new LList();
        int x = cell.getX();
        int y = cell.getY();
        for (int i = x - 1; i <= x+1; i++) {
            if (i==x) continue;
            try {
                Figure tempFigure = cells[y][i].getFigure();
                if (tempFigure != null) {
                    if (player.getTeam() != tempFigure.getTeam()) {
                        list.add(cells[y][i]);
                    }
                }
            } catch(ArrayIndexOutOfBoundsException e) {
                continue;
            }
            
        }
        for (int j = y - 1; j <= y+1; j++) {
                if (j == y) continue;
                try {
                Figure tempFigure = cells[j][x].getFigure();
                if (tempFigure != null) {
                    if (player.getTeam() != tempFigure.getTeam()) {
                        list.add(cells[j][x]);
                    }
                }
            } catch(ArrayIndexOutOfBoundsException e) {
                continue;
            }
        }
        return list;
    }
    
}
