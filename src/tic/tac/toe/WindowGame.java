/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author rodrigo
 */
public class WindowGame extends JFrame {
    private Board board;
    private Game gameTicTacToe;
    
    public WindowGame() {
        int player=1;
        this.gameTicTacToe = new Game(player,3);
        this.board = new Board();
        this.board.setObserver(this.gameTicTacToe);
        this.add(board.getBoardPanel());
    }

}
