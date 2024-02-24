# Gamify-ToDo

# Architecture

## Users
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

## Authtokens
### Attributes
- token VARCHAR(255)
- userId 

## Tasks
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

## Difficulties
### Attributes
- difficulty ENUM('easy', 'medium', 'hard')
- type ENUM('daily', 'weekly', 'to-do')
- gold INT
### Methods
- getGold(difficulty, type) returns int gold

## Players
### Attributes
- userId VARCHAR(255) FOREIGN KEY references User(id)
- characterName VARCHAR(255)
- level INT DEFAULT 0
- experience INT DEFAULT 0
- gold INT DEFAULT 0
### Methods
- getPlayer(userId) returns Player
- createPlayer(userId, characterName) returns Player
- updateExperience(experience) returns int experience, int level
- updateGold(gold) returns int gold
- updateName(name) returns string name
- getItems(userID)

## Enemies
### Attributes
- enemyName VARCHAR(255)
- enemyType VARCHAR(255)
- levelType INT
- hitPoints INT DEFAULT 0
- experiencePoints INT
### Methods
- getRandEnemy(level) return Enemy

## Player Items
### Attributes
- itemID INT FOREIGN KEY references Store(itemID)
- userId VARCHAR(255) FOREIGN KEY references Player(userID)
### Methods
- getPlayerItem(itemID, userID) return Player Item
- listPlayerItems(userID) return list of Player Items
- removePlayerItem(itemID, userID) return success

## Store Items
### Attributes
- itemID INT 
- effects VARCHAR(255)
- cost INT
### Methods
- listStoreItems() return list of Store Items
