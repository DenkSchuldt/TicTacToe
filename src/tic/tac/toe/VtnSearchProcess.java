/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.canvas.mxGraphicsCanvas2D;
import com.mxgraph.shape.mxStencil;
import com.mxgraph.shape.mxStencilRegistry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author rodrigo
 */
public class VtnSearchProcess extends JFrame {

    private mxGraph treeDrawer = null;
    private mxGraphComponent treePanel = null;
    private Object parentDrawer = null;
    private StateSpace tree;
    private String style, styleEdge;
     JScrollPane pane;
    public void create(StateSpace tree) {
        this.tree = tree;
        this.treeDrawer = new mxGraph();
        this.parentDrawer = treeDrawer.getDefaultParent();
        setTitle("Search Process Progress");
        setSize(1300, 720);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        this.toBack();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        styleEdge = "fontColor=000000;fontSize=18;fontWidth=3;strokeColor=000000";
        
        pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
    }

    public void initTreePanel() {
        if (this.treePanel != null) {
            this.treeDrawer = new mxGraph();
            this.parentDrawer = treeDrawer.getDefaultParent();
            this.treePanel = new mxGraphComponent(this.treeDrawer);
            this.treePanel.updateUI();
        } else {
            this.treePanel = new mxGraphComponent(this.treeDrawer);
        }
    }

    public void drawGame() {
        this.initTreePanel();
        this.setBackground(this.treePanel);
        //this.add(this.treePanel);
        this.add(pane);
        this.add(this.treePanel);
        
        int circle = 75;
        int separateX = 20;
        int rootTreeX = separateX + this.getWidth() / 2;
        int rootTreeY = 10;
        ArrayList<State> path = this.tree.getPath();
        int size = this.tree.getPath().size();

        this.treeDrawer.getModel().beginUpdate();

        State emptyState = path.get(0);
        emptyState.setPosX(rootTreeX);
        emptyState.setPosY(rootTreeY);
        callGenerator(emptyState,"#ffcc00",0);
        this.readXML("state" + 0 + ".xml");
        String style = "shape=state0;shadow=1;fontColor=#000000";
        Object rootObject = this.treeDrawer.insertVertex(parentDrawer, null,
                emptyState,
                emptyState.getPosX(),
                emptyState.getPosY(),
                circle, circle,
                style);

        State currentState = null, parentState = null;
        Object childObject;
        parentState = emptyState;
        int i = 1;
        if (size > 2) {
            for (i = 1; i < size - 1; i++) {
                currentState = path.get(i);
                parentState = tree.getPath().get(i - 1);
                callGenerator(currentState,"#ffcc00", i);
            }
            for (i = 1; i < size - 1; i++) {
                currentState = path.get(i);
                parentState = tree.getPath().get(i - 1);
                currentState.setPosX(parentState.getPosX());
                currentState.setPosY(parentState.getPosY() + 100);
                readXML("state" + i + ".xml");
                style = "shape=state" + i + ";shadow=1;fontColor=#000000";
                childObject = this.treeDrawer.insertVertex(parentDrawer, null,
                        currentState,
                        currentState.getPosX(),
                        currentState.getPosY(),
                        circle, circle,
                        style);
                this.treeDrawer.insertEdge(parentDrawer, null, "  " + parentState.getBestValue(),
                        rootObject, childObject, styleEdge);
                rootObject = childObject;
            }
            parentState = tree.getPath().get(i - 1);
        }
        State newRoot = this.tree.getCurrentState();//next move in the game: new root
        newRoot.setPosX(parentState.getPosX());
        newRoot.setPosY(parentState.getPosY() + 100);
        callGenerator(newRoot,"#ffcc00", i);
        readXML("state" + i + ".xml");
        style = "shape=state" + i + ";shadow=1;fontColor=#000000";

        Object newRootObject = this.treeDrawer.insertVertex(parentDrawer, null,
                newRoot,
                newRoot.getPosX(), newRoot.getPosY(),
                circle, circle,
                style);

        this.treeDrawer.insertEdge(parentDrawer, null, "   " + parentState.getBestValue(),
                rootObject, newRootObject, styleEdge);

        this.drawChild(newRootObject, this.tree.getTreeSpace(),0, -1, i);
        this.treeDrawer.getModel().endUpdate();
        this.treePanel.updateUI();
    }

