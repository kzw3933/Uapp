import subprocess
import udata.config as config
import pymysql

class DB:
    def __init__(self, logger):
        self.logger = logger
        with open(config.sql_script, "r") as f:
            script = f.read()
        cmd = [config.mysql_exec_path, "-u", config.username, "-p"+config.password]
        proc = subprocess.Popen(cmd, stdin=subprocess.PIPE,
                                stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out, err = proc.communicate(input=script.encode())

        if proc.returncode == 0:
            logger.info("Success on mysql database initialize")
        else:
            logger.error("Fail in mysql database initialize", err.decode())

    def connect(self):
        self.conn = pymysql.connect(host='localhost',port=3306, user=config.username, password=config.password,
                                     database=config.database, charset='utf8')

    def close(self):
        self.conn.close()

    # 插入用户数据
    def insert_user(self, student_id, username, email, password):
        cursor = self.conn.cursor()
        sql = "INSERT INTO User(StudentID, Username, Email, Password) VALUES (%s, %s, %s, %s)"
        try:
            cursor.execute(sql, (student_id, username, email, password))
            self.conn.commit()
            return True
        except Exception as e:
            self.logger.error("Insert user error:", e)
            self.conn.rollback()
            return False
        finally:
            cursor.close()

    # 查询用户数据
    def query_user(self, student_id):
        cursor = self.conn.cursor()
        sql = "SELECT * FROM User WHERE StudentID=%s"
        try:
            cursor.execute(sql, student_id)
            result = cursor.fetchone()
            return result
        except Exception as e:
            self.logger.error("Query user error:", e)
            return None
        finally:
            cursor.close()

    # 更新用户数据
    def update_user(self, student_id, username, email, password):
        cursor = self.conn.cursor()
        sql = "UPDATE User SET Username=%s, Email=%s, Password=%s WHERE StudentID=%s"
        try:
            cursor.execute(sql, (username, email, password, student_id))
            self.conn.commit()
            return True
        except Exception as e:
            self.logger.error("Update user error:", e)
            self.conn.rollback()
            return False
        finally:
            cursor.close()

    # 删除用户数据
    def delete_user(self, student_id):
        cursor = self.conn.cursor()
        sql = "DELETE FROM User WHERE StudentID=%s"
        try:
            cursor.execute(sql, student_id)
            self.conn.commit()
            return True
        except Exception as e:
            self.logger.error("Delete user error:", e)
            self.conn.rollback()
            return False
        finally:
            cursor.close()

    # 插入物品数据
    def insert_item(self, item_time, item_type, item_img_path, item_img_name, item_location, item_description):
        cursor = self.conn.cursor()
        sql = "INSERT INTO Item(ItemTime, ItemType, ItemImgPath, ItemImgName, ItemLocation, ItemDescription) VALUES (%s, %s, %s, %s, %s, %s)"
        try:
            cursor.execute(sql, (item_time, item_type, item_img_path, item_img_name, item_location, item_description))
            self.conn.commit()
            return cursor.lastrowid
        except Exception as e:
            self.logger.error("Insert item error:", e)
            self.conn.rollback()
            return None
        finally:
            cursor.close()

    # 插入帖子数据
    def insert_post(self, post_id, student_id, item_id, is_for_lost, available, post_date):
        cursor = self.conn.cursor()
        sql = "INSERT INTO Post(PostID, StudentID, ItemID, IsForLost, Available, PostTime) VALUES (%s, %s, %s, %s, %s, %s)"
        try:
            cursor.execute(sql, (post_id, student_id, item_id, is_for_lost, available, post_date))
            self.conn.commit()
            return cursor.lastrowid
        except Exception as e:
            self.logger.error("Insert post error:", e)
            self.conn.rollback()
            return None
        finally:
            cursor.close()

    # 查询帖子数据
    def query_post_by10(self):
        cursor = self.conn.cursor()
        sql = "SELECT Post.PostID, Post.StudentID, Post.IsForLost, Item.ItemImgPath, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        Post.Available, UNIX_TIMESTAMP(Item.ItemTime), Item.ItemDescription, UNIX_TIMESTAMP(Post.PostTime) FROM Post, Item WHERE Post.ItemID=Item.ItemID AND\
        Post.IsForLost=True ORDER BY PostTime LIMIT 10"
        try:
            cursor.execute(sql)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:", e)
            return None
        finally:
            cursor.close()
            
    def query_post_by_ids(self, last_id, related_ids):
        cursor = self.conn.cursor()
        sql = f"SELECT Post.PostID, Post.StudentID, Post.IsForLost, Item.ItemImgPath, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        Post.Available, UNIX_TIMESTAMP(Item.ItemTime), Item.ItemDescription, UNIX_TIMESTAMP(Post.PostTime) FROM Post, Item WHERE Post.ItemID=Item.ItemID AND\
        Post.IsForLost=True AND PostID IN {tuple(related_ids)} AND UNIX_TIMESTAMP(Post.PostTime) <= (SELECT UNIX_TIMESTAMP(PostTime) FROM Post WHERE PostID = {last_id}) ORDER BY PostTime LIMIT 10"
        try:
            cursor.execute(sql)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:", e)
            return None
        finally:
            cursor.close()
            
    def query_next_post_by10(self, last_id):
        cursor = self.conn.cursor()
        sql = f"SELECT Post.PostID, Post.StudentID, Post.IsForLost, Item.ItemImgPath, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        Post.Available, UNIX_TIMESTAMP(Item.ItemTime), Item.ItemDescription, UNIX_TIMESTAMP(Post.PostTime) FROM Post, Item WHERE Post.ItemID=Item.ItemID AND\
        Post.IsForLost=True AND UNIX_TIMESTAMP(Post.PostTime) < (SELECT UNIX_TIMESTAMP(PostTime) FROM Post WHERE PostID = {last_id}) ORDER BY PostTime LIMIT 10"
        try:
            cursor.execute(sql)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:", e)
            return None
        finally:
            cursor.close()
    
    def query_prev_post_by10(self, last_id, related_ids):
        cursor = self.conn.cursor()
        sql = f"SELECT Post.PostID, Post.StudentID, Post.IsForLost, Item.ItemImgPath, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        Post.Available, UNIX_TIMESTAMP(Item.ItemTime), Item.ItemDescription, UNIX_TIMESTAMP(Post.PostTime) AS UTIME FROM Post, Item WHERE Post.ItemID=Item.ItemID AND\
        Post.IsForLost=True AND UTIME > (SELECT UNIX_TIMESTAMP(Post.PostTime) FROM Post WHERE PostID = {last_id}) ORDER BY PostTime LIMIT 10"
        try:
            cursor.execute(sql)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:", e)
            return None
        finally:
            cursor.close()

    # 插入回复数据
    def insert_reply(self, post_id, student_id, reply_content):
        cursor = self.conn.cursor()
        sql = "INSERT INTO Reply(PostID, StudentID, ReplyContent) VALUES (%s, %s, %s)"
        try:
            cursor.execute(sql, (post_id, student_id, reply_content))
            self.conn.commit()
            return cursor.lastrowid
        except Exception as e:
            self.logger.error("Insert reply error:", e)
            self.conn.rollback()
            return None
        finally:
            cursor.close()

        # 查询回复数据
    def query_all_reply(self, post_id):
        cursor = self.conn.cursor()
        sql = "SELECT * FROM Reply WHERE PostID=%s"
        try:
            cursor.execute(sql, post_id)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:", e)
            return None
        finally:
            cursor.close()


