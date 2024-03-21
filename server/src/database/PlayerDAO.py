from Model import Player

class PlayerDAO:
    def __init__(self, conn):
        self.conn = conn

    def insert(self, player):
        sql = ''' INSERT INTO Players(userId, characterName, level, experience, gold)
                  VALUES(?,?,?,?,?) '''
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (player.userId, player.characterName, player.level, player.experience, player.gold))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def delete_by_userId(self, userId):
        sql = 'DELETE FROM Players WHERE userId=?'
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (userId,))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def find_by_userId(self, userId):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT * FROM Players WHERE userId=?", (userId,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        if rows:
            player = Player(rows[0][0], rows[0][1], rows[0][2], rows[0][3], rows[0][4])
            return player
        else:
            return None
    
    def updateExperience(self, userId, experience):
        cur = self.conn.cursor()
        try:
            cur.execute("UPDATE Players SET experience=? WHERE userId=?", (experience, userId))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def updateGold(self, userId, gold):
        cur = self.conn.cursor()
        try:
            cur.execute("UPDATE Players SET gold=? WHERE userId=?", (gold, userId))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def updateLevel(self, userId, level):
        cur = self.conn.cursor()
        try:
            cur.execute("UPDATE Players SET level=? WHERE userId=?", (level, userId))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def updateCharacterName(self, userId, characterName):
        cur = self.conn.cursor()
        try:
            cur.execute("UPDATE Players SET characterName=? WHERE userId=?", (characterName, userId))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
