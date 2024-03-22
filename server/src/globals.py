from routes.notifications import Notifications
from flask import Flask
app = Flask("todo_server")
notifications = Notifications(app)