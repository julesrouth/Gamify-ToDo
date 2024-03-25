from enum import Enum as enum

NAME_MAX = 10
NUMBER_MAX = NAME_MAX
CHAR_LINE = "-"

class Stance(enum):
    ATTACK = 1
    DEFEND = 2
    AGGRO = 3
    WATER = 4
    FIRE = 5

    def __int__(self):
        return self.value

class StanceArray():
    stance = []
    def __init__(self, attack = 1, defend = 1, aggro = 1, water = 1, fire = 1):
        self.stance = [attack, defend, aggro, water, fire]
    def strWeakness(self):
        temp = ""
        for i in range(len(self.stance)):
            if self.stance[i] < 1:
                temp += (Stance(i+1))
        return temp
    
    def strStrength(self):
        temp = ""
        for i in range(len(self.stance)):
            if self.stance[i] > 1:
                temp += (Stance(i+1))
        return temp
    
    def __getitem__(self, key):
        return self.stance[key]
    def __str__(self):
        tempStr = ""
        for i in self.stance:
            tempStr += str(i) + ' ' * (NUMBER_MAX - len(str(i)) )+ "|"
        return tempStr
    

class StanceGrid():
    nameList = ["Attack", "Defend", "Aggro", "Water", "Fire"]
    grid = []
    def __init__(self):
        self.grid = []
        #Attack
        self.grid.append(StanceArray(defend = .5, aggro = 1.5))

        #Defend
        self.grid.append(StanceArray(attack= 1.5, aggro = .5))
        #Aggro
        self.grid.append(StanceArray(attack = .5, defend = 1.5))
        #Water
        self.grid.append(StanceArray(water = .5, fire = 1.5))
        #Fire
        self.grid.append(StanceArray(fire = .5, water =.5))
    def getStance(self, stance1, stance2):
        return self.grid[int(stance1)][int(stance2)]
    

    def strGrid(self):
        tempStr = ""
        j = 0
        tempStr += " " * (NAME_MAX + 2) + CHAR_LINE * ((NAME_MAX + 1) * len(self.nameList) + 1)  + "\n"
        tempStr += " " * (NAME_MAX + 1) + " |"
        for i in self.nameList:
            tempStr += i + " " * (NAME_MAX - len(i)) + "|"
        tempStr += "\n"
        tempStr += CHAR_LINE *((NAME_MAX + 1) * (len(self.nameList) + 1 ) + 2)  + "\n"
        for i in self.grid:
# self.nameList[j] 
            tempNameStr ="| " +  self.nameList[j] + " " * (NAME_MAX - len(self.nameList[j])) + "|"
            tempTotalStr =  tempNameStr + str(i) + "\n"
        
            tempLineStr = CHAR_LINE * (len(tempTotalStr) - 1) + "\n"
            j+=1
            tempStr += tempTotalStr + tempLineStr
        return tempStr



if __name__ == "__main__":
    print("Testing Stance")
    stance = StanceArray()
    print(stance.stance)
    print(stance.strWeakness())
    print(stance.strStrength())
    stance = StanceGrid()
    print("Testing StanceGrid")
    print("GRID: ", stance.grid)
    print("VALUE: ", stance.getStance(0, 1))
    print(stance.strGrid())

    pass