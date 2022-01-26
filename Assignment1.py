import numpy as np
from random import random
from math import floor

tempBoard = [
[0,0,0,0,0,0,1,0],
[0,0,1,0,0,0,0,0],
[1,0,0,0,0,0,0,0],
[0,0,0,0,0,1,0,0],
[0,0,0,0,0,0,0,0],
[0,0,0,0,1,0,0,0],
[0,1,0,0,0,0,0,0],
[0,0,0,0,0,0,0,1]
]

tempBoard = [
[0,0,0,0,0,0,1,0,0,0],
[0,0,1,0,0,0,0,0,0,0],
[1,0,0,0,0,0,0,0,0,0],
[0,0,0,0,0,1,0,0,0,0],
[0,0,0,0,0,0,0,0,1,0],
[0,0,0,0,1,0,0,0,0,0],
[0,0,0,0,0,0,0,0,0,1],
[0,0,0,0,0,0,0,1,0,0],
[0,0,0,1,0,0,0,0,0,0],
[0,1,0,0,0,0,0,0,0,0]
]
boardSize = 10

def main():
  # initialize matrix
  # board = np.zeros([boardSize, boardSize], dtype = int)

  # # randomly place a queen in each coloumn
  # for x in range(boardSize):
  #   board[int(floor(random()*boardSize))][x] = 1

  # while checkGoalState(board) :
  #   printBoard(board)
  board = [
  [0,0,0,0,0,0,1,0,0,0],
  [0,0,1,0,0,0,0,0,0,0],
  [1,0,0,0,0,0,0,0,0,0],
  [0,0,0,0,0,1,0,0,0,0],
  [0,0,0,0,0,0,0,0,1,0],
  [0,0,0,0,1,0,0,0,0,0],
  [0,0,0,0,0,0,0,0,0,1],
  [0,0,0,0,0,0,0,1,0,0],
  [0,0,0,1,0,0,0,0,0,0],
  [0,1,0,0,0,0,0,0,0,0]
  ]
  printBoard(board)
  print(checkGoalState(board))

  printBoard(tempBoard)





def printBoard(board):
  print('')
  for y in range(boardSize):
    for x in range(boardSize):
      if x!= boardSize - 1 :
        print(board[y][x], end = ",") 
      else:
        print(board[y][x])

def checkGoalState(board):
  # loop through every space on board
  for y in range(boardSize):
    for x in range(boardSize):
      if board[y][x] == 1:
        # tempBoard = [
        # [0,0,0,0,0,0,1,0,0,0],
        # [0,0,1,0,0,0,0,0,0,0],
        # [1,0,0,0,0,0,0,0,0,0],
        # [0,0,0,0,0,1,0,0,0,0],
        # [0,0,0,0,0,0,0,0,1,0],
        # [0,0,0,0,1,0,0,0,0,0],
        # [0,0,0,0,0,0,0,0,0,1],
        # [0,0,0,0,0,0,0,1,0,0],
        # [0,0,0,1,0,0,0,0,0,0],
        # [0,1,0,0,0,0,0,0,0,0]]
        #loop through board, if queen is in spot

        #loop through column of queen
        for i in range(boardSize):
          if x != i:
            if board[y][i] == 1:
              print(f"Column, Queen at x:{x} y:{y}")
              return False


        #loop through row of queen
        for i in range(boardSize):
          if y != i: 
            if board[i][x] == 1:
              print(f"Row, Queen at x:{x} y:{y}")
              return False

        #check left diagonal \ for queen collision
        tempX = x
        tempY = y
        if tempX > tempY:           #------#
            tempX -= tempY          # \    #
            tempY = 0               #  \   #
        else:                       #   \  #
          tempY -= tempX            #    \ #
          tempX = 0                 #     \#
                                    #------#
        while True:
          if board[tempY][tempX] == 1:
            if tempY != y and tempX != x:
              #hit
              print(f"Left diagonal , Queen at x:{x} y:{y}")
              print(f"with tempX:{tempX} tempY:{tempY}")
              return False
          tempX +=1
          tempY +=1
          if tempX < 0 or tempX > boardSize - 1 or tempY < 0  or tempY > boardSize - 1 :
            break


        #check right diagonal / for queen collision
        print('Check right diagonal')
        tempX = x
        tempY = y                             #------#
        if tempX + tempY <= boardSize :       #     /#
            tempX += tempY                    #    / #
            tempY = 0                         #   /  #
        else:                                 #  /   #
            tempY -= boardSize - 1 - tempX    # /    #
            tempX = boardSize - 1             #/     #
        while True :                          #------#
          if tempX < 0 or tempX > boardSize - 1 or tempY < 0  or tempY > boardSize - 1 :
            print('break')
            break
          if board[tempY][tempX] == 1: 
            if tempY != y and tempX != x:
              #hit
              print(f"Right diagonal , Queen at x:{x} y:{y}")
              print(f"with tempX:{tempX} tempY:{tempY}")

              return False
          else:
            print(f"Placing X (y:{tempY},x:{tempX})")
            tempBoard[tempY][tempX] = "X"

          tempX -=1
          tempY +=1



  return True








if __name__ == "__main__":
  main()