/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Game.Figures.Figure;
import java.awt.Rectangle;

/**
 *
 * @author fokin
 */
public class Cell {
    private int x, y;
    private Figure figure;
    private Rectangle rect;
    
    public Cell(int x, int y, Figure figure) {
        this.x = x;
        this.y = y;
        if (figure != null) {
            figure.setX(x);
            figure.setY(y);
        }
        rect = new Rectangle(x*60+30, y*60+30, 60, 60);
    }
    
    public Figure getFigure() {
        return this.figure;
    }
    
    public void setFigure(Figure figure) {
        this.figure = figure;
        if (figure != null) {
            figure.setX(x);
            figure.setY(y);
        }
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public Rectangle getRect() {
        return this.rect;
    }
}
