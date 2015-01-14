/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tic.tac.toe;
import tic.tac.toe.Chip;

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
       State beginning = new State(1);
       //System.out.println(beginning);
       StateSpace tree = new StateSpace(beginning, 2);
       //System.out.println(tree);
       int[][] chips = {{0,0,0},{0,-1,0},{0,0,0}};
       State newState = beginning.createChilds().get(4);
       tree.updateStateSpace(newState);
       System.out.println(tree);
       //System.out.println(tree);
    }
    
}
