from flask import jsonify, request
import sys

sys.path.append('..')

from database.Model import Authtoken, Task
from database.TaskDAO import TaskDAO
from database.AuthtokenDAO import AuthtokenDAO
from database.PlayerDAO import PlayerDAO
from notifications import Notifications
from database.conn import create_connection
from tables import get_gold



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
        
        #schedule task
        try:
            match json_data['task']['type']:
                case "task":
                    Notifications.schedule_notification_task(task)
                case "daily":
                    Notifications.schedule_notification_daily(task)
                case "weekly":
                    Notifications.schedule_notification_weekly(task)
                case _:
                    return jsonify({'success': False, 'message': 'Invalid task type', 'task': None})
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'task': None})

    return jsonify({'success': True, 'message': 'Task created', 'task': task.__dict__})


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
        
        gold_added = get_gold(task.difficulty, task.type)
        player_dao = PlayerDAO(conn)
        try:
            player = player_dao.find_by_userId(auth.userId)
            player_dao.updateGold(authtoken.userId, player.gold + gold_added)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        #delete from scheduler
        
    return jsonify({'success': True, 'message': 'Task checked'})