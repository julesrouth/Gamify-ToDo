from flask import jsonify, request
import sys

sys.path.append('..')

from Model import Authtoken, Player
from database.PlayerDAO import PlayerDAO
from database.AuthtokenDAO import AuthtokenDAO
from database.conn import create_connection
from tables import get_experience, get_level_up_experience


def onesignal_webhook():
    data = request.json
    player_id = data.get('id')  # Assuming OneSignal sends player ID as 'id'
    # Store player_id in your database
    # Example: 
    # db.save_player_id(player_id)
    return 'Player ID received and stored successfully', 200