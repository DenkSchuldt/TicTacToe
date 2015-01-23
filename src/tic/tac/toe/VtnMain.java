/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tic.tac.toe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Denny
 */
public class VtnMain extends JFrame{
    
    private JPanel wrapper = null;
    private JPanel section = null;
    private JLabel turn = null;
    private Board board = null;
    private Game gameTicTacToe = null;
    public boolean nought = true;
    public final int DIM = 3;
    
    
    public VtnMain(){
        setTitle("TicTacToe");
        setLayout(new BorderLayout());
        setSize(440,594);
        wrapper = new JPanel();
        init();
        setContentPane(wrapper);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void init(){
        wrapper.removeAll();
        setBackground(wrapper);
        
        JPanel header = new JPanel();
            header.setPreferredSize(new Dimension(400,175));
            header.setOpaque(false);
        String titleText = String.format("<html><div WIDTH=%d style=\"text-align:center;margin-top:35;\">%s</div><html>",320,"Tic-Tac-Toe");
        JLabel title = customJLabel(titleText,Font.BOLD,60);
            header.add(title);
        String descriptionText = String.format("<html><div WIDTH=%d style=\"text-align:center;\">%s</div><html>",320,"A Game Implementation based on Artificial Intelligence");
        JLabel description = customJLabel(descriptionText,Font.CENTER_BASELINE,22);
            header.add(description);
        wrapper.add(header);
        
        section = new JPanel(new FlowLayout(FlowLayout.CENTER));
            section.setPreferredSize(new Dimension(300,305));
            section.setOpaque(false);
            JLabel space = new JLabel("");
            space.setPreferredSize(new Dimension(300,100));
            section.add(space);
        JButton game = customJButton("img/newGame.png");
            game.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    newGame(section);
                }
            });
            section.add(game);
        JButton about = customJButton("img/about.png");
            about.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    about();
                    System.out.println("About");
                }
            });
            section.add(about);
        wrapper.add(section);
        
        JPanel footer = new JPanel();
            footer.setOpaque(false);
        String courseText = String.format("<html><div WIDTH=%d style=\"text-align:center;\">%s</div><html>",320,"Artificial Intelligence<br>ESPOL 2014-II");
        JLabel course = new JLabel(courseText);
            course.setForeground(Color.white);
            course.setFont(new Font("Calibri Light",Font.BOLD,18));
            footer.add(course);
        wrapper.add(footer);
        wrapper.updateUI();
    }
    
    public void newGame(JPanel panel){
        panel.removeAll();
        JLabel space = new JLabel("");
            space.setPreferredSize(new Dimension(300,80));
            panel.add(space);
        JButton user = customJButton("img/user.png");
            user.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    instructions(false);
                }
            });
            panel.add(user);
        JButton pc = customJButton("img/pc.png");
            pc.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    instructions(true);
                }
            });
            panel.add(pc);
        JButton settings = customJButton("img/settings.png");
            settings.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    settings();
                }
            });
            panel.add(settings);
        panel.updateUI();
    }
    
    public void about(){
        wrapper.removeAll();
        setBackground(wrapper);
        JButton back = customJButton("img/back.png");
            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    init();
                }
            });
        JLabel spaceOne = new JLabel();
            spaceOne.setOpaque(false);
            spaceOne.setPreferredSize(new Dimension(440,20));
        wrapper.add(spaceOne);
        wrapper.add(back);
        
        String courseTitle = String.format("<html><div WIDTH=%d style=\"margin-left:10px\">%s</div><html>",320,"About | Tic-Tac-Toe");
        JLabel title = customJLabel(courseTitle,Font.BOLD,24);
        wrapper.add(title);

        JLabel spaceTwo = new JLabel();
            spaceTwo.setOpaque(false);
            spaceTwo.setPreferredSize(new Dimension(440,10));
        wrapper.add(spaceTwo);
        
        String contentTextOne = String.format("<html><div WIDTH=%d style=\"margin-left:10px;text-align:justify;line-height:2px;\">%s</div><html>",300,"Tic-Tac-Toe is a game in which the first player to complete a pattern, wins.");
        JLabel content = customJLabel(contentTextOne,Font.PLAIN,20);
            wrapper.add(content);      
            
        String contentTextTwo = String.format("<html><div WIDTH=%d style=\"margin: 5px 0 0 10px;text-align:justify;line-height:10px;\">%s</div><html>",300,"This game let the user to select one of these two Search Algorithms based on Artificial Intelligence: MIN-MAX and ALPHA- BETA Pruning.");
        JLabel contentTwo = customJLabel(contentTextTwo,Font.PLAIN,20);
            wrapper.add(contentTwo);
            
        String contentTextThree = String.format("<html><div WIDTH=%d style=\"margin: 5px 0 0 10px;text-align:justify;line-height:10px;\">%s</div><html>",300,"Group Members:<br> * Rodrigo Castro<br> * Gabriel Falcones<br> * Denny K. Schuldt");
        JLabel contentThree = customJLabel(contentTextThree,Font.PLAIN,20);
            wrapper.add(contentThree);

        String contentTextFour = String.format("<html><div WIDTH=%d style=\"margin: 5px 0 0 10px;text-align:justify;line-height:10px;\">%s</div><html>",300,"{rodfcast|gafalcon|dschuldt}<br>@espol.edu.ec"); 
        JLabel contentFour = customJLabel(contentTextFour,Font.PLAIN,20);
            wrapper.add(contentFour);
       
        JPanel footer = new JPanel();
            footer.setOpaque(false);
        String courseText = String.format("<html><div WIDTH=%d style=\"text-align:center;margin-top:10px;\">%s</div><html>",320,"Artificial Intelligence<br>ESPOL 2014-II");
        JLabel course = customJLabel(courseText,Font.BOLD,18);
            footer.add(course);
        wrapper.add(footer);     
            
        wrapper.updateUI();
    }
    
    public void instructions(boolean againtsPc){
        wrapper.removeAll();
        setBackground(wrapper);
        JButton back = customJButton("img/back.png");
            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    init();
                    newGame(section);
                }
            });
        JLabel spaceOne = new JLabel();
            spaceOne.setOpaque(false);
            spaceOne.setPreferredSize(new Dimension(440,20));
        wrapper.add(spaceOne);
        wrapper.add(back);
        
        String courseTitle = String.format("<html><div WIDTH=%d style=\"margin-left:10px\">%s</div><html>",320,"Instructions | Tic-Tac-Toe");
        JLabel title = customJLabel(courseTitle,Font.BOLD,24);
        wrapper.add(title);

        JLabel spaceTwo = new JLabel();
            spaceTwo.setOpaque(false);
            spaceTwo.setPreferredSize(new Dimension(440,50));
        wrapper.add(spaceTwo);
        
        if(againtsPc){
            String instructions = String.format("<html><div WIDTH=%d style=\"margin-left:10px;text-align:justify;line-height:2px;\">%s</div><html>",300,"The first player puts a Nought in a cell, then the opponent will have to place a cross in another cell. Keep alternating moves until one of the players has drawn a row of three symbols or until no one can win.<br><br>Select a Search Algorithm:");
            JLabel content = customJLabel(instructions,Font.PLAIN,20);
            wrapper.add(content);      

            JPanel algorithms = new JPanel();
                algorithms.setOpaque(false);
                algorithms.setPreferredSize(new Dimension(280,118));
            JRadioButton jrb1 = customJRadioButton("MIN-MAX");
            jrb1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gamePC("MIN-MAX");
                }
            });
            algorithms.add(jrb1);
            JRadioButton jrb2 = customJRadioButton("ALPHA-BETA Pruning");
            jrb2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gamePC("ALPHA-BETA");
                }
            });
            algorithms.add(jrb2);
            ButtonGroup algorithmsGroup = new ButtonGroup();
            algorithmsGroup.add(jrb1);
            algorithmsGroup.add(jrb2);
            wrapper.add(algorithms);
        }else {
            String instructions = String.format("<html><div WIDTH=%d style=\"margin-left:10px;text-align:justify;line-height:2px;\">%s</div><html>",300,"The first player puts a Nought in a cell, then the opponent will have to place a cross in another cell. Keep alternating moves until one of the players has drawn a row of three symbols or until no one can win.<br><br>");
            JLabel content = customJLabel(instructions,Font.PLAIN,20);
            wrapper.add(content);      
            
            JButton ok = customJButton("img/ok.png");
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameUser();
                }
            });
            wrapper.add(ok);
            
            JLabel spaceThree = new JLabel();
                spaceThree.setOpaque(false);
                spaceThree.setPreferredSize(new Dimension(440,81));
            wrapper.add(spaceThree);
        }
       
        JPanel footer = new JPanel();
            footer.setOpaque(false);
        String courseText = String.format("<html><div WIDTH=%d style=\"text-align:center;margin-top:10px;\">%s</div><html>",320,"Artificial Intelligence<br>ESPOL 2014-II");
        JLabel course = customJLabel(courseText,Font.BOLD,18);
            footer.add(course);
        wrapper.add(footer);     
            
        wrapper.updateUI();
    }
    
    public void gamePC(String algorithm){
        wrapper.removeAll();
        setBackground(wrapper);
        JButton back = customJButton("img/back.png");
            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    instructions(true);
                }
            });
        JLabel spaceOne = new JLabel();
            spaceOne.setOpaque(false);
            spaceOne.setPreferredSize(new Dimension(440,20));
        wrapper.add(spaceOne);
        wrapper.add(back);
        
        String courseTitle = String.format("<html><div WIDTH=%d style=\"margin-left:10px\">%s</div><html>",320,algorithm + " | Tic-Tac-Toe");
        JLabel title = customJLabel(courseTitle,Font.BOLD,24);
        wrapper.add(title);
        
        JLabel spaceTwo = new JLabel();
            spaceTwo.setOpaque(false);
            spaceTwo.setPreferredSize(new Dimension(440,30));
        wrapper.add(spaceTwo);

        setBoard();
        PnlToken[][] chip_panel = this.board.getChipsPanel();
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                final PnlToken token = chip_panel[i][j];
                token.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(!token.paint){
                            token.paint = true;
                            token.nought = false;
                            token.repaint();
                            turn.setText(String.format("<html><div WIDTH=%d style=\"text-align:center;margin-top:10px;\">%s</div><html>",320,"*User's turn*"));
                        }
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
            }
        }
        
        JLabel spaceThree = new JLabel();
            spaceThree.setOpaque(false);
            spaceThree.setPreferredSize(new Dimension(440,16));
        wrapper.add(spaceThree);
        
        String turnText = String.format("<html><div WIDTH=%d style=\"text-align:center;margin-top:10px;\">%s</div><html>",320,"*Nought's turn*");
        turn = customJLabel(turnText,Font.BOLD,18);
        wrapper.add(turn);
        
        JLabel spaceFour = new JLabel();
            spaceFour.setOpaque(false);
            spaceFour.setPreferredSize(new Dimension(440,15));
        wrapper.add(spaceFour);
        
        String courseText = String.format("<html><div WIDTH=%d style=\"text-align:center;margin-top:10px;\">%s</div><html>",320,"Artificial Intelligence<br>ESPOL 2014-II");
        JLabel course = customJLabel(courseText,Font.BOLD,18);
        wrapper.add(course);
        
        wrapper.updateUI();
    }
    
    public void gameUser(){
        wrapper.removeAll();
        setBackground(wrapper);
        JButton back = customJButton("img/back.png");
            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    init();
                    newGame(section);
                }
            });
        JLabel spaceOne = new JLabel();
            spaceOne.setOpaque(false);
            spaceOne.setPreferredSize(new Dimension(440,20));
        wrapper.add(spaceOne);
        wrapper.add(back);
        
        String courseTitle = String.format("<html><div WIDTH=%d style=\"margin-left:10px\">%s</div><html>",320,"Game | Tic-Tac-Toe");
        JLabel title = customJLabel(courseTitle,Font.BOLD,24);
        wrapper.add(title);
        
        JLabel spaceTwo = new JLabel();
            spaceTwo.setOpaque(false);
            spaceTwo.setPreferredSize(new Dimension(440,30));
        wrapper.add(spaceTwo);
        
        JPanel tablero = new JPanel(new GridLayout(3,3));
            tablero.setPreferredSize(new Dimension(300,300));
            tablero.setOpaque(false);
            for(int i=0; i<9; ++i){
                final PnlToken token = new PnlToken();
                token.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(!token.paint){
                            token.paint = true;
                            token.nought = nought;
                            token.repaint();
                            nought = !nought;
                            if(nought) turn.setText(String.format("<html><div WIDTH=%d style=\"text-align:center;margin-top:10px;\">%s</div><html>",320,"*Nought's turn*"));
                            else turn.setText(String.format("<html><div WIDTH=%d style=\"text-align:center;margin-top:10px;\">%s</div><html>",320,"*Cross's turn*"));
                        }
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
                tablero.add(token);
            }
        wrapper.add(tablero);
        
        JLabel spaceThree = new JLabel();
            spaceThree.setOpaque(false);
            spaceThree.setPreferredSize(new Dimension(440,16));
        wrapper.add(spaceThree);
        
        String turnText = String.format("<html><div WIDTH=%d style=\"text-align:center;margin-top:10px;\">%s</div><html>",320,"*Nought's turn*");
        turn = customJLabel(turnText,Font.BOLD,18);
        wrapper.add(turn);
        
        JLabel spaceFour = new JLabel();
            spaceFour.setOpaque(false);
            spaceFour.setPreferredSize(new Dimension(440,15));
        wrapper.add(spaceFour);
        
        String courseText = String.format("<html><div WIDTH=%d style=\"text-align:center;margin-top:10px;\">%s</div><html>",320,"Artificial Intelligence<br>ESPOL 2014-II");
        JLabel course = customJLabel(courseText,Font.BOLD,18);
        wrapper.add(course);
        wrapper.updateUI();
    }
    
    public void settings(){
        wrapper.removeAll();
        setBackground(wrapper);
        JButton back = customJButton("img/back.png");
            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    init();
                    newGame(section);
                }
            });
        JLabel spaceOne = new JLabel();
            spaceOne.setOpaque(false);
            spaceOne.setPreferredSize(new Dimension(440,20));
        wrapper.add(spaceOne);
        wrapper.add(back);
        
        String courseTitle = String.format("<html><div WIDTH=%d style=\"margin-left:10px\">%s</div><html>",320,"Settings | Tic-Tac-Toe");
        JLabel title = customJLabel(courseTitle,Font.BOLD,24);
        wrapper.add(title);

        JLabel spaceTwo = new JLabel();
            spaceTwo.setOpaque(false);
            spaceTwo.setPreferredSize(new Dimension(440,10));
        wrapper.add(spaceTwo);
        
        JPanel order = new JPanel();
            order.setOpaque(false);
            order.setPreferredSize(new Dimension(280,200));
        JLabel space = new JLabel();
            space.setOpaque(false);
            space.setPreferredSize(new Dimension(280,50));
        order.add(space);    
        JLabel orderText = customJLabel("Game order:",Font.BOLD,24);
            orderText.setPreferredSize(new Dimension(280,30));
        order.add(orderText);
        JRadioButton jrb1 = customJRadioButton("User first");
        order.add(jrb1);
        JRadioButton jrb2 = customJRadioButton("Computer first");
        order.add(jrb2);
        ButtonGroup orderGroup = new ButtonGroup();
        orderGroup.add(jrb1);
        orderGroup.add(jrb2);
        wrapper.add(order);
        
        JPanel sound = new JPanel();
            sound.setOpaque(false);
            sound.setPreferredSize(new Dimension(280,192));
        JLabel soundText = customJLabel("Sound:",Font.BOLD,24);
            soundText.setPreferredSize(new Dimension(280,30));
        sound.add(soundText);
        JRadioButton jrb3 = customJRadioButton("On");
        sound.add(jrb3);
        JRadioButton jrb4 = customJRadioButton("Off");
        sound.add(jrb4);
        ButtonGroup soundGroup = new ButtonGroup();
        soundGroup.add(jrb3);
        soundGroup.add(jrb4);
        wrapper.add(sound);
       
        JPanel footer = new JPanel();
            footer.setOpaque(false);
        String courseText = String.format("<html><div WIDTH=%d style=\"text-align:center;margin-top:10px;\">%s</div><html>",320,"Artificial Intelligence<br>ESPOL 2014-II");
        JLabel course = customJLabel(courseText,Font.BOLD,18);
        footer.add(course);
        wrapper.add(footer);    
        wrapper.updateUI();
    }
    
    
    public JRadioButton customJRadioButton(String text){
        JRadioButton radio = new JRadioButton(text);
            radio.setOpaque(false);
            radio.setContentAreaFilled(false);
            radio.setPreferredSize(new Dimension(255,30));
            radio.setBorder(null);
            radio.setForeground(Color.WHITE);
            radio.setFont(new Font("Calibri Light",Font.PLAIN,20));
        return radio;
    }
    
    public JLabel customJLabel(String text, int type, int size){
        JLabel jlabel = new JLabel(text);
            jlabel.setForeground(Color.white);
            jlabel.setFont(new Font("Calibri Light",type,size));
        return jlabel;
    }
    
    public JButton customJButton(String path){
        JButton btn = new JButton(new ImageIcon(VtnMain.class.getResource(path)));
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorder(null);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    public void setBackground(JPanel background){
        try{
            URL imagen = VtnMain.class.getResource("img/background.jpg");                                                    
            BufferedImage img = ImageIO.read(imagen);
            BorderBg borde = new BorderBg(img);
            background.setBorder(borde);            
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Background error");
        }
    }
    
    public void setBoard(){
        this.gameTicTacToe = new Game(1,3);
        this.board = new Board();
        this.board.setObserver(this.gameTicTacToe);
        wrapper.add(board.getBoardPanel());        
    }
    
}
