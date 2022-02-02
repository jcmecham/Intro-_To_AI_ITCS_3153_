import java.util.ArrayList;

public class Assignment1{
  public static int boardSize = 10;

  
  public static void printBoard(){};
  public static void checkGoalState(){};
  public static void main(String[] args){
    ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>(); //create 2d arrays
    ArrayList<ArrayList<Integer>> heuristicBoard = new ArrayList<ArrayList<Integer>>();


    //initializes 2d arraylist for board 
    for(int a = 0; a < boardSize; a++){
      board.add(new ArrayList<Integer>(2));
      for(int b = 0;b < boardSize; b++){
        board.get(a).add(0);
      }
    }
    //initializes 2d arraylist for heuristicBoard
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

  public static Boolean checkGoalState(ArrayList<ArrayList<Integer>> board){

    /*
     *   Returns True or False
     *  Loops through board and checks if any queens collide with each other
     *  returns True if no queens collide and returns False otherwise
     *
     */
    for(int y = 0; y < boardSize; y++){
      for(int x = 0; x < boardSize; x++){
        if(board.get(y).get(x) == 1){

          //loop through column of queen
          for(int i = 0;i < boardSize;i++){
            if(x != i ){
              if(board.get(y).get(i) == 1){
                // System.out.println("row hit: Queen at x:"+x+" y:"+y+" hit Queen at x:"+i+" y:"+y);
                return false;

              }
              
            }
          }

          //loop through row of queen
          for(int i = 0;i < boardSize;i++){
            if(y != i && board.get(i).get(x) == 1){
              // System.out.println("column hit: Queen at x:"+x+" y:"+y+" hit Queen at x:"+i+" y:"+y);
              return false;
            }
          }
          //loop through Left diagonal of queen
          // #------#
          // # \    #
          // #  \   #
          // #   \  #
          // #    \ #
          // #     \#
          // #------#
          int tempX = x;
          int tempY = y;
          //get highest point of diagonal
          if(tempX > tempY){
            tempX -= tempY;
            tempY = 0;
          }
          else{
            tempY -= tempX;
            tempX = 0;
          }
          while(true){
            if(board.get(tempY).get(tempX) == 1){
              if(tempY != y && tempX != x){
                // System.out.println("left diagonal hit: Queen at x:"+x+" y:"+y+" hit Queen at x:"+tempX+" y:"+tempY);
                return false;
                }
              }
            tempX += 1;
            tempY += 1;
            if(tempX < 0 || tempX > boardSize - 1 || tempY < 0  || tempY > boardSize - 1){
              break;
            }
          }

          //loop through Right diagonal of queen
          // #------#
          // #     /#
          // #    / #
          // #   /  #
          // #  /   #
          // # /    #
          // #/     #
          // #------#

          tempX = x;
          tempY = y;
          //get the highest point of right diagonal
          if(tempX + tempY <= boardSize){
            tempX += tempY;
            tempY = 0;
          }else{
            tempY -= boardSize - 1 - tempX;
            tempX = boardSize - 1;
          }
          while(true){
            
            if(tempX < 0 || tempX > boardSize - 1 || tempY < 0  || tempY > boardSize - 1 ){
              break;
            }
            if(board.get(tempY).get(tempX) == 1){
              if(tempY != y && tempX != x){
                
                // System.out.println("right diagonal hit: Queen at x:"+x+" y:"+y+" hit Queen at x:"+tempX+" y:"+tempY);

                return false;
              }
            }
            tempX -= 1;
            tempY += 1;
          }


        }
      }
    }
    return true;
  }

      }
    }
  }


}


