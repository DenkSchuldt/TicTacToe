/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tic.tac.toe;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Denny
 */
public class PnlToken extends JPanel{
    
    public boolean paint = false;
    public boolean nought = false;
    public boolean sound = true;
    private Image image = null;

    public PnlToken(int top, int left, int bottom, int right){ 
        setPreferredSize(new Dimension(100,100));
        setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.WHITE));
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(paint){
            if(nought){
                image = new ImageIcon(getClass().getResource("img/nought.png")).getImage();
            }else{
                image = new ImageIcon(getClass().getResource("img/cross.png")).getImage();
            }
            g.drawImage(image,0,0,image.getWidth(null),image.getHeight(null),null);                
            setPreferredSize(new Dimension(image.getWidth(null),image.getHeight(null)));
        }
    }                                        
}
