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
- login(username, password) returns User
- register(username, password, email, firstName, lastName) returns Success
- updateUser(id, username, password, email, firstName, lastName) returns User
- deleteUser(id)

## Authtokens
- token VARCHAR(255)
- username VARCHAR(255)

## Tasks
### Attributes
- taskId INT
- taskName VARCHAR(255)
- description VARCHAR(255)
- dueDate VARCHAR(255)
- difficulty ENUM('easy', 'medium', 'hard')
- type ENUM('daily', 'task', 'weekly')
- userId VARCHAR(255) FOREIGN KEY references User(id)
- completed BOOLEAN
### Methods
- listTasksForUser(userId) returns Task[]
- getTask(taskId) returns Task
- createTask(taskName, description, type, username, finished) returns Task
- deleteTask(taskId) returns Success
- updateTask(taskId, taskName, description, type, username, finished) returns Task

## Difficulties
### Attributes
- difficulty ENUM('easy', 'medium', 'hard')
- type ENUM('daily', 'weekly', 'to-do')
- experiencePoints INT
### Methods
- getExperience(difficulty, type) returns experiencePoints

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
- updateExperience(experience) returns int experience
- updateGold(gold) returns int gold
- updateName(name) returns string name

## Enemies


## Items



