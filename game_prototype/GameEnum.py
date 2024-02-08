from enum import Enum
import math

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


class MoveType(enumerate):
    ATTACK = 0
    DEFEND = 1
    SPELL = 2



