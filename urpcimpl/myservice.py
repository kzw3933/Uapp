from config import *
import urpc.UappService as UService
from urpc.ttypes import *
from io import BytesIO
from PIL import Image
import datetime


class UappService(UService.Iface):
    
    def bindLogger(self, logger):
        self.logger = logger

    def register(self, info):
        res = DBSERVER.insert_user(info.student_id, info.username, info.email, info.password)
        if res:
            self.logger.info(f"User {info.username} register successful")
            return True
        else:
            self.logger.error(f"Error happen wher user {info.username} register") 
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
        
        lost_time = datetime.datetime.fromtimestamp(info.lost_time / 10**9).strftime('%Y-%m-%d %H:%M:%S.%f')[:-3]
        post_date = datetime.datetime.fromtimestamp(info.date / 10**9).strftime('%Y-%m-%d %H:%M:%S.%f')[:-3]
        
        item_id = DBSERVER.insert_item(lost_time, info.item_type, img_path, info.image_name, info.item_position, info.item_desc)

        if item_id:
            res =  DBSERVER.insert_post(student_id, item_id, info.for_lost_item, info.status, post_date)
            if res:
                self.logger.info(f"User with student_id {info.student_id} upload a post successfully")
                return True
        self.logger.error(f"Error happend when user with student_id {student_id} upload post")
        return False



    def uploadReply(self, info):
        student_id = info.student_id
        if student_id in ALL_ONLINE_USERS:
            return DBSERVER.insert_reply(info.post_id, student_id, info.content)

        return False
    
    def getPostBy10(self):
        res = []
        columns = DBSERVER.query_post_by10()
        if columns:
            for column in columns:
                try:
                    img = Image.open(column[2])

                    buffered = BytesIO()
                    img.save(buffered, format='PNG')
                    img_byte = buffered.getvalue()
                    res.append(PostInfo(column[0], column[1], img_byte, column[3], column[4], column[5], column[6], column[7], column[8], column[9]))
                except Exception as e:
                    self.logger.error("Get posts error:", e)
                    return []
        return res

    def getAllReply(self, id):
        pass