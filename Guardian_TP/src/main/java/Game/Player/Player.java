/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game.Player;

import DataStructures.LList;
import Game.Cell;
import Game.Figures.Figure;
import Game.Figures.King;

/**
 *
 * @author fokin
 */
public class Player {
    private Team team;
    private LList<Figure> figures;
    
    public Cell lastMoveFrom;
    public Cell lastMoveTo;
    
    public Player(Team team)  {
        this.team = team;
        figures = new LList();
    }
    
    public Team getTeam(){
        return this.team;
    }
    
    public LList<Figure> getFigures(){
        return this.figures;
    }
    
}
