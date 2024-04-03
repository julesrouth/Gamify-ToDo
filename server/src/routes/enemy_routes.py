from flask import jsonify, request
import sys

sys.path.append('..')

from Model import Authtoken, Enemy, Player
from database.PlayerDAO import PlayerDAO
from database.AuthtokenDAO import AuthtokenDAO
from database.conn import create_connection

def getRandEnemy():
    try:
        json_data = request.json()
        player_level = json_data['level']
    except Exception as e:
        return jsonify({'success': False, 'message': "Invalid Request Format", "enemy": None})
