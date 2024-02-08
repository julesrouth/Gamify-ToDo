import time
import random
from colorama import Fore, Back, Style
from enum import Enum
import copy
import math

from collections import deque
from queue import Queue

import GameEnum

import Global
import Map
# class syntax
import Player
import Game
import Shop
    
if __name__ == '__main__':
    print("YEPPERS")

    # Create a new instance of the main window
    # print("\0s33c")
    map = Map.Map()
    print(map)
    Global.printColoredString(map)
    print("\n\n")
    (Global.printColoredString(map.getSurrounding(0,0)))

    player = Player.Player(0, 0)
    # print("\033c")

    game = Game.Game()
    # print(game.currentEnemy.stat.health)
    # game.combat()
    print("ITEMS 1")
    print(game.shop.printItems())
    Shop.addItemsShopTest(game.shop)
    print(game.shop.printItems())
    print(game.shop.items)
    #set color
    print(Fore.WHITE)

    game.shop.printShop()
    game.userInputShop()

    print("INVENTORY")
    print(game.player.printInventory())
    print(game.player.getAttack())
    # print(game.player.printInventory())
    # item = Item("Sword", Stat(attack = 10))
    # clearScreen()
    game.player.status.addStatus("rage", 125)
    game.combat()
    # game.moveOnMap()
    # game.moveOnMap()
