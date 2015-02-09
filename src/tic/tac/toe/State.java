/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

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

    public ArrayList<Token> tokens;
    private ArrayList<Token> tokensDrawer;

    public State(int[][] chips, int player) {
        this.chips = chips;
        this.player = player;
        this.heuristicValue = calculateHValue();
        tokens = new ArrayList();
        tokensDrawer = new ArrayList();
    }
    
    
    public ArrayList<Token> getTokensDrawer(){
        return this.tokensDrawer;
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

    public void setPosX(int x) {
        this.pos_X = x;
    }

    public void setPosY(int y) {
        this.pos_Y = y;
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

    private boolean rowEmpty(int row) {
        boolean flag = false;
        for (int i = 0; i < 3; i++) {
            if (chips[row][i] == 0) {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private boolean columnEmpty(int col) {
        boolean flag = false;
        for (int i = 0; i < 3; i++) {
            if (chips[i][col] == 0) {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private boolean diagonalEmpty(int diag) {
        boolean flag = false;
        for (int i = 0; i < 3; i++) {
            if (chips[i][diag] == 0) {
                flag = true;
            } else {
                flag = false;
                break;
            }
            if (diag == 0) {
                diag++;
            } else {
                diag--;
            }
        }
        return flag;
    }

    private int getPossibleWinnings(int player) {
        int total = 0;
        if(chips[1][1] == player || chips[1][1] == 0){
            if((chips[0][2] == player || chips[0][2] == 0) && (chips[2][0] == player || chips[2][0] == 0))
                total++;
            if((chips[0][0] == player || chips[0][0] == 0) && (chips[2][2] == player || chips[2][2] == 0))
                total++;
            if((chips[1][0] == player || chips[1][0] == 0) && (chips[1][2] == player || chips[1][2] == 0))
                total++;
            if((chips[0][1] == player || chips[0][1] == 0) && (chips[2][1] == player || chips[2][1] == 0))
                total++;
        }
        if(chips[0][0] == player || chips[0][0] == 0){
            if((chips[1][0] == player || chips[1][0] == 0) && (chips[2][0] == player || chips[2][0] == 0))
                total++;
            if((chips[0][1] == player || chips[0][1] == 0) && (chips[0][2] == player || chips[0][2] == 0))
                total++;
        }
        if(chips[2][2] == player || chips[2][2] == 0){
            if((chips[2][1] == player || chips[2][1] == 0) && (chips[2][0] == player || chips[2][0] == 0))
                total++;
            if((chips[0][2] == player || chips[0][2] == 0) && (chips[1][2] == player || chips[1][2] == 0))
                total++;
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
            int winnings=getPossibleWinnings(1);
            int lost=getPossibleWinnings(-1);
            return winnings-lost;
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

    //@Override
    public String toString() {
        return /*"" + chips[0][0] + " " + chips[0][1] + " " + chips[0][2] + "\n"
                 + chips[1][0] + " " + chips[1][1] + " " + chips[1][2] + "\n"
                 + chips[2][0] + " " + chips[2][1] + " " + chips[2][2] + "\n "*/
                "\n\n\n\n\n"+"Heuristic value: " + this.heuristicValue + " \n";
               //  "Best value: " + this.bestValue + "\n";*/"";
    }

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

    public void generateTokens() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (chips[i][j] != 0) {
                    Token t = new Token(i, j,chips[i][j]);
                    this.tokens.add(t);
                }
            }
        }
    }

    public void setPositionForDrawing() {       
        
        for(Token t: tokens){
            Token newToken=null;
            if(t.getPoint().x==0&&t.getPoint().y==0){
                if(t.getJugador()==1){
                    newToken=new Token(13,9,t.getJugador());
                }else{
                    newToken=new Token(18,15,t.getJugador());
                }
            }
            if(t.getPoint().x==0&&t.getPoint().y==1){
                if(t.getJugador()==1){
                    newToken=new Token(37,9,t.getJugador());
                }else{
                    newToken=new Token(43,15,t.getJugador());    
                }
            }
            if(t.getPoint().x==0&&t.getPoint().y==2){
                if(t.getJugador()==1){
                    newToken=new Token(60,9,t.getJugador());
                }else{
                    newToken=new Token(67,15,t.getJugador());    
                }  
            }
            
            if(t.getPoint().x==1&&t.getPoint().y==0){
                if(t.getJugador()==1){
                    newToken=new Token(10,35,t.getJugador());
                }else{
                    newToken=new Token(15,40,t.getJugador());    
                } 
            }
            if(t.getPoint().x==1&&t.getPoint().y==1){
                if(t.getJugador()==1){
                     newToken=new Token(35,35,t.getJugador());
                }else{
                    newToken=new Token(42,40,t.getJugador());    
                } 
            }
            if(t.getPoint().x==1&&t.getPoint().y==2){
                if(t.getJugador()==1){
                    newToken=new Token(63,35,t.getJugador());
                }else{
                    newToken=new Token(68,40,t.getJugador());  
                }                 
            }
            
            if(t.getPoint().x==2&&t.getPoint().y==0){
                
                if(t.getJugador()==1){
                    newToken=new Token(13,60,t.getJugador());
                }else{
                    newToken=new Token(20,67,t.getJugador()); 
                }
                
            }
            if(t.getPoint().x==2&&t.getPoint().y==1){
                
                if(t.getJugador()==1){
                    newToken=new Token(35,60,t.getJugador());
                }else{
                    newToken=new Token(42,67,t.getJugador());
                }
                
                
            }
            if(t.getPoint().x==2&&t.getPoint().y==2){
                if(t.getJugador()==1){
                    newToken=new Token(63,60,t.getJugador());
                }else{
                    newToken=new Token(65,67,t.getJugador());  
                }
                
            }
            this.tokensDrawer.add(newToken);
        }
        
        
    }

}
