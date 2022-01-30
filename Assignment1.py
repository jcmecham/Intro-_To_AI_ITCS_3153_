import numpy as np
from random import random
from math import floor
import time

boardSize = 8

"""
The heuristic is the number of queen collisions based one the given state of the board
"""

def main():

  # initialize matrix
  board = np.zeros([boardSize, boardSize], dtype = int)
  heuristicBoard = np.zeros([boardSize, boardSize], dtype = int)

  # randomly place a queen in each coloumn
  for x in range(boardSize):
    board[int(random()*boardSize)][x] = 1

  numLowerNeighborStates = 0
  StateChanges = 0
  restarts = 0
  while not checkGoalState(board):

    if numLowerNeighborStates == 0:
      board = np.zeros([boardSize, boardSize], dtype = int)
      heuristicBoard = np.zeros([boardSize, boardSize], dtype = int)

      # randomly place a queen in each coloumn
      for x in range(boardSize):
        board[int(floor(random()*boardSize))][x] = 1

      currentH = generateHeuristic(board)
    else:

      numLowerNeighborStates = 0

    lowestH = currentH
    print(f'Current h: {currentH}')
    print('Current State')
    printBoard(board)
    for x in range(boardSize):

      #find y index of queen
      queenIndex = 0
      for y in range(boardSize):
        if board[y][x] == 1:
          queenIndex = y

      tempBoard = board.copy() #create a copy of the board
      tempBoard[queenIndex][x] = 0 #remove queen from previous position

      # generate heuristic values for a single column
      for y in range(boardSize):
        tempBoard[y][x] = 1 # add queen to new position

        #The heuristic is the number of queen collisions based one the given state of the board
        heuristicBoard[y][x] = generateHeuristic(tempBoard) #based on the positions of the queens in tempBoard, a heuristic value is returned
        tempBoard[y][x] = 0 #remove queen from previous position



    # holds the x and y values of first position on the board with the lowestH value , if 
    lowestPos = []
    restart = True
    # check for lowest hueristic present on the board
    for x in range(boardSize):
      for y in range(boardSize):
        if heuristicBoard[y][x] < currentH: #count how many positions have a lower heuristic value
          numLowerNeighborStates += 1
        if heuristicBoard[y][x] < lowestH: # if heuristic value in the current pos is lower than the lowest heuristic, change the lowest heuristic to the current pos value
          lowestH = heuristicBoard[y][x]
          lowestPos = [y,x]
          currentH = lowestH
          restart = False #signals reset doesn't need to happen 

    StateChanges += 1
    if restart:
      restarts +=1
      print("RESTART\n")
    else:
      print(f'Neighbors found with lower h: {numLowerNeighborStates}')
      print('Setting new current state\n')
      
      for y in range(boardSize):
        board[y][lowestPos[1]] = 0
        if [y,lowestPos[1]] == lowestPos:
          board[y][lowestPos[1]] = 1


  print("Current State")
  printBoard(board)

  print("Solution Found!")
  print(f"State Changes: {StateChanges}")
  print(f"Restarts: {restarts}")







def printBoard(board):
  
  for y in range(boardSize):
    for x in range(boardSize):
      if x!= boardSize - 1 :
        print(board[y][x], end = ",") 
      else:
        print(board[y][x])


def checkGoalState(board):
  # loop through every space on board to find queens
  for y in range(boardSize):
    for x in range(boardSize):
      if board[y][x] == 1:#loop through board to check for queen collitions, if current position is a queen

        #loop through column of currernt queen
        for i in range(boardSize):
          if x != i:
            if board[y][i] == 1:
              # print(f"Column, Queen at x:{x} y:{y}")
              return False


        #loop through row of current queen
        for i in range(boardSize):
          if y != i: 
            if board[i][x] == 1:
              # print(f"Row, Queen at x:{x} y:{y}")
              return False

        #check left diagonal \ for current queen collision
        tempX = x
        tempY = y
        #find the heightest point of the diagonal
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
              # print(f"Left diagonal , Queen at x:{x} y:{y}")
              # print(f"with tempX:{tempX} tempY:{tempY}")
              return False
          tempX +=1
          tempY +=1
          if tempX < 0 or tempX > boardSize - 1 or tempY < 0  or tempY > boardSize - 1 :
            break


        #check right diagonal / for current queen collision
        tempX = x
        tempY = y
        #find the heightest point of the diagonal
                                              #------#
        if tempX + tempY <= boardSize :       #     /#
            tempX += tempY                    #    / #
            tempY = 0                         #   /  #
        else:                                 #  /   #
            tempY -= boardSize - 1 - tempX    # /    #
            tempX = boardSize - 1             #/     #
                                              #------#
        while True :
          if tempX < 0 or tempX > boardSize - 1 or tempY < 0  or tempY > boardSize - 1 :

            break
          if board[tempY][tempX] == 1: 
            if tempY != y and tempX != x:
              #hit
              # print(f"Right diagonal , Queen at x:{x} y:{y} collided with Queen at x:{tempX} y:{tempY} ")
              return False

          tempX -=1
          tempY +=1



  return True


def generateHeuristic(board):
  # loop through every space on board to find queens
  collisionCount = 0
  for x in range(boardSize):
    for y in range(boardSize):
      if board[y][x] == 1:
        #loop through column of currernt queen
        for i in range(x+1,boardSize):
          if board[y][i] == 1:
            collisionCount+=1
            # print(f"Column, Queen at x:{x} y:{y} collided with Queen at x:{i} y:{y}")

        #loop through row of current queen
        for i in range(y+1,boardSize):
          if board[i][x] == 1:
            collisionCount+=1
            # print(f"Row, Queen at x:{x} y:{y} collided with Queen at x:{x} y:{i}")


        #check left diagonal \ for current queen collision
        #------#
        # \    #
        #  \   #
        #   \  #
        #    \ #
        #     \#
        #------#
        tempX = x
        tempY = y
        while True:
          tempX +=1
          tempY +=1
          if tempX > boardSize - 1 or tempY > boardSize - 1 :
            break
          if board[tempY][tempX] == 1:
            collisionCount+=1
            # print(f"Left diagonal , Queen at x:{x} y:{y} collided with Queen at x:{tempX} y:{tempY}")


        #check right diagonal / for current queen collision
        #------#
        #     /#
        #    / #
        #   /  #
        #  /   #
        # /    #
        #/     #
        #------#

        tempX = x
        tempY = y
        while True :
          tempX +=1
          tempY -=1
          if tempY < 0 or tempX > boardSize - 1 :
            break

          if board[tempY][tempX] == 1: 
            collisionCount+=1
            # print(f"Right diagonal , Queen at x:{x} y:{y} collided with Queen at x:{tempX} y:{tempY}")

        # print(f'-- Collision Count {round(collisionCount,2)}')
        # heuristicBoard[y][x] = collisionCount
      
  return collisionCount





if __name__ == "__main__":
  main()