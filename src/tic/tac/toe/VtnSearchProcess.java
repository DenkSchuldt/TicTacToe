/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author rodrigo
 */
public class VtnSearchProcess extends JFrame {
    
    private mxGraph treeDrawer = null;
    private mxGraphComponent treePanel = null;
    private Object parentDrawer = null;
    private StateSpace tree;
    
    public void create(StateSpace tree) {
        setVisible(true);
        setResizable(false);
        this.tree = tree;
        this.treeDrawer = new mxGraph();
        this.parentDrawer = treeDrawer.getDefaultParent();
        setTitle("Search Process Progress");
        setLocationRelativeTo(null);
        setSize(1300,720);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
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
        this.add(this.treePanel);
        int circle = 75;
        int separateX = 20;
        int rootTreeX = separateX+this.getWidth()/2;
        int rootTreeY = 10;
        
        String style = "shape=ellipse;perimter=ellipsePerimeter";

        ArrayList<State> path = this.tree.getPath();
        int size = this.tree.getPath().size();

        this.treeDrawer.getModel().beginUpdate();
        
        State emptyState = path.get(0);
        emptyState.setPosX(rootTreeX);
        emptyState.setPosY(rootTreeY);

        Object rootObject = this.treeDrawer.insertVertex(parentDrawer, null,
                emptyState,
                emptyState.getPosX(),
                emptyState.getPosY(),
                circle, circle,
                style);
        
        State currentState=null,parentState=null;
        Object childObject;
        parentState=emptyState;
        int i=1;
        if (size > 2) {
            for (i = 1; i < size-1; i++) {
                currentState = path.get(i);
                parentState = tree.getPath().get(i - 1);
                currentState.setPosX(parentState.getPosX());
                currentState.setPosY(parentState.getPosY()+100);
                childObject = this.treeDrawer.insertVertex(parentDrawer, null,
                        currentState,
                        currentState.getPosX(),
                        currentState.getPosY(),
                        circle, circle,
                        style);
                this.treeDrawer.insertEdge(parentDrawer, null, "Edge", rootObject, childObject);
                rootObject = childObject;
            }
            parentState = tree.getPath().get(i-1);
        } 
        State newRoot = this.tree.getCurrentState();//next move in the game: new root
        newRoot.setPosX(parentState.getPosX());
        newRoot.setPosY(parentState.getPosY()+100);

        Object newRootObject = this.treeDrawer.insertVertex(parentDrawer, null,
                newRoot,
                newRoot.getPosX(), newRoot.getPosY(),
                circle, circle,
                style);
        
        this.treeDrawer.insertEdge(parentDrawer, null, "Edge", rootObject, newRootObject);
        
        this.drawChild(newRootObject, this.tree.getTreeSpace(),-1);
        this.treeDrawer.getModel().endUpdate();
        this.treePanel.updateUI();
    }

    public void drawChild(Object padre, Tree<State> root, int order) {
        String style = "shape=ellipse;perimter=ellipsePerimeter";
        State content;
        State bestState;
        int k = 0;
        int circle = 75;
        Object childs[];
        if (!root.hasChildren()) {
            return;
        } else {
            ArrayList<State> bestStates = getBestState(root, -order);//select 3-first states for showing 
            content = root.getNode();
            this.setPositions(content, bestStates);
            childs = new Object[3];
            bestState = bestStates.get(0);

            for (State currentState : bestStates) {
                childs[k] = this.treeDrawer.insertVertex(this.parentDrawer, null,
                        currentState,
                        currentState.getPosX(), currentState.getPosY(),
                        circle, circle, style);
                this.treeDrawer.insertEdge(parentDrawer, null, "Edge", padre, childs[k]);
                k++;
            }
            this.drawChild(childs[0], root.getChildTree(bestState),-order);
        }
    }

    public void setPositions(State root, ArrayList<State> bestStates) {
        int separate = -50;
        int separateY = 100;
        int maxSize=bestStates.size();
        
        bestStates.get(0).setPosX(separate + 2 * root.getPosX() / 3);
        bestStates.get(0).setPosY(root.getPosY() + separateY);
        
        if(maxSize>1){//root have two child
            bestStates.get(1).setPosX(root.getPosX());
            bestStates.get(1).setPosY(root.getPosY() + separateY); 
        }
        if(maxSize>2){//root have three child
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
        
        if(treeStates.size()>=3){
            maxSize=3;
        }else{
            maxSize=treeStates.size();
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
    
}
