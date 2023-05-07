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
        post = "LostPost" if is_for_lost else "FoundPost"
        cursor = self.conn.cursor()
        sql = f"INSERT INTO {post}(PostID, StudentID, ItemID, Available, PostTime) VALUES (%s, %s, %s, %s, %s)"
        try:
            cursor.execute(sql, (post_id, student_id, item_id, available, post_date))
            self.conn.commit()
            return cursor.lastrowid
        except Exception as e:
            self.logger.error("Insert post error:", e)
            self.conn.rollback()
            return None
        finally:
            cursor.close()

    # 查询帖子数据
    def query_post_by10(self, is_for_lost):
        if is_for_lost:
            sql = "SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgPath, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID\
        ORDER BY PostTime LIMIT 10"
        else:
            sql = "SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgPath, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID\
        ORDER BY PostTime LIMIT 10"
        
        cursor = self.conn.cursor()
        
        try:
            cursor.execute(sql)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:", e)
            return None
        finally:
            cursor.close()
            
    def query_post_by_ids(self, related_ids, is_for_lost):
        cursor = self.conn.cursor()
        if is_for_lost:
            sql = f"SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgPath, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
            LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID \
            AND PostID IN {tuple(related_ids)} ORDER BY PostTime"
        else:
            sql = f"SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgPath, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
            FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID \
            AND PostID IN {tuple(related_ids)} ORDER BY PostTime"
        try:
            cursor.execute(sql)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:", e)
            return None
        finally:
            cursor.close()
            
    def query_next_post_by10(self, last_id, is_for_lost):
        cursor = self.conn.cursor()
        if is_for_lost:
            sql = f"SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgPath, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID\
        AND LostPost.PostTime < (SELECT PostTime FROM LostPost WHERE PostID = {last_id}) ORDER BY PostTime LIMIT 10"
        else:
            sql = f"SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgPath, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID\
        AND FoundPost.PostTime < (SELECT PostTime FROM FoundPost WHERE PostID = {last_id}) ORDER BY PostTime LIMIT 10"
        try:
            cursor.execute(sql)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:", e)
            return None
        finally:
            cursor.close()
    
    def query_prev_post_by10(self, last_id, is_for_lost):
        cursor = self.conn.cursor()
        if is_for_lost:
            sql = f"SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgPath, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID\
        AND LostPost.PostTime > (SELECT PostTime FROM LostPost WHERE PostID = {last_id}) ORDER BY PostTime LIMIT 10"
        else:
            sql = f"SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgPath, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID\
        AND FoundPost.PostTime > (SELECT PostTime FROM FoundPost WHERE PostID = {last_id}) ORDER BY PostTime LIMIT 10"
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


