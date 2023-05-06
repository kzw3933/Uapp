package com.example.uapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uapp.item.LostItem;
import com.example.uapp.thr.PostInfo;
import com.example.uapp.thr.RegisterInfo;
import com.example.uapp.thr.UappService;
import com.example.uapp.utils.BitmapUtils;
import com.example.uapp.utils.CameraUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.litepal.LitePal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class PostLostActivity extends AppCompatActivity {
    //*********** item数据相关 ************
    private String itemName;
    private int imageId;
    private java.util.Date lostTime;
    private String pos;
    private String desc;
    //帖子信息
    private String posterId;        //pk
    private java.util.Date postTime; //pk
    private boolean state;
    private byte[] img;
    private String imgPath = null;
    private String imgName = null;
    //*********** 照片相关 ************
    //权限请求
    private RxPermissions rxPermissions;
    //是否拥有权限
    private boolean hasPermissions = false;
    //底部弹窗
    private BottomSheetDialog bottomSheetDialog;
    //弹窗视图
    private View bottomView;
    //存储拍完照后的图片
    private File outputImagePath;
    //启动相机标识
    public static final int TAKE_PHOTO = 1;
    //启动相册标识
    public static final int SELECT_PHOTO = 2;
    private String base64Pic;
    //拍照和相册获取图片的Bitmap
    private Bitmap orc_bitmap;
    //******************* 控件 *******************
    private Button btn_img;
    private ImageView iv_img;
    //******************* 控件 *******************
    private UappService.Client UappServiceClient;
    private void initializeUappServiceClient() throws TException {
        // 创建TTransport对象
        TTransport transport = new TSocket("202.38.72.73", 7860);
        // 创建TProtocol对象
        TProtocol protocol = new TBinaryProtocol(transport);
        // 创建ItemService.Client对象
        UappServiceClient = new UappService.Client(protocol);
        // 打开transport
        transport.open();
    }

    private void closeItemServiceClient() {
        if (UappServiceClient != null) {
            UappServiceClient.getInputProtocol().getTransport().close();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lost);
        LitePal.getDatabase();
        EditText et_item_name = findViewById(R.id.et_type);
        EditText et_pos = findViewById(R.id.et_pos);
        EditText et_desc = findViewById(R.id.et_desc);
        Button btn_post = findViewById(R.id.btn_post);
        EditText et_lost_time = findViewById(R.id.et_lost_time);

        btn_img = findViewById(R.id.btn_img);
        iv_img = findViewById(R.id.iv_img);
        et_lost_time.setFocusable(false);

        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkVersion();
                changeAvatar(view);
            }
        });

        //按下上传键
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.getDatabase();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    //获取输入框中的日期、丢失物品名、地点
                    //用户名
                    SharedPreferences pref = getSharedPreferences("login_info",MODE_PRIVATE);
                    posterId = pref.getString("username","匿名");
                    //物品名
                    if(TextUtils.isEmpty(et_item_name.getText().toString())){
                        Toast.makeText(PostLostActivity.this,"丢失物品名不能为空！",
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else{
                        itemName = et_item_name.getText().toString();
                    }
                    //地点
                    if(TextUtils.isEmpty(et_pos.getText().toString())){
                        Toast.makeText(PostLostActivity.this,"丢失地点不能为空！",
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else{
                        pos = et_pos.getText().toString();
                    }
                    //时间
                    if(TextUtils.isEmpty(et_lost_time.getText().toString())){//若未选择丢失时间，默认当天
                        lostTime = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
                    } else{
                        try {
                            lostTime = dateFormat.parse(et_lost_time.getText().toString());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    //描述
                    if(TextUtils.isEmpty(et_desc.getText().toString())){
                        desc = "";
                    }else {
                        desc = et_desc.getText().toString();
                    }
                    //上传时间
                    postTime = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
                    //state
                    state = false;


                    //设置数据，存入数据库
                    LostItem lostItem = new LostItem(itemName,imgPath,postTime,pos);
                    lostItem.setDesc(desc);
                    lostItem.setPosterId(posterId);
                    lostItem.setLostTime(lostTime);
                    lostItem.setPostTime(postTime);
                    lostItem.setState(state);
                    lostItem.setImageId(R.drawable.img_2);
                    lostItem.setPostId(postTime.toString() + posterId);
                    lostItem.save();

                    Log.d("MyApp", imgPath);

                    new PostLostActivity.PostLostTask().execute();
                }

            }
        });
        //用户点击EditText框，弹出calendar进行日期选择
        et_lost_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取当前日期
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                // 创建DatePickerDialog实例
                DatePickerDialog datePickerDialog = new DatePickerDialog(PostLostActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            // 处理用户选择的日期
                            String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                            et_lost_time.setText(date);
                        }
                    }, year, month, dayOfMonth);
                // 显示DatePickerDialog
                datePickerDialog.show();
            }
        });
    }

    // ************* 与服务器交互的新线程 *************
    private class PostLostTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            // 创建Item对象
            try {
                Boolean reactFromServer;
                initializeUappServiceClient();
                PostInfo lostItemInfo = new PostInfo();
                lostItemInfo.setItem_type(itemName);
                lostItemInfo.setFor_lost_item(true);
                lostItemInfo.setStudent_id(posterId);
                lostItemInfo.setItem_position(pos);
                lostItemInfo.setItem_desc(desc);
                lostItemInfo.setDate(postTime.getTime());
                lostItemInfo.setStatus(state);
                lostItemInfo.setLost_time(lostTime.getTime());
                lostItemInfo.setImage_name(imgName);
                // 读取图片为字节数组
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Path imagePath = Paths.get(imgPath);
                    byte[] imageBytes = Files.readAllBytes(imagePath);
                    lostItemInfo.setItem_image(imageBytes);
                }
                reactFromServer = UappServiceClient.uploadPost(lostItemInfo);
                return reactFromServer;
            } catch (TException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(Boolean result){
            if(result){
                Toast.makeText(PostLostActivity.this,"上传成功！",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(PostLostActivity.this,"上传失败",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }




    //************** 辅助函数 ***************
    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 检查版本
     */
    private void checkVersion() {
        //Android6.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rxPermissions = new RxPermissions(this);
            //权限请求
            rxPermissions.request(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {//申请成功
                            hasPermissions = true;
                            showMsg("已获取权限");
                        } else {//申请失败
                            showMsg("权限未开启");
                        }
                    });
        } else {
            //Android6.0以下
            showMsg("无需请求动态权限");
        }
    }


    /**
     * 拍照
     */
    private void takePhoto() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        SharedPreferences pref = getSharedPreferences("login_info",MODE_PRIVATE);
        posterId = pref.getString("username","null");
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss_");
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        String filename = timeStampFormat.format(new java.util.Date());
        outputImagePath = new File(storageDir,
                filename + posterId + ".jpg");
        imgName = filename + posterId + ".jpg";
        Intent takePhotoIntent = CameraUtils.getTakePhotoIntent(this, outputImagePath);

        // 开启一个带有返回值的Activity，请求码为TAKE_PHOTO
        startActivityForResult(takePhotoIntent, TAKE_PHOTO);
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }

        Intent selectPhotoIntent = CameraUtils.getSelectPhotoIntent();
        showMsg("openAlbum");
        startActivityForResult(selectPhotoIntent, SELECT_PHOTO);
    }

    /**
     * 通过图片路径显示图片
     */
    private void displayImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
            //显示图片
            Glide.with(this).load(imagePath).into(iv_img);
            //压缩图片
            orc_bitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));
            //转Base64
            base64Pic = BitmapUtils.bitmapToBase64(orc_bitmap);

        } else {
            showMsg("图片获取失败");
        }
    }


    /**
     * 返回到Activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //拍照后返回
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //显示图片
                    imgPath = outputImagePath.getAbsolutePath();
                    displayImage(imgPath);
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        //4.4及以上系统使用这个方法处理图片
                        imgPath = CameraUtils.getImageOnKitKatPath(data, this);
                    } else {
                        imgPath = CameraUtils.getImageBeforeKitKatPath(data, this);
                    }
                    //保存图片
                    saveSelectedImage(imgPath);
                    //显示图片
                    displayImage(imgPath);
                }
                break;
            default:
                break;
        }
    }
    public void changeAvatar(View view) {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        bottomSheetDialog.setContentView(bottomView);
        bottomSheetDialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
        TextView tvTakePictures = bottomView.findViewById(R.id.tv_take_pictures);
        TextView tvOpenAlbum = bottomView.findViewById(R.id.tv_open_album);
        TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);

        //拍照
        tvTakePictures.setOnClickListener(v -> {
            takePhoto();
            showMsg("拍照");
            bottomSheetDialog.cancel();
        });
        //打开相册
        tvOpenAlbum.setOnClickListener(v -> {
            openAlbum();
            showMsg("打开相册");
            bottomSheetDialog.cancel();
        });
        //取消
        tvCancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
        bottomSheetDialog.show();
    }


    public void saveSelectedImage(String imageUrl) {
        File file = new File(imageUrl);
        showMsg("saveSelectedImage");
        SharedPreferences pref = getSharedPreferences("login_info",MODE_PRIVATE);
        posterId = pref.getString("username","null");
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss_");
        String filename = timeStampFormat.format(new java.util.Date());
        try {
            InputStream inputStream = getContentResolver().openInputStream(Uri.fromFile(file));
            // 将输入流转换为Bitmap对象
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            // 获取应用程序私有文件夹
            File privateDir = getApplicationContext().getFilesDir();
            // 创建一个新的File对象，表示要保存的文件
            File savedFile = new File(privateDir, filename + posterId + ".jpg");
            // 使用FileOutputStream和compress()方法将Bitmap对象写入文件
            imgPath = privateDir + "/" + filename + posterId + ".jpg";
            imgName = filename + posterId + ".jpg";
            FileOutputStream outputStream = new FileOutputStream(savedFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

}
