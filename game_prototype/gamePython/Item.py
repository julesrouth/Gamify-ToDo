import Stat



class ItemStat(Stat.Stat):
    def __init__(self, health = 0, attack = 0, defense=0, speed=0, level=0, mana = 0, features = ""):
        super().__init__(health, attack, defense, speed, level)
        self.mana = mana
        self.features = features
        


#Move Class
#  Power (double): How strong a move is
#  moveType (MoveType): What type of move it is 
#  effect: Optional if a move has an additional effect

            


class Item():
    name = "Null"
    stats = 0
    attributes = ""
    def __init__(self, name, stats, attributes = ""):
        self.stats = stats
        self.attributes = attributes
        self.name = name
    
    def __str__(self) -> str:
        return str(self.name)

class ShopItem(Item):
    price = 0
    def __init__(self, name, stats, price, attributes = ""):
        self.price = price
        super().__init__(name, stats, attributes)

if __name__ == "__main__":
    print("Testing Item")
    item = Item("Sword", ItemStat(attack = 10))
    print(item)
    print(item.stats)
    print(item.attributes)
    print("Testing ShopItem")
    shopItem = ShopItem("Sword", ItemStat(attack = 10), 100)
    print(shopItem)
    print(shopItem.price)
    print(shopItem.stats)
    print(shopItem.attributes)
    print("Testing ItemStat")
    itemStat = ItemStat(attack = 10)
    print(itemStat)
    print(itemStat.attack)
    print(itemStat.health)
    print(itemStat.defense)
    print(itemStat.speed)
    print(itemStat.level)
    print(itemStat.mana)
    print(itemStat.features)
    print("Testing ItemStat")
    itemStat = ItemStat(attack = 10, features = "Fire")
    print(itemStat)
    print(itemStat.attack)
    print(itemStat.health)
    print(itemStat.defense)
    print(itemStat.speed)
    print(itemStat.level)
    print(itemStat.mana)
    print(itemStat.features)