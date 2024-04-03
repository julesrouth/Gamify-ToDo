from Model import User

class UserDAO:
    def __init__(self, conn):
        self.conn = conn

    def insert(self, user):
        sql = ''' INSERT INTO Users(uuid, username, password, email, firstName, lastName)
                  VALUES(?,?,?,?,?,?) '''
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (user.uuid, user.username, user.password, user.email, user.firstName, user.lastName))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def delete(self, uuid):
        sql = 'DELETE FROM Users WHERE uuid=?'
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (uuid,))
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
    
    def find_by_id(self, uuid):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT * FROM Users WHERE uuid=?", (uuid,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        if rows:
            user = User(rows[0][0], rows[0][1], rows[0][2], rows[0][3], rows[0][4], rows[0][5])
            return user
        else:
            return None
        
    def find_by_username(self, username):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT * FROM Users WHERE username=?", (username,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        if rows:
            user = User(rows[0][0], rows[0][1], rows[0][2], rows[0][3], rows[0][4], rows[0][5])
            return user
        else:
            return None
        
    def update_user(self, user):
        sql = ''' UPDATE Users
                  SET username = ? ,
                      password = ? ,
                      email = ? ,
                      firstName = ? ,
                      lastName = ?
                  WHERE uuid = ?'''
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (user.username, user.password, user.email, user.firstName, user.lastName, user.uuid))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid