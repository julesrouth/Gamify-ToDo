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
    uuid = ''.join(random.choices(string.ascii_uppercase +
                             string.digits, k=12))

    try:
        json_data = request.get_json()
        user = User(uuid,
                    json_data["user"]['username'],
                    json_data["user"]['password'],
                    json_data["user"]['email'],
                    json_data["user"]['firstName'],
                    json_data["user"]['lastName'])
    except Exception as e:
        login_response = LoginResponse(False, "Invalid Request Format", None, None)
        return jsonify(login_response.__dict__)

    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        login_response = LoginResponse(False, str(e), None, None)
        return jsonify(login_response.__dict__)
    
    with conn:
        user_dao = UserDAO(conn)
        try:
            user_dao.insert(user)
        except Exception as e:
            login_response = LoginResponse(False, str(e), None, None)
            return jsonify(login_response.__dict__)
    
    token = ''.join(random.choices(string.ascii_lowercase +
                             string.digits, k=12))
    authtoken = Authtoken(token, user.uuid)
    auth_dao = AuthtokenDAO(conn)
    try:
        auth_dao.insert(authtoken)
    except Exception as e:
        login_response = LoginResponse(False, str(e), None, None)
        return jsonify(login_response.__dict__)       
    
    login_response = LoginResponse(True, "Registration successful", authtoken, user)
    return jsonify(login_response.__dict__)

@app.route('/login', methods=['POST'])
def login():
    try:
        json_data = request.get_json()
        username = json_data['username']
        password = json_data['password']
    except Exception as e:
        login_response = LoginResponse(False, "Invalid Request Format", None, None)
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
            authtoken = Authtoken(token, user.uuid)
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
        user = User(json_data['user']['uuid'],
                    json_data['user']['username'],
                    json_data['user']['password'],
                    json_data['user']['email'],
                    json_data['user']['firstName'],
                    json_data['user']['lastName'])
        authtoken = Authtoken(json_data['authtoken']['token'], json_data['authtoken']['userId'])
    except Exception as e:
        return jsonify({'success': False, 'message': "Invalid Request Format", 'user': None})

    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({'success': False, 'message': str(e), 'user': None})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'user': None})
        
        if auth == None:
            return jsonify({'success': False, 'message': 'Invalid token', 'user': None})
        
        if auth.userId != user.uuid:
            return jsonify({'success': False, 'message': 'Token does not match user', 'user': None})

        user_dao = UserDAO(conn)
        try:
            user_dao.update_user(user)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'user': None})

    return jsonify({'success': True, 'message': 'User updated', 'user': user.__dict__})

@app.route('/deleteUser', methods=['POST'])
def deleteUser():
    try:
        json_data = request.get_json()
        authtoken = Authtoken(json_data['authtoken']['token'], json_data['authtoken']['userId'])
    except Exception as e:
        return jsonify({'success': False, 'message': "Invalid Request Format"})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({'success': False, 'message': str(e)})
    

    
    with conn:
        authtoken_dao = AuthtokenDAO(conn)
        try:
            auth = authtoken_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        if auth == None:
            return jsonify({'success': False, 'message': 'Invalid token'})

        user_dao = UserDAO(conn)
        try:
            user = user_dao.find_by_id(authtoken.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        if user == None:
            return jsonify({'success': False, 'message': 'User not found'})

        task_dao = TaskDAO(conn)
        try:
            user_dao.delete(authtoken.userId)
            authtoken_dao.delete_forUser(authtoken.userId)
            task_dao.delete_forUser(authtoken.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
    return jsonify({'success': True, 'message': 'User deleted'})

@app.route('/createTask', methods=['POST'])
def createTask():
    try:
        json_data = request.get_json()
        authtoken = Authtoken(json_data['authtoken']['token'], json_data['authtoken']['userId'])
    except Exception as e:
        return jsonify({'success': False, 'message': "Invalid Request Format", 'task': None})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({'success': False, 'message': str(e), 'task': None})

    with conn:
        task_dao = TaskDAO(conn)
        try:
            highest_id = task_dao.get_highest_id(authtoken.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'task': None})

        if highest_id == None:
            highest_id = 0


    
    try:
        task = Task(highest_id + 1,
                    json_data['task']['taskName'],
                    json_data['task']['description'],
                    json_data['task']['dueDate'],
                    json_data['task']['difficulty'],
                    json_data['task']['type'],
                    json_data['task']['userId'],
                    json_data['task']['completed'])
    except Exception as e:
        return jsonify({'success': False, 'message': "Invalid Request Format", 'task': None})
    
    
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'task': None})
        
        if auth == None:
            return jsonify({'success': False, 'message': 'Invalid token', 'task': None})
        
        if auth.userId != task.userId:
            return jsonify({'success': False, 'message': 'Token does not match user', 'task': None})

        task_dao = TaskDAO(conn)
        try:
            task_dao.insert(task)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'task': None})

    return jsonify({'success': True, 'message': 'Task created', 'task': task.__dict__})

