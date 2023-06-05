from config import *
import urpc.UappService as UService
from urpc.ttypes import *
from io import BytesIO
from PIL import Image


class UappService(UService.Iface):
    
    def bindLogger(self, logger):
        self.logger = logger
        
    def bindSearcher(self, searcher):
        self.searcher = searcher

    def register(self, info):
        
        res = DBSERVER.insert_user(info.student_id, info.username, info.email, info.password, info.contact)
        if res:
            self.logger.info(f"User {info.username} register successful")
            return True
        else:
            self.logger.error(f"Error happen when user {info.username} register") 
            return False


    def login(self, info):
        
        column = DBSERVER.query_user(info.student_id)
        password = column[3]

        if password == info.password:
            self.logger.info(f"User with student_id {info.student_id} login succeed")
            return True
        else:
            self.logger.error(f"User with student_id {info.student_id} login fail")
            return False

    def getUserInfo(self, student_id):
        
        column = DBSERVER.query_user(student_id)
        if column:
            try:
                img = Image.open(AVATAR_IMG_ROOT+column[0]+".jpg")
                buffered = BytesIO()
                img.save(buffered, format='JPEG')
                img_byte = buffered.getvalue()
            except Exception as e:
                img_byte = b''
            self.logger.info(f"User with student_id {student_id} get his or her user-information successfully")
            return RegisterInfo(column[0], column[1], column[2], column[3], column[4], img_byte)
        
        self.logger.error(f"Error happend when user with student_id {student_id} get his or her user-information")
        return RegisterInfo()


    def uploadPost(self, info):
        
        student_id = info.student_id
        
        full_img_data = BytesIO(info.item_image)
        full_img = Image.open(full_img_data)
        full_img_path = ITEMS_FULL_IMG_ROOT+info.image_name
        full_img.save(full_img_path, 'JPEG')
        
        thumb_img_data = BytesIO(info.thumbnail)    
        thumb_img = Image.open(thumb_img_data)        
        thumb_img_path = ITEMS_THUMB_IMG_ROOT+info.image_name       
        thumb_img.save(thumb_img_path, 'JPEG')
        
        item_id = DBSERVER.insert_item(info.lost_time, info.item_type, info.image_name, info.item_position, info.item_desc)

        if item_id != None:
            res =  DBSERVER.insert_post(info.post_id, student_id, item_id, info.for_lost_item, info.status, info.date, info.contact)
            if res != None:
                self.searcher.update(info.post_id, info.item_type, info.item_position, info.item_desc, info.for_lost_item)
                self.logger.info(f"User with student_id {info.student_id} upload a post successfully")
                return True
        self.logger.error(f"Error happend when user with student_id {student_id} upload post")
        return False
    
    def help_get_thumb_posts(self, columns, is_for_lost):
        res = []
        if columns:
            for column in columns:
                try:
                    img = Image.open(ITEMS_THUMB_IMG_ROOT+column[2])
                    buffered = BytesIO()
                    img.save(buffered, format='JPEG')
                    img_byte = buffered.getvalue()
                    res.append(AbbrInfo(column[0], column[1], is_for_lost, img_byte, column[2], column[3], column[4], column[5], column[6], column[7], column[8], column[9]))
                except Exception as e:
                    self.logger.error("Get thumb posts error:", e)
                    return []
        return res
    
    def help_get_full_post(self, column):
        if column:
            try:
                img = Image.open(ITEMS_FULL_IMG_ROOT+column[2])
                buffered = BytesIO()
                img.save(buffered, format='JPEG')
                img_byte = buffered.getvalue()
                return DetailInfo(img_byte)
            except Exception as e:
                self.logger.error("Get detail post error:", e)
                return DetailInfo()
                
        return DetailInfo()
    
    
    
    def getPostBy10(self, for_lost_item):
        
        columns = DBSERVER.query_post_by10(for_lost_item)
        return self.help_get_thumb_posts(columns, for_lost_item)
    
    def searchNext10(self, searchText, post_id, searchEnable, for_lost_item):
        
        if searchEnable:
            related_post_ids = self.searcher.get_related_post_ids(searchText, for_lost_item, True)
            if post_id.strip():
                related_post_ids = related_post_ids[related_post_ids.index(post_id)+1:]
            if len(related_post_ids) > 10:
                related_post_ids = related_post_ids[:10]
            if len(related_post_ids) == 0:
                return []
            columns = DBSERVER.query_post_by_ids(related_post_ids, for_lost_item)
        else:
            if post_id.strip():
                columns = DBSERVER.query_next_post_by10(post_id, for_lost_item)
            else:
                columns = DBSERVER.query_post_by10(for_lost_item)
            
        return self.help_get_thumb_posts(columns, for_lost_item)
            
            
    
    def searchPrev10(self, searchText, post_id, searchEnable, for_lost_item):
        
        if searchEnable:
            related_post_ids = self.searcher.get_related_post_ids(searchText, for_lost_item, True)
            if post_id.strip():
                related_post_ids = related_post_ids[:related_post_ids.index(post_id)]
            if len(related_post_ids) > 10:
                related_post_ids = related_post_ids[-10:]
            if len(related_post_ids) == 0:
                return []
            columns = DBSERVER.query_post_by_ids(related_post_ids, for_lost_item)
        else:
            if post_id.strip():
                columns = DBSERVER.query_prev_post_by10(post_id, for_lost_item)
            else:
                columns = DBSERVER.query_post_by10(for_lost_item)
            
        return self.help_get_thumb_posts(columns, for_lost_item)
    
    def reqDetail(self, req_info):
        
        column = DBSERVER.query_post_by_id(req_info.post_id, req_info.for_lost_item)
        return self.help_get_full_post(column)
    
    
    def setUserInfo(self, set_user_info):
        
        student_id = set_user_info.student_id
        res = False
        column = DBSERVER.query_user(student_id)
        target = set_user_info.which
        if target == 2:
            res = DBSERVER.update_user(column[0], set_user_info.username, column[2], column[3], column[4])
        elif target == 3:
            res = DBSERVER.update_user(column[0], column[1], set_user_info.email, column[3], column[4])
        elif target == 4:
            res = DBSERVER.update_user(column[0], column[1], column[2], set_user_info.password, column[4])
        elif target == 5:
            res = DBSERVER.update_user(column[0], column[1], column[2], column[3], set_user_info.contact)
        elif target == 6:
            res = True
            try:
                img_data = BytesIO(set_user_info.headshot)
                img = Image.open(img_data)
                img_path = AVATAR_IMG_ROOT+set_user_info.student_id+".jpg"
                img.save(img_path, 'JPEG')
            except Exception as e:
                res =  False     
        if not res:
            self.logger.error(f"Error happend when user with student_id {student_id} update user info")
        return res
    
    def setPostInfoFound(self, post_id, for_lost_item):
        
        return DBSERVER.update_post(post_id, for_lost_item)
    
    def getAllPostHistory(self, student_id, for_lost_item):
        
        columns = DBSERVER.query_history_posts_of_user(student_id, for_lost_item)
        return self.help_get_thumb_posts(columns, for_lost_item)
    
    def historyNext10(self, searchText, post_id, searchEnable, for_lost_item, poster_id):
        
        if searchEnable:
            related_post_ids = self.searcher.get_related_post_ids(searchText, for_lost_item, True)
            if post_id.strip():
                related_post_ids = related_post_ids[related_post_ids.index(post_id)+1:]
            if len(related_post_ids) > 10:
                related_post_ids = related_post_ids[:10]
            if len(related_post_ids) == 0:
                return []
            columns = DBSERVER.query_post_by_ids_of_user(related_post_ids, for_lost_item, poster_id)
        else:
            if post_id.strip():
                columns = DBSERVER.query_next_post_by10_of_user(post_id, for_lost_item, poster_id)
            else:
                columns = DBSERVER.query_post_by10_of_user(for_lost_item, poster_id)
            
        return self.help_get_thumb_posts(columns, for_lost_item)
        
        
    def historyPrev10(self, searchText, post_id, searchEnable, for_lost_item, poster_id):
        
        if searchEnable:
            related_post_ids = self.searcher.get_related_post_ids(searchText, for_lost_item)
            if post_id.strip():
                related_post_ids = related_post_ids[:related_post_ids.index(post_id)]
            if len(related_post_ids) > 10:
                related_post_ids = related_post_ids[-10:]
            if len(related_post_ids) == 0:
                return []
            columns = DBSERVER.query_post_by_ids_of_user(related_post_ids, for_lost_item, poster_id)
        else:
            if post_id.strip():
                columns = DBSERVER.query_prev_post_by10_of_user(post_id, for_lost_item, poster_id)
            else:
                columns = DBSERVER.query_post_by10_of_user(for_lost_item, poster_id)
            
        return self.help_get_thumb_posts(columns, for_lost_item)

    def uploadWords(self, words):
        self.searcher.expand_dictionary_by_words(words)
        return True
        
            

