/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tic.tac.toe;
import java.util.ArrayList;
/**
 *
 * @author gabo
 * @param <T>
 */
public class Tree<T> {
    private T node;
    private Tree<T> parent;
    private ArrayList<Tree<T>> children;
    
    public Tree(T node){
        this.node = node;
        this.children = new ArrayList<Tree<T>>();
    }
    
    public Tree(T node, ArrayList<Tree<T>> children){
        this.children = children;
        this.node = node;
    }
    
    public Tree<T> addChild(T child){
        Tree<T> childNode = new Tree<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
    public void addChildTree(Tree<T> tree){
        this.children.add(tree);
    }
    public T getNode() {
        return node;
    }

    public void setRoot(T root) {
        this.node = root;
    }

    public Tree<T> getParent() {
        return parent;
    }


    public ArrayList<Tree<T>> getChildren() {
        return children;
    }
    
    public void setChildren(ArrayList<Tree<T>> children) {
        this.children = children;
    }
    
    public boolean hasChildren(){
        return !this.children.isEmpty();
    }
    
    public Tree<T> getChildTree(T child){
        for(Tree<T> tChild : children){
            if(State.areEqual((State) child, (State) tChild.node))
                return tChild;
        }
        return null;
    }
    
    public String toString(){
        String s = this.node.toString();
        s += "\n";
        for(Tree<T> child: this.children){
            s += child.toString();
            s += "\n";
        }
        
        return s;
    }
}
