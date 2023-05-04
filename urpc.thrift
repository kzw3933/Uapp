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
    1: string student_id;
    2: bool for_lost_item;
    3: binary item_image;
    4: string image_name;
    5: string item_type;
    6: string item_position;
    7: bool status;
    8: i64 lost_time;
    9: string item_desc="";
    10: i64 date;
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
    list<PostInfo> getPostBy10();
    bool uploadReply(1: ReplyInfo info);
    list<ReplyInfo> getAllReply(1: i32 post_id);
}