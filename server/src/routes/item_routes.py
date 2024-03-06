from flask import jsonify, request
import sys

sys.path.append('..')

from database.Model import Authtoken, PlayerItem, StoreItem
from database.PlayerItemDAO import PlayerItemDAO
from database.AuthtokenDAO import AuthtokenDAO
from database.conn import create_connection
from tables import get_store_table

def addPlayerItem():
    try:
        json_data = request.get_json()
        authtoken = Authtoken(json_data['authtoken']['token'], json_data['authtoken']['userId'])
        player_item = PlayerItem(json_data["itemName"], authtoken.userId)
    except Exception as e:
        return jsonify({"success": False, "message": str(e)})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({"success": False, "message": str(e)})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        if auth == None:
            return jsonify({'success': False, 'message': 'Invalid token'})
        
        if auth.userId != authtoken.userId:
            return jsonify({'success': False, 'message': 'Invalid token'})
        
        player_item_dao = PlayerItemDAO(conn)
        try:
            count = player_item_dao.get_highest_count(player_item.itemName, player_item.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        if count == None:
            count = 0

        try:
            player_item_dao.insert(count+1, player_item)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
    return jsonify({'success': True, 'message': 'Item added'})
        

def removePlayerItem():
    try:
        json_data = request.get_json()
        authtoken = Authtoken(json_data['authtoken']['token'], json_data['authtoken']['userId'])
        itemName = json_data["itemName"]
    except Exception as e:
        return jsonify({"success": False, "message": str(e)})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({"success": False, "message": str(e)})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        if auth == None:
            return jsonify({'success': False, 'message': 'Invalid token'})
        
        if auth.userId != authtoken.userId:
            return jsonify({'success': False, 'message': 'Invalid token'})
        

        
        player_item_dao = PlayerItemDAO(conn)
        try:
            count = player_item_dao.get_highest_count(itemName, authtoken.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        if count == None:
            return jsonify({'success': False, 'message': 'Item not found'})

        try:
            player_item_dao.delete_single_item(count, itemName, authtoken.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
    return jsonify({'success': True, 'message': 'Item removed'})

def listPlayerItems():
    try:
        json_data = request.get_json()
        authtoken = Authtoken(json_data['authtoken']['token'], json_data['authtoken']['userId'])
    except Exception as e:
        return jsonify({"success": False, "message": str(e)})
    
    try:
        conn = create_connection("database/todo_data.db")
    except Exception as e:
        return jsonify({"success": False, "message": str(e)})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        if auth == None:
            return jsonify({'success': False, 'message': 'Invalid token'})
        
        if auth.userId != authtoken.userId:
            return jsonify({'success': False, 'message': 'Invalid token'})
        
        player_item_dao = PlayerItemDAO(conn)
        try:
            player_items = player_item_dao.find_by_userId(authtoken.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
    return jsonify({'success': True, 'message': 'Items found', 'items': [item.__dict__ for item in player_items]})

def listStoreItems():
    try:
        store_items = get_store_table()
        return jsonify({'success': True, 'message': 'Items found', 'items': [item.__dict__ for item in store_items]})
    except Exception as e:
        return jsonify({"success": False, "message": str(e)})