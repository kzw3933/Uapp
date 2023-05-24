import subprocess
import utils.data.config as config
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
            logger.error("Fail in mysql database initialize %s", err.decode())

    def connect(self):
        self.conn = pymysql.connect(host='localhost',port=3306, user=config.username, password=config.password,
                                     database=config.database, charset='utf8')

    def close(self):
        self.conn.close()

    # 插入用户数据
    def insert_user(self, student_id, username, email, password, contact):
        cursor = self.conn.cursor()
        sql = "INSERT INTO User(StudentID, Username, Email, Password, Contact) VALUES (%s, %s, %s, %s, %s)"
        try:
            cursor.execute(sql, (student_id, username, email, password, contact))
            self.conn.commit()
            return True
        except Exception as e:
            self.logger.error("Insert user error:%s", e)
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
            self.logger.error("Query user error:%s", e)
            return None
        finally:
            cursor.close()

    # 更新用户数据
    def update_user(self, student_id, username, email, password, contact):
        cursor = self.conn.cursor()
        sql = "UPDATE User SET Username=%s, Email=%s, Password=%s, Contact=%s WHERE StudentID=%s"
        try:
            cursor.execute(sql, (username, email, password, contact, student_id))
            self.conn.commit()
            return True
        except Exception as e:
            self.logger.error("Update user error:%s", e)
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
            self.logger.error("Delete user error:%s", e)
            self.conn.rollback()
            return False
        finally:
            cursor.close()

    # 插入物品数据
    def insert_item(self, item_time, item_type, item_img_name, item_location, item_description):
        cursor = self.conn.cursor()
        sql = "INSERT INTO Item(ItemTime, ItemType, ItemImgName, ItemLocation, ItemDescription) VALUES (%s, %s, %s, %s, %s)"
        try:
            cursor.execute(sql, (item_time, item_type, item_img_name, item_location, item_description))
            self.conn.commit()
            return cursor.lastrowid
        except Exception as e:
            self.logger.error("Insert item error:%s", e)
            self.conn.rollback()
            return None
        finally:
            cursor.close()

    # 插入帖子数据
    def insert_post(self, post_id, student_id, item_id, is_for_lost, available, post_date, contact):
        post = "LostPost" if is_for_lost else "FoundPost"
        cursor = self.conn.cursor()
        sql = f"INSERT INTO {post}(PostID, StudentID, ItemID, Available, PostTime, Contact) VALUES (%s, %s, %s, %s, %s, %s)"
        try:
            cursor.execute(sql, (post_id, student_id, item_id, available, post_date, contact))
            self.conn.commit()
            return cursor.lastrowid
        except Exception as e:
            self.logger.error("Insert post error:%s", e)
            self.conn.rollback()
            return None
        finally:
            cursor.close() 
            
    # 更新帖子状态
    def update_post(self, post_id, is_for_lost):
        cursor = self.conn.cursor()
        if is_for_lost:
            sql = "UPDATE LostPost SET Available=False WHERE PostID=%s"
        else:
            sql = "UPDATE FoundPost SET Available=False WHERE PostID=%s"
        try:
            cursor.execute(sql, post_id)
            self.conn.commit()
            return True
        except Exception as e:
            self.logger.error("Update post status error:%s", e)
            self.conn.rollback()
            return False
        finally:
            cursor.close() 

    # 查询帖子数据
    def query_post_by10(self, is_for_lost):
        if is_for_lost:
            sql = "SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime, LostPost.Contact FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID\
        AND LostPost.Available = True ORDER BY PostTime LIMIT 10"
        else:
            sql = "SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime, FoundPost.Contact FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID\
        AND FoundPost.Available = True ORDER BY PostTime LIMIT 10"
        
        cursor = self.conn.cursor()
        
        try:
            cursor.execute(sql)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:%s", e)
            return None
        finally:
            cursor.close()
            
    def query_post_by_ids(self, related_ids, is_for_lost):
        cursor = self.conn.cursor()
        if len(related_ids) == 1:      
            if is_for_lost:
                sql = f"SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
                LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime, LostPost.Contact FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID \
                AND LostPost.Available = True AND PostID = %s ORDER BY PostTime"
            else:
                sql = f"SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
                FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime, FoundPost.Contact FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID \
                AND FoundPost.Available = True AND PostID = %s  ORDER BY PostTime"
        else:
            if is_for_lost:
                sql = f"SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
                LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime, LostPost.Contact FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID \
                AND LostPost.Available = True AND PostID IN {tuple(related_ids)} ORDER BY PostTime"
            else:
                sql = f"SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
                FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime, FoundPost.Contact FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID \
                AND FoundPost.Available = True AND PostID IN {tuple(related_ids)} ORDER BY PostTime"
        try:
            if len(related_ids) == 1: 
                cursor.execute(sql, related_ids[0])
            else:
                cursor.execute(sql)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:%s", e)
            return None
        finally:
            cursor.close()
            
    def query_post_by_id(self, related_id, is_for_lost):
        cursor = self.conn.cursor()
        if is_for_lost:
            sql = f"SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
            LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime, LostPost.Contact FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID \
            AND PostID = %s"
        else:
            sql = f"SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
            FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime, FoundPost.Contact FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID \
            AND PostID = %s"
        try:
            cursor.execute(sql, related_id)
            result = cursor.fetchone()
            return result
        except Exception as e:
            self.logger.error("Query post error:%s", e)
            return None
        finally:
            cursor.close()
            
    def query_next_post_by10(self, last_id, is_for_lost):
        cursor = self.conn.cursor()
        if is_for_lost:
            sql = f"SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID\
        AND LostPost.Available = True AND LostPost.PostTime > (SELECT PostTime FROM LostPost WHERE PostID = %s) ORDER BY PostTime LIMIT 10"
        else:
            sql = f"SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID\
        AND FoundPost.Available = True AND FoundPost.PostTime > (SELECT PostTime FROM FoundPost WHERE PostID = %s) ORDER BY PostTime LIMIT 10"
        try:
            cursor.execute(sql, last_id)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:%s", e)
            return None
        finally:
            cursor.close()
    
    def query_prev_post_by10(self, last_id, is_for_lost):
        cursor = self.conn.cursor()
        if is_for_lost:
            sql = f"SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID\
        AND LostPost.Available = True AND LostPost.PostTime < (SELECT PostTime FROM LostPost WHERE PostID = %s) ORDER BY PostTime LIMIT 10"
        else:
            sql = f"SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID\
        AND FoundPost.Available = True AND FoundPost.PostTime < (SELECT PostTime FROM FoundPost WHERE PostID = %s) ORDER BY PostTime LIMIT 10"
        try:
            cursor.execute(sql, last_id)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:%s", e)
            return None
        finally:
            cursor.close()
            
    def query_history_posts_of_user(self, user_id, is_for_lost):
        cursor = self.conn.cursor()
        if is_for_lost:
            sql = f"SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID\
        WHERE LostPost.StudentID={user_id}"
        else:
            sql = f"SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID\
        WHERE LostPost.StudentID={user_id}"
        try:
            cursor.execute(sql)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:%s", e)
            return None
        finally:
            cursor.close()

    def query_next_post_by10_of_user(self, last_id, is_for_lost, user_id):
        cursor = self.conn.cursor()
        if is_for_lost:
            sql = f"SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID\
        AND LostPost.StudentID = {user_id} AND LostPost.PostTime > (SELECT PostTime FROM LostPost WHERE PostID = %s) ORDER BY PostTime LIMIT 10"
        else:
            sql = f"SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID\
        AND FoundPost.StudentID = {user_id} AND FoundPost.PostTime > (SELECT PostTime FROM FoundPost WHERE PostID = %s) ORDER BY PostTime LIMIT 10"
        try:
            cursor.execute(sql, last_id)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:%s", e)
            return None
        finally:
            cursor.close()
    
    def query_prev_post_by10_of_user(self, last_id, is_for_lost, user_id):
        cursor = self.conn.cursor()
        if is_for_lost:
            sql = f"SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID\
        AND LostPost.StudentID = {user_id} AND LostPost.PostTime < (SELECT PostTime FROM LostPost WHERE PostID = %s) ORDER BY PostTime LIMIT 10"
        else:
            sql = f"SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID\
        AND FoundPost.StudentID = {user_id} AND FoundPost.PostTime < (SELECT PostTime FROM FoundPost WHERE PostID = %s) ORDER BY PostTime LIMIT 10"
        try:
            cursor.execute(sql, last_id)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:%s", e)
            return None
        finally:
            cursor.close()

    def query_post_by10_of_user(self, is_for_lost, user_id):
        if is_for_lost:
            sql = "SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime, LostPost.Contact FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID\
        AND LostPost.StudentID = %s ORDER BY PostTime LIMIT 10"
        else:
            sql = "SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
        FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime, FoundPost.Contact FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID\
        AND FoundPost.StudentID = %s ORDER BY PostTime LIMIT 10"
        
        cursor = self.conn.cursor()
        
        try:
            cursor.execute(sql, user_id)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:%s", e)
            return None
        finally:
            cursor.close()
            
    def query_post_by_ids_of_user(self, related_ids, is_for_lost, user_id):
        cursor = self.conn.cursor()
        if len(related_ids) == 1:      
            if is_for_lost:
                sql = f"SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
                LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime, LostPost.Contact FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID \
                AND LostPost.StudentID = {user_id} PostID = %s ORDER BY PostTime"
            else:
                sql = f"SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
                FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime, FoundPost.Contact FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID \
                AND FoundPost.StudentID = {user_id} AND PostID = %s  ORDER BY PostTime"
        else:
            if is_for_lost:
                sql = f"SELECT LostPost.PostID, LostPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
                LostPost.Available, Item.ItemTime, Item.ItemDescription, LostPost.PostTime, LostPost.Contact FROM LostPost, Item WHERE LostPost.ItemID=Item.ItemID \
                AND LostPost.StudentID = {user_id} AND PostID IN {tuple(related_ids)} ORDER BY PostTime"
            else:
                sql = f"SELECT FoundPost.PostID, FoundPost.StudentID, Item.ItemImgName, Item.ItemType, Item.ItemLocation,\
                FoundPost.Available, Item.ItemTime, Item.ItemDescription, FoundPost.PostTime, FoundPost.Contact FROM FoundPost, Item WHERE FoundPost.ItemID=Item.ItemID \
                AND FoundPost.StudentID = {user_id} AND PostID IN {tuple(related_ids)} ORDER BY PostTime"
        try:
            if len(related_ids) == 1: 
                cursor.execute(sql, related_ids[0])
            else:
                cursor.execute(sql)
            result = cursor.fetchall()
            return result
        except Exception as e:
            self.logger.error("Query post error:%s", e)
            return None
        finally:
            cursor.close()