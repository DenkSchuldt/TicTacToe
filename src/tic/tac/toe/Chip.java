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
public class Chip {
    
    public static enum Type {CIRCLE, CROSS};
    private int x;
    private int y;
    private Type markType;
    
    public Chip(int x, int y, Type markType){
        this.x = x;
        this.y = y;
        this.markType = markType;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Type getMarkType() {
        return markType;
    }

    public void setMarkType(Type markType) {
        this.markType = markType;
    }
    
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
}
