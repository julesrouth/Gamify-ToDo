class User:
    def __init__(self, uuid, username, password, email, firstName, lastName):
        self.uuid = uuid
        self.username = username
        self.password = password
        self.email = email
        self.firstName = firstName
        self.lastName = lastName

class Authtoken:
    def __init__(self, token, userId):
        self.token = token
        self.userId = userId

class LoginResponse:
    def __init__(self, success, message, authtoken, user):
        self.success = success
        self.message = message
        if authtoken is not None:
            self.authtoken = authtoken.__dict__
        else:
            self.authtoken = None
        if user is not None:
            self.user = user.__dict__
        else:
            self.user = None

class RegisterResponse:
    def __init__(self, success, message, authtoken, user, player):
        self.success = success
        self.message = message
        if authtoken is not None:
            self.authtoken = authtoken.__dict__
        else:
            self.authtoken = None
        if user is not None:
            self.user = user.__dict__
        else:
            self.user = None
        if player is not None:
            self.player = player.__dict__
        else:
            self.player = None

class Task:
    def __init__(self, taskId, taskName, description, dueDate, difficulty, type, userId, completed):
        self.taskId = taskId
        self.taskName = taskName
        self.description = description
        self.dueDate = dueDate
        self.difficulty = difficulty
        self.type = type
        self.userId = userId
        self.completed = completed

class Player:
    def __init__(self, characterName, userId, level, experience, gold):
        self.characterName = characterName
        self.userId = userId
        self.level = level
        self.experience = experience
        self.gold = gold

class Enemy:
    def __init__(self, enemyName, enemyType, enemylevel, hitPoints, experiencePoints):
        self.enemyName = enemyName
        self.enemyType = enemyType
        self.enemylevel = enemylevel
        self.hitPoints = hitPoints

class PlayerItem:
    def __init__(self, itemName, userId):
        self.itemName = itemName
        self.userId = userId

class StoreItem:
    def __init__(self, itemName, effects, cost):
        self.itemName = itemName
        self.effects = effects
        self.cost = cost

class Stat:
    def __init__(self, userId, attack, defense, level, speed, maxHealth, maxMana):
        self.userId = userId
        self.attack = attack
        self.defense = defense
        self.level = level
        self.speed = speed
        self.maxHealth = maxHealth
        self.maxMana = maxMana