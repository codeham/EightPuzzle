import java.lang.reflect.Array;
import java.util.*;

public class PuzzleDriver {
    public enum Action{
        UP, DOWN, LEFT, RIGHT;
    }

    private Node currentBoard;
    private Node initialBoard;
    private int[] goalBoard;
    Comparator<Node> comparator = new Comparator<>() {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.getF() - o2.getF();
        }
    };
    private PriorityQueue<Node> openList = new PriorityQueue<Node>(200, comparator);
    private HashSet<Node> closedList = new HashSet<>();



    PuzzleDriver(int[] initialBoard, int[] goalBoard){
        this.initialBoard = new Node(initialBoard);
        this.currentBoard = this.initialBoard;
        this.goalBoard = goalBoard;
    }

    /**
     * this method checks if the current puzzle sequence is solvable
     * by counting it's inversions in the sequence.
     * even inversions = solvable
     * odd inversions = not solvable
     * @param currentBoard current puzzle state array
     * @return boolean value validating the puzzle
     */
    public boolean isSolvable(int[] currentBoard){
        int inversionsCount = getInversionCount();
        if((inversionsCount%2) == 0){
            System.out.println("inversion count: " + inversionsCount);
            return true;
        }
        System.out.println("inversion count: " + inversionsCount);
        return false;
    }

    public boolean isGoal(Node successor){
        if(Arrays.equals(successor.getStateArray(), goalBoard)){
            System.out.println("Goal board match !");
           return true;
        }
        return false;
    }

    /**
     * helper method calculates amount of inversions in the puzzle.
     * @return int represents inversion qty.
     */
    public int getInversionCount(){
        int inversionCount = 0;
        int[] boardArray = currentBoard.getStateArray();
        int n = currentBoard.getStateArray().length;
        for(int i = 0; i < n-1; i++){
            for(int j = i+1; j < n; j++) {
                if(boardArray[j] == 0){
                    continue;
                }
                else if ((boardArray[i] > boardArray[j])){
                    System.out.println(boardArray[i] + " is greater than " + boardArray[j]);
                    inversionCount++;
                }
            }
        }
        return inversionCount;
    }

    /**
     * h1(n)
     * Heuristic 1 - calculating misplaced tiles
     * @return int that holds misplaced tile count
     * subtract 1 since blank space doesn't rep. a move
     */
    public int getMisplacedTiles(Node successor){
        int misplacedCount = 0;
        //int[] board = currentBoard.getStateArray();
        int[] boardArray = successor.getStateArray();
        for(int i = 0; i < 9; i++){
            if(boardArray[i] != goalBoard[i]){
                misplacedCount++;
            }
        }
        if(misplacedCount == 0){
            return misplacedCount;
        }
        System.out.println();
        System.out.println("Misplaced Count: " + (misplacedCount-1));
        return misplacedCount-1;
    }

    public int getManhattanDistance(Node successor){
        //int[] boardArray = currentBoard.getStateArray();
        int[] boardArray = successor.getStateArray();
        int manhattanSum = 0;
        for(int i = 0; i < boardArray.length; i++){
            if(boardArray[i] == 0){ continue; }
            int boardCol = i % ((boardArray.length/2)-1);
            int boardRow = (i - boardCol) / ((boardArray.length/2)-1);
            int tileNumber = boardArray[i];
//            System.out.println("col: " + boardCol + " row: " + boardRow + "    -> " + tileNumber);
            int[] goalPosition = getGoalPosition(tileNumber);
            manhattanSum += Math.abs(goalPosition[0] - boardRow) + Math.abs(goalPosition[1] - boardCol);
        }
        //System.out.println("Manhattan Sum is : " + manhattanSum);
        return manhattanSum;
    }

    public int[] getGoalPosition(int tileNumber){
        int[] position;
        for(int i = 0; i < goalBoard.length; i++){
            if(goalBoard[i] == tileNumber){
                int col = i % ((goalBoard.length/2)-1);
                int row = (i - col) / ((goalBoard.length/2)-1);
                position = new int[]{row, col};
                return position;
            }
        }
        return null;
    }

    public int[] locateEmptyTile(){
        int[] boardArray = currentBoard.getStateArray();
        int[] emptyPosition = new int[2];
        for(int i = 0; i < boardArray.length; i++){
            if(boardArray[i] == 0){
                int col = i % ((goalBoard.length/2)-1);
                int row = (i - col) / ((goalBoard.length/2)-1);
                emptyPosition = new int[]{row, col};
                return emptyPosition;
            }
        }
        return emptyPosition;
    }

    public int locateTileIndex(int rPos, int cPos){
        int[] currentArray = currentBoard.getStateArray();
        for(int i = 0; i < currentArray.length; i++){
            int boardCol = i % ((currentArray.length/2)-1);
            int boardRow = (i - boardCol) / ((currentArray.length/2)-1);
            if(boardRow == rPos && boardCol == cPos){
                return i;
            }
        }
        return 0;
    }

    public List<Node> swap(Stack<Action> x){
        //List<int[]> stateArrays = new ArrayList<>();
        List<Node> successors = new ArrayList<>();
        int[] emptyPos = locateEmptyTile();

        for(Action a: x){
            Action currentAction = a;
            int[] copyArray = Arrays.copyOf(currentBoard.getStateArray(), currentBoard.getArraySize());

            int rPos = emptyPos[0];
            int cPos = emptyPos[1];
            int swapIndex = 0;
            int emptyTileIndex = locateTileIndex(rPos, cPos);
            switch (currentAction){
                case UP:
                    // decrement row
                    swapIndex = locateTileIndex(--rPos, cPos);
                    //System.out.println("index: " + swapIndex + " UP");
                    break;
                case DOWN:
                    // increment row
                    swapIndex = locateTileIndex(++rPos, cPos);
                    //System.out.println("index: " +swapIndex + " DOWN");
                    break;
                case LEFT:
                    // decrement col
                    swapIndex = locateTileIndex(rPos, --cPos);
                    //System.out.println("index: " +swapIndex + " LEFT");
                    break;
                case RIGHT:
                    // increment col
                    swapIndex = locateTileIndex(rPos, ++cPos);
                    //System.out.println("index: " +swapIndex + " RIGHT");
                    break;
            }
            // do the swap in the copy array
            // add this array inside the list
            int temp = copyArray[swapIndex];
            copyArray[swapIndex] = copyArray[emptyTileIndex];
            copyArray[emptyTileIndex] = temp;
//            System.out.println(Arrays.toString(copyArray));
//            System.out.println("******************************");
//            stateArrays.add(copyArray);
            successors.add(new Node(copyArray));
        }
        return successors;
    }

    public List<Node> generateSuccessors(){
        Stack<Action> actionStack = new Stack<Action>();
        int[] emptyTile = locateEmptyTile();
        int rPos = emptyTile[0];
        int cPos = emptyTile[1];
        if(rPos == 0){
            // only down
            actionStack.push(Action.DOWN);
        }else if (rPos == 2){
            // only up
            actionStack.push(Action.UP);
        }else {
            // up & down
            actionStack.push(Action.UP);
            actionStack.push(Action.DOWN);
        }

        if(cPos == 0){
            // right
            actionStack.push(Action.RIGHT);
        }else if (cPos == 2){
            // left
            actionStack.push(Action.LEFT);
        }else{
            // left & right
            actionStack.push(Action.LEFT);
            actionStack.push(Action.RIGHT);
        }
//        System.out.println();
//        System.out.println("actions taken: " + actionStack.toString());

        return swap(actionStack);
    }

    public List<Node> setParents(List<Node> successors){
        for(Node eachSuccessor: successors){
            eachSuccessor.setParent(currentBoard);
        }
        return successors;
    }

    public void solvePuzzle(){
        // TESTING STUFF
        if(!isSolvable(currentBoard.getStateArray())){
//            throw new IllegalArgumentException("Unsolvable Puzzle");
            System.out.println("Puzzle Not Solvable !");
            return;
        }

        System.out.println();
        System.out.println("**Initial Board**");
        System.out.println("Initial Puzzle " + Arrays.toString(currentBoard.getStateArray()));
        System.out.println("Misplaced Tiles: " + getMisplacedTiles(initialBoard));
        getManhattanDistance(initialBoard);

        System.out.println();


        //A*
        // initialize open list
        // initialize closed list
//        int depth = 0;

        openList.add(this.initialBoard);

        while(!openList.isEmpty()){
            this.currentBoard = openList.poll();
            System.out.println("Move --> " + Arrays.toString(this.currentBoard.getStateArray()));
            System.out.println("Depth Count: " + currentBoard.getCurrentDepth());
            System.out.println();
            //generate successors and set their parents to q
            List<Node> successors = setParents(generateSuccessors());
            for(Node successor: successors){
                successor.setCurrentDepth(successor.getParent().getCurrentDepth() + 1);
                if(isGoal(successor)){
                    // if the successor is in fact the goal state we need, stop the loop and return
                    System.out.println("Matched ---> " + Arrays.toString(successor.getStateArray()));
                    System.out.println("Final Depth Count: " + successor.getCurrentDepth());
                    return;
                }
                successor.setG(successor.getCurrentDepth());
                successor.setH(getManhattanDistance(successor));
                successor.setF(successor.getG() + successor.getH());
                
                if(openList.contains(successor)){
                    // skip the successor, it's already in the list
                    continue;
                }

                if(closedList.contains(successor)){
                    // skip the successor, already in closed list
                    continue;
                }else {
                    // add node to open list
                    openList.add(successor);
                }
            }
            closedList.add(currentBoard);
            openList.remove(currentBoard);
        }
    }


}
