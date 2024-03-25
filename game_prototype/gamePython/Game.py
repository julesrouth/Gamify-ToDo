import random
import copy
import Shop
import Player
import Map
import Enemy
import GameEnum
from collections import deque
import time
import Global
import math
import Stances
LOG_LENGTH = 4
class Game():
    stances = Stances.StanceGrid()
    message_log =  deque(maxlen=LOG_LENGTH)
    combatTime = 0

    player = 0
    map = 0
    currentEnemy = 0
    def __init__(self):
        self.player = Player.Player(0, 0)
        self.map = Map.Map()
        self.currentEnemy = Enemy.Enemy()
        self.shop = Shop.Shop()
        for i in range(LOG_LENGTH):
            self.message_log.append("")
    
    def userInputShop(self):
        while True:
            print("Input Item to buy, you have " + str(self.player.gold) + " gold")
            inputPlayer = input()
            inputPlayer = inputPlayer.lower()
            for i in self.shop.items:
                if i.name.lower() == inputPlayer:
                    if self.player.gold < i.price:
                        print("Not enough gold")
                        return
                    self.player.gold -= i.price
                    self.player.addItem(i)
                    self.shop.removeItem(i)
                    print("Item ", i.name, " bought for ", i.price)
                    return
            print("Invalid item")
            time.sleep(.5)



    def moveOnMap(self):
            (Global.printColoredString(map.getSurrounding(self.player.location[0], self.player.location[1])))
            print("\n\n")
            print("Input gameElements.Direction\n")
            inputDir = input()
            if inputDir == "w":
                inputDir = GameEnum.Direction.UP
            elif inputDir == "s":
                inputDir = GameEnum.Direction.DOWN
            elif inputDir == "a":
                inputDir = GameEnum.Direction.LEFT
            elif inputDir == "d":
                inputDir = GameEnum.Direction.RIGHT
            else:
                print("Invalid input")
                return
            print(inputDir)
            #try to move
            print("\033c")

            tempLoc = self.player.moveLoc(inputDir, map)
            if not tempLoc:
                print("Invalid move")
                return 
            #see if its valid
            terrain = map.move(tempLoc[0], tempLoc[1])

            self.player.move(tempLoc, terrain)

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
    

    def damageCalc(self, attack, defense, adv = 1, attackerLevel = 1, defenderLevel = 1):
        attackDefenseRatio = attack/( defense/2)
        levelRatio = attackerLevel/defenderLevel
        # adv = self.typeAdvantage(0, 0)
        randomValue = random.randint(90, 110)/100
        return math.ceil(attackDefenseRatio * levelRatio * adv * randomValue)
    

    def damagePlayer(self, damage):
        # print("OUCH: ", damage)
        self.player.stat.health -= damage
    def damageEnemy(self, damage):
        # print("YAH: ", damage)
        self.currentEnemy.stat.health -= damage


    def getStanceValue(self , player = 1):
        print("Player: ", self.player.stance)
        print("STR: ", int(self.player.stance))
        print("Enemy: ", self.currentEnemy.stance)
        if player:
            return self.stances.getStance(self.player.stance, self.currentEnemy.stance)
        else:
            return self.stances.getStance(self.currentEnemy.stance, self.player.stance)
    def resolvePlayerMove(self, playerMove):
        if playerMove.moveType == GameEnum.MoveType.ATTACK:
            damage = self.damageCalc(self.player.getAttack() * playerMove.power, self.currentEnemy.getDefense(), self.getStanceValue())
            # print("DAMAGE: ", damage)
            #action str
            actionStr = "Player used " + playerMove.name + " for " + str(damage) + " damage"
            self.message_log.append(actionStr)
            self.damageEnemy(damage)
        elif playerMove.moveType == GameEnum.MoveType.DEFEND:
            # print("Player Defended")
            self.player.shielding = playerMove.power
            actionStr = "Player used " + playerMove.name + " for " + str(playerMove.power) + " shield"
            self.message_log.append(actionStr)
        elif playerMove.moveType == GameEnum.MoveType.SPELL:
            if not self.spellDoSomething(playerMove):
                print("Invalid Spell\n")
                self.player.initiative += 2
                time.sleep(1)
                return
        elif playerMove.moveType == GameEnum.MoveType.USEITEM:
            self.message_log.append("Player used " + playerMove.name)
            print(self.player.printInventory())
            print("Input item to use")
            inputPlayer = input()
            for i in self.player.inventory:
                print(i)
                if i.name.lower() == inputPlayer:
                    
                    self.message_log.append("Using item: " + i.name)
                    if self.useItem(i):
                        return
            self.message_log.append("Invalid item")
            return
        self.player.lastMoveInit = self.player.initiative
        self.player.initiative += math.ceil(playerMove.speed * self.player.getSpeed())
        print('\n')

    def useItem(self, item):
        self.message_log.append("The item: " + item.name)
        if item.name.lower() == "potion":
            self.message_log.append("Player used " + item.name + " for " + str(item.stats.health) + " health")
            self.player.stat.health += item.stats.health
            if self.player.stat.health > self.player.stat.max_health:
                self.player.stat.health = self.player.stat.max_health
            self.player.inventory.remove(item)
            return True
        else:
            self.message_log.append("Invalid item" + " " + item.name)



    def spellDoSomething(self, spell):
        if spell.name == "fireball":
            if self.player.stat.mana < spell.manacost:

                self.message_log.append("Not enough mana for fireball, you need "+ str(spell.manacost) + " mana" + " you have " + str(self.player.stat.mana) + " mana")
                return False
            self.player.stat.mana -= spell.manacost
            damage = self.damageCalc(self.player.getAttack() * spell.power, self.currentEnemy.getDefense())
            self.damageEnemy(damage)
            actionStr = "Player used " + spell.name + " for " + str(damage) + " damage"
            self.message_log.append(actionStr)

        else:
            self.message_log.append("Invalid spell")
            return False
        return True
    def resolveEnemyMove(self, enemyMove):
        if enemyMove.moveType == GameEnum.MoveType.ATTACK:
            # print("ENEMY ATTACKED")
            
            damage = self.damageCalc(self.currentEnemy.getAttack() * enemyMove.power, self.player.getDefense(), self.getStanceValue(0))
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
        Global.clearScreen()
        tempStr = "Player:"
        tempHealthRatio = self.player.stat.health/self.player.stat.max_health
        tempHealthRatio = round(tempHealthRatio, 2) * 10
        tempStr += "Health Bar[" + "X" * int(tempHealthRatio) +" " * (10 - int(tempHealthRatio)) +  "]\n"
        try:
            percentInitPlayer =  math.ceil(10 * (self.combatTime - self.player.lastMoveInit) / (self.player.initiative - self.player.lastMoveInit))
            # print("Percent Init: ", percentInit)
        except Exception as e:
            print(e)
            percentInitPlayer = 0
        tempStr += "Action [" + "X" * percentInitPlayer + " " * (10 - percentInitPlayer)  +"]\n\n"
        # tempStr += "Initiative: " + str(self.player.initiative) + " Last Move " + str(self.player.lastMoveInit) + " Combat Time: " + str(self.combatTime) + "\n\n"
        tempStr += "Enemy:"
        tempHealthRatio = self.currentEnemy.stat.health/self.currentEnemy.stat.max_health
        tempHealthRatio = round(tempHealthRatio, 2) * 10
        tempStr += "Health Bar[" + "X" * int(tempHealthRatio) + " " * (10 - int(tempHealthRatio)) +  "]\n"
        try:
            percentInitEnemy =  math.ceil(10 * (self.combatTime - self.currentEnemy.lastMoveInit) / (self.currentEnemy.initiative - self.currentEnemy.lastMoveInit))
            # print("Percent Init: ", percentInit)
        except Exception as e:
            print(e)
            percentInitEnemy = 0
        tempStr += "Action [" + "X" * percentInitEnemy + " " * (10 - percentInitEnemy)  +"]\n\n"

        for i in self.message_log:
            tempStr += i + "\n"

        print(tempStr)
        # print('\n')

    def combat(self):
        Global.clearScreen()
        self.combatTime = 0
        printCnt = 0
        while True:
            self.printCombatStatus()
            tempStatus = self.player.status.checkStatus(self.combatTime)
            if(tempStatus):
                for i in tempStatus:
                    self.message_log.append(i)
                
            printCnt +=1
            if self.combatTime >= self.player.initiative:
                playerMove = self.player.getMove()
                self.resolvePlayerMove(playerMove)
                # print("Combat Time: ", combatTime, "Player Initiative: ", self.player.initiative, "Enemy Initiative: ", self.currentEnemy.initiative)            
                # self.printCombatStatus()
                # time.sleep(.5)
            if self.combatTime >= self.currentEnemy.initiative:
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