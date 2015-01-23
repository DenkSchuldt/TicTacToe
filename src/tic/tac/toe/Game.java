/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

/**
 *
 * @author rodrigo
 */
public class Game implements Observer {

    private enum GameState {
        WINNER,DRAW,PLAYING
    }
    private int turn;
    private static final int playerOne = 1;
    private static final int playerTwo = -1;//can be computer
    private GameState flagGameState;
    private StateSpace tree;
    private int depth;
    private Observable Board_Observable;

    public Game(int initial_player, int depth) {
        State beginning = new State(-initial_player);
        this.depth = depth;
        this.tree = new StateSpace(beginning, depth);
        this.turn = initial_player;
        this.flagGameState = GameState.PLAYING;
    }

    public int getTurn() {
        return this.turn;
    }

    @Override
    public void update(Observable board, Object arg1) {
        if (this.flagGameState == GameState.PLAYING) {
            turn = -this.turn;
            State currentState = tree.getCurrentState();
            if (!currentState.isComplete()) {
                if (!currentState.isWinner()) {
                    if (this.turn == Game.playerTwo) {
                        playComputer(board);
                        turn = -this.turn;
                    }
                } else {
                    this.flagGameState = GameState.WINNER;
                    ((Board) board).blockHandledOnClick();
                    JOptionPane.showMessageDialog(null,null,"We have a winner! "+this.turn,JOptionPane.PLAIN_MESSAGE);
                }
            }else{
               JOptionPane.showMessageDialog(null,null,"There's no winner.",JOptionPane.PLAIN_MESSAGE);
               this.flagGameState=GameState.DRAW;
               ((Board) board).blockHandledOnClick();
            }
        }
    }

    public void playComputer(Observable board) {
        State nextMove = tree.selectNextMove(Algorithm.MINIMAX);
        tree.updateStateSpace(nextMove);
        ((Board) board).updateBoard(nextMove);
        if (nextMove.isWinner()) {
            this.flagGameState = GameState.WINNER;
            ((Board) board).blockHandledOnClick();
            JOptionPane.showMessageDialog(null,null,"We have a winner! Computer.",JOptionPane.PLAIN_MESSAGE);
        }
    }

    public StateSpace getStateSpace() {
        return this.tree;
    }

}
