class User:
    def __init__(self, uuid, username, password, email, firstName, lastName):
        self.uuid = uuid
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
        if authtoken is not None:
            self.authtoken = authtoken.__dict__
        else:
            self.authtoken = None
        if user is not None:
            self.user = user.__dict__
        else:
            self.user = None

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