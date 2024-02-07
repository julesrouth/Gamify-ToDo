import time
import random
from colorama import Fore, Back, Style
from enum import Enum
import copy
import math

from collections import deque
from queue import Queue



#type chart


def clearScreen():
    print("\033c")

# class syntax
class Terrain(Enum):
    INVALID = -1
    FOREST = 0
    WATER = 1
    MOUNTAIN = 2

class Direction(Enum):
    UP = 0
    DOWN = 1
    LEFT = 2
    RIGHT = 3




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
            return Terrain.INVALID
        else:
            return Terrain.FOREST

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

def printColoredString(inputString: str) -> str:
    inputString = str(inputString)
    for char in inputString:
        if char == '0':
            print(Fore.GREEN + char, end='')
        elif char == '1':
            print(Fore.BLUE + char, end='')
        elif char == '2':
            print(Fore.WHITE + char, end='')
        else:
            print(Fore.RED + char, end='')

class Stat():
    health = 0
    attack = 0
    defense = 0
    level = 0
    mana = 0
    def __init__(self, health = 30, attack = 10, defense=10, speed=10, level=5):
        self.health = health
        self.attack = attack
        self.defense = defense
        self.level = level
        self.mana = level
        self.speed = speed
        self.max_health = health
        self.max_mna = self.mana



class MoveType(enumerate):
    ATTACK = 0
    DEFEND = 1
    SPELL = 2

#Move Class
#  Power (double): How strong a move is
#  moveType (MoveType): What type of move it is 
#  effect: Optional if a move has an additional effect
class Move():
    power = 0
    moveType = 0
    effect = 0
    name = "Null"
    def __init__(self, moveType, power,speed,  effect = 0, name = "Null"):
        self.moveType = moveType
        self.power = power
        self.speed = speed
        self.effect = effect
        self.name = name

    def __str__(self) -> str:
        return self.name
class Player:
    location = [0, 0]
    stat = 0
    shielding = 0
    lastMoveInit = 0
    moves = [Move(MoveType.ATTACK, 2, 100,  name = "attack"), Move(MoveType.DEFEND, 2, 75,  name = "defend"), Move(MoveType.ATTACK, 4, 150, name = "strong attack")]
    initiative = 5
    def __init__(self, x, y):
        self.location = [x, y]
        self.stat = Stat()

    def getSpeed(self):
        return 1 - (self.stat.speed/100)
    def getDefense(self):
        if self.shielding:

            temp=  self.stat.defense * self.shielding
            self.shielding = 0
            return temp
        
        return self.stat.defense
        
    def location(self):
        return self.location
    def moveLoc(self, dir, map):

        tempLoc = copy.copy(self.location)
        if dir == Direction.UP:
            tempLoc[1] -= 1
        elif dir == Direction.DOWN:
            tempLoc[1] += 1
        elif dir == Direction.LEFT:
            tempLoc[0] -= 1
        elif dir == Direction.RIGHT:
            tempLoc[0] += 1
        else:
            input("Invalid direction")
            return False
        return tempLoc
    
    def move(self, tempLoc, terrain: Terrain):
        if terrain == Terrain.INVALID:
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


    

class Enemy():
    stat = 0
    shielding = 0
    lastMoveInit = 0
    moves = [Move(MoveType.ATTACK, 2, 100), Move(MoveType.DEFEND, 2, 75)]
    def __init__(self):
        self.stat = Stat()
        self.initiative = 10
        # self.moves = []
    def getSpeed(self):
        return 1 - (self.stat.speed/100)
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
            if self.shielding and tempMove.moveType == MoveType.DEFEND:
                continue
            else:
                return tempMove
