from flask import Flask
from markupsafe import escape
from flask import jsonify, request
from Model import Authtoken, User
from DAO import AuthtokenDAO, UserDAO, create_connection
import random
import string

app = Flask(__name__)

@app.route("/")
def hello_world():
    return "<p>Hello, World!</p>"

@app.route('/register', methods=['POST'])
def register():
    try:
        json_data = request.get_json()
        user = User(json_data['username'],
                    json_data['password'],
                    json_data['email'],
                    json_data['firstname'],
                    json_data['lastname'])
    except Exception as e:
        return str(e)

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

    return "User created"

@app.route('/login', methods=['POST'])
def login():
    try:
        json_data = request.get_json()
        username = json_data['username']
        password = json_data['password']
    except Exception as e:
        return str(e)

    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return str(e)
    
    with conn:
        user_dao = UserDAO(conn)
        try:
            user = user_dao.find_by_username(username)
        except Exception as e:
            return str(e)
        if user == None:
            return 'User not found'
        
        if user.password != password:
            return 'Incorrect password'
        else:
            token = ''.join(random.choices(string.ascii_lowercase +
                             string.digits, k=12))
            authtoken = Authtoken(token, username)
            auth_dao = AuthtokenDAO(conn)
            try:
                auth_dao.insert(authtoken)
            except Exception as e:
                return 'Error creating authtoken'
            
            return jsonify(authtoken.__dict__)
        
@app.route('/updateUser', methods=['POST'])
def updateUser():
    try:
        json_data = request.get_json()
        user = User(json_data['username'],
                    json_data['password'],
                    json_data['email'],
                    json_data['firstname'],
                    json_data['lastname'])
    except Exception as e:
        return str(e)

    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return str(e)
    
    with conn:
        user_dao = UserDAO(conn)
        try:
            user_dao.delete(user)
            user_dao.insert(user)
        except Exception as e:
            return str(e)

    return jsonify(user.__dict__)