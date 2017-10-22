public class Node {
    private Node parent;
    private int[] stateArray;
    private int position;
    private float f, g, h;

    Node(int[] state){
        this.stateArray = state;
    }

    public int[] getStateArray() {
        return stateArray;
    }

    public boolean checkSolved(){

        return false;
    }
}
