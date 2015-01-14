/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tic.tac.toe;

/**
 *
 * @author gabo
 */
public class TICTACTOE {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       State beginning = new State(-1);
       StateSpace tree = new StateSpace(beginning, 2);       
       System.out.print(tree);
       //System.out.println("Minimax value: "+tree.minimax(tree.getTreeSpace()));
       State nextMove = tree.selectNextMove();
       System.out.println("next Move\n"+nextMove);
       //State newState = beginning.createChilds().get(4);
       tree.updateStateSpace(nextMove);
       State nextMove2 = tree.selectNextMove();
       System.out.println("next Move\n"+nextMove2);
       tree.updateStateSpace(nextMove2);
       State nextMove3 = tree.selectNextMove();
       System.out.println("next Move\n"+nextMove3);
       
       //System.out.println(tree);

    }
    
}
