from flask import jsonify, request
import sys

sys.path.append('..')

from Model import Authtoken, Stat
from database.StatDao import StatDao
from database.AuthtokenDAO import AuthtokenDAO
from database.conn import create_connection

def getPlayerStat():
    try:
        json_data = request.get_json()
        authtoken = Authtoken(json_data['authtoken']['token'], json_data['authtoken']['userId'])
    except Exception as e:
        return jsonify({'success': False, 'message': "Invalid Request Format", "stat": None})
    

    try:
        conn = create_connection()
    except Exception as e:
        return jsonify({'success': False, 'message': str(e), 'stat': None}) 
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'stat': None})
        
        if auth == None:
            return jsonify({'success': False, 'message': "Invalid Token", 'stat': None})
        
        stat_dao = StatDao(conn)
        try:
            stat = stat_dao.find_by_userId(auth.userId)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e), 'stat': None})
        
        if stat == None:
            return jsonify({'success': False, 'message': "Stat not found", 'stat': None})
        
        return jsonify({'success': True, 'message': "Stat found", 'stat': stat.__dict__})
    
def updatePlayerStat():
    try:
        json_data = request.get_json()
        stat = Stat(json_data['stat']['userId'], 
                    json_data['stat']['health'], 
                    # add more fields here
                    )
        authtoken  = Authtoken(json_data['authtoken']['token'], json_data['authtoken']['userId'])
    except Exception as e:
        return jsonify({'success': False, 'message': "Invalid Request Format"})
    
    try:
        conn = create_connection()
    except Exception as e:
        return jsonify({'success': False, 'message': str(e)})
    
    with conn:
        auth_dao = AuthtokenDAO(conn)
        try:
            auth = auth_dao.find_by_token(authtoken.token)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        if auth == None:
            return jsonify({'success': False, 'message': "Invalid Token"})
        
        stat_dao = StatDao(conn)
        try:
            stat_dao.update(stat)
        except Exception as e:
            return jsonify({'success': False, 'message': str(e)})
        
        return jsonify({'success': True, 'message': "Stat updated"})
    

