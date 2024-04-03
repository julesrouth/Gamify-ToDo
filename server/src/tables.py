import random
from Model import StoreItem


gold_table = {"easy": {"daily": 10, "weekly": 12, "task": 15}, 
              "medium": {"daily": 20, "weekly": 25, "task": 30}, 
              "hard": {"daily": 60, "weekly": 75, "task": 100}}

experience_table = [0, 20, 30, 50, 70, 
                    100, 130, 170, 220, 280,
                    350, 430, 520, 620, 730,
                    850, 980, 1120, 1270, 1430]

level_up_table = [0, 30, 100, 300, 800, 
                  1200, 1800, 2500, 3500, 5000, 
                  7500, 10000, 15000, 20000, 30000, 
                  50000, 75000, 100000, 150000, 200000]

store_table = [StoreItem("potion", "It heals you in battle", 50),
                StoreItem("sword", "It gives you more attack power", 100),
                StoreItem("shield", "It gives you more defense", 100),
                StoreItem("armor", "It gives you more health", 100)]
                  
def get_gold(difficulty, task_type):
    return gold_table[difficulty][task_type]

def get_experience(level):
    return experience_table[level] + level * random.randint(-5, 15)

def get_level_up_experience(level):
    return level_up_table[level]

def get_store_table():
    return store_table