@app.route('/getTask', methods=['POST'])
def getTask():
    try:
        json_data = request.get_json()
        taskId = json_data['taskId']
        authtoken = Authtoken(json_data['authtoken']['token'], json_data['authtoken']['userId'])
    except Exception as e:
        return jsonify({'success': False, 'message': "Invalid Request Format", 'task': None})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({'success': False, 'message': str(e), 'task': None})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'task': None})
        
        if auth == None:
            return jsonify({'success': False, 'message': 'Invalid token', 'task': None})

        task_dao = TaskDAO(conn)
        try:
            task = task_dao.find_by_id(taskId, auth.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'task': None})
        
        if task == None:
            return jsonify({'success': False, 'message': 'Task not found', 'task': None})
        
        if auth.userId != task.userId:
            return jsonify({'success': False, 'message': 'Token does not match user', 'task': None})
        
    return jsonify({'success': True, 'message': 'Task found', 'task': task.__dict__})

@app.route('/listTasksForUser', methods=['POST'])
def listTasksForUser():
    try:
        json_data = request.get_json()
        authtoken = Authtoken(json_data['authtoken']['token'], json_data['authtoken']['userId'])
    except Exception as e:
        return jsonify({'success': False, 'message': "Invalid Request Format", 'tasks': None})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({'success': False, 'message': str(e), 'tasks': None})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'tasks': None})
        
        if auth == None:
            return jsonify({'success': False, 'message': 'Invalid token', 'tasks': None})
        
        if auth.userId != authtoken.userId:
            return jsonify({'success': False, 'message': 'Token does not match user', 'tasks': None})

        task_dao = TaskDAO(conn)
        try:
            tasks = task_dao.find_by_userId(authtoken.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'tasks': None})
        
    return jsonify({'success': True, 'message': 'Tasks found', 'tasks': [task.__dict__ for task in tasks]})

@app.route('/deleteTask', methods=['POST'])
def deleteTask():
    try:
        json_data = request.get_json()
        taskId = json_data['taskId']
        authtoken = Authtoken(json_data['authtoken']['token'], json_data['authtoken']['userId'])
    except Exception as e:
        return jsonify({'success': False, 'message': "Invalid Request Format"})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({'success': False, 'message': str(e)})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        if auth == None:
            return jsonify({'success': False, 'message': 'Invalid token'})
        
        if auth.userId != authtoken.userId:
            return jsonify({'success': False, 'message': 'Token does not match user'})

        task_dao = TaskDAO(conn)
        try:
            task = task_dao.find_by_id(taskId, auth.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        if task == None:
            return jsonify({'success': False, 'message': 'Task not found'})

        try:
            task_dao.delete_task(taskId, auth.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
    return jsonify({'success': True, 'message': 'Task deleted'})

@app.route('/updateTask', methods=['POST'])
def updateTask():
    try:
        json_data = request.get_json()
        new_task = Task(json_data['task']['taskId'],
                    json_data['task']['taskName'],
                    json_data['task']['description'],
                    json_data['task']['dueDate'],
                    json_data['task']['difficulty'],
                    json_data['task']['type'],
                    json_data['task']['userId'],
                    json_data['task']['completed'])
        authtoken = Authtoken(json_data['authtoken']['token'], json_data['authtoken']['userId'])
    except Exception as e:
        return jsonify({'success': False, 'message': "Invalid Request Format", 'task': None})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({'success': False, 'message': str(e), 'task': None})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'task': None})
        
        if auth == None:
            return jsonify({'success': False, 'message': 'Invalid token', 'task': None})
        
        if auth.userId != new_task.userId:
            return jsonify({'success': False, 'message': 'Token does not match user', 'task': None})

        task_dao = TaskDAO(conn)
        try:
            old_task = task_dao.find_by_id(new_task.taskId, auth.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'task': None})
        
        if old_task == None:
            return jsonify({'success': False, 'message': 'Task not found', 'task': None})

        try:
            task_dao.update_task(new_task)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'task': None})
        
    return jsonify({'success': True, 'message': 'Task updated', 'task': new_task.__dict__})

@app.route('/checkTask', methods=['POST'])
def checkTask():
    try:
        json_data = request.get_json()
        taskId = json_data['taskId']
        completed = json_data['completed']
        authtoken = Authtoken(json_data['authtoken']['token'], json_data['authtoken']['userId'])
    except Exception as e:
        return jsonify({'success': False, 'message': "Invalid Request Format"})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({'success': False, 'message': str(e)})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        if auth == None:
            return jsonify({'success': False, 'message': 'Invalid token'})
        
        if auth.userId != authtoken.userId:
            return jsonify({'success': False, 'message': 'Token does not match user'})

        task_dao = TaskDAO(conn)
        try:
            task = task_dao.find_by_id(taskId, auth.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        if task == None:
            return jsonify({'success': False, 'message': 'Task not found'})
        
        task.completed = completed

        try:
            task_dao.update_task(task)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
    return jsonify({'success': True, 'message': 'Task checked'})