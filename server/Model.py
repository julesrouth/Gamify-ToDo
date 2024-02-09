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