public class Node {
    private Node parent;
    private int[] stateArray;
    private int position;
    private int f, g, h;
    private int arraySize;

    Node(int[] state){
        this.stateArray = state;
        arraySize = stateArray.length;
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

}
