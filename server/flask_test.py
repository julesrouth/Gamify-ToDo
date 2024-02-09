from flask import Flask
from markupsafe import escape
from flask import url_for
from Model import Authtoken, User
from DAO import AuthtokenDAO, UserDAO, create_connection

app = Flask(__name__)

@app.route("/")
def hello_world():
    return "<p>Hello, World!</p>"

@app.route('/new_user/<username>/<password>/<email>/<first_name>/<last_name>')
def new_user(username, password, email, first_name, last_name):
    user = User(username, password, email, first_name, last_name)
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return str(e)
    
    with conn:
        user_dao = UserDAO(conn)
        try:
            user_dao.insert(user)
        except Exception as e:
            return str(e)

    return f'Created new user with username "{user.username}"'

@app.route('/login/<username>/<password>')
def login(username, password):
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return str(e)
    
    with conn:
        user_dao = UserDAO(conn)
        try:
            result = user_dao.find_by_username(username)
        except Exception as e:
            return str(e)
        if result == None:
            return 'User not found'
        
        if result.password != password:
            return 'Incorrect password'
        else:
            authtoken = Authtoken("abcd", username)
            auth_dao = AuthtokenDAO(conn)
            try:
                auth_dao.insert(authtoken)
            except Exception as e:
                return 'Error creating authtoken'
            
            return f'Logged in as {username} with authtoken {authtoken.token}'