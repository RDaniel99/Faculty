# MAIN DRIVER PROBLEM TO SOLVE AN INPUT SUDOKU PUZZLE

import sys
import time
from tema4_CSP import csp
from tema4_AC3 import *

if __name__ == "__main__":
    '''
    The function takes arguments from commandline
    Argument 1 - Python file name 
    Argument 2 - Input String Showing the Sudoku 
    '''
    if len(sys.argv) == 2:
        grid = sys.argv[1]
        assert len(grid) == 81
        sudoku = csp(grid=grid)
        solved = AC3(sudoku)

        f = open("output.txt", "w")
        if isComplete(sudoku) and solved:
            display(solved)
            print("Perfectly solved")
        else:
            print("Not solved")
    else:
        print("INVALID NUMBER OF INPUTS")
