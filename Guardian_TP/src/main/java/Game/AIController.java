/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import DataStructures.LList;
import Game.Figures.Figure;
import Game.Figures.Regular;
import Game.Player.Move;
import Game.Player.Player;
import Game.Player.Team;

/**
 *
 * @author fokin
 */
public class AIController {
    
    private GameManager manager;
    private Cell[][] cells;
    private MoveController moveController;
    
    
    public AIController(GameManager manager) {
        this.manager  = manager;
        this.cells = manager.getBoard().getCells();
        this.moveController = manager.getController();
    }
    
    /*факторы: количество ходов для победы + колчество фишек для захвата
      чем больше фишек нужно - тем лучше
      чем меньше ходов - тем лучше
      
      коэф. на клетке = 

      количество фишек, необходимое для захвата
    /
      минимальное количество ходов для дохождения до ближайшего выхода
     
    */
    private final float[][] KING_MULTIPLIER = {
            {20000f, 1f,   2f,  2f,  1f,   2f,  2f,   1f, 20000f},
            {1f,     1f,   1f,  1f, 2f/3f, 1f,  1f,   1f,     1f},
            {2f,     1f,   1f,  1f, 2f/3f, 1f,  1f,   1f,     2f},
            {2f,     1f,   1f,  1f,  1f,   1f,  1f,   1f,     2f},
            {1f,    2/3f, 2/3f, 1f,  1f,   1f, 2/3f, 2/3f,    1f},
            {2f,     1f,   1f,  1f,  1f,   1f,  1f,   1f,     2f},
            {2f,     1f,   1f,  1f, 2/3f,  1f,  1f,   1f,     2f},
            {1f,     1f,   1f,  1f, 2/3f,  1f,  1f,   1f,     1f},
            {20000f, 1f,   2f,  2f,  1f,   2f,  2f,   1f, 20000f}
    };
    
    private final int REGULAR_BONUS = 60;
    
    private int eval(Player player)  {
        int sum = 0;
        
        for (int i = 0; i < 9; i ++) {
            for (int j = 0; j < 9; j ++)  {
                Cell cell = cells[i][j];
                if (cell.getFigure() != null) {
                    Figure figure = cell.getFigure();
                    if (figure instanceof Regular) { //подсчет обычных фигур
                        sum += (player.getTeam() == figure.getTeam() ? REGULAR_BONUS : -REGULAR_BONUS);
                    } else {   // учет позиции князя
                        sum += (player.getTeam() == Team.VARANGIANS ? 
                                KING_MULTIPLIER[i][j] : -KING_MULTIPLIER[i][j])
                                *REGULAR_BONUS*8;
                    }
                }
            }
        }
        return sum;
    }
    
    //update: alfa-beta added
    public int minimax(int depth, int alpha, int beta, Player player, boolean isMax) {
        if (depth == 0 || manager.isWin(player) || manager.isLoss(player)) 
            return (isMax) ? eval(player) : eval(manager.getOpponent(player));
        int res = 0;
        Player opponent = manager.getOpponent(player);
        
        boolean alphaBetaCut = false;
        for (Figure figure : player.getFigures()) {
            
            if (alphaBetaCut) break;
            
            for(Cell cell : figure.getAvailableCells()) {
                Move move = new Move(moveController.findCellByFigure(figure, cells), cell, player);
                
                //Запоминаем фигуры, если они будут съедены
                //получение клеток с врагами
                LList<Cell> tempFigures_Cells = moveController.getEnemiesAround(move.to, player);
                int indexes[] = new int[tempFigures_Cells.getSize()];
                
                //получение самих фигур
                LList<Figure> tempFigures = new LList<Figure>();
                for (int i = 0; i < indexes.length; i++)  {
                    tempFigures.add(tempFigures_Cells.get(i).getFigure());
                }
                
                //ход
                moveController.move(move.from, move.to, move.player);
                
                //минимакс
                if (isMax) {
                    res = Integer.MIN_VALUE;
                    res = Math.max(res, minimax(depth-1, alpha, beta, opponent, false));
                    alpha = Math.max(alpha, res);
                } else {
                    res = Integer.MAX_VALUE;
                    res = Math.min(res, minimax(depth-1,alpha, beta, opponent, true));
                    beta = Math.min(beta, res);
                }
                
                if (beta <= alpha) alphaBetaCut = true;
                
                //возврат поля в изначальное состояние (до хода) 
                moveController.moveBack(move.to, move.from, move.player);

                for (int i = 0; i < indexes.length; i++)  {
                    if (opponent.getFigures().indexOf(tempFigures.get(i)) == -1) {
                        opponent.getFigures().add(indexes[i], tempFigures.get(i)); //возврат оппоненту
                        tempFigures_Cells.get(i).setFigure(tempFigures.get(i)); //возврат на клетки
                    } 
                }
            }
        }
        return res;
    }
    
    private Move rootMinimax(int level,Player player) {
        Move bestMove = null;
        int bestValue = Integer.MIN_VALUE;
        
        Player opponent = manager.getOpponent(player);
        for (Figure figure : player.getFigures()) {
            System.out.println("first level figure");
            for(Cell cell : figure.getAvailableCells()) {
                Move move = new Move(moveController.findCellByFigure(figure, cells), cell, player);
                
                //Запоминаем фигуры, если они будут съедены
                //получение клеток с врагами
                LList<Cell> tempFigures_Cells = moveController.getEnemiesAround(move.to, player);
                int indexes[] = new int[tempFigures_Cells.getSize()];
                
                //получение самих фигур
                LList<Figure> tempFigures = new LList<Figure>();
                for (int i = 0; i < indexes.length; i++)  {
                    tempFigures.add(tempFigures_Cells.get(i).getFigure());
                }
                //запись индексов
                for (int i = 0; i < indexes.length; i++)  {
                    indexes[i] = opponent.getFigures().indexOf(tempFigures.get(i));
                }
                
                //ход
                moveController.move(move.from, move.to, move.player);
                
                int value  = minimax(level-1, Integer.MIN_VALUE, Integer.MAX_VALUE, opponent, false );
                if(value > bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
                
                //возврат поля в изначальное состояние (до хода) 
                moveController.moveBack(move.to, move.from, move.player);
                for (int i = 0; i < indexes.length; i++)  {
                    if (opponent.getFigures().indexOf(tempFigures.get(i)) == -1) {
                        opponent.getFigures().add(indexes[i], tempFigures.get(i)); //возврат оппоненту
                        tempFigures_Cells.get(i).setFigure(tempFigures.get(i)); //возврат на клетки
                    } 
                }
//                for (int i = 0; i < tempFigures_Cells.getSize(); i++)  {
//                    opponent.getFigures().add(tempFigures.get(i)); //возврат оппоненту
//                    tempFigures_Cells.get(i).setFigure(tempFigures.get(i)); //возврат на клетки
//                }
            }
        }
        System.out.println("---");
        return bestMove;
        
    }
    
    
    public Move getMove(Player player) {
        return rootMinimax(4, player);
    }
    
}
