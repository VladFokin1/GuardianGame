/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Figures;

import DataStructures.LList;
import Game.Cell;
import Game.Player.Team;



/**
 *
 * @author fokin
 */
public class Regular extends Figure{

    public Regular(Team team, Cell[][] cells) {
        super(team, cells);
        
        if (team == Team.VARANGIANS) image = tileset.getSubimage(120, 0, 60, 60);
        else image = tileset.getSubimage(60, 0, 60, 60);
       
    }

    @Override
    public LList<Cell> getAvailableCells() {
        LList<Cell> list = new LList();
        for (int i = x-1; i >= 0; i--) {
            Cell cell = cells[y][i];
            if (checkExitOrThrone(cell)){
                if (cell.getFigure() != null) break;
                continue;
            }
            if (addAvailableCell(list, cell)) break;
        }
        for (int i = x+1; i < 9; i++) {
            Cell cell = cells[y][i];
            if (checkExitOrThrone(cell)){
                if (cell.getFigure() != null) break;
                continue;
            }
            if (addAvailableCell(list, cell)) break;
        }
        for (int i = y-1; i >= 0; i--) {
            Cell cell = cells[i][x];
            if (checkExitOrThrone(cell)){
                if (cell.getFigure() != null) break;
                continue;
            }
            if (addAvailableCell(list, cell)) break;
        }
        for (int i = y+1; i < 9; i++) {
            Cell cell = cells[i][x];
            if (checkExitOrThrone(cell)){
                if (cell.getFigure() != null) break;
                continue;
            }
            if (addAvailableCell(list, cell)) break;
        }
        return list;
    }
    
    private boolean checkExitOrThrone(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        if (x == 4 && y == 4) return true; //трон
        else if ((x==0 && y == 0) ||
            (x==8 && y == 8) ||
            (x==0 && y == 8) ||
            (x==8 && y == 0)) return true; //выходы
        else return false;
    }
    
}
