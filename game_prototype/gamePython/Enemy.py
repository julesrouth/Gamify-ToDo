import random
import copy
import math
import GameEnum
import Item
import Move
import Statuses
import Stat
import Stances

class Enemy():
    stance = Stances.Stance.DEFEND
    stat = 0
    shielding = 0
    lastMoveInit = 0
    status = Statuses.Statuses()
    moves = [Move.Move(GameEnum.MoveType.ATTACK, 2, 100, name="Slash"), Move.Move(GameEnum.MoveType.DEFEND, 2, 75, name="Shield")]
    def __init__(self):
        self.stat = Stat.Stat()
        self.initiative = 10
        # self.moves = []
    def getSpeed(self):
        return 1 - (self.stat.speed/100)
    
    def getAttack(self):
        tempAttack = self.stat.attack
        if self.status.rage:
            tempAttack = tempAttack * 1.3
        if self.status.frightened:
            tempAttack = tempAttack * .75
        return math.ceil(tempAttack)
        
    def getDefense(self):
        if self.shielding:
            temp = self.stat.defense * self.shielding
            self.shielding = 0
            # print("Shielding: ", temp)
            return temp
        # print("Defense: ", self.stat.defense)
        return self.stat.defense
    
    def move(self):
        for i in range(0, 100):
            temp = random.randint(0, len(self.moves)) - 1
       
            tempMove =  self.moves[temp]
            if self.shielding and tempMove.moveType == GameEnum.MoveType.DEFEND:
                continue
            else:
                return tempMove
            

if __name__ == "__main__":
    enemy = Enemy()
    print(enemy.getAttack())
    enemy.move()
    print(enemy.getDefense())