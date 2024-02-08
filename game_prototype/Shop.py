import Global
import Item


class Shop():
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
    def printShop(self):
        Global.clearScreen()
        tempStr = ""    
        tempStr += '-'*Global.LINELENGTH + "\n"
        tempStr += "|  Shop" + " " * (Global.LINELENGTH - 8) + "|\n"
        tempStr += '-'*Global.LINELENGTH + "\n"
        for i in self.items:
            intLen = len(str(i.price))
            tempStr += "|  " + str(i)
            tempStr += " " * (Global.LINELENGTH - len(str(i)) - 7 - intLen) + str(i.price) + " "*3 + "|\n"
        tempStr += '-'*Global.LINELENGTH + "\n"
        print(tempStr)


def addItemsShopTest(shop):
    shop.addItem(Item.ShopItem("Sword", Item.ItemStat(attack = 10), 100))
    shop.addItem(Item.ShopItem("Shield", Item.ItemStat(defense = 10), 100))
    shop.addItem(Item.ShopItem("Potion", Item.ItemStat(health = 10), 100))

if __name__ == '__main__':
    print("Testing Shop")
    shop = Shop()
    addItemsShopTest(shop)
    print(shop.printItems())
    print(shop.items)
    shop.removeItem(shop.items[0])
    print(shop.items)