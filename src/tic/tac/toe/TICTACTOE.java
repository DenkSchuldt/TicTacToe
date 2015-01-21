/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tic.tac.toe;

import javax.swing.JFrame;

/**
 *
 * @author gabo
 */
public class TICTACTOE{

    /**
     * @param args the command line arguments
     */
/*
    public static void main(String[] args) {
        // TODO code application logic here
       State beginning = new State(-1);
       //int chips[][] = {{1,0,0},{0,0,0},{-1,0,1}};
       //State beginning = new State(chips,1);
       System.out.println("Start\n"+beginning);
       StateSpace tree = new StateSpace(beginning,6);       
       //System.out.print(tree);
       State nextMove = tree.selectNextMove(Algorithm.ALPHA_BETA);
       System.out.println("next Move\n"+nextMove);
       //State newState = beginning.createChilds().get(4);
       
       tree.updateStateSpace(nextMove);
       
       State nextMove2 = tree.selectNextMove(Algorithm.MINIMAX);
       System.out.println("next Move\n"+nextMove2);
       tree.updateStateSpace(nextMove2);
       State nextMove3 = tree.selectNextMove(Algorithm.ALPHA_BETA);
       System.out.println("next Move\n"+nextMove3);
       tree.updateStateSpace(nextMove3);
       State nextMove4 = tree.selectNextMove(Algorithm.MINIMAX);
       System.out.println("next Move\n"+nextMove4);
       tree.updateStateSpace(nextMove4);
       State nextMove5 = tree.selectNextMove(Algorithm.ALPHA_BETA);
       System.out.println("next Move\n"+nextMove5);
       tree.updateStateSpace(nextMove5);
       State nextMove6 = tree.selectNextMove(Algorithm.MINIMAX);
       System.out.println("next Move\n"+nextMove6);
       tree.updateStateSpace(nextMove6);
       //System.out.println(tree);
    }
    */
    
    public static void main(String[] args) {
        new VtnMain();
        WindowGame w=new WindowGame();
        w.setSize(750,650);
	w.setVisible(true);
	w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
   
}
