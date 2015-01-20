/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import java.awt.Point;

/**
 *
 * @author rodrigo
 */
public class Token {
    private Point positions;
    private int jugador;
    
    public Token(int i,int j,int jugador){
        this.positions=new Point(i,j);
        this.jugador=jugador;
    }
    
    public Token(Point positions,int jugador){
        this.positions=positions;
        this.jugador=jugador;
    }
    
    public Point getPoint(){
        return this.positions;
    }
    
    @Override
    public boolean equals(Object o){
        boolean flag=false;
        Token token=(Token)o;
        if(this.positions.x==token.positions.x&& this.positions.y==token.positions.y
           && this.jugador==token.jugador){
            flag=true;
        }
        return flag;    
    }
    
    public int getJugador(){
        return this.jugador;
    }
    
    
}
