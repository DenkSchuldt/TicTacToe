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
public class StateSpace {
    private State currentState;
    private Tree<State> treeSpace;
    private int depth;
    public StateSpace(State current, int depth){
        this.currentState = current;
        this.depth = depth;
        this.treeSpace = generateTree(current, depth);
    }
    
    final public Tree<State> generateTree(State state, int depth){
        java.util.ArrayList<State> childs = state.createChilds();
        Tree<State> tree = new Tree<State>(state);
        if(depth == 1){    
            for(State child: childs){
                tree.addChild(child);
            }
         
        }else{
            for(State child: childs){
                Tree<State> childTrees = generateTree(child, depth - 1);
                tree.addChildTree(childTrees);
            }
        }
        return tree;
    }
    
    private void expandTree(Tree<State> tree){
        if(tree.hasChildren()){
            for(Tree<State> childTree : tree.getChildren()){
                expandTree(childTree);
            }
        }else{
            tree.setChildren(generateTree(tree.getNode(),1).getChildren());
        }
            
    }
    
    public void updateStateSpace(State state){
        treeSpace = treeSpace.getChildTree(state);
        currentState = treeSpace.getNode();
        expandTree(treeSpace);
    }
    
    public Tree<State> getTreeSpace(){
        return this.treeSpace;
    }
    
    @Override
    public String toString(){
        return treeSpace.toString();
    }
}