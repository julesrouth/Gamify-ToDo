# Gamify-ToDo

# Architecture

## User
### Attributes
- id uuid
- username VARCHAR(255)
- password VARCHAR(255)
- email VARCHAR(255)
- firstName VARCHAR(255)
- lastName VARCHAR(255)
### Methods
- login(username, password) returns User, Authtoken
- register(User) returns User, Authtoken
- updateUser(User, Authtoken) returns User
- deleteUser(Authtoken) return Success

## Authtoken
### Attributes
- token VARCHAR(255)
- userId 

## Task
### Attributes
- taskId INT
- taskName VARCHAR(255)
- description VARCHAR(255)
- dueDate VARCHAR(255) (with time)
- difficulty ENUM('easy', 'medium', 'hard')
- type ENUM('daily', 'task', 'weekly')
- userId VARCHAR(255) FOREIGN KEY references User(id)
- completed BOOLEAN
### Methods
- listTasksForUser(Authtoken) returns Task[]
- getTask(taskId, Authtoken) returns Task
- createTask(Task, Authtoken) returns Task
- deleteTask(taskId, Authtoken) returns Success
- updateTask(Task, Authtoken) returns Task //purely for editing tasks
- checkTask(taskId, bool completed, Authtoken) return success //for finishing and unchecking tasks

## Difficulty
### Attributes
- difficulty ENUM('easy', 'medium', 'hard')
- type ENUM('daily', 'weekly', 'to-do')
- gold INT
### Methods
- getGold(difficulty, type) returns int gold

## Player
### Attributes
- userId VARCHAR(255) FOREIGN KEY references User(id)
- characterName VARCHAR(255)
- level INT DEFAULT 0
- experience INT DEFAULT 0
- gold INT DEFAULT 0
### Methods
- getPlayer(Authtoken) returns Player
- createPlayer(Authtoken, characterName) returns Player
- updateExperience(Authtoken, experience) returns int experience, int level
- updateGold(Authtoken, gold) returns int gold
- updateName(Authtoken, name) returns string name

## Enemy
### Attributes
- enemyName VARCHAR(255)
- enemyType VARCHAR(255)
- levelType INT
- hitPoints INT DEFAULT 0
- experiencePoints INT
### Methods
- getRandEnemy(level) return Enemy

## PlayerItem
### Attributes
- itemId INT FOREIGN KEY references Store(itemID)
- userId VARCHAR(255) FOREIGN KEY references Player(userID)
### Methods
- removePlayerItem(itemId, Authtoken) return success

## StoreItem
### Attributes
- itemId INT 
- effects VARCHAR(255)
- cost INT
### Methods
- listStoreItems() return [] StoreItems
- getPlayerItem(itemId) return StoreItem
- listPlayerItems(userId) return [] StoreItem

## Stat
### Attributes
- userId PRIMARY KEY
- health INT
- attack INT
- defence INT
### Methods
- getPlayerStats(AuthToken)
- updatePlayerStats(Stat, Authtoken)

![alt text](https://github.com/julesrouth/Gamify-ToDo/blob/main/images/Tables.png)
![alt text](https://github.com/julesrouth/Gamify-ToDo/blob/main/images/Flow.png)
