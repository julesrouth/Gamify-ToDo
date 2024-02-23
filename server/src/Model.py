class User:
    def __init__(self, username, password, email, firstName, lastName):
        self.username = username
        self.password = password
        self.email = email
        self.firstName = firstName
        self.lastName = lastName

class Authtoken:
    def __init__(self, token, username):
        self.token = token
        self.username = username

class LoginResponse:
    def __init__(self, success, message, authtoken, user):
        self.success = success
        self.message = message
        self.authtoken = authtoken.__dict__
        self.user = user.__dict__

class Task:
    def __init__(self, taskId, taskName, description, dueDate, difficulty, type, username, completed):
        self.taskId = taskId
        self.taskName = taskName
        self.description = description
        self.dueDate = dueDate
        self.difficulty = difficulty
        self.type = type
        self.username = username
        self.completed = completed