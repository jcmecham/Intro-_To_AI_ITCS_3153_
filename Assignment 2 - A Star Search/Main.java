import java.util.*;
import java.util.regex.Pattern;
import java.lang.ProcessBuilder;




public class Main {
  public static int BoardSize = 15;
  public static ArrayList<Node> openList = new ArrayList<Node>();//needs to be initialized for printBoard()
  public static ArrayList<Node> closedList = new ArrayList<Node>();//needs to be initialized for printBoard()
  public static Node startNode = new Node(-1,-1,0); //needs to be initialized for printBoard()
  public static Node goalNode = new Node(-1,-1,0);  //needs to be initialized for printBoard()

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
    try{Thread.sleep(300);}catch(InterruptedException e){};
    ClearConsole();
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
        Node tempNode = gameBoard.get(r).get(c);
        pathable = tempNode.getType();
        if(r == startNode.getRow() && c == startNode.getCol()){
          System.out.print(ConsoleColors.BLACK + ConsoleColors.GREEN_BACKGROUND + " " + pathable + " " + ConsoleColors.RESET);

        }else if(r == goalNode.getRow() && c == goalNode.getCol()){
          System.out.print(ConsoleColors.BLACK +ConsoleColors.RED_BACKGROUND+" "+ pathable +" "+ ConsoleColors.RESET);
        }else{
          if(pathable == 0){
            if(closedList.contains(tempNode)){
              System.out.print(ConsoleColors.BLACK +ConsoleColors.BLUE_BACKGROUND+" "+ pathable +" "+ ConsoleColors.RESET);
            }else{
            System.out.print(ConsoleColors.BLACK +ConsoleColors.WHITE_BACKGROUND+" "+ pathable +" "+ ConsoleColors.RESET);
            }
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

    // print(gameBoard);s
    printBoard(gameBoard);
    Scanner sc = new Scanner(System.in);
    do{
      System.out.println("Enter "+ConsoleColors.GREEN+"Start Node"+ConsoleColors.RESET+" cordinates in the format: row,column");
      String temp = sc.nextLine();
      if(Pattern.matches(".*,.*",temp) ){
        try {
          int r = Integer.parseInt(temp.split(",")[0]);
          int c = Integer.parseInt(temp.split(",")[1]);
          Node tempNode = new Node(r,c,0);
          if(isValid(tempNode, BoardSize)){
            if(gameBoard.get(r).get(c).getType() == 0){
              startNode = tempNode;
              break;
            }else{
              System.out.println("Unable to make the "+ConsoleColors.GREEN +"Starting Node"+ ConsoleColors.RESET+" a blocked node");
            }
          }else{
            System.out.println("Please enter cordinates that range from 0 - "+(BoardSize-1));
          }
        } catch (Exception e) {
          System.out.println("Only Numbers are accepted");
        }

        
      }else{
        System.out.println("Enter coordinates in the format: row,column");
      }


    }while(true);
    printBoard(gameBoard);
    do{
      System.out.println("Enter "+ConsoleColors.RED + "Goal Node" + ConsoleColors.RESET + " cordinates in the format: row,column");
      String temp = sc.nextLine();
      if(Pattern.matches(".*,.*",temp)){
        try{
          int r = Integer.parseInt(temp.split(",")[0]); // get row from input
          int c = Integer.parseInt(temp.split(",")[1]); // get column from input
          Node tempNode = new Node(r,c,0);
          if (isValid(tempNode,BoardSize)){ //verify 
            if(gameBoard.get(r).get(c).getType() == 0){
              goalNode = tempNode;
              break;
            }else{
              System.out.println("Unable to make the "+ConsoleColors.RED +"Goal Node"+ ConsoleColors.RESET +" a blocked node");

            }
          }else{
            System.out.println("Please enter cordinates that range from 0 - "+(BoardSize-1));
          }
        }
        catch(Exception e){
          System.out.println("Only Numbers are accepted");
        }

      }else{
        System.out.println("Enter coordinates in the format: row,column");
      }

    }while(true);

    printBoard(gameBoard);

    aStar(gameBoard);

    sc.close();
  }

  public static int calculateH(Node n){
    // Manhattan method
    int h = Math.abs(n.getRow() - goalNode.getRow()) + 
     Math.abs(n.getCol() - goalNode.getCol());
    return h;
  }

  public static boolean isValid(Node n,int BoardSize){
		int r = n.getRow();
		int c = n.getCol();
		return r >= 0 && r<BoardSize && c >= 0 && c<BoardSize;
	}

  public static ArrayList<Node> sort(ArrayList<Node> sortList) {
    int fLow; 
    Node n;
    for(int i = 0; i < sortList.size(); i++) {
        fLow = i;
        for(int id = i; id < sortList.size() - 1; id++) {
            if (sortList.get(id + 1).getF() < sortList.get(fLow).getF()) {
                fLow = id + 1;
            }
        }
        n = sortList.get(i);
        sortList.set(i, sortList.get(fLow));
        sortList.set(fLow, n);
    }
    return sortList;

  }


  public static void aStar(ArrayList<ArrayList<Node>> gameBoard){
    openList = new ArrayList<Node>();//Initialize the open list
    closedList = new ArrayList<Node>();//Initialize the closed list

    //put the starting node on the open list
    openList.add(startNode);

    do{
      // pop off the smallest element
      Node q = openList.get(0);
      openList.remove(q);

      //create successor 
      ArrayList<Node> successors = new ArrayList<Node>();
      successors.add(new Node(q.getRow(),q.getCol()+1,0));
      successors.add(new Node(q.getRow(),q.getCol()-1,0));
      successors.add(new Node(q.getRow()+1,q.getCol(),0));
      successors.add(new Node(q.getRow()-1,q.getCol(),0));
      successors.add(new Node(q.getRow()-1,q.getCol()-1,0));
      successors.add(new Node(q.getRow()-1,q.getCol()+1,0));
      successors.add(new Node(q.getRow()+1,q.getCol()-1,0));
      successors.add(new Node(q.getRow()+1,q.getCol()+1,0));


      for(int i = 0; i<successors.size();i++){
        if(successors.get(i).equals(goalNode)){
          //reached goal node0,0
          closedList.add(q);
          printBoard(gameBoard);
          System.out.println("Solution Found!");
          return;
        };
        //check successor is within bounds of the board
        if(!isValid(successors.get(i),BoardSize)){
          continue;
        }
        //check is successor is not obstacle
        if(gameBoard.get(successors.get(i).getRow()).get(successors.get(i).getCol()).getType() == 1){
          continue;
        }
        //calculate g
        int g = q.getG() + 1;
        successors.get(i).setG(g);
        //aclcualte h
        int h = calculateH(successors.get(i));
        successors.get(i).setH(h);

        //f=g+h
        successors.get(i).setF();

        //makes q the parent
        successors.get(i).setParent(q);

        /**
         *  if a node with the same position as 
            successor is in the OPEN list which has a 
           lower f than successor, skip this successor
         */
        boolean skip = false;
        for(int o = 0; o < openList.size();o++){
          if(successors.get(i).equals(openList.get(o)) && openList.get(o).getF() < successors.get(i).getF()){
            //skip successor
            skip = true;
            break;
          }
        }
        if(skip){
          //skip successor
          continue;
        }
        /**
         * if a node with the same position as 
            successor  is in the CLOSED list which has
            a lower f than successor, skip this successor
            otherwise, add  the node to the open list
         */
        for(int o = 0; o < closedList.size();o++){
          if(successors.get(i).equals(closedList.get(o)) && closedList.get(o).getF() < successors.get(i).getF()){
            //skip successor
            skip = true;
            break;
          }
        }
        if(skip){
          //skip successor
          continue;
        }else{
          //add new sucessor to openList
          openList.add(successors.get(i));
        }
      };
      printBoard(gameBoard);
      //add q to closed list
      closedList.add(q);

      //sorts lowest F value to beginning of the list
      openList = sort(openList);
    }while(openList.size() != 0 );
    System.out.println("no path could be found");
  };
  
  public static void ClearConsole(){
    try{
        String operatingSystem = System.getProperty("os.name"); //Check the current operating system
          
        if(operatingSystem.contains("Windows")){        
            //clears screen on windows
            Process startProcess = new ProcessBuilder("cmd", "/c", "cls").inheritIO().start();
            startProcess.waitFor();
        } else {
            //supposedly clears screen on other devices
            Process startProcess = new ProcessBuilder("clear").inheritIO().start();
            startProcess.waitFor();
        } 
    }catch(Exception e){
        System.out.println(e);
    }
  }

}



