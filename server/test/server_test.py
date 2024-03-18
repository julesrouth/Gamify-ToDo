import requests
import json
import argparse

def send_register(dest, username):
    url = f"http://{dest}/register"


    user = {
        "uuid": None,
        "username": username,
        "password": "value4",
        "email": "value1",
        "firstName": "value2",
        "lastName": "value3"
    }

    data = {
        "user": user
    }
    

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)

    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_login(dest, username):
    url = f"http://{dest}/login"

    data = {
        "username": username,
        "password": "value4"
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent!")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)

    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_updateUser(dest, username, userId, token):
    url = f"http://{dest}/updateUser"

    user = {
        "uuid": userId,
        "username": username,
        "password": "new_value4",
        "email": "value1",
        "firstName": "value2",
        "lastName": "value3"
    }

    authtoken = {"token": token,
                 "userId": userId}

    data = {
        "user": user,
        "authtoken": authtoken
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)
    
    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_deleteUser(dest, userId, token):
    url = f"http://{dest}/deleteUser"

    authtoken = {"token":token,
                 "userId":userId}
    
    data = {
        "authtoken": authtoken
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)
    
    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_createTask(dest, userId, token):
    url = f"http://{dest}/createTask"

    task = {
            'taskId': None,
            'taskName': "dishes",
            'description': "clean all of the dishes",
            'dueDate': "1/2/13,12:00,pm",
            'difficulty' : "easy",
            'type': "daily",
            'userId': userId,
            'completed': False
    }

    authtoken = {"token": token,
                 "userId": userId}
    
    data = {
        "task": task,
        "authtoken": authtoken
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)
    
    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_getTask(dest, userId, token):
    url = f"http://{dest}/getTask"

    authtoken = {"token":token,
                 "userId":userId}
    
    data = {
        "authtoken": authtoken,
        "taskId": 5
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)
    
    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_listTasksForUser(dest, userId, token):
    url = f"http://{dest}/listTasksForUser"

    authtoken = {"token":token,
                 "userId":userId}
    
    data = {
        "authtoken": authtoken
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)
    
    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_deleteTask(dest, userId, token):
    url = f"http://{dest}/deleteTask"

    authtoken = {"token":token,
                 "userId":userId}
    
    data = {
        "authtoken": authtoken,
        "taskId": 5
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)
    
    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_updateTask(dest, userId, token):
    url = f"http://{dest}/updateTask"

    authtoken = {"token":token,
                 "userId":userId}

    task = {
            'taskId': 5,
            'taskName': "cool dishes",
            'description': "clean all of the cool dishes",
            'dueDate': "1/2/13,12:00,pm",
            'difficulty' : "easy",
            'type': "daily",
            'userId': userId,
            'completed': False
    }
    
    data = {
        "task": task,
        "authtoken": authtoken
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)
    
    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_checkTask(dest, userId, token):
    url = f"http://{dest}/checkTask"

    authtoken = {"token":token,
                 "userId":userId}
    
    data = {
        "authtoken": authtoken,
        "taskId": 1,
        "completed": True
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)
    
    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_createPlayer(dest, userId, token):
    url = f"http://{dest}/createPlayer"

    authtoken = {"token":token,
                 "userId":userId}
    
    data = {
        "characterName": "character1",
        "authtoken": authtoken
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)
    
    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_getPlayer(dest, userId, token):
    url = f"http://{dest}/getPlayer"

    authtoken = {"token":token,
                 "userId":userId}
    
    data = {
        "authtoken": authtoken
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)

    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_updateCharacterName(dest, userId, token):
    url = f"http://{dest}/updateCharacterName"

    authtoken = {"token":token,
                 "userId":userId}
    
    data = {
        "characterName": "character1_new",
        "authtoken": authtoken
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)

    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_enemyKilled(dest, userId, token):
    url = f"http://{dest}/enemyKilled"

    authtoken = {"token":token,
                 "userId":userId}
    
    data = {
        "authtoken": authtoken,
        "enemyName": "enemy1",
        "enemyLevel": 5
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)

    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_addPlayerItem(dest, userId, token):
    url = f"http://{dest}/addPlayerItem"

    authtoken = {"token":token,
                 "userId":userId}
    
    data = {
        "authtoken": authtoken,
        "itemName": "potion",
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)

    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_removePlayerItem(dest, userId, token):
    url = f"http://{dest}/removePlayerItem"

    authtoken = {"token":token,
                 "userId":userId}
    
    data = {
        "authtoken": authtoken,
        "itemName": "potion",
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)

    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_listPlayerItems(dest, userId, token):
    url = f"http://{dest}/listPlayerItems"

    authtoken = {"token":token,
                 "userId":userId}
    
    data = {
        "authtoken": authtoken,
    }

    json_data = json.dumps(data)

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)

    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def send_listStoreItems(dest, userId, token):
    url = f"http://{dest}/listStoreItems"

    # authtoken = {"token":token,
    #              "userId":userId}
    
    # data = {
    #     "authtoken": authtoken,
    # }

    # json_data = json.dumps(data)

    json_data = json.dumps({})

    headers = {"Content-Type": "application/json"}

    try:
        response = requests.post(url, data=json_data, headers=headers)

        if response.status_code == 200:
            print("Request sent")
            print("Response:", response.text)
        else:
            print(f"Request failed with status code {response.status_code}")
            print("Response:", response.text)

    except requests.RequestException as e:
        print(f"An error occurred: {e}")

