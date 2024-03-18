from flask import request
import requests
from flask_apscheduler import APScheduler 
from datetime import datetime
from apscheduler.schedulers.background import BackgroundScheduler
from database.Model import Task

class Notifications:
    ONE_SIGNAL_API_KEY = ""
    ONE_SIGNAL_APP_ID = ""
    scheduler = None
    
    def __init__(self, app):
        self.app = app
        Notifications.init_scheduler()
        Notifications.import_config()

    #import config file for OneSignal api key and app id
    @staticmethod
    def import_config():
        import configparser
        config = configparser.ConfigParser()
        config.read('src\\routes\\notification.config') # file should be in the same directory as the server


        if 'OneSignal' in config:
            Notifications.ONESIGNAL_API_KEY = config.get('OneSignal', 'API_KEY')
            Notifications.ONESIGNAL_APP_ID = config.get('OneSignal', 'APP_ID')
        else:
            print("OneSignal section not found in notifications.config")

    #initialize scheduler
    @staticmethod
    def init_scheduler():
        scheduler =  BackgroundScheduler()    #APScheduler()
        scheduler.start()

    @staticmethod
    # Send a push notification to the Kotlin app
    def send_notification(notification):
        # Logic to send push notification to Kotlin app
        print("Sending notification:", notification)
        
        # Get the player ID and notification message from the request
        player_id = request.json['player_id']
        message = request.json['message']

        # Create the request payload
        payload = {
            "app_id": Notifications.ONESIGNAL_APP_ID,
            "include_player_ids": [player_id],
            "contents": {"en": message}
        }

        # Make the HTTP request to send the notification
        headers = {
            "Authorization": "Basic " + Notifications.ONESIGNAL_API_KEY,
            "Content-Type": "application/json"
        }
        response = requests.post("https://onesignal.com/api/v1/notifications", json=payload, headers=headers)

        if response.status_code == 200:
            return 'Notification sent successfully'
        else:
            return 'Failed to send notification'

    # Schedule a notification for a task
    def schedule_notification_task(task):
        notification_time = datetime.strptime(task.dueDate, '%Y-%m-%d %H:%M:%S')
        message = "Task: " + task.taskName
        notification = {'time': notification_time, 'message': message}
        #notifications.append(notification)
        Notifications.scheduler.add_job(Notifications.send_notification, 'date', run_date=notification_time, args=[message])
        return "Task Notification scheduled successfully"

    # Schedule a daily notification for a task
    def schedule_notification_daily(task):
        notification_time = datetime.strptime(task.dueDate, '%Y-%m-%d %H:%M:%S')
        message = "Task: " + task.taskName
        Notifications.scheduler.add_job(Notifications.send_notification, 'interval', days=1, start_date=notification_time, args=[message])
        return "Daily Task Notification scheduled successfully"

    # Schedule a weekly notification for a task
    def schedule_notification_weekly(task):
        notification_time = datetime.strptime(task.dueDate, '%Y-%m-%d %H:%M:%S')
        day_of_week = notification_time.weekday() #.strftime('%A')
        message = "Task: " + task.taskName
        Notifications.scheduler.add_job(Notifications.send_notification, 'cron', day_of_week=day_of_week, hour=notification_time.hour, minute=notification_time.minute, args=[message])
