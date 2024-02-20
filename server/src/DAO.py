from Model import Authtoken, User, Task
import sqlite3

def create_connection(db_file):
    """ create a database connection to the SQLite database
        specified by db_file
    :param db_file: database file
    :return: Connection object or None
    """
    conn = None
    try:
        conn = sqlite3.connect(db_file)
    except Exception as e:
        raise e
    else:
        return conn

class AuthtokenDAO:
    def __init__(self, conn):
        self.conn = conn

    def insert(self, authtoken):
        sql = ''' INSERT INTO Authtokens(token, username)
                  VALUES(?,?) '''
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (authtoken.token, authtoken.username))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def delete(self, authtoken):
        sql = 'DELETE FROM Authtokens WHERE token=?'
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (authtoken.token,))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def clear(self):
        sql = 'DELETE FROM Authtokens'
        cur = self.conn.cursor()
        try:
            cur.execute(sql)
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid

    def find_by_username(self, username):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT * FROM Authtokens WHERE username=?", (username,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        if rows:
            authtoken = Authtoken(rows[0][0], rows[0][1])
            return authtoken
        else:
            return None
        
    def find_by_authtoken(self, authtoken):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT * FROM Authtokens WHERE token=?", (authtoken,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        if rows:
            authtoken = Authtoken(rows[0][0], rows[0][1])
            return authtoken
        else:
            return None
    
class UserDAO:
    def __init__(self, conn):
        self.conn = conn

    def insert(self, user):
        sql = ''' INSERT INTO Users(username, password, email, firstName, lastName)
                  VALUES(?,?,?,?,?) '''
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (user.username, user.password, user.email, user.firstName, user.lastName))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def delete(self, user):
        sql = 'DELETE FROM Users WHERE username=?'
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (user.username,))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def clear(self):
        sql = 'DELETE FROM Users'
        cur = self.conn.cursor()
        try:
            cur.execute(sql)
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def find_by_username(self, username):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT * FROM Users WHERE username=?", (username,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        if rows:
            user = User(rows[0][0], rows[0][1], rows[0][2], rows[0][3], rows[0][4])
            return user
        else:
            return None
        

class TaskDAO:
    def __init__(self, conn):
        self.conn = conn
    
    def insert(self, task):
        sql = ''' INSERT INTO Tasks(taskId, taskName, description, dueDate, difficulty, type, username, completed)
                  VALUES(?,?,?,?,?,?,?) '''
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (task.taskId, task.taskName, task.description, task.dueDate, task.difficulty, task.type, task.username, task.completed))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def delete(self, task):
        sql = 'DELETE FROM Tasks WHERE taskId=?'
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (task.taskId,))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def clear(self):
        sql = 'DELETE FROM Tasks'
        cur = self.conn.cursor()
        try:
            cur.execute(sql)
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def find_by_taskId(self, taskId):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT * FROM Tasks WHERE taskId=?", (task.taskId,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        if rows:
            task = Task(rows[0][0], rows[0][1], rows[0][2], rows[0][3], rows[0][4], rows[0][5], rows[0][6], rows[0][7])
            return task
        else:
            return None
        
    def update_task(self, task):
        sql = ''' UPDATE Tasks
                  SET taskName = ? ,
                      description = ? ,
                      dueDate = ? ,
                      difficulty = ? ,
                      type = ? ,
                      username = ? ,
                      completed = ?
                  WHERE taskId = ?'''
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (task.taskName, task.description, task.dueDate, task.difficulty, task.type, task.username, task.completed, task.taskId))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
        
    def find_by_username(self, username):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT * FROM Tasks WHERE username=?", (username,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        tasks = []
        for row in rows:
            task = Task(row[0], row[1], row[2], row[3], row[4], row[5], row[6],  row[7])
            tasks.append(task)
        return tasks
    
    