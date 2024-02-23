from flask import Flask
from markupsafe import escape
from flask import jsonify, request
from Model import Authtoken, User, Task, LoginResponse
from DAO import AuthtokenDAO, UserDAO, TaskDAO, create_connection
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
        return jsonify({'success': False, 'message': str(e)})

    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({'success': False, 'message': str(e)})
    
    with conn:
        user_dao = UserDAO(conn)
        try:
            user_dao.insert(user)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
            
    return jsonify({'success': True, 'message': 'User created'})

@app.route('/login', methods=['POST'])
def login():
    try:
        json_data = request.get_json()
        username = json_data['username']
        password = json_data['password']
    except Exception as e:
        login_response = LoginResponse(False, str(e), None, None)
        return jsonify(login_response.__dict__)

    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        login_response = LoginResponse(False, str(e), None, None)
        return jsonify(login_response.__dict__)
    
    with conn:
        user_dao = UserDAO(conn)
        try:
            user = user_dao.find_by_username(username)
        except Exception as e:
            login_response = LoginResponse(False, str(e), None, None)
            return jsonify(login_response.__dict__)
        
        if user == None:
            login_response = LoginResponse(False, 'User not found', None, None)
            return jsonify(login_response.__dict__)
        
        if user.password != password:
            login_response = LoginResponse(False, 'Incorrect password', None, None)
            return jsonify(login_response.__dict__)
        else:
            token = ''.join(random.choices(string.ascii_lowercase +
                             string.digits, k=12))
            authtoken = Authtoken(token, username)
            auth_dao = AuthtokenDAO(conn)
            try:
                auth_dao.insert(authtoken)
            except Exception as e:
                login_response = LoginResponse(False, str(e), None, None)
                return jsonify(login_response.__dict__)

            login_response = LoginResponse(True, "Login successful", authtoken, user)
            return jsonify(login_response.__dict__)
        
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

@app.route('/getTask/<taskId>', methods=['GET'])
def getTask(taskId):
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return str(e)
    
    with conn:
        task_dao = TaskDAO(conn)
        try:
            task = task_dao.find_by_taskId(taskId)
        except Exception as e:
            return str(e)
        
    if task == None:
        return 'Task not found'
    else:
        return jsonify(task.__dict__)
    
@app.route('/getTasksForUser/<username>', methods=['GET'])
def getTasksForUser(username):
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return str(e)
    
    with conn:
        task_dao = TaskDAO(conn)
        try:
            tasks = task_dao.find_by_username(username)
        except Exception as e:
            return str(e)
        
    if tasks == None:
        return 'No tasks found'
    else:
        return jsonify([task.__dict__ for task in tasks])
    
@app.route('/insertTask', methods=['POST'])
def insertTask():
    try:
        json_data = request.get_json()
        task = Task(json_data['taskId'],
                    json_data['taskName'],
                    json_data['description'],
                    json_data['dueDate'],
                    json_data['difficulty'],
                    json_data['type'],
                    json_data['username'],
                    json_data['completed'])
    except Exception as e:
        return str(e)

    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return str(e)
    
    with conn:
        task_dao = TaskDAO(conn)
        try:
            task_dao.insert(task)
        except Exception as e:
            return str(e)

    return "Task created"

@app.route('/deleteTask/<taskId>', methods=['POST'])
def deleteTask(taskId):
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return str(e)
    
    with conn:
        task_dao = TaskDAO(conn)
        try:
            task = task_dao.find_by_taskId(taskId)
            task_dao.delete(task)
        except Exception as e:
            return str(e)

    return "Task deleted"

@app.route('/updateTask', methods=['POST'])
def updateTask():
    try:
        json_data = request.get_json()
        task = Task(json_data['taskId'],
                    json_data['taskName'],
                    json_data['description'],
                    json_data['dueDate'],
                    json_data['difficulty'],
                    json_data['type'],
                    json_data['username'],
                    json_data['completed'])
    except Exception as e:
        return str(e)

    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return str(e)
    
    with conn:
        task_dao = TaskDAO(conn)
        try:
            task_dao.update_task(task)
        except Exception as e:
            return str(e)

    return jsonify(task.__dict__)