class Game():
    message_log =  deque(maxlen=4)
    combatTime = 0

    player = 0
    map = 0
    currentEnemy = 0
    def __init__(self):
        self.player = Player(0, 0)
        self.map = Map()
        self.currentEnemy = Enemy()
    
    def moveOnMap(self):
            (printColoredString(map.getSurrounding(player.location[0], player.location[1])))
            print("\n\n")
            print("Input Direction\n")
            inputDir = input()
            if inputDir == "w":
                inputDir = Direction.UP
            elif inputDir == "s":
                inputDir = Direction.DOWN
            elif inputDir == "a":
                inputDir = Direction.LEFT
            elif inputDir == "d":
                inputDir = Direction.RIGHT
            else:
                print("Invalid input")
                return
            print(inputDir)
            #try to move
            print("\033c")

            tempLoc = player.moveLoc(inputDir, map)
            if not tempLoc:
                print("Invalid move")
                return 
            #see if its valid
            terrain = map.move(tempLoc[0], tempLoc[1])

            player.move(tempLoc, terrain)

    def speedCheck(self):
        if self.player.stat.level > self.currentEnemy.stat.level:
            return True
        elif self.player.stat.level < self.currentEnemy.stat.level:
            return False
        else:
            if random.randint(0, 1) == 0:
                return True
            else:
                return False
    
    def typeAdvantage(self, attackType, defenseType):
        return 1
    

    def damageCalc(self, attack, defense, attackerLevel = 1, defenderLevel = 1):
        attackDefenseRatio = attack/( defense/2)
        levelRatio = attackerLevel/defenderLevel
        adv = self.typeAdvantage(0, 0)
        randomValue = random.randint(90, 110)/100
        return math.ceil(attackDefenseRatio * levelRatio * adv * randomValue)
    

    def damagePlayer(self, damage):
        # print("OUCH: ", damage)
        self.player.stat.health -= damage
    def damageEnemy(self, damage):
        # print("YAH: ", damage)
        self.currentEnemy.stat.health -= damage

    def resolvePlayerMove(self, playerMove):
        if playerMove.moveType == MoveType.ATTACK:
            damage = self.damageCalc(self.player.stat.attack * playerMove.power, self.currentEnemy.getDefense())
            # print("DAMAGE: ", damage)
            #action str
            actionStr = "Player used " + playerMove.name + " for " + str(damage) + " damage"
            self.message_log.append(actionStr)
            self.damageEnemy(damage)
        else:
            # print("Player Defended")
            self.player.shielding = playerMove.power
            actionStr = "Player used " + playerMove.name + " for " + str(playerMove.power) + " shield"
            self.message_log.append(actionStr)
        self.player.lastMoveInit = self.player.initiative
        self.player.initiative += math.ceil(playerMove.speed * self.player.getSpeed())
        print('\n')

    def resolveEnemyMove(self, enemyMove):
        if enemyMove.moveType == MoveType.ATTACK:
            # print("ENEMY ATTACKED")
            
            damage = self.damageCalc(self.currentEnemy.stat.attack * enemyMove.power, self.player.getDefense())
            # print("Enemy DAMAGE: ", damage)

            self.damagePlayer(damage)
            actionStr = "Enemy used " + enemyMove.name + " for " + str(damage) + " damage"
            self.message_log.append(actionStr)
        else:
            # print("Enemy Defended")
            self.currentEnemy.shielding = enemyMove.power
            actionStr = "Enemy used " + enemyMove.name + " for " + str(enemyMove.power) + " shield"
            self.message_log.append(actionStr)
        self.currentEnemy.lastMoveInit = self.currentEnemy.initiative
        self.currentEnemy.initiative += math.ceil(enemyMove.speed * self.currentEnemy.getSpeed())
        
        print('\n')

    def printCombatStatus(self):
        # time.sleep(.3)
        clearScreen()
        tempStr = "Player:"
        tempHealthRatio = self.player.stat.health/self.player.stat.max_health
        tempHealthRatio = round(tempHealthRatio, 2) * 10
        tempStr += "Health Bar[" + "X" * int(tempHealthRatio) +" " * (10 - int(tempHealthRatio)) +  "]\n"
        try:
            percentInit =  math.ceil(10 * (self.combatTime - self.player.lastMoveInit) / (self.player.initiative - self.player.lastMoveInit))
            # print("Percent Init: ", percentInit)
        except Exception as e:
            print(e)
            percentInit = 0
        tempStr += "Action [" + "X" * percentInit + " " * (10 - percentInit)  +"]\n\n"
        # tempStr += "Initiative: " + str(self.player.initiative) + " Last Move " + str(self.player.lastMoveInit) + " Combat Time: " + str(self.combatTime) + "\n\n"
        tempStr += "Enemy:"
        tempHealthRatio = self.currentEnemy.stat.health/self.currentEnemy.stat.max_health
        tempHealthRatio = round(tempHealthRatio, 2) * 10
        tempStr += "Health Bar[" + "X" * int(tempHealthRatio) + " " * (10 - int(tempHealthRatio)) +  "]\n\n"
        for i in self.message_log:
            tempStr += i + "\n"

        print(tempStr)
        # print('\n')

    def combat(self):
        clearScreen()
        self.combatTime = 0
        printCnt = 0
        while True:
            self.printCombatStatus()

            printCnt +=1
            if self.combatTime == self.player.initiative:
                playerMove = self.player.getMove()
                self.resolvePlayerMove(playerMove)
                # print("Combat Time: ", combatTime, "Player Initiative: ", self.player.initiative, "Enemy Initiative: ", self.currentEnemy.initiative)            
                # self.printCombatStatus()
                # time.sleep(.5)
            if self.combatTime == self.currentEnemy.initiative:
                enemyMove = self.currentEnemy.move()
                self.resolveEnemyMove(enemyMove)
                
                # print("Combat Time: ", combatTime, "Player Initiative: ", self.player.initiative, "Enemy Initiative: ", self.currentEnemy.initiative)  
                # time.sleep(.5)
            time.sleep(.01)


            # clearScreen()


            if self.player.stat.health <= 0:
                print("You lose")
                break
            if self.currentEnemy.stat.health <= 0:
                print("You win")
                break
            self.combatTime += 1
    
if __name__ == '__main__':
    # Create a new instance of the main window
    # print("\0s33c")
    map = Map()
    print(map)
    printColoredString(map)
    print("\n\n")
    (printColoredString(map.getSurrounding(0,0)))

    player = Player(0, 0)
    print("\033c")

    game = Game()
    print(game.currentEnemy.stat.health)
    game.combat()
    # game.moveOnMap()
    # game.moveOnMap()
