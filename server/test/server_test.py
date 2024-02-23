import requests
import json
import argparse

def send_register(dest, username):
    url = f"http://{dest}/register"

    data = {
        "email": "value1",
        "firstname": "value2",
        "lastname": "value3",
        "password": "value4",
        "username": username
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

def send_updateUser(dest, username):
    url = f"http://{dest}/updateUser"

    data = {
        "email": "value1",
        "firstname": "value2",
        "lastname": "value3",
        "password": "new_value4",
        "username": username
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
    


def main():
    parser = argparse.ArgumentParser(description="Send a request to the server")
    parser.add_argument("request", help="The request to send (register, login, or updateUser)")
    parser.add_argument("--dest", "-d", help="The destination URL for the request", default="127.0.0.1:8080")
    parser.add_argument("--username", "-u", help="The username for the request", default="value5")

    args = parser.parse_args()

    if args.request == "register":
        send_register(args.dest, args.username)
    elif args.request == "login":
        send_login(args.dest, args.username)
    elif args.request == "updateUser":
        send_updateUser(args.dest, args.username)
    else:
        print("Invalid request")

if __name__ == "__main__":
    main()