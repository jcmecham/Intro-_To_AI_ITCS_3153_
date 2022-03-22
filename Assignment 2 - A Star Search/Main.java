import java.util.*;
import java.util.regex.Pattern;
import java.lang.*;




public class Main {
  // public static ArrayList<ArrayList<Node>> gameBoard;
  public static int[] startNode = {-1,-1};
  public static int[] goalNode = {-1,-1};
  public static int BoardSize = 15;

  public static ArrayList<ArrayList<Node>> createGameBoard(int BoardSize){
    ArrayList<ArrayList<Node>> gameBoard = new ArrayList<ArrayList<Node>>();
    for(int r =0; r < BoardSize; r++){
      gameBoard.add(new ArrayList<Node>());
      for(int c = 0; c < BoardSize; c++){
        int pathable;
        if(Math.random() * 100 <= 10){
          pathable = 1;
        }else{
          pathable = 0;
        }
        gameBoard.get(r).add(new Node(r,c,pathable));

      }

    }


    return gameBoard;
  };  

  public static void printBoard(ArrayList<ArrayList<Node>> gameBoard){
    System.out.println("                      GAME BOARD");
    System.out.println("                      ----------");

    System.out.println("     0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 \n   +---------------------------------------------+");
    
    
    for(int r =0; r < BoardSize; r++){
      if(r<10){
        System.out.print(r + "  |");
      }else{
        System.out.print(r + " |");
      };
      
      gameBoard.add(new ArrayList<Node>());
      for(int c = 0; c < BoardSize; c++){
        int pathable;
        // System.out.println(BoardSize);
        // System.out.println(r);
        // System.out.println(c);
        Node tempNode = gameBoard.get(r).get(c);
        pathable = tempNode.getType();
        if(r == startNode[0] && c == startNode[1]){
          System.out.print(ConsoleColors.BLACK + ConsoleColors.GREEN_BACKGROUND + " " + pathable + " " + ConsoleColors.RESET);

        }else if(r == goalNode[0] && c == goalNode[1]){
          System.out.print(ConsoleColors.BLACK +ConsoleColors.RED_BACKGROUND+" "+ pathable +" "+ ConsoleColors.RESET);
        }else{
          if(pathable == 0){
            System.out.print(ConsoleColors.BLACK +ConsoleColors.WHITE_BACKGROUND+" "+ pathable +" "+ ConsoleColors.RESET);
          }else{
            System.out.print(ConsoleColors.GREY_BACKGROUND +" "+ pathable + " "+ConsoleColors.RESET);
          }
        }
        
      }
      System.out.println( "| ");

      
    }
    System.out.println("   +---------------------------------------------+");
  };
  public static void main(String[] args){
    

    ArrayList<ArrayList<Node>> gameBoard = createGameBoard(BoardSize);


    printBoard(gameBoard);
    Scanner sc = new Scanner(System.in);
    do{
      System.out.println("Enter "+ConsoleColors.GREEN+"Start Node"+ConsoleColors.RESET+" cordinates in the format: row,column");
      String temp = sc.nextLine();
      if(Pattern.matches(".*,.*",temp) ){
        try {
          int row = Integer.parseInt(temp.split(",")[0]);
          int column = Integer.parseInt(temp.split(",")[1]);
          if(row >= 0 && row <= 14 && column >= 0 && column <= 14){
            startNode[0] = Integer.parseInt(temp.split(",")[0]);
            startNode[1] = Integer.parseInt(temp.split(",")[1]);
          }
        } catch (Exception e) {
          System.out.println("Only Numbers are accepted");
        }

        break;
      }else{
        System.out.println("Enter coordinates between 0-14 in the format: row,column");
      }


    }while(true);
    printBoard(gameBoard);
    do{
      System.out.println("Enter "+ConsoleColors.RED + "Goal Node" + ConsoleColors.RESET + " cordinates in the format: row,column");
      String temp = sc.nextLine();
      if(Pattern.matches(".*,.*",temp)){
        try{
          int row = Integer.parseInt(temp.split(",")[0]);
          int column = Integer.parseInt(temp.split(",")[1]);
          if(row >= 0 && row <= 14 && column >= 0 && column <= 14){
            goalNode[0] =  row;
            goalNode[1] = column;
            break;
          }else{
            System.out.println("Please enter cordinates that range from 0 - 14");
          }
        }
        catch(Exception e){
          System.out.println("Only Numbers are accepted");
        }

      }else{
        System.out.println("Enter coordinates between 0-14 in the format: row,column");
      }

    }while(true);

    printBoard(gameBoard);



    sc.close();
  };


}
