from database.Model import Authtoken

class AuthtokenDAO:
    def __init__(self, conn):
        self.conn = conn

    def insert(self, authtoken):
        sql = ''' INSERT INTO Authtokens(token, userId)
                  VALUES(?,?) '''
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (authtoken.token, authtoken.userId))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def delete_forUser(self, userId):
        sql = 'DELETE FROM Authtokens WHERE userId=?'
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (userId,))
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

    def find_by_userId(self, userId):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT * FROM Authtokens WHERE userId=?", (userId,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        if rows:
            authtoken = Authtoken(rows[0][0], rows[0][1])
            return authtoken
        else:
            return None
        
    def find_by_token(self, token):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT * FROM Authtokens WHERE token=?", (token,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        if rows:
            authtoken = Authtoken(rows[0][0], rows[0][1])
            return authtoken
        else:
            return None