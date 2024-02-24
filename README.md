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
- register(username, password, email, firstName, lastName) returns User, Authtoken
- updateUser(id, username, password, email, firstName, lastName, authtoken) returns User
- deleteUser(id, authtoken) return Success

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
- listTasksForUser(authtoken) returns Task[]
- getTask(taskId, authtoken) returns Task
- createTask(taskName, description, dueDate, difficulty, type, completed, authtoken) returns Task
- deleteTask(taskId, authtoken) returns Success
- updateTask(taskId, taskName, description, dueDate, difficulty, type, authtoken) returns Task //purely for editing tasks
- checkTask(taskId, bool completed, authtoken) return success //for finishing and unchecking tasks

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
- getPlayer(authtoken) returns Player
- createPlayer(authtoken, characterName) returns Player
- updateExperience(authtoken, experience) returns int experience, int level
- updateGold(authtoken, gold) returns int gold
- updateName(authtoken, name) returns string name

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
- itemID INT FOREIGN KEY references Store(itemID)
- userId VARCHAR(255) FOREIGN KEY references Player(userID)
### Methods
- getPlayerItem(itemID, authtoken) return PlayerItems
- listPlayerItems(authtoken) return []PlayerItems
- removePlayerItem(itemID, authtoken) return success

## StoreItem
### Attributes
- itemID INT 
- effects VARCHAR(255)
- cost INT
### Methods
- listStoreItems() return [] StoreItems

![alt text](https://github.com/julesrouth/Gamify-ToDo/blob/main/images/Tables.png)
![alt text](https://github.com/julesrouth/Gamify-ToDo/blob/main/images/Flow.png)
