/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
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
    private PnlToken[][] chip_panel;
    private LinkedList<Token> tokens;
    private Observer Game_Observer;
    private final HandledOnClick handled;
    private final int DIM = 3;

    public Board() {
        this.board_panel = new JPanel(new GridLayout(DIM,DIM));
            this.board_panel.setPreferredSize(new Dimension(300,300));
            this.board_panel.setOpaque(false);
        this.tokens = new LinkedList();
        this.chip_panel = new PnlToken[DIM][DIM];
        this.handled = new HandledOnClick();
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                this.chip_panel[i][j] = new PnlToken();
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
        //build a new state taking reference last state in the game
        int[][] chips = pastState.duplicateChips();//copy board configuration
        chips[newPosition.x][newPosition.y] = game.getTurn();//next move
        State newState = new State(chips, game.getTurn());//newState represent a new board configuration
        game.getStateSpace().updateStateSpace(newState);
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
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
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
        int width = panelSelected.getSize().width;
        int height = panelSelected.getSize().height;
        Graphics2D painter = (Graphics2D) panelSelected.getGraphics();
        Image background;
        if (jugador == 1) {
            background = new ImageIcon(getClass().getResource("img/cross.png")).getImage();
        } else {
            background = new ImageIcon(getClass().getResource("img/nought.png")).getImage();
        }
        painter.drawImage(background, 0, 0, width, height, null);
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
        for(int i=0;i<DIM;i++){
           for(int j=0;j<DIM;j++){
               this.chip_panel[i][j].removeMouseListener(handled);
           }
        }
    }
    
    public Point getCoordinates(JPanel selected) {
        JPanel currentPanel;
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                currentPanel = this.chip_panel[i][j];
                if (currentPanel.equals(selected)) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }
    
    public PnlToken[][] getChipsPanel(){
        return this.chip_panel;
    }
}
