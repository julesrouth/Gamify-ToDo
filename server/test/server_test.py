import requests
import json
import argparse

def send_register():
    url = "http://127.0.0.1:5000/register"

    data = {
        "email": "value1",
        "firstname": "value2",
        "lastname": "value3",
        "password": "value4",
        "username": "value5"
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

def send_login():
    url = "http://127.0.0.1:5000/login"

    data = {
        "username": "value5",
        "password": "new_value4",
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

def send_updateUser():
    url = "http://127.0.0.1:5000/updateUser"

    data = {
        "email": "value1",
        "firstname": "value2",
        "lastname": "value3",
        "password": "new_value4",
        "username": "value5"
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

    args = parser.parse_args()

    if args.request == "register":
        send_register()
    elif args.request == "login":
        send_login()
    elif args.request == "updateUser":
        send_updateUser()
    else:
        print("Invalid request")

if __name__ == "__main__":
    main()