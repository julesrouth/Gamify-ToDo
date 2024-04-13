from flask import jsonify, request
import sys

sys.path.append('..')

from Model import Authtoken, Player, Stat
from database.PlayerDAO import PlayerDAO
from database.AuthtokenDAO import AuthtokenDAO
from database.StatDao import StatDao
from database.conn import create_connection
from tables import get_experience, get_level_up_experience

def createPlayer():
    try:
        json_data = request.get_json()
        authtoken = Authtoken(json_data['authtoken']['token'], json_data["authtoken"]['userId'])
    except Exception as e:
        return jsonify({'success': False, 'message': "Invalid Request Format poo", 'player': None})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({'success': False, 'message': "Database Connection Error", 'player': None})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': "Database Error", 'player': None})
        
        if auth is None:
            return jsonify({'success': False, 'message': "Invalid Token", 'player': None})
        
        if auth.userId != authtoken.userId:
            return jsonify({'success': False, 'message': "Token does not match user", 'player': None})
        
        player = Player(json_data['characterName'], authtoken.userId, 1, 0, 0)
        player_dao = PlayerDAO(conn)
        try:
            player_dao.insert(player)
        except Exception as e:
            return jsonify({'success': False, 'message': "Database Error", 'player': None})
        
        return jsonify({'success': True, 'message': "Player Created", 'player': player.__dict__})
    

def getPlayer():
    try:
        json_data = request.get_json()
        authtoken = Authtoken(json_data['authtoken']['token'], json_data["authtoken"]['userId'])
    except Exception as e:
            return jsonify({'success': False, 'message': "Invalid Request Format", 'player': None})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({'success': False, 'message': "Database Connection Error", 'player': None})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': "Database Error", 'player': None})
        
        if auth is None:
            return jsonify({'success': False, 'message': "Invalid Token", 'player': None})
        
        if auth.userId != authtoken.userId:
            return jsonify({'success': False, 'message': "Token does not match user", 'player': None})
        
        player_dao = PlayerDAO(conn)
        try:
            player = player_dao.find_by_userId(authtoken.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': "Database Error", 'player': None})
        
        if player is None:
            return jsonify({'success': False, 'message': "Player not found", 'player': None})
        
        return jsonify({'success': True, 'message': "Player Found", 'player': player.__dict__})
    
def updateCharacterName():
    try:
        json_data = request.get_json()
        authtoken = Authtoken(json_data['authtoken']['token'], json_data["authtoken"]['userId'])
        new_name = json_data['characterName']
    except Exception as e:
            return jsonify({'success': False, 'message': "Invalid Request Format", 'player': None})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({'success': False, 'message': "Database Connection Error", 'player': None})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': "Database Error", 'player': None})
        
        if auth is None:
            return jsonify({'success': False, 'message': "Invalid Token", 'player': None})
        
        if auth.userId != authtoken.userId:
            return jsonify({'success': False, 'message': "Token does not match user", 'player': None})
        
        player_dao = PlayerDAO(conn)
        try:
            player = player_dao.find_by_userId(authtoken.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': "Database Error", 'player': None})
        
        if player is None:
            return jsonify({'success': False, 'message': "Player not found", 'player': None})
        
        try:
            player_dao.updateCharacterName(authtoken.userId, new_name)
        except Exception as e:
            return jsonify({'success': False, 'message': "Database Error", 'player': None})
        
        return jsonify({'success': True, 'message': "Name Updated", 'player': player.__dict__})
    
def enemyKilled():
    try:
        json_data = request.get_json()
        authtoken = Authtoken(json_data['authtoken']['token'], json_data["authtoken"]['userId'])
        enemyLevel = json_data['enemyLevel']
    except Exception as e:
            return jsonify({'success': False, 'message': "Invalid Request Format", "experience": None, "level": None})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({'success': False, 'message': "Database Connection Error", "experience": None, "level": None})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': "Database Error", "experience": None, "level": None})
        
        if auth is None:
            return jsonify({'success': False, 'message': "Invalid Token", "experience": None, "level": None})
        
        if auth.userId != authtoken.userId:
            return jsonify({'success': False, 'message': "Token does not match user", "experience": None, "level": None})
        
        player_dao = PlayerDAO(conn)
        try:
            player = player_dao.find_by_userId(authtoken.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': "Database Error", "experience": None, "level": None})
        
        if player is None:
            return jsonify({'success': False, 'message': "Player not found", "experience": None, "level": None})
        
        experience_gained = get_experience(enemyLevel)
        new_experience = player.experience + experience_gained
        old_level = player.level
        while new_experience >= get_level_up_experience(player.level):
            new_experience -= get_level_up_experience(player.level)
            player.level += 1

        try:
            player_dao.updateLevel(authtoken.userId, player.level)
        except Exception as e:
            return jsonify({'success': False, 'message': "Database Error", "experience": None, "level": None})
        
        try:
            player_dao.updateExperience(authtoken.userId, new_experience)
        except Exception as e:
            return jsonify({'success': False, 'message': "Database Error", "experience": None, "level": None})
        
        if (player.level != old_level):
            stat_dao = StatDao(conn)
            try:
                stat = stat_dao.find_by_userId(authtoken.userId)
                stat.attack += 3 * (player.level - old_level)
                stat.defense += 3 * (player.level - old_level)
                stat.speed += 3 * (player.level - old_level)
                stat.maxMana += 3 * (player.level - old_level)
                stat.maxHealth += 6 * (player.level - old_level)
                stat.level = player.level
                stat_dao.update(authtoken.userId, stat)
            except Exception as e:
                return jsonify({'success': False, 'message': "Database Error", "experience": None, "level": None})
            
        return jsonify({'success': True, 'message': "Experience Updated", "experience": new_experience, "level": player.level})