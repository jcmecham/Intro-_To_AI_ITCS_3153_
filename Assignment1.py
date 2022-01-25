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

def main():
  # initialize matrix
  # board = np.zeros([8, 8], dtype = int)

  # # randomly place a queen in each coloumn
  # for x in range(8):
  #   board[int(floor(random()*8))][x] = 1

  # while checkGoalState(board) :
  #   printBoard(board)
  board = [
  [0,0,0,0,0,0,1,0],
  [0,0,1,0,0,0,0,0],
  [1,0,0,0,0,0,0,0],
  [0,0,0,0,0,1,0,0],
  [0,0,0,0,0,0,0,0],
  [0,0,0,0,1,0,0,0],
  [0,1,0,0,0,0,0,0],
  [0,0,0,0,0,0,0,1]
  ]
  printBoard(board)
  print(checkGoalState(board))

  printBoard(tempBoard)





def printBoard(board):
  print('')
  for y in range(8):
    for x in range(8):
      if x!= 7 :
        print(board[y][x], end = ",") 
      else:
        print(board[y][x])

def checkGoalState(board):
  # loop through every space on board
  for y in range(8):
    for x in range(8):
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
        for i in range(8):
          if x != i:
            if board[y][i] == 1:
              print(f"Column, Queen at x:{x} y:{y}")
              return False


        #loop through row of queen
        for i in range(8):
          if y != i: 
            if board[i][x] == 1:
              print(f"Row, Queen at x:{x} y:{y}")
              return False

        #check left diagonal \ for queen
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
          if tempX < 0 or tempX > 7 or tempY < 0  or tempY > 7 :
            break


        #check right diagonal / for queen
        print('Check right diagonal')
        tempX = x
        tempY = y                     #------#
        if tempX + tempY <= 8 :       #     /#
            tempX += tempY            #    / #
            tempY = 0                 #   /  #
        else:                         #  /   #
            tempY -= 7 - tempX        # /    #
            tempX = 7                 #/     #
        while True :                  #------#
          if tempX < 0 or tempX > 7 or tempY < 0  or tempY > 7 :
            print('break')
            break
          if board[tempY][tempX] == 1: 
            if tempY != y and tempX != x:
              #hit
              print(f"Right diagonal , Queen at x:{x} y:{y}")
              print(f"with tempX:{tempX} tempY:{tempY}")

              return False
          else:
            tempBoard[tempY][tempX] = "X"

          tempX -=1
          tempY +=1



  return True








if __name__ == "__main__":
  main()