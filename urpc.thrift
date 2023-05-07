namespace py urpc

struct RegisterInfo {
    1: string student_id;
    2: string username;
    3: string email;
    4: string password;
}

struct LoginInfo {
    1: string student_id;
    2: string password;
}

struct PostInfo {
    1: string post_id;
    2: string student_id;
    3: bool for_lost_item;
    4: binary item_image;
    5: string image_name;
    6: string item_type;
    7: string item_position;
    8: bool status;
    9: i64 lost_time;
    10: string item_desc="";
    11: i64 date;
}

struct ReplyInfo {
    1: string student_id;
    2: i32 post_id;
    3: string content;
    4: i64 date;
}

service UappService {
    bool register(1: RegisterInfo info);
    bool login(1: LoginInfo info);
    RegisterInfo getUserInfo(1: string student_id);
    bool uploadPost(1: PostInfo info);
    list<PostInfo> getPostBy10(1: bool for_lost_item);
    list<PostInfo> searchNext10(1: string searchText,2: string post_id ,3: bool searchEnable, 4: bool for_lost_item)//向后翻页，返回10条信息
    list<PostInfo> searchPrev10(1: string searchText,2: string post_id ,3: bool searchEnable, 4: bool for_lost_item)//向前翻页，返回10条信息
    bool uploadReply(1: ReplyInfo info);
    list<ReplyInfo> getAllReply(1: i32 post_id);
}