    //generarXML para un estado id
    void callGenerator(State st, int id) {
        XMLGenerator generator = null;
        generator = new XMLGenerator(st.getTokensDrawer(), id);
        try {
            generator.generateStateXML(id);
        } catch (Exception ex) {
            Logger.getLogger(VtnSearchProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void callGenerator(State st,String color, int id) {
        XMLGenerator generator = null;
        generator = new XMLGenerator(st.getTokensDrawer(), id);
        generator.setColor(color);
        try {
            generator.generateStateXML(id);
        } catch (Exception ex) {
            Logger.getLogger(VtnSearchProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void drawChild(Object padre, Tree<State> root,int level, int order, int id) {
        State content;
        State bestState;
        int k = 0,circle = 75;
        int i = id;
        Object childs[];
        if (root.hasChildren()&&level<4) {
            ArrayList<State> bestStates = getBestState(root, -order);//select 3-first states for showing 
            content = root.getNode();
            this.setPositions(content, bestStates);
            childs = new Object[3];
            bestState = bestStates.get(0);
            for (State currentState : bestStates) {
                i++;
                if(i-1==id){
                    callGenerator(currentState,"#ffcc00",i);
                }else{
                    callGenerator(currentState,i);
                }
            }

            i=id;
            for (State currentState : bestStates) {
                i++;
                readXML("state" + i + ".xml");
                style = "shape=state" + i + ";shadow=1;fontColor=#000000";
                childs[k] = this.treeDrawer.insertVertex(this.parentDrawer, null,
                        currentState,
                        currentState.getPosX(), currentState.getPosY(),
                        circle, circle, style);
                this.treeDrawer.insertEdge(parentDrawer, null, "   " + currentState.getBestValue(), padre, childs[k], styleEdge);
                k++; 
            }
            this.drawChild(childs[0], root.getChildTree(bestState),level+1,-order, i);
        }
    }

    public void setPositions(State root, ArrayList<State> bestStates) {
        int separate = -50;
        int separateY = 100;
        int maxSize = bestStates.size();

        bestStates.get(0).setPosX(separate + 2 * root.getPosX() / 3);
        bestStates.get(0).setPosY(root.getPosY() + separateY);

        if (maxSize > 1) {//root have two child
            bestStates.get(1).setPosX(root.getPosX());
            bestStates.get(1).setPosY(root.getPosY() + separateY);
        }
        if (maxSize > 2) {//root have three child
            bestStates.get(2).setPosX(-separate + 2 * 2 * root.getPosX() / 3);
            bestStates.get(2).setPosY(root.getPosY() + separateY);
        }
    }

    public ArrayList<State> getBestState(Tree<State> root, int order) {
        int maxSize;
        ArrayList<State> bestState = new ArrayList();//new collection with the best children-states

        ArrayList<Tree<State>> treeStates;
        treeStates = root.getChildren();

        //menor a mayor
        Collections.sort(treeStates, new Comparator() {
            @Override
            public int compare(Object object, Object objectTwo) {
                Tree<State> treeOne, treeTwo;
                treeOne = (Tree<State>) object;
                treeTwo = (Tree<State>) objectTwo;

                State one, two;

                one = treeOne.getNode();
                two = treeTwo.getNode();

                if (one.getBestValue() == two.getBestValue()) {
                    return 0;
                }
                if (one.getBestValue() > two.getBestValue()) {
                    return 1;
                }
                return -1;
            }
        });

        //-1:mayor a menor, 1 menor a mayor
        if (order == -1) {
            Collections.reverse(treeStates);
        }

        if (treeStates.size() >= 3) {
            maxSize = 3;
        } else {
            maxSize = treeStates.size();
        }

        for (int i = 0; i < maxSize; i++) {
            Tree<State> currentTree = treeStates.get(i);
            if (currentTree != null) {
                State currentState = currentTree.getNode();
                if (currentState != null) {
                    bestState.add(currentState);
                }
            }
        }

        return bestState;
    }

    public void readXML(String path) {
        try {
            String filename = path;
            Document doc = (Document) mxXmlUtils.parseXml(mxUtils.readFile(filename));
            Element shapes = (Element) doc.getDocumentElement();
            NodeList list = shapes.getElementsByTagName("shape");
            for (int i = 0; i < list.getLength(); i++) {
                Element shape = (Element) list.item(i);
                mxStencilRegistry.addStencil(
                        shape.getAttribute("name"),
                        new mxStencil((Element) (Element) shape) {
                            protected mxGraphicsCanvas2D createCanvas(
                                    final mxGraphics2DCanvas gc) {
                                        // Redirects image loading to graphics canvas
                                        return new mxGraphicsCanvas2D(gc.getGraphics()) {
                                            protected Image loadImage(String src) {
                                                // Adds image base path to relative image URLs
                                                if (!src.startsWith("/")
                                                && !src.startsWith("http://")
                                                && !src.startsWith("https://")
                                                && !src.startsWith("file:")) {
                                                    src = gc.getImageBasePath() + src;
                                                }
                                                // Call is cached
                                                return gc.loadImage(src);
                                            }
                                        };
                                    }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBackground(mxGraphComponent background) {
        try {
            URL imagen = VtnSearchProcess.class.getResource("img/back.jpg");
            // BufferedImage img = ImageIO.read(imagen);
            // BorderBg borde = new BorderBg(img);
            ImageIcon img = new ImageIcon(imagen.getPath());
            background.setBackgroundImage(img);
            

            // background.setBorder(borde);            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Background error");
        }
    }

}
