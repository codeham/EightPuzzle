public class PuzzleDriver {

    private Node currentBoard;
    private int[] goalBoard;

    PuzzleDriver(int[] initialBoard, int[] goalBoard){
        this.currentBoard = new Node(initialBoard);
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
     */
    public int getMisplacedTiles(){
        int misplacedCount = 0;
        int[] board = currentBoard.getStateArray();
        for(int i = 0; i < 9; i++){
            if(board[i] != goalBoard[i]){
                misplacedCount++;
            }
        }
        return misplacedCount-1;
    }

    public int getManhattanDistance(){
        return 0;
    }

    public void solvePuzzle(){
        if(isSolvable(currentBoard.getStateArray())){ System.out.println("Puzzle is solvable !");
        }else{ System.out.println("Not solvable !");}

        System.out.println("Misplaced Tiles: " + getMisplacedTiles());

    }


}
