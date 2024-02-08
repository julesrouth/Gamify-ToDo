
import math

class Statuses():
    rage = 0
    poison = 0
    burn = 0
    frozen = 0
    frightened = 0
    confused = 0
    paralyzed = 0
    clear = []
    
    def clearStatuses(self):
        self.rage = 0
        self.poison = 0
        self.burn = 0
        self.frozen = 0
        self.frightened = 0
        self.confused = 0
        self.paralyzed = 0


    def addStatus(self, status, length):
        self.setStatus(status, 1)
        self.clear.append([status, length])
        
    def setStatus(self, status, value):
        if status == "rage":
            self.rage = value
        elif status == "poison":
            self.poison = value
        elif status == "burn":
            self.burn = value
        elif status == "frozen":
            self.frozen = value
        elif status == "frightened":
            self.frightened = value
        elif status == "confused":
            self.confused = value
        elif status == "paralyzed":
            self.paralyzed = value
        else:
            print("Invalid status")


    def calculateAttack(self, attack):
        
        if self.rage:
            attack = math.ceil(attack * 1.3)
        if self.frightened:
            attack = math.ceil(attack * .75)
        
        return attack

    def checkStatus(self, time):
        tempList = []
        for i in self.clear:
            if i[1] <= time:
                self.clear.remove(i)
                self.setStatus(i[0], 0)
                tempList.append("Cleared: " + str(i[0]) + "\n")
                 
        return tempList
    

if __name__ == "__main__":
    status = Statuses()
    status.addStatus("rage", 10)
    status.checkStatus(5)
    status.checkStatus(15)