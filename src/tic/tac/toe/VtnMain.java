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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

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
    private boolean nought = true;
    private boolean soundOn = true;
    private boolean userFirst = true;
    private boolean minMax = true;
    private final int DIM = 3;
    private String check = "sounds/check.wav";
    BasicPlayer player;
    
    
    public void create(){
        setTitle("TicTacToe");
        setLayout(new BorderLayout());
        setSize(440,594);
        wrapper = new JPanel();
        init();
        setContentPane(wrapper);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setIconImage(new ImageIcon(VtnMain.class.getResource("img/nought.png")).getImage());
        try{
            player = new BasicPlayer();
            player.open(new File(VtnMain.class.getResource(check).getPath()));
        }catch(BasicPlayerException ex){
            System.out.print("Error: "+ex.getMessage());
        }
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
    
    public void instructions(final boolean againtsPc){
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
                gameStart("MIN-MAX",againtsPc);
                minMax = true;
            }
        });
        algorithms.add(jrb1);
        JRadioButton jrb2 = customJRadioButton("ALPHA-BETA Pruning");
        jrb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameStart("ALPHA-BETA",againtsPc);
                minMax = false;
            }
        });
        algorithms.add(jrb2);
        ButtonGroup algorithmsGroup = new ButtonGroup();
        algorithmsGroup.add(jrb1);
        algorithmsGroup.add(jrb2);
        wrapper.add(algorithms);
       
        JPanel footer = new JPanel();
            footer.setOpaque(false);
        String courseText = String.format("<html><div WIDTH=%d style=\"text-align:center;margin-top:10px;\">%s</div><html>",320,"Artificial Intelligence<br>ESPOL 2014-II");
        JLabel course = customJLabel(courseText,Font.BOLD,18);
            footer.add(course);
        wrapper.add(footer);     
            
        wrapper.updateUI();
    }
    
    public void gameStart(String algorithm, boolean againtsPc){
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

        setBoard(algorithm, againtsPc);
        PnlToken[][] chip_panel = this.board.getChipsPanel();
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                final PnlToken token = chip_panel[i][j];
                if(i==0 && j==0)
                    token.setBorder(BorderFactory.createMatteBorder(0,0,1,1, Color.WHITE));
                if(i==2 && j==0)
                    token.setBorder(BorderFactory.createMatteBorder(1,0,0,1, Color.WHITE));
                if(i==1 && j==1)
                    token.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.WHITE));                
                if(i==0 && j==2)
                    token.setBorder(BorderFactory.createMatteBorder(0,1,1,0, Color.WHITE));
                if(i==2 && j==2)
                    token.setBorder(BorderFactory.createMatteBorder(1,1,0,0, Color.WHITE));
                /*token.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(!token.paint){
                            token.paint = true;
                            token.nought = false;
                            token.repaint();
                            try {
                                if(soundOn){
                                    player.play();
                                }
                            } catch (BasicPlayerException ex) {
                                Logger.getLogger(VtnMain.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
                });*/
            }
        }
        
        JLabel spaceThree = new JLabel();
            spaceThree.setOpaque(false);
            if(!againtsPc) spaceThree.setPreferredSize(new Dimension(440,5));
            else spaceThree.setPreferredSize(new Dimension(440,16));
        wrapper.add(spaceThree);
        
        String turnText = String.format("<html><div WIDTH=%d style=\"text-align:center;margin-top:10px;\">%s</div><html>",320,"*Nought's turn*");
        turn = customJLabel(turnText,Font.BOLD,18);
        wrapper.add(turn);
        
        if(againtsPc){
            JLabel spaceFour = new JLabel();
                spaceFour.setOpaque(false);
                spaceFour.setPreferredSize(new Dimension(440,15));
            wrapper.add(spaceFour);
        } else {
            JButton suggestion = new JButton("<html><u>*Get Suggestion*</u><html>");
                suggestion.setOpaque(false);
                suggestion.setContentAreaFilled(false);
                suggestion.setBorderPainted(false);
                suggestion.setCursor(new Cursor(Cursor.HAND_CURSOR));
                suggestion.setFocusPainted(false);
                suggestion.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gameTicTacToe.getStateSpace().executeAlgorithm();
                        gameTicTacToe.showAISearch();
                    }
                });
            wrapper.add(suggestion);
        }
        
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
        if(userFirst) jrb1.doClick();
        order.add(jrb1);
        JRadioButton jrb2 = customJRadioButton("Computer first");
        if(!userFirst) jrb2.doClick();
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
        if(soundOn) jrb3.doClick();
        sound.add(jrb3);
        JRadioButton jrb4 = customJRadioButton("Off");
        if(!soundOn) jrb4.doClick();
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
        final JRadioButton radio = new JRadioButton(text);
            radio.setOpaque(false);
            radio.setContentAreaFilled(false);
            radio.setPreferredSize(new Dimension(255,30));
            radio.setBorder(null);
            radio.setForeground(Color.WHITE);
            radio.setFont(new Font("Calibri Light",Font.PLAIN,20));
            radio.setIcon(new ImageIcon(VtnMain.class.getResource("img/radioOff.png")));
            radio.setCursor(new Cursor(Cursor.HAND_CURSOR));
            radio.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        radio.setIcon(new ImageIcon(VtnMain.class.getResource("img/radioOn.png")));
                        changeSettings(radio.getText());
                    } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                        radio.setIcon(new ImageIcon(VtnMain.class.getResource("img/radioOff.png")));
                        changeSettings(radio.getText());
                    }
                    System.out.println(radio.getText());
                }
            });
        return radio;
    }
    
    public void changeSettings(String text){
        switch(text){
            case "On":
                soundOn = true;
                break;
            case "Off":
                soundOn = false;
                break;
            case "User first":
                userFirst = true;
                break;
            case "Computer first":
                userFirst = false;
                break;
            case "MIN-MAX":
                minMax = true;
                break;
            case "ALPHA-BETA Pruning":
                minMax = false;
                break;
        }
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
    
    public void setBoard(String algorithm, Boolean againstPc){
        gameTicTacToe = new Game(1,3, againstPc);
        gameTicTacToe.getStateSpace().setAlgorithm(algorithm);
        board = new Board();
        board.setObserver(this.gameTicTacToe, againstPc);
        wrapper.add(board.getBoardPanel());        
    }
    
}
