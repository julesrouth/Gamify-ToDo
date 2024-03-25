
import random
import GameEnum
from colorama import Fore, Back, Style
class Map:

    map = [[0 for i in range(10)] for j in range(10)]
    
    def __init__(self):
        #randomly fill the map
        for i in range(10):
            for j in range(10):

                if random.randint(0, 1) == 0:
                    self.map[i][j] = 0
                elif random.randint(0, 1) == 1:
                    self.map[i][j] = 1
                else:
                    self.map[i][j] = 2

    #override print for the map
    def __str__(self):
        tempStr = ""
        for i in self.map:
            for j in i:
                tempStr += str(j) + " "
            tempStr += "\n"
        return tempStr
    def move(self, x, y):
        if (x < 0) or (x > 9) or (y < 0) or (y > 9):
            return GameEnum.Terrain.INVALID
        else:
            return GameEnum.Terrain.FOREST

    def getSurrounding(self, x, y):
        surrounding = ""
        for x_row in range(-3,4):
            for y_col in range(-3,4):

                if x_row + x < 0 or x_row + x > 9 or y_col + y < 0 or y_col + y > 9:
                    continue
                else:
                    surrounding += str(self.map[x_row + x][y_col + y]) + " "
            surrounding += '\n'
        return surrounding

if __name__ == "__main__":
    map = Map()
    print(map)
    print(map.getSurrounding(5, 5))
    print(map.move(5, 5))
    print(map.move(10, 10))
    print(map.move(-1, -1))
    print(map.move(0, 0))
    print(map.move(9, 9))
    print(map.move(0, 9))
    print(map.move(9, 0))
    