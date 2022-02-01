import java.util.ArrayList;

public class Assignment1{
  public static int boardSize = 10;

  public static void printBoard(ArrayList<ArrayList<Integer>> board){
    for(int y = 0; y < boardSize; y++){
      for(int x = 0; x < boardSize; x++){
        System.out.print(board.get(y).get(x).toString());
        if(x != boardSize - 1){
          System.out.print(",");
        }else{
          System.out.println("");
        }
      }
    }
  }

  public static void main(String[] args){
    ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>(); //create 2d arrays
    ArrayList<ArrayList<Integer>> heuristicBoard = new ArrayList<ArrayList<Integer>>();


    //initializes 2d arraylist 
    for(int a = 0; a < boardSize; a++){
      board.add(new ArrayList<Integer>(2));
      for(int b = 0;b < boardSize; b++){
        board.get(a).add(0);
      }
    }
    //initializes 2d arraylist 
    for(int a = 0; a < boardSize; a++){
      heuristicBoard.add(new ArrayList<Integer>(2));
      for(int b = 0;b < boardSize; b++){
        heuristicBoard.get(a).add(0);
      }
    }
    //randomly add queens to a column
    for(int a = 0; a < boardSize; a++){
      board.get((int)(Math.random()*boardSize)).add(a,1);
    }

    System.out.println("board");
    printBoard(board);

    System.out.println("heuristicBoard");
    printBoard(heuristicBoard);
  }


  
}


