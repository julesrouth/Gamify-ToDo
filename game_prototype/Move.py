import GameEnum

class Move():
    power = 0
    moveType = 0
    effect = ""
    name = "Null"
    def __init__(self, moveType, power,speed,  effect = 0, name = "Null"):
        self.moveType = moveType
        self.power = power
        self.speed = speed
        self.effect = effect
        self.name = name
        if moveType == GameEnum.MoveType.SPELL:
            self.manacost = int(effect.split(" ")[0])

    def __str__(self) -> str:
        return self.name


if __name__ == "__main__":
    move = Move(GameEnum.MoveType.ATTACK, 10, 100)