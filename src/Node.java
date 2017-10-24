import java.util.Comparator;

public class Node{
    private Node parent;
    private int[] stateArray;
    private int position;
    private int f, g, h;
    private int arraySize;

    Node(int[] state){
        this.stateArray = state;
        arraySize = stateArray.length;
        f = 0;
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

    public void setParent(Node parent){
        this.parent = parent;
    }
}
