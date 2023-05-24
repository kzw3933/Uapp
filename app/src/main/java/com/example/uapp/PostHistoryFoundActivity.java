package com.example.uapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uapp.config.Config;
import com.example.uapp.item.LostItem;
import com.example.uapp.item.LostItemAdapter;
import com.example.uapp.thr.AbbrInfo;
import com.example.uapp.thr.UappService;
import com.example.uapp.utils.AppearanceUtils;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.litepal.LitePal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class PostHistoryFoundActivity extends AppCompatActivity {
    private List<LostItem> lostItemList = new ArrayList<>();
    private androidx.appcompat.widget.SearchView searchView;
    private String searchText;
    private String firstPostId = "";
    private String lastPostId = "";
    private Boolean onSearch = false;//表示当前是否在搜索中
    private long debug_lost_time;
    private long debug_post_time;
    private String sno;
    private SharedPreferences pref;

    private SwipeRefreshLayout swipeRefreshLayout;

    private UappService.Client UappServiceClient;
    private void initializeUappServiceClient() throws TException {
        // 创建TTransport对象
        TTransport transport = new TSocket(getString(R.string.ip), getResources().getInteger(R.integer.port));
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
    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_found);
        //导航栏及菜单
        Toolbar toolbar = findViewById(R.id.toolbar);
        AppCompatActivity activity = this;
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("'失物招领'发帖记录");
        toolbar.setBackgroundColor(getResources().getColor(Config.themeColor));
        toolbar.setTitleTextColor(getResources().getColor(Config.themeColor_Text));
        //获取个人信息
        pref = getSharedPreferences("login_info", Context.MODE_PRIVATE);
        sno = pref.getString("sno","");
        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.lost_menu, menu);
                MenuItem searchItem = menu.findItem(R.id.action_search);
                searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
                // ************* 搜索 *************
                searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        // 提交文本事件
                        searchText = query;
                        onSearch = true;
                        lostItemList.clear();
                        new searchTask().execute();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        // 在此处处理文本变化的事件
                        String text = newText;
                        // 处理文本
                        return false;
                    }
                });
            }
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                onSearch = false;
                new PostHistoryFoundActivity.getPostTask().execute();
                return true;
            }
        });


        // 设置下拉刷新的监听器
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lostItemList.clear();
                new PostHistoryFoundActivity.getPostTask().execute();
