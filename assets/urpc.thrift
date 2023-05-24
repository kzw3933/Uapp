namespace py urpc

struct RegisterInfo {
    1: string student_id;
    2: string username;
    3: string email;
    4: string password;
    5: string contact;//新增字段：联系方式
    6: binary headshot;//新增字段：用户头像
}

struct LoginInfo {
    1: string student_id;
    2: string password;
}

//新增数据类型：用于修改用户信息，student_id指明用户，2~6字段是要修改的内容，which指明本次修改2~6中的哪一个(一次请求只修改一个)
struct SetUserInfo {
    1: string student_id;
    2: string username;
    3: string email;
    4: string password;
    5: string contact;
    6: binary headshot;
    7: i32 which;
}

struct PostInfo {
    1: string post_id;
    2: string student_id;
    3: bool for_lost_item;
    4: binary thumbnail;//新增字段：缩略图
    5: binary item_image;//完整图
    6: string image_name;
    7: string item_type;
    8: string item_position;
    9: bool status;
    10: i64 lost_time;
    11: string item_desc="";
    12: i64 date;
    13: string contact;
}


//新增数据类型：缩略信息
struct AbbrInfo {
    1: string post_id;
    2: string student_id;
    3: bool for_lost_item;
    4: binary thumbnail;//缩略图
    5: string image_name;
    6: string item_type;
    7: string item_position;
    8: bool status;
    9: i64 lost_time;
    10: string item_desc="";
    11: i64 date;
    12: string contact;
}

//新增数据类型：请求信息
struct ReqInfo {
// 发送一个帖子id、for_lost_item（标识lost还是found），返回一个DetailInfo
    1: string post_id;
    2: bool for_lost_item;
}

//新增数据类型：详情页信息
struct DetailInfo {
    1: binary item_image;
}

service UappService {
    bool register(1: RegisterInfo info);
    bool login(1: LoginInfo info);
    RegisterInfo getUserInfo(1: string student_id);
    bool uploadPost(1: PostInfo info);
    list<AbbrInfo> getPostBy10(1: bool for_lost_item);
    /* 下面四个函数是实现搜索、翻页所需的
    ** searchText：用户输入的查询信息，你需要在服务器端实现一个检索功能，匹配与查询信息相同、相近的物品
       post_id：帖子id
       是则返回true，否则返回false。我会根据这个信息判断是否可以翻页。
       比较麻烦的是，你需要基于用户的查询信息对应的数据库内容来返回信息，所以有一个searchText字段
       如果用户没有查询，只是在按时间排序的状态下翻页,则searchEnable字段为false
       如果post_id字段为空，则默认返回top 10（有无查询都如此）
       for_lost_item:标识查询板块。true：寻物启事板块。false：失物招领板块
    */
    list<AbbrInfo> searchNext10(1: string searchText,2: string post_id ,3: bool searchEnable, 4: bool for_lost_item)//向后翻页，返回10条信息
    list<AbbrInfo> searchPrev10(1: string searchText,2: string post_id ,3: bool searchEnable, 4: bool for_lost_item)//向前翻页，返回10条信息
    
    DetailInfo reqDetail(1: ReqInfo req_info);
    bool setUserInfo(1:SetUserInfo set_user_info);
    bool setPostInfoFound(1:string post_id,2:bool for_lost_item);
    list<AbbrInfo> getAllPostHistory(1: string student_id,2: bool for_lost_item);
    list<AbbrInfo> historyNext10(1: string searchText,2: string post_id ,3: bool searchEnable, 4: bool for_lost_item, 5: string poster_id)//向后翻页，返回10条信息
    list<AbbrInfo> historyPrev10(1: string searchText,2: string post_id ,3: bool searchEnable, 4: bool for_lost_item, 5: string poster_id)//向前翻页，返回10条信息
}