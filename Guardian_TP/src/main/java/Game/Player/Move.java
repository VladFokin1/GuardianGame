/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game.Player;

import Game.Cell;

/**
 *
 * @author fokin
 */
public class Move {
    public Cell from, to;
    public Player player;

    public Move(Cell from, Cell to, Player player) {
        this.from = from;
        this.to = to;
        this.player = player;
    }
}