//                swipeRefreshLayout.setRefreshing(false);
            }
        });

        try {
            initLostItem();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        LostItemAdapter adapter = new LostItemAdapter(PostHistoryFoundActivity.this,
                R.layout.lost_item,lostItemList);
        ListView listview = findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 处理ListView的点击事件
                // position 是所选中的项目在ListView中的位置
                Intent intent = new Intent(PostHistoryFoundActivity.this, FoundItemDetailActivity.class);
                // 添加要传递的数据
                LostItem lostItem = lostItemList.get(position);
                intent.putExtra("itemName", lostItem.getName());
                intent.putExtra("postId",lostItem.getPostId());
                intent.putExtra("lostTime", lostItem.getLostTime().getTime());
                intent.putExtra("lostPos", lostItem.getPos());
                intent.putExtra("contact", lostItem.getPosterId());
                intent.putExtra("description", lostItem.getDesc());
                intent.putExtra("imagePath", lostItem.getImagePath());
                intent.putExtra("sno", lostItem.getPosterId());
                intent.putExtra("contact", lostItem.getContact());
                intent.putExtra("status", lostItem.getState());
                // 启动活动
                startActivity(intent);
            }
        });



        // *********** 翻页 *************
        Button btn_next = findViewById(R.id.btn_next);
        Button btn_prev = findViewById(R.id.btn_prev);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lastPostId.isEmpty()){
                    showMsg("无法翻页！");
                }else {
                    new turnPageTask().execute(true);
                }
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstPostId.isEmpty()){
                    showMsg("无法翻页！");
                }else {
                    new turnPageTask().execute(false);
                }
            }
        });

    }

    private void initLostItem() throws ParseException {
        List<LostItem> lostItems = LitePal.findAll(LostItem.class);
        for(int i=0; i<1; i++){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                for(LostItem lostItem : lostItems) {
//                    LostItem bike = new LostItem(lostItem.getName(), lostItem.getImageId(), lostItem.getLostTime(), lostItem.getPos());
                    lostItemList.add(lostItem);
                }
            }
        }
    }


    // ************* 下拉刷新线程 **************
    private class getPostTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            // 创建Item对象
            try {
                initializeUappServiceClient();
                List<AbbrInfo> AbbrInfos = UappServiceClient.historyNext10(searchText,"",false,false,sno);
                for(AbbrInfo abbrInfo:AbbrInfos){
                    //判断图片是否存在,若不存在,则创建图片
                    String imageName = abbrInfo.getImage_name();
                    File file_full = new File(getFilesDir(), imageName);
                    // 去掉文件后缀
                    int dotIndex = imageName.lastIndexOf(".");
                    if (dotIndex > 0 && dotIndex < imageName.length() - 1) {
                        imageName = imageName.substring(0, dotIndex);
                    }
                    imageName = imageName + "_thumbnail.jpg";
                    File file = new File(getFilesDir(), imageName);
                    if (!file.exists()) {
                        try {
                            byte [] image = abbrInfo.getThumbnail();
                            // 将 byte[] 数据转换为 Bitmap
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                            // 将 Bitmap 保存为文件
                            FileOutputStream outputStream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Date lost_time = new Date(abbrInfo.getLost_time());
                    Date post_time = new Date(abbrInfo.getDate());
                    debug_lost_time = abbrInfo.getLost_time();
                    debug_post_time = abbrInfo.getDate();
                    LostItem lostItem = new LostItem(abbrInfo.getItem_type(),
                            file_full.getPath(),
                            lost_time,
                            abbrInfo.getItem_position(),
                            abbrInfo.getStudent_id(),
                            post_time,
                            abbrInfo.isStatus(),
                            abbrInfo.getPost_id(),
                            abbrInfo.getItem_desc(),
                            abbrInfo.getContact()
                    );
                    lostItemList.add(lostItem);
                }

            } catch (TException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result){
            if(result){
                //重新载入lostItemList
                LostItemAdapter adapter = new LostItemAdapter(PostHistoryFoundActivity.this,
                        R.layout.lost_item,lostItemList);
                ListView listview = findViewById(R.id.listview);
                listview.setAdapter(adapter);
                //获取首尾帖子id
                if (lostItemList == null || lostItemList.isEmpty()) {
                    // List<AbbrInfo> AbbrInfos 为空
                    firstPostId = "";
                    lastPostId = "";
                }else {
                    firstPostId = lostItemList.get(0).getPostId();
                    lastPostId = lostItemList.get(lostItemList.size() - 1).getPostId();
                }
                showMsg("刷新成功");
                Log.d("========== debug_lost_time ==========", Long.toString(debug_lost_time));
                Log.d("========== debug_post_time ==========", Long.toString(debug_post_time));
            }
            else{
                showMsg("刷新失败");
            }
            closeItemServiceClient();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    // ************* 搜索线程 **************
    private class searchTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            // 创建Item对象
            try {
                initializeUappServiceClient();
                List<AbbrInfo> AbbrInfos = UappServiceClient.historyNext10(searchText,"",true,false,sno);
                Log.d("=======debug_AbbrInfos=======", " "+AbbrInfos.size());
                for(AbbrInfo abbrInfo:AbbrInfos){
                    //判断图片是否存在,若不存在,则创建图片
                    String imageName = abbrInfo.getImage_name();
                    File file_full = new File(getFilesDir(), imageName);
                    // 去掉文件后缀
                    int dotIndex = imageName.lastIndexOf(".");
                    if (dotIndex > 0 && dotIndex < imageName.length() - 1) {
                        imageName = imageName.substring(0, dotIndex);
                    }
                    imageName = imageName + "_thumbnail.jpg";
                    File file = new File(getFilesDir(), imageName);
                    if (!file.exists()) {
                        try {
                            byte [] image = abbrInfo.getThumbnail();
                            // 将 byte[] 数据转换为 Bitmap
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                            // 将 Bitmap 保存为文件
                            FileOutputStream outputStream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Date lost_time = new Date(abbrInfo.getLost_time());
                    Date post_time = new Date(abbrInfo.getDate());
                    debug_lost_time = abbrInfo.getLost_time();
                    debug_post_time = abbrInfo.getDate();
                    LostItem lostItem = new LostItem(abbrInfo.getItem_type(),
                            file_full.getPath(),
                            lost_time,
                            abbrInfo.getItem_position(),
                            abbrInfo.getStudent_id(),
                            post_time,
                            abbrInfo.isStatus(),
                            abbrInfo.getPost_id(),
                            abbrInfo.getItem_desc(),
                            abbrInfo.getContact()
                    );
                    lostItemList.add(lostItem);
                    Log.d("=======debug_add(lostItem)=======", " "+file_full.getPath());
                    Log.d("=======debug_add(lostItem)=======", " "+abbrInfo.getItem_type());
                }

            } catch (TException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result){
            if(result){
                //重新载入lostItemList
                LostItemAdapter adapter = new LostItemAdapter(PostHistoryFoundActivity.this,
                        R.layout.lost_item,lostItemList);
                ListView listview = findViewById(R.id.listview);
                listview.setAdapter(adapter);
                if (lostItemList == null || lostItemList.isEmpty()) {
                    // List<AbbrInfo> AbbrInfos 为空
                    firstPostId = "";
                    lastPostId = "";
                }else {
                    firstPostId = lostItemList.get(0).getPostId();
                    lastPostId = lostItemList.get(lostItemList.size() - 1).getPostId();
                }
                //设置搜索状态
                onSearch = true;
                showMsg("搜索成功");
            }
            else{
                showMsg("搜索失败");
            }
            closeItemServiceClient();
        }
    }

    // ************* 翻页线程 **************
    private class turnPageTask extends AsyncTask<Boolean, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            // 创建Item对象
            try {
                Boolean isNext = booleans[0];
                initializeUappServiceClient();
                List<AbbrInfo> AbbrInfos;
                if(isNext){//NextPage
                    AbbrInfos = UappServiceClient.historyNext10(searchText,lastPostId,onSearch, false,sno);
                }
                else{//PrevPage
                    AbbrInfos = UappServiceClient.historyPrev10(searchText,firstPostId,onSearch,false,sno);
                }
                Log.d("=======debug_AbbrInfos=======", " "+AbbrInfos.size());
                if (AbbrInfos == null || AbbrInfos.isEmpty()) {
                    // List<AbbrInfo> AbbrInfos 为空
//                    firstPostId = "";
//                    lastPostId = "";
                    return false;
                }
                lostItemList.clear();
                for(AbbrInfo abbrInfo:AbbrInfos){
                    //判断图片是否存在,若不存在,则创建图片
                    String imageName = abbrInfo.getImage_name();
                    File file_full = new File(getFilesDir(), imageName);
                    // 去掉文件后缀
                    int dotIndex = imageName.lastIndexOf(".");
                    if (dotIndex > 0 && dotIndex < imageName.length() - 1) {
                        imageName = imageName.substring(0, dotIndex);
                    }
                    imageName = imageName + "_thumbnail.jpg";
                    File file = new File(getFilesDir(), imageName);
                    if (!file.exists()) {
                        try {
                            byte [] image = abbrInfo.getThumbnail();
                            // 将 byte[] 数据转换为 Bitmap
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                            // 将 Bitmap 保存为文件
                            FileOutputStream outputStream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Date lost_time = new Date(abbrInfo.getLost_time());
                    Date post_time = new Date(abbrInfo.getDate());
                    debug_lost_time = abbrInfo.getLost_time();
                    debug_post_time = abbrInfo.getDate();
                    LostItem lostItem = new LostItem(abbrInfo.getItem_type(),
                            file_full.getPath(),
                            lost_time,
                            abbrInfo.getItem_position(),
                            abbrInfo.getStudent_id(),
                            post_time,
                            abbrInfo.isStatus(),
                            abbrInfo.getPost_id(),
                            abbrInfo.getItem_desc(),
                            abbrInfo.getContact()
                    );
                    lostItemList.add(lostItem);
                    Log.d("=======debug_add(lostItem)=======", " "+file_full.getPath());
                    Log.d("=======debug_add(lostItem)=======", " "+abbrInfo.getItem_type());
                }

            } catch (TException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result){
            if(result){
                //重新载入lostItemList
                LostItemAdapter adapter = new LostItemAdapter(PostHistoryFoundActivity.this,
                        R.layout.lost_item,lostItemList);
                ListView listview = findViewById(R.id.listview);
                listview.setAdapter(adapter);
                //获取首尾帖子id
                if(lostItemList.isEmpty()){
                    firstPostId = "";
                    lastPostId = "";
                }else {
                    firstPostId = lostItemList.get(0).getPostId();
                    lastPostId = lostItemList.get(lostItemList.size() - 1).getPostId();
                }
                //设置搜索状态
//                onSearch = true;
                showMsg("翻页成功");
            }
            else{
                showMsg("当前页面无法翻页");
//                Log.d("=======debug_page=======", " "+);
            }
            closeItemServiceClient();
        }
    }

    protected void onStart() {
        super.onStart();
        ViewGroup rootView = findViewById(android.R.id.content);
        ViewTreeObserver observer = rootView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pref = getSharedPreferences("login_info", Context.MODE_PRIVATE);
                if(pref.getBoolean("careMode",false)){
                    //关怀模式
                    AppearanceUtils.increaseFontSize(rootView,1.25f);
                }
                // 移除监听器，避免重复回调
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
