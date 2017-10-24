import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class PuzzleDriver {
    public enum Action{
        UP, DOWN, LEFT, RIGHT;
    }

    private Node currentBoard;
    private Node initialBoard;
    private int[] goalBoard;
    private PriorityQueue<Node> pQueue = new PriorityQueue<Node>();

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
    public int getMisplacedTiles(){
        int misplacedCount = 0;
        int[] board = currentBoard.getStateArray();
        for(int i = 0; i < 9; i++){
            if(board[i] != goalBoard[i]){
                misplacedCount++;
            }
        }
        if(misplacedCount == 0){ return misplacedCount; }
        return misplacedCount-1;
    }

    public int getManhattanDistance(){
        int[] boardArray = currentBoard.getStateArray();
        int manhattanSum = 0;
        for(int i = 0; i < boardArray.length; i++){
            if(boardArray[i] == 0){ continue; }
            int boardCol = i % ((boardArray.length/2)-1);
            int boardRow = (i - boardCol) / ((boardArray.length/2)-1);
            int tileNumber = boardArray[i];
            System.out.println("col: " + boardCol + " row: " + boardRow + "    -> " + tileNumber);
            int[] goalPosition = getGoalPosition(tileNumber);
            manhattanSum += Math.abs(goalPosition[0] - boardRow) + Math.abs(goalPosition[1] - boardCol);
        }
        System.out.println("Manhattan Sum is : " + manhattanSum);
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

    public void swap(){
        // middle
        int[] copyArray = Arrays.copyOf(currentBoard.getStateArray(), currentBoard.getArraySize());
        System.out.println(Arrays.toString(copyArray));
        int cache = copyArray[4];
        copyArray[4] = copyArray[1];
        copyArray[1] = cache;
        System.out.println(Arrays.toString(copyArray));

    }

    public void swap1(Stack<Action> x){
        
        for(int i = 0; i < x.size()+1; i++){
            Action currentAction = x.pop();
            switch (currentAction){
                case UP:
            }
        }
    }

    public void generateSuccessors(int rPos, int cPos){
        Stack<Action> actionStack = new Stack<Action>();
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
            //left & right
            actionStack.push(Action.LEFT);
            actionStack.push(Action.RIGHT);
        }

        System.out.println(actionStack.toString());

        swap1(actionStack);




//        int[] emptyTilePos = locateEmptyTile();
//        int x = emptyTilePos[0];
//        int y = emptyTilePos[1];
//        if(x == 1 && y == 1){
//            //middle position
//            //do swap (left, right, up, down)
//            swap();
//            System.out.println("Empty is in the middle !");
//        }
    }

    public void solvePuzzle(){
        // TESTING STUFF
        if(isSolvable(currentBoard.getStateArray())){ System.out.println("Puzzle is solvable !");
        }else{ System.out.println("Not solvable !");}
        System.out.println();
        System.out.println("Misplaced Tiles: " + getMisplacedTiles());
        getManhattanDistance();

        // initiate A*
        int[] emptyTile = locateEmptyTile();
        int x = emptyTile[0];
        int y = emptyTile[1];

        System.out.println("Empty Tile Location: " + "row: " + x + " | col: " + y);
        generateSuccessors(x, y);


        System.out.println(Arrays.toString(currentBoard.getStateArray()));

    }


}
