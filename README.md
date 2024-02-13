# Gamify-ToDo

# Architecture

## Users
### Attributes
- username VARCHAR(255)
- password VARCHAR(255)
- email VARCHAR(255)
- firstName VARCHAR(255)
- lastName VARCHAR(255)
### Methods
- login(username, password) returns User
- register(username, password, email, firstName, lastName) returns Success
- updateUser(username, password, email, firstName, lastName) returns User

## Authtokens
- token VARCHAR(255)
- username VARCHAR(255)

## Tasks
### Attributes
- taskId INT
- taskName VARCHAR(255)
- description VARCHAR(255)
- difficulty ENUM('easy', 'medium', 'hard')
- type ENUM('daily', 'task', 'weekly')
- username VARCHAR(255)
- completed BOOLEAN
### Methods
- getTasksForUser(username) returns Task[]
- getTask(taskId) returns Task
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
- username VARCHAR(255)
- characterName VARCHAR(255)
- level INT
- experience INT
- gold INT

## Enemies

## Items



