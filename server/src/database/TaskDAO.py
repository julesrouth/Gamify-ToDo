from Model import Task

class TaskDAO:
    def __init__(self, conn):
        self.conn = conn

    def insert(self, task):
        sql = ''' INSERT INTO Tasks(taskId, taskName, description, dueDate, difficulty, type, userId, completed)
                  VALUES(?,?,?,?,?,?,?,?) '''
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (task.taskId, task.taskName, task.description, task.dueDate, task.difficulty, task.type, task.userId, task.completed))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid
    
    def delete_task(self, taskId, userId):
        sql = 'DELETE FROM Tasks WHERE taskId=? AND userId=?'
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (taskId, userId))
        except Exception as e:
            raise e
        self.conn.commit()
        print(cur.lastrowid)
        return cur.lastrowid
    
    def delete_forUser(self, userId):
        sql = 'DELETE FROM Tasks WHERE userId=?'
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (userId,))
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
    
    def find_by_id(self, taskId, userId):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT * FROM Tasks WHERE taskId=? AND userId=?", (taskId, userId))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        if rows:
            task = Task(rows[0][0], rows[0][1], rows[0][2], rows[0][3], rows[0][4], rows[0][5], rows[0][6], rows[0][7])
            return task
        else:
            return None
        
    
    def find_by_userId(self, userId):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT * FROM Tasks WHERE userId=?", (userId,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        tasks = []
        for row in rows:
            task = Task(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7])
            tasks.append(task)
        return tasks
    
    def get_highest_id(self, userId):
        cur = self.conn.cursor()
        try:
            cur.execute("SELECT MAX(taskId) FROM Tasks WHERE userId=?", (userId,))
        except Exception as e:
            raise e
        rows = cur.fetchall()
        if rows:
            return rows[0][0]
        else:
            return None

    def update_task(self, task):
        sql = ''' UPDATE Tasks
                  SET taskName = ? ,
                      description = ? ,
                      dueDate = ? ,
                      difficulty = ? ,
                      type = ? ,
                      userId = ? ,
                      completed = ?
                  WHERE taskId = ?'''
        cur = self.conn.cursor()
        try:
            cur.execute(sql, (task.taskName, task.description, task.dueDate, task.difficulty, task.type, task.userId, task.completed, task.taskId))
        except Exception as e:
            raise e
        self.conn.commit()
        return cur.lastrowid