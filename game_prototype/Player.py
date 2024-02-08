import Move
import Statuses
import Stat
import Item
import GameEnum
import math
import copy
import Map

class Inventory():
    items = []
    def __init__(self):
        self.items = []
    def addItem(self, item):
        self.items.append(item)
    def removeItem(self, item):
        self.items.remove(item)

    def printItems(self):
        tempStr = ""
        for i in self.items:
            tempStr += str(i) + "\n"
        return tempStr


class Player:
    gold = 100
    location = [0, 0]
    stat = 0
    shielding = 0
    lastMoveInit = 0
    inventory = []
    moves = [Move.Move(GameEnum.MoveType.ATTACK, 2, 100,  name = "attack"), Move.Move(GameEnum.MoveType.DEFEND, 2, 75,  name = "defend"), Move.Move(GameEnum.MoveType.ATTACK, 4, 150, name = "strong attack")]
    initiative = 5
    def __init__(self, x, y):
        self.location = [x, y]
        self.stat = Stat.Stat()
        self.status = Statuses.Statuses()

    def addItem(self, item):
        self.inventory.append(item)
    
    def printInventory(self):
        tempStr = ""
        for i in self.inventory:
            tempStr += str(i) + "\n"
        return tempStr

    def getSpeed(self):
        return 1 - (self.stat.speed/100)
    
    def getAttack(self):
        tempAttack = self.stat.attack
        for i in self.inventory:
            tempAttack += i.stats.attack
        tempAttack = self.status.calculateAttack(tempAttack)
        return math.ceil(tempAttack)
    
    def getDefense(self):
        tempDef = self.stat.defense
        for i in self.inventory:
            tempDef += i.stats.defense
        
        if self.shielding:

            temp=  tempDef * self.shielding
            self.shielding = 0
            return temp
        
        return self.stat.defense
        
    def location(self):
        return self.location
    def moveLoc(self, dir, map):

        tempLoc = copy.copy(self.location)
        if dir == GameEnum.Direction.UP:
            tempLoc[1] -= 1
        elif dir == GameEnum.Direction.DOWN:
            tempLoc[1] += 1
        elif dir == GameEnum.Direction.LEFT:
            tempLoc[0] -= 1
        elif dir == GameEnum.Direction.RIGHT:
            tempLoc[0] += 1
        else:
            input("Invalid direction")
            return False
        return tempLoc
    
    def move(self, tempLoc, terrain):
        if GameEnum.terrain == GameEnum.Terrain.INVALID:
            print("Invalid move")
            return False
        else:
            print("Valid move")
            self.location = tempLoc
    def movesStr(self):
        tempStr = ""
        for i in self.moves:
            tempStr += i.name + " "
        return tempStr

    def getMove(self):
        while True:
            inputPlayer = input("Input Move Valid Moves:" + self.movesStr() + "\n")
            inputPlayer = inputPlayer.lower()

            for i in self.moves:
                if i.name == inputPlayer:
                    return i
            
            print("Invalid move")


if __name__ == "__main__":
    player = Player(0, 0)
    print(player.getAttack())
    player.moveLoc(GameEnum.Direction.UP, Map.Map())
    print(player.getDefense())