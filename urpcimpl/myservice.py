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
        res = DBSERVER.insert_user(info.student_id, info.username, info.email, info.password)
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
            ALL_ONLINE_USERS.append(info.student_id)
            self.logger.info(f"User with student_id {info.student_id} login succeed")
            return True
        else:
            self.logger.error(f"User with student_id {info.student_id} login fail")
            return False

    def getUserInfo(self, student_id):

        if student_id in ALL_ONLINE_USERS:
            column = DBSERVER.query_user(student_id)
            if column:
                self.logger.info(f"User with student_id {student_id} get his or her user-information successfully")
                return RegisterInfo(column[0], column[1], column[2], column[3])
        
        self.logger.error(f"Error happend when user with student_id {student_id} get his or her user-information")
        return RegisterInfo()


    def uploadPost(self, info):
        student_id = info.student_id
        img_data = BytesIO(info.item_image)
        img = Image.open(img_data)
        img_path = ITEMS_IMG_ROOT+info.image_name+'.png'
        img.save(img_path, 'PNG')
        
        item_id = DBSERVER.insert_item(info.lost_time, info.item_type, img_path, info.image_name, info.item_position, info.item_desc)

        if item_id != None:
            res =  DBSERVER.insert_post(info.post_id, student_id, item_id, info.for_lost_item, info.status, info.date)
            if res != None:
                self.searcher.update(info.post_id, info.item_type, info.item_position, info.item_desc, info.for_lost_item)
                self.logger.info(f"User with student_id {info.student_id} upload a post successfully")
                return True
        self.logger.error(f"Error happend when user with student_id {student_id} upload post")
        return False
    
    def help_get_post(self, columns, is_for_lost):
        res = []
        if columns:
            for column in columns:
                try:
                    img = Image.open(column[3])
                    buffered = BytesIO()
                    img.save(buffered, format='PNG')
                    img_byte = buffered.getvalue()
                    res.append(PostInfo(column[0], column[1], is_for_lost, img_byte, column[3], column[4], column[5], column[6], column[7], column[8], column[9]))
                except Exception as e:
                    self.logger.error("Get posts error:", e)
                    return []
        return res
    
    def getPostBy10(self):
        columns = DBSERVER.query_post_by10()
        return self.help_get_post(columns)
    
    def searchNext10(self, searchText, post_id, searchEnable, for_lost_item):
        if searchEnable:
            related_post_ids = self.searcher.get_related_post_ids(searchText, for_lost_item)
            if post_id.strip():
                related_post_ids = related_post_ids[related_post_ids.index(post_id)+1:]
            if len(related_post_ids) > 10:
                related_post_ids = related_post_ids[:10]
            columns = DBSERVER.query_post_by_ids(related_post_ids, for_lost_item)
        else:
            columns = DBSERVER.query_next_post_by10(post_id, for_lost_item)
            
        return self.help_get_post(columns, for_lost_item)
            
            
    
    def searchPrev10(self, searchText, post_id, searchEnable, for_lost_item):
        if searchEnable:
            related_post_ids = self.get_related_post_ids(searchText, for_lost_item)
            if post_id.strip():
                related_post_ids = related_post_ids[:related_post_ids.index(post_id)]
            if len(related_post_ids) > 10:
                related_post_ids = related_post_ids[-10:]
            columns = DBSERVER.query_post_by_ids(related_post_ids, for_lost_item)
        else:
            columns = DBSERVER.query_prev_post_by10(post_id, for_lost_item)
            
        return self.help_get_post(columns, for_lost_item)

    def getAllReply(self, id):
        pass
    
    def uploadReply(self, info):
        pass

