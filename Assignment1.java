import java.util.ArrayList;
import java.util.Collections;

public class Assignment1{
  public static int boardSize = 8;


  public static void printBoard(){};
  public static void checkGoalState(){};
  public static void generateHeuristic(){};
  public static void main(String[] args){
    // declare 2d arraylists
    ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>(); 
    ArrayList<ArrayList<Integer>> heuristicBoard = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> tempBoard = new ArrayList<ArrayList<Integer>>();

    //initializes all 2d arraylists to be correct size with 0 zeros 
    for(int a = 0; a < boardSize; a++){

      board.add(new ArrayList<Integer>());
      heuristicBoard.add(new ArrayList<Integer>());
      tempBoard.add(new ArrayList<Integer>());

      for(int b = 0;b < boardSize; b++){
        board.get(a).add(0);
        heuristicBoard.get(a).add(0);
        tempBoard.get(a).add(0);
      }
    }

    //randomly add queens to a column
    for(int a = 0; a < boardSize; a++){
      board.get((int)(Math.random()*boardSize)).add(a,1);
    }


    int numLowerNeighborStates = 0; //counts number of lower neighbor states
    int stateChanges = 0; //counts number of state changes
    int restarts = 0; //counts # of restarts
    int currentH = 0; //holds current board heuristic value
    int lowestH; //holds lowest Heuristic value
    int lowestPos[] = new int[2]; //holds the x and y values of first position on the board with the lowestH value  
    boolean restart; // determines whether the board gets stuck and needs to restart

    while(!checkGoalState(board)){

      if(numLowerNeighborStates == 0){

        board = new ArrayList<ArrayList<Integer>>(); 
        heuristicBoard = new ArrayList<ArrayList<Integer>>();
        tempBoard = new ArrayList<ArrayList<Integer>>();

        //initializes all 2d arraylists with 0 zeros
        for(int a = 0; a < boardSize; a++){
          board.add(new ArrayList<Integer>());
          heuristicBoard.add(new ArrayList<Integer>());
        tempBoard.add(new ArrayList<Integer>());
          for(int b = 0;b < boardSize; b++){
            board.get(a).add(0);
            heuristicBoard.get(a).add(0);
            tempBoard.get(a).add(0);
          }
        }

        //randomly add queens to a column
        for(int a = 0; a < boardSize; a++){
          board.get((int)(Math.random()*boardSize)).add(a,1);
        }

        //gets current heuristic for board
        currentH = generateHeuristic(board);

      }else{
        // resest number of neighbor states that are lower after moving a queen
        numLowerNeighborStates = 0;
      }

      lowestH = currentH; //start with currentH as the lowest

      System.out.println("Current h: "+currentH);
      System.out.println("Current State");
      printBoard(board);

      // loop through columns checking heuristics
      for(int x = 0; x < boardSize; x++){
        int queenIndex = 0;
        // find y index of queen
        for(int y = 0; y < boardSize; y++){
          if(board.get(y).get(x) == 1){
            queenIndex = y;
          }
        }
        //create a copy of the board
        for(int b = 0 ; b < boardSize;b++){
          for(int c = 0 ; c < boardSize;c++){
            int e = board.get(b).get(c);
            tempBoard.get(b).set(c,e);
          }
        }
        //removes queen from previous position on copied board
        tempBoard.get(queenIndex).set(x, 0); 
        
        for(int y = 0; y < boardSize ;y++){
          tempBoard.get(y).set(x, 1); 

          // The heuristic is the number of queen collisions based one the given state of the board
          int tempHeuristic = generateHeuristic(tempBoard);
          
          heuristicBoard.get(y).set(x,tempHeuristic);// based on the positions of the queens in tempBoard, a heuristic value is returned
          tempBoard.get(y).set(x,0);//remove queen from previous position

        }
      }

      restart = true;
      

      //check for lowest hueristic present on the board
      for(int x =0;x<boardSize;x++){
        for(int y = 0; y< boardSize;y++){
          if(heuristicBoard.get(y).get(x) < currentH){
            //count how many positions have a lower heuristic value
            numLowerNeighborStates += 1;
          }
          if(heuristicBoard.get(y).get(x)<lowestH){
            //if heuristic value in the current pos is lower than the lowest heuristic, change the lowest heuristic to the 
            lowestH = heuristicBoard.get(y).get(x);
            lowestPos[0] = y;
            lowestPos[1] = x;
            currentH = lowestH;
            restart = false; //#signals reset doesn't need to happen
          }
        }
      }

      stateChanges += 1;

      if(restart){
        //if no position has been moved to have a lower hueristic, reset board
        restarts +=1;
        System.out.println("RESTART");
      }else{
        //if a position has been found to have a lower hueristic, move queen
        System.out.println("Neighbors found with lower h:" + numLowerNeighborStates);
        System.out.println("Setting new current state\n");

        // move queen to row with lowest heuristic
        for(int y = 0; y < boardSize;y++){
          board.get(y).set(lowestPos[1],0);
          if(lowestPos[0] == y ){
            board.get(y).set(lowestPos[1],1);
          }
        }

      }

    }
    System.out.println("Current State");
    printBoard(board);

    System.out.println("Solution Found!");
    System.out.println("State Changes: "+ stateChanges);
    System.out.println("Restarts: "+ restarts);


  }

  public static void printBoard(ArrayList<ArrayList<Integer>> board){
    //Allows for easy print formatting of the boards
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

  public static int generateHeuristic(ArrayList<ArrayList<Integer>> board){
    /**
     * Recieves a board of queens , one queen in each row
     * 
     * This method returns an integer heuristic that counts the number of queen collitions on a given board
     * 
     */

    // loop through every space on board to find queens
    int collisionCount = 0;
    for(int x = 0; x < boardSize ; x++){
      for(int y = 0; y < boardSize; y++){
        if(board.get(y).get(x) == 1){

          // loop through column of current queen
          for(int i = x+1;i< boardSize;i++){
            if(board.get(y).get(i) == 1){
              collisionCount += 1;
            }
          }
          // loop through row of current queen
          for(int i = y+1;i< boardSize;i++){
            if(board.get(i).get(x) == 1){
              collisionCount += 1;
            }
          }

        //  check left diagonal \ for current queen collision
        // #------#
        // # \    #
        // #  \   #
        // #   \  #
        // #    \ #
        // #     \#
        // #------#
          int tempX = x;
          int tempY = y;
          while(true){
            tempX +=1;
            tempY +=1;
            if(tempX > boardSize - 1 || tempY > boardSize - 1 ){
              break;
            }
            if(board.get(tempY).get(tempX) == 1){
              collisionCount += 1;
            }
          }

        // check right diagonal / for current queen collision
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

          while(true){
            tempX += 1;
            tempY -= 1;

            if(tempY < 0 || tempX > boardSize - 1 ){
              break;
            }
            if(board.get(tempY).get(tempX) == 1){
              collisionCount += 1;
            }
          }




        }
      }
    }

    return collisionCount;
    
  }

}


