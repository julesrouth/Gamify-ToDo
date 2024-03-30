from flask import Flask
import routes.user_routes as user_routes
import routes.task_routes as task_routes
import routes.player_routes as player_routes
import routes.item_routes as item_routes
import notifications as Notifications
from globals import app, notifications



# set configuration values
class Config:
    SCHEDULER_API_ENABLED = True

app.config.from_object(Config())



@app.route("/")
def hello_world():
    return "<p>Hello, World!</p>"

# User Routes
app.route('/register', methods=['POST'])(user_routes.register)
app.route('/login', methods=['POST'])(user_routes.login)
app.route('/updateUser', methods=['POST'])(user_routes.updateUser)
app.route('/deleteUser', methods=['POST'])(user_routes.deleteUser)

# Task Routes
app.route('/createTask', methods=['POST'])(task_routes.createTask)
app.route('/getTask', methods=['POST'])(task_routes.getTask)
app.route('/listTasksForUser', methods=['POST'])(task_routes.listTasksForUser)
app.route('/deleteTask', methods=['POST'])(task_routes.deleteTask)
app.route('/updateTask', methods=['POST'])(task_routes.updateTask)
app.route('/checkTask', methods=['POST'])(task_routes.checkTask)

# Player Routes
app.route('/createPlayer', methods=['POST'])(player_routes.createPlayer)
app.route('/getPlayer', methods=['POST'])(player_routes.getPlayer)
app.route('/updateCharacterName', methods=['POST'])(player_routes.updateCharacterName)
app.route('/enemyKilled', methods=['POST'])(player_routes.enemyKilled)

# Item Routes
app.route('/addPlayerItem', methods=['POST'])(item_routes.addPlayerItem)
app.route('/removePlayerItem', methods=['POST'])(item_routes.removePlayerItem)
app.route('/listPlayerItems', methods=['POST'])(item_routes.listPlayerItems)
app.route('/listStoreItems', methods=['POST', 'GET'])(item_routes.listStoreItems)
