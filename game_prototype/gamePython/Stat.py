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
        self.mana = level * 3
        self.speed = speed
        self.max_health = health
        self.max_mana = self.mana

if __name__ == "__main__":
    print("Testing Stat")
    stat = Stat()