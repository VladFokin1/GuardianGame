/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Figures;

import DataStructures.LList;
import Game.Cell;
import Game.Player.Team;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author fokin
 */
public class King extends Figure{

    public King(Cell[][] cells) {
        super(Team.VARANGIANS, cells);
        this.image = tileset.getSubimage(0, 0, 60, 60);
    }

    @Override
    public LList<Cell> getAvailableCells() {
        LList<Cell> list = new LList();
        for (int i = x-1; i >= 0; i--) {
            Cell cell = cells[y][i];
            if (addAvailableCell(list, cell)) break;
            if (x-i == 3) break;
        }
        for (int i = x+1; i < 9; i++) {
            Cell cell = cells[y][i];
            if (addAvailableCell(list, cell)) break;
            if (i-x == 3) break;
        }
        for (int i = y-1; i >= 0; i--) {
            Cell cell = cells[i][x];
            if (addAvailableCell(list, cell)) break;
            if (y-i == 3) break;
        }
        for (int i = y+1; i < 9; i++) {
            Cell cell = cells[i][x];
            if (addAvailableCell(list, cell)) break;
            if (i-y == 3) break;
        }
        return list;
    }
    
    public boolean isOnExit() {
        if ((x == 0 && y==0) ||
            (x == 8 && y==8) ||
            (x == 0 && y==8) ||
            (x == 8 && y==0))
            return true;
        return false;
    }
    
    public boolean isOnThrone() {
        if (x==4 && y == 4) {
            return true;
        }
        return false;
    }
    public boolean isNearThrone() {
        if (x==4) {
            if (y == 3 || y == 5) return true;
        } 
        if (y == 4) {
            if (x == 3 || x == 5) return true;
        }
        return false;
    }
    
}
