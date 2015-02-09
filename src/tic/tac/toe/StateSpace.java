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
 */
enum Algorithm {

    MINIMAX, ALPHA_BETA
};

public class StateSpace {

    private State currentState;
    private Tree<State> treeSpace;
    private final int depth;
    private Algorithm typeAlgorithm;
    private ArrayList<State> pathStates;

    public StateSpace(State current, int depth) {
        this.currentState = current;
        this.depth = depth;
        this.treeSpace = generateTree(current, depth);//generate StateSpace
        pathStates = new ArrayList();
        pathStates.add(current);
    }

    public State getCurrentState() {
        return this.currentState;
    }

    public ArrayList<State> getPath() {
        return this.pathStates;
    }

    public void setAlgorithm(String algorithm) {
        if (algorithm.equals("MIN-MAX")) {
            this.typeAlgorithm = Algorithm.MINIMAX;
        } else {
            this.typeAlgorithm = Algorithm.ALPHA_BETA;
        }
    }

    public Tree<State> generateTree(State state, int depth) {
        ArrayList<State> childs = state.createChilds();
        //create all posibles board configuration and this point
        Tree<State> tree = new Tree<State>(state);
        if (childs != null) {
            //add childs to StateSpace
            if (depth == 1) {
                for (State child : childs) {
                    child.generateTokens();
                    child.setPositionForDrawing();
                    tree.addChild(child);
                }
            } else {
                for (State child : childs) {
                    Tree<State> childTrees = generateTree(child, depth - 1);
                    child.generateTokens();
                    child.setPositionForDrawing();
                    tree.addChildTree(childTrees);
                }
            }
        }

        return tree;

    }

    private void expandTree(Tree<State> tree) {
        if (tree.hasChildren()) {
            for (Tree<State> childTree : tree.getChildren()) {
                expandTree(childTree);
            }
        } else {
            tree.setChildren(generateTree(tree.getNode(), 1).getChildren());
        }
    }

    public void updateStateSpace(State newState) {
        Tree<State> treeNew = treeSpace.getChildTree(newState);

        if (treeNew == null) {
            this.treeSpace = this.treeSpace.addChild(newState);
        } else {
            this.treeSpace = treeNew;
        }

        this.expandTree(treeSpace);
        this.currentState = this.treeSpace.getNode();
        pathStates.add(this.currentState);

    }

    public Tree<State> getTreeSpace() {
        return this.treeSpace;
    }

    @Override
    public String toString() {
        return treeSpace.toString();
    }

    private int minimax(Tree<State> state) {
        if (!state.hasChildren()) {
            state.getNode().setBestValue(state.getNode().getHeuristicValue());
            return state.getNode().getBestValue();
        } else {
            int bestValue;
            if (state.getNode().getPlayer() == -1) {
                bestValue = -100;
                for (Tree<State> child : state.getChildren()) {
                    int value = minimax(child);
                    bestValue = Math.max(value, bestValue);
                }
                state.getNode().setBestValue(bestValue);
                return bestValue;
            } else {
                bestValue = 100;
                for (Tree<State> child : state.getChildren()) {
                    int value = minimax(child);
                    bestValue = Math.min(value, bestValue);
                }
                state.getNode().setBestValue(bestValue);
                return bestValue;
            }
        }

    }

    private int alphabeta(Tree<State> state, int alpha, int beta) {
        if (!state.hasChildren()) {
            int bestValue = state.getNode().getHeuristicValue();
            state.getNode().setBestValue(bestValue);
            return bestValue;
        } else {
            int bestValue;
            if (state.getNode().getPlayer() == -1) {
                bestValue = -100;
                for (Tree<State> child : state.getChildren()) {
                    int value = alphabeta(child, alpha, beta);
                    bestValue = Math.max(value, bestValue);
                    int alpha1 = Math.max(bestValue, alpha);
                    if (beta <= alpha1) {
                        break; // beta cut-off
                    }
                }
                state.getNode().setBestValue(bestValue);
                return bestValue;
            } else {
                bestValue = 100;
                for (Tree<State> child : state.getChildren()) {
                    int value = alphabeta(child, alpha, beta);
                    bestValue = Math.min(value, bestValue);
                    int beta1 = Math.min(bestValue, beta);
                    if (beta1 <= alpha) {
                        break; // alpha cut-off
                    }
                }
                state.getNode().setBestValue(bestValue);
                return bestValue;
            }
        }
    }

    public State selectNextMove() {
        int minmaxVal = executeAlgorithm();
        for (Tree<State> TreeState : treeSpace.getChildren()) {
            State state = TreeState.getNode();
            if (state.getBestValue() == minmaxVal) {
                return state;
            }
        }
        return null;
    }

    public int executeAlgorithm() {
        return (typeAlgorithm == Algorithm.MINIMAX)
                ? minimax(this.treeSpace) : alphabeta(this.treeSpace, -100, 100);
    }
}
