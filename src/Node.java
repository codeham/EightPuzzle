import java.util.Comparator;

public class Node{
    private Node parent;
    private int[] stateArray;
    private int position;
    private int f, g, h;
    private int arraySize;
    private int currentDepth;

    Node(int[] state){
        this.stateArray = state;
        arraySize = stateArray.length;
        f = 0;
        currentDepth = 0;
    }

    public int[] getStateArray() {
        return stateArray;
    }

    public boolean checkSolved(){
        return false;
    }

    public int getArraySize(){
        return arraySize;
    }

    public Node getParent(){
        return parent;
    }

    public int getF(){
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG(){
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }

    public int getCurrentDepth() {
        return currentDepth;
    }

    public void setCurrentDepth(int currentDepth) {
        this.currentDepth = currentDepth;
    }

    public void incrementDepth(){
        this.currentDepth = currentDepth++;
    }
}
