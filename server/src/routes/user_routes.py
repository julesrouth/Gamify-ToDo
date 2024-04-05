from flask import jsonify, request
import random
import string
import sys

sys.path.append('..')

from Model import Authtoken, User, Player, Stat, LoginResponse, RegisterResponse
from database.AuthtokenDAO import AuthtokenDAO
from database.PlayerDAO import PlayerDAO
from database.StatDao import StatDao
from database.UserDAO import UserDAO
from database.TaskDAO import TaskDAO
from database.conn import create_connection



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
        login_response = RegisterResponse(False, "Invalid Request Format", None, None, None)
        return jsonify(login_response.__dict__)

    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        login_response = RegisterResponse(False, str(e), None, None, None)
        return jsonify(login_response.__dict__)
    
    with conn:
        user_dao = UserDAO(conn)
        try:
            user_dao.insert(user)
        except Exception as e:
            login_response = RegisterResponse(False, str(e), None, None, None)
            return jsonify(login_response.__dict__)
    
        token = ''.join(random.choices(string.ascii_lowercase +
                                string.digits, k=12))
        authtoken = Authtoken(token, user.uuid)
        auth_dao = AuthtokenDAO(conn)
        try:
            auth_dao.insert(authtoken)
        except Exception as e:
            login_response = RegisterResponse(False, str(e), None, None, None)
            return jsonify(login_response.__dict__)
    
        player = Player("Mr. Default", authtoken.userId, 1, 0, 0)
        player_dao = PlayerDAO(conn)
        try:
            player_dao.insert(player)
        except Exception as e:
            login_response = RegisterResponse(False, str(e), None, None, None)
            return jsonify(login_response.__dict__)
        
        stat = Stat(authtoken.userId, 10, 10, 1, 10, 20, 10)
        stat_dao = StatDao(conn)
        try:
            stat_dao.insert(stat)
        except Exception as e:
            login_response = RegisterResponse(False, str(e), None, None, None)
            return jsonify(login_response.__dict__)

    
    login_response = RegisterResponse(True, "Registration successful", authtoken, user, player)
    return jsonify(login_response.__dict__)


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