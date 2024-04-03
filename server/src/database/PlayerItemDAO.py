from Model import PlayerItem

class PlayerItemDAO:
    def __init__(self, conn):
        self.conn = conn
    
    def insert(self, itemCount, playerItem):
        sql = ''' INSERT INTO PlayerItems(itemCount, itemName, userId)
                  VALUES(?,?,?) '''
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (itemCount, playerItem.itemName, playerItem.userId))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def delete_single_item(self, itemCount, itemName, userId):
        sql = 'DELETE FROM PlayerItems WHERE itemCount=? AND itemName=? AND userId=?'
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (itemCount, itemName, userId))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def delete_by_userId(self, userId):
        sql = 'DELETE FROM PlayerItems WHERE userId=?'
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
            cur.execute("SELECT * FROM PlayerItems WHERE userId=?", (userId,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        playerItems = []
        for row in rows:
            playerItem = PlayerItem(row[1], row[2])
            playerItems.append(playerItem)
        return playerItems
    
    def get_highest_count(self, itemName, userId):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT MAX(itemCount) FROM PlayerItems WHERE itemName=? AND userId=?", (itemName, userId))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        if rows:
            return rows[0][0]
        else:
            return None