def main():
    parser = argparse.ArgumentParser(description="Send a request to the server")
    parser.add_argument("request", help="The request to send (register, login, or updateUser)")
    parser.add_argument("--dest", "-d", help="The destination URL for the request", default="127.0.0.1:8080")
    parser.add_argument("--username", "-u", help="The username for the request", default="value5")
    parser.add_argument("--token", "-t", help="The token for the request", default="ko4qtuxw2wi3")
    parser.add_argument("--userId", "-i", help="The userId for the request", default="I5G57F8FPTKT")

    args = parser.parse_args()

    if args.request == "register":
        send_register(args.dest, args.username)
    elif args.request == "login":
        send_login(args.dest, args.username)
    elif args.request == "updateUser":
        send_updateUser(args.dest, args.username, args.userId, args.token)
    elif args.request == "deleteUser":
        send_deleteUser(args.dest, args.userId, args.token)
    elif args.request == "createTask":
        send_createTask(args.dest, args.userId, args.token)
    elif args.request == "getTask":
        send_getTask(args.dest, args.userId, args.token)
    elif args.request == "listTasksForUser":
        send_listTasksForUser(args.dest, args.userId, args.token)
    elif args.request == "deleteTask":
        send_deleteTask(args.dest, args.userId, args.token)
    elif args.request == "updateTask":
        send_updateTask(args.dest, args.userId, args.token)
    elif args.request == "checkTask":
        send_checkTask(args.dest, args.userId, args.token)
    elif args.request == "createPlayer":
        send_createPlayer(args.dest, args.userId, args.token)
    elif args.request == "getPlayer":
        send_getPlayer(args.dest, args.userId, args.token)
    elif args.request == "updateCharacterName":
        send_updateCharacterName(args.dest, args.userId, args.token)
    elif args.request == "enemyKilled":
        send_enemyKilled(args.dest, args.userId, args.token)
    elif args.request == "addPlayerItem":
        send_addPlayerItem(args.dest, args.userId, args.token)
    elif args.request == "removePlayerItem":
        send_removePlayerItem(args.dest, args.userId, args.token)
    elif args.request == "listPlayerItems":
        send_listPlayerItems(args.dest, args.userId, args.token)
    elif args.request == "listStoreItems":
        send_listStoreItems(args.dest, args.userId, args.token)
    else:
        print("Invalid request")

if __name__ == "__main__":
    main()