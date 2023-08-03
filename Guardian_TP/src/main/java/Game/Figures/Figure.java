/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Figures;

import DataStructures.LList;
import Game.Cell;
import Game.Player.Team;
import java.awt.image.BufferedImage;
import framework.Game;


/**
 *
 * @author fokin
 */
public abstract class Figure {
    protected int x, y;       //позиция на поле
    
    protected Team team;      //команда, к которой принадлежит фигура
    protected Cell[][] cells; //клетки игрового поля
    
    protected BufferedImage image;
    
    protected BufferedImage tileset = Game.files.loadImage("res/tileset1.png");

    
    public Figure(Team team, Cell[][] cells){
        this.team = team;
        this.cells = cells;
    }
    
    /**
     * Возвращает список клеток, на которые может сходить фигура
     * @return список доступных для хода клеток
     */
    public abstract LList<Cell> getAvailableCells();
    
    /**
     * Проверяет, может ли фигура сходить на указанную параметром клетку, 
     * и если может, то добавляет её в список
     * @param list список доступных клеток для хода
     * @param cell клетка, которую нужно проверить
     * @return true если 
     */
    protected boolean addAvailableCell(LList<Cell> list, Cell cell) {
        if(cell.getFigure() == null){
            list.add(cell);
            return false;
        }
        else {
            return true;
        }

    }
    
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public Team getTeam(){
        return this.team;
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
}
