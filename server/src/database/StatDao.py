from Model import Stat

class StatDao:
    def __init__(self, conn):
        self.conn = conn

    def insert(self, stat):
        sql = ''' INSERT INTO Stats(userId, attack, defense, level, speed, maxHealth, maxMana)
                  VALUES(?,?,?,?,?,?,?) '''
        
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (stat.userId, stat.attack, stat.defense, stat.level, stat.speed, stat.maxHealth, stat.maxMana))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid

    def delete_by_userId(self, userId):
        sql = 'DELETE FROM Stats WHERE userId=?'
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
            cur.execute("SELECT * FROM Stats WHERE userId=?", (userId,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        if rows:
            stat = Stat(rows[0][0], 
                        rows[0][1], 
                        rows[0][2],
                        rows[0][3],
                        rows[0][4],
                        rows[0][5],
                        rows[0][6])
            return stat
        else:
            return None

    def update(self, stat):
        cur = self.conn.cursor()
        try:
            cur.execute("UPDATE Stats SET attack=?, defense=?, level=?, speed=?, maxHealth=?, maxMana=? WHERE userId=?", 
                        (stat.attack, stat.defense, stat.level, stat.speed, stat.maxHealth, stat.maxMana, stat.userId))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    