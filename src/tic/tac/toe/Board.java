/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author rodrigo
 * @param <T>
 */
public class Board extends Observable {

    private JPanel board_panel;
    private GridLayout layout;
    private JPanel[][] chip_panel;
    private LinkedList<Token> tokens;
    private int dim = 3;
    private Observer Game_Observer;
    private final HandledOnClick handled;

    public Board() {
        this.board_panel = new JPanel();
        this.layout = new GridLayout(dim, dim);
        this.board_panel.setLayout(layout);
        this.tokens = new LinkedList();
        this.chip_panel = new JPanel[dim][dim];
        this.handled = new HandledOnClick();

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                this.chip_panel[i][j] = new JPanel();
                this.chip_panel[i][j].addMouseListener(this.handled);
                this.board_panel.add(chip_panel[i][j]);
            }
        }
    }

    public JPanel getBoardPanel() {
        return this.board_panel;
    }

    public void setObserver(Observer game) {
        this.Game_Observer = game;
        this.addObserver(game);
    }

    public void updateGame(Point newPosition) {
        Game game = (Game) Game_Observer;
        State pastState = (State) game.getStateSpace().getCurrentState();
        //System.out.println("estado pasado");
        //System.out.println(pastState.toString());

        //build a new state taking reference last state in the game
        int[][] chips = pastState.duplicateChips();//copy board configuration
        chips[newPosition.x][newPosition.y] = game.getTurn();//next move
        State newState = new State(chips, game.getTurn());//newState represent a new board configuration

       // System.out.println("actualizando el juego");
       // System.out.println(newState.toString());

        game.getStateSpace().updateStateSpace(newState);

//        Token newToken = new Token(newPosition, game.getTurn());
  //      tokens.add(newToken);
        this.setChanged();//set that observable has changed
        this.notifyObservers();//notify to Game for update
    }

    public void updateBoard(JPanel panelSelected) {
        this.drawMove(panelSelected);
    }

    public void updateBoard(State new_state) {
        int chip;
        //get the coordinates x,y for draw the next move in the board panel
        LinkedList<Token> tmp = new LinkedList();
        //create a temporal list with the information abour new configuration board
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                chip = new_state.getChips()[i][j];
                if (chip != 0) {
                    tmp.add(new Token(i, j, chip));
                }
            }
        }
        //tmp-tokens-> should be return a list with one element with coordinates for draw the next move 
        Token newToken = substract(tmp, tokens).getFirst();
        tokens.add(newToken);
        Point newPosition = newToken.getPoint();
        this.updateBoard(chip_panel[(int) newPosition.getX()][(int) newPosition.getY()]);
    }

    public LinkedList<Token> substract(LinkedList<Token> A, LinkedList<Token> B) {
        LinkedList<Token> subs = new LinkedList();
        boolean flag = false;
        if (B.isEmpty()) {
            return A;
        }
        for (Token it : A) {
            if (!elementExist(B, it)) {
                subs.add(it);
            }
        }
        return subs;
    }

    public boolean elementExist(LinkedList<Token> lista, Token element) {
        boolean flag = false;
        for (Token it : lista) {
            if (it.equals(element)) {
                flag = true;
                return flag;
            }
        }
        return flag;
    }

    void drawMove(JPanel panelSelected) {
        Token token = tokens.getLast();
        int jugador = token.getJugador();
        Graphics2D painter = (Graphics2D) panelSelected.getGraphics();
        Image background;
        int width=panelSelected.getSize().width;
        int height=panelSelected.getSize().height;
        String path;
        if (jugador == 1) {
            path="ex.jpg";
        } else {
            path="circle.jpg";
        }
        background=new ImageIcon(getClass().getResource(path)).getImage();
        if(background!=null){
            painter.drawImage(background, 0, 0, width, height, null);        
        }
    }

    class HandledOnClick extends MouseAdapter {

        public void mouseClicked(MouseEvent evento) {
            JPanel panelSelected = (JPanel) evento.getSource();
            Point newPosition = getCoordinates(panelSelected);
            Token newToken = new Token(newPosition, 1);
            tokens.add(newToken);
            updateBoard(panelSelected);
            updateGame(newPosition);
        }
    }
    
    public void blockHandledOnClick(){
        for(int i=0;i<dim;i++){
           for(int j=0;j<dim;j++){
               this.chip_panel[i][j].removeMouseListener(handled);
           }
        }
    }
    

    public Point getCoordinates(JPanel selected) {
        JPanel currentPanel;
        Point coordinate = null;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                currentPanel = this.chip_panel[i][j];
                if (currentPanel.equals(selected)) {
                    coordinate = new Point(i, j);
                    break;
                }
            }
        }
        return coordinate;
    }
}
