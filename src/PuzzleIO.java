import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class PuzzleIO {

    public static void main(String[] args){
        if(mainMenu() == 1){
            randomizePuzzles();
        }else{
            customPuzzle();
        }
    }

    public static int mainMenu(){
        System.out.println("A* 8-Puzzle Simulation");
        System.out.println("1. Randomize Results");
        System.out.println("2. Custom Puzzle");
        Scanner k = new Scanner(System.in);
        int choice = k.nextInt();
        return choice;
    }

    public static void customPuzzle(){
        System.out.println("Enter Puzzle (9 numbers (1-8) including 0)");
        Scanner k = new Scanner(System.in);
        String scanLine = k.nextLine();
        scanLine = formatInput(scanLine);
        initiateDriver(scanLine);
    }

    public static void randomizePuzzles(){
        Random rand = new Random();
        List<String> randomNumbers = new ArrayList<String>();
        while(randomNumbers.size() < 9){
            String random = Integer.toString(rand.nextInt(9));
            if(!randomNumbers.contains(random)){
                randomNumbers.add(random);
            }
        }

        StringBuilder sb = new StringBuilder();
        for(String x: randomNumbers){
            sb.append(x);
        }
        initiateDriver(sb.toString());
    }

    public static String formatInput(String inputLine){
        return inputLine.replaceAll("\\s", "");
    }

    public static void initiateDriver(String x){
        int[] puzzleBoard = new int[9];
        for(int i = 0; i < x.length(); i++){
            puzzleBoard[i] = Character.getNumericValue(x.charAt(i));
        }
        Scanner k = new Scanner(System.in);
        System.out.println("Pick Heuristic to Run");
        System.out.println("1. Misplaced Tiles (h1)");
        System.out.println("2. Manhattan Distance (h2)");
        int heuristicChoice = k.nextInt();
        int[] goalPuzzle = {0,1,2,3,4,5,6,7,8};
        PuzzleDriver game = new PuzzleDriver(puzzleBoard, goalPuzzle);
        game.solvePuzzle(heuristicChoice);
    }
}
