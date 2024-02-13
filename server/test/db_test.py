import sqlite3
from DAO import *
from Model import Authtoken, User

def main():
    database = "database/todo_data.db"

    # create a database connection
    conn = create_connection(database)
    with conn:
        auth_dao = AuthtokenDAO(conn)
        authtoken = Authtoken("9876", "tester")
        try:
            auth_dao.insert(authtoken)
        except Exception as e:
            print(e)
        result = auth_dao.find_by_username(authtoken.username)
        print(result)
        result = auth_dao.find_by_authtoken(authtoken.token)
        print(result)

        user_dao = UserDAO(conn)
        user = User("tester", "password2", "email2", "firstName2", "lastName2")
        try:
            user_dao.insert(user)
        except Exception as e:
            print(e)
        result = user_dao.find_by_username(user.username)
        print(result)

if __name__ == '__main__':
    main()