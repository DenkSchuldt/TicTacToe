/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.border.Border;

/*
 * 
 * Autor: DSchuldt
 */
public class BorderBg implements Border{
    private BufferedImage mImagen = null;
    public BorderBg(BufferedImage pImagen) {
        mImagen = pImagen;       
    }

    public void paintBorder(Component c,Graphics g,int x,int y,int width,int height){
        if (mImagen != null)
            g.drawImage(mImagen, 0, 0, width, height, null);
    }

    public Insets getBorderInsets(Component c){
        return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque(){
        return true;
    }
}