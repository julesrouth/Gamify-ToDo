from flask import request
import requests
import os
from flask_apscheduler import APScheduler 
from datetime import datetime
from apscheduler.schedulers.background import BackgroundScheduler
from Model import Task
# import onesignal as onesignal_sdk

class Notifications:

    def __init__(self, app):
        self.scheduler = None
        self.ONE_SIGNAL_API_KEY = ""
        self.ONE_SIGNAL_APP_ID = ""
        self.app = app
        self.init_scheduler()
        self.import_config()
        # self.client = onesignal_sdk.Client(app_id=self.ONE_SIGNAL_APP_ID, rest_api_key=self.ONE_SIGNAL_API_KEY)

    # import config file for OneSignal api key and app id
    def import_config(self):
        import configparser
        print("Importing OneSignal config")
        config = configparser.ConfigParser()

        file_path = os.path.join('src', 'notifications.config')
        if os.path.exists(file_path):
            print("File exists")
        else:
            print("File does not exist")

        config.read(file_path)
        print("Config sections:", config.sections())
        if 'OneSignal' in config:
            self.ONE_SIGNAL_API_KEY = config.get('OneSignal', 'API_KEY')
            self.ONE_SIGNAL_APP_ID = config.get('OneSignal', 'APP_ID')
        else:
            print("OneSignal section not found in notifications.config")

        print("OneSignal API Key:", self.ONE_SIGNAL_API_KEY)
        print("OneSignal APP ID:", self.ONE_SIGNAL_APP_ID)

    # initialize scheduler
    # @staticmethod
    def init_scheduler(self):
        self.scheduler =  BackgroundScheduler()    #APScheduler()
        self.scheduler.start()

    @staticmethod
    # Send a push notification to the Kotlin app
    def send_notification(self, user_id, message):
        # Logic to send push notification to Kotlin app
        print("Sending notification:", message, "to user:", user_id)

        # Create the request payload
        payload = {
            "app_id": self.ONE_SIGNAL_APP_ID,
            "include_player_ids": [	'046921b5-9c41-4396-a63c-7dff197edc79'], #change back to user_id
            "contents": {"en": message}
        }

        # Make the HTTP request to send the notification
        headers = {
            "Authorization": "Basic " + self.ONE_SIGNAL_API_KEY,
            "Content-Type": "application/json"
        }

        print("posting notification")
        print("Request payload:", payload)
        response = requests.post("https://onesignal.com/api/v1/notifications", json=payload, headers=headers)

        # response = self.client.send_notification(
        #     contents={"en": message},
        #     include_player_ids=[user_id]
        # )
        if response.status_code == 200:
            print("Notification sent successfully")
            return 'Notification sent successfully'
        else:
            print("Failed to send notification")
            print(response.content)
            return 'Failed to send notification'

    # Schedule a notification for a task
    def schedule_notification_task(self, task):
        print("Scheduling notification for task:", task.taskName)
        # time will be formated like this: 2021-10-10/hh:mm a

        notification_time = datetime.strptime(task.dueDate, '%Y-%m-%d %I:%M %p')

        message = "Task: " + task.taskName
        try:
            self.scheduler.add_job(self.send_notification, 'date', run_date=notification_time, args=[self, task.userId, message])
            print("Task Notification scheduled successfully")
            return "Task Notification scheduled successfully"
        except Exception as e:
            print("Error scheduling notification:", str(e))
            return str(e)

    # Schedule a daily notification for a task
    def schedule_notification_daily(self, task):
        # time will be formated like this: 2021-10-10 hh:mm am/pm
        notification_time = datetime.strptime(task.dueDate, '%Y-%m-%d %I:%M %p')
        message = "Task: " + task.taskName
        self.scheduler.add_job(self.send_notification, 'interval', days=1, start_date=notification_time, args=[message])
        return "Daily Task Notification scheduled successfully"

    # Schedule a weekly notification for a task
    def schedule_notification_weekly(self, task):
        # parse the date and time of the task correctly
        notification_time = datetime.strptime(task.dueDate, '%Y-%m-%d %I:%M %p')
        day_of_week = notification_time.weekday() #.strftime('%A')
        message = "Task: " + task.taskName
        self.scheduler.add_job(self.send_notification, 'cron', day_of_week=day_of_week, hour=notification_time.hour, minute=notification_time.minute, args=[message])
