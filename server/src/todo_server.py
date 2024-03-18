from flask import Flask
import routes.user_routes as user_routes
import routes.task_routes as task_routes
import routes.notifications as notifications

# set configuration values
class Config:
    SCHEDULER_API_ENABLED = True

app = Flask(__name__)
app.config.from_object(Config())

#init scheduler
notifications.init_scheduler(app)

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

#Notification Routes
#app.route('/scheduleNotification', methods=['POST'])(notification.schedule_notification)
