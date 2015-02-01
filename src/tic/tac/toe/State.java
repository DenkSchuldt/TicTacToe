/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import java.util.ArrayList;

/**
 *
 * @author gabo
 */
public class State {

    private int[][] chips = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    final private int heuristicValue;
    private int bestValue;
    final private int player;

    private int pos_X, pos_Y;

    public State(int[][] chips, int player) {
        this.chips = chips;
        this.player = player;
        this.heuristicValue = calculateHValue();
    }

    public State(int player) {
        this.player = player;
        this.heuristicValue = calculateHValue();
    }

    public int[][] getChips() {
        return chips;
    }

    public int getPosX() {
        return this.pos_X;
    }

    public int getPosY() {
        return this.pos_Y;
    }

    public void setPosX(int x){
        this.pos_X=x;
    }
    
    public void setPosY(int y){
        this.pos_Y=y;
    }
    
    
    
    public void setChips(int[][] chips) {
        this.chips = chips;
    }

    public int getHeuristicValue() {
        return heuristicValue;
    }

    public int getBestValue() {
        return bestValue;
    }

    public void setBestValue(int value) {
        this.bestValue = value;
    }

    public int getPlayer() {
        return player;
    }

    private int getPossibleWinnings(int player) {
        int total = 0;
        if (chips[1][1] == player || chips[1][1] == 0) {
            if ((chips[0][2] == player || chips[0][2] == 0) && (chips[2][0] == player || chips[2][0] == 0)) {
                total++;
            }
            if ((chips[0][0] == player || chips[0][0] == 0) && (chips[2][2] == player || chips[2][2] == 0)) {
                total++;
            }
            if ((chips[1][0] == player || chips[1][0] == 0) && (chips[1][2] == player || chips[1][2] == 0)) {
                total++;
            }
            if ((chips[0][1] == player || chips[0][1] == 0) && (chips[2][1] == player || chips[2][1] == 0)) {
                total++;
            }

        }
        if (chips[0][0] == player || chips[0][0] == 0) {
            if ((chips[1][0] == player || chips[1][0] == 0) && (chips[2][0] == player || chips[2][0] == 0)) {
                total++;
            }
            if ((chips[0][1] == player || chips[0][1] == 0) && (chips[0][2] == player || chips[0][2] == 0)) {
                total++;
            }
        }
        if (chips[2][2] == player || chips[2][2] == 0) {
            if ((chips[2][1] == player || chips[2][1] == 0) && (chips[2][0] == player || chips[2][0] == 0)) {
                total++;
            }
            if ((chips[0][2] == player || chips[0][2] == 0) && (chips[1][2] == player || chips[1][2] == 0)) {
                total++;
            }
        }

        return total;
    }

    public boolean isWinner() {
        if (chips[1][1] == player) {
            if ((chips[0][2] == player) && (chips[2][0] == player)) {
                return true;
            }
            if ((chips[0][0] == player) && (chips[2][2] == player)) {
                return true;
            }
            if ((chips[1][0] == player) && (chips[1][2] == player)) {
                return true;
            }
            if ((chips[0][1] == player) && (chips[2][1] == player)) {
                return true;
            }

        }
        if (chips[0][0] == player) {
            if ((chips[1][0] == player) && (chips[2][0] == player)) {
                return true;
            }
            if ((chips[0][1] == player) && (chips[0][2] == player)) {
                return true;
            }
        }
        if (chips[2][2] == player) {
            if ((chips[2][1] == player) && (chips[2][0] == player)) {
                return true;
            }
            if ((chips[0][2] == player) && (chips[1][2] == player)) {
                return true;
            }
        }
        return false;
    }

    public boolean isComplete() {
        boolean flag = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.chips[i][j] == 0) {
                    flag = false;
                    return flag;
                }

            }

        }
        return flag;
    }

    private int calculateHValue() {
        if (isWinner()) {
            return 100 * this.player;
        } else {
            return getPossibleWinnings(1) - getPossibleWinnings(-1);
        }
    }

    public ArrayList<State> createChilds() {
        if (isWinner()) {
            return null;
        } else {
            ArrayList<State> children = new ArrayList<State>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (chips[i][j] == 0) {
                        int[][] new_chips = duplicateChips();
                        new_chips[i][j] = -player;
                        children.add(new State(new_chips, -player));
                    }
                }
            }
            return children;
        }
    }

    /**
     *
     * @return
     */
    //@Override

    public String toString() {
        return "" + chips[0][0] + " " + chips[0][1] + " " + chips[0][2] + "\n"
                + chips[1][0] + " " + chips[1][1] + " " + chips[1][2] + "\n"
                + chips[2][0] + " " + chips[2][1] + " " + chips[2][2] + "\n "
               // + "Heuristic value: " + this.heuristicValue + " \n"
                + "Best value: " + this.bestValue + "\n";
    }
    
 //   public String toString(){
   //     return "x";
    //}

    //public String toString() {
      //  return "Best value: " + this.bestValue + "\n";
    //}
    
    public int[][] duplicateChips() {
        int[][] duplicate = {{chips[0][0], chips[0][1], chips[0][2]},
        {chips[1][0], chips[1][1], chips[1][2]},
        {chips[2][0], chips[2][1], chips[2][2]}};
        return duplicate;

    }

    public boolean equals(State state2) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (chips[i][j] != state2.chips[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean areEqual(State state1, State state2) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state1.chips[i][j] != state2.chips[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

}
