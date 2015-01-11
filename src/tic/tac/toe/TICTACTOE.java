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
       System.out.println(beginning);
       java.util.ArrayList<State> childs = beginning.createChilds();
       for(State child: childs)
           System.out.println(child);
       System.out.println(beginning);
    }
    
}
