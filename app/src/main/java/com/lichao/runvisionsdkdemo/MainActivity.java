package com.lichao.runvisionsdkdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lichao.http.CallDeleteTemplate;
import com.lichao.http.CallInsertTemplate;
import com.lichao.http.CallQueryRecord;
import com.lichao.http.CallQueryTemplate;
import com.lichao.http.CallSettingServer;
import com.lichao.utils.ImageProcessHelper;
import com.lichao.utils.ImageUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    /**
     * 请求权限的请求码
     */
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    /**
     * 请求选择本地图片文件的请求码
     */
    private static final int ACTION_CHOOSE_IMAGE = 0x201;

    /**
     * 被处理的图片
     */
    private Bitmap mBitmap = null;
    private ImageProcessHelper imageProcessHelper;

    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.bt_add)
    Button btAdd;
    @BindView(R.id.bt_delete)
    Button btDelete;
    @BindView(R.id.bt_select)
    Button btSelect;
    @BindView(R.id.bt_query_record)
    Button btQueryRecord;
    @BindView(R.id.bt_base64)
    Button btBase64;
    @BindView(R.id.iv_image)
    ImageView ivImage;

    /**
     * 所需的所有权限信息
     */
    private static String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        imageProcessHelper = ImageProcessHelper.getInstance();

        /**
         * 在选择图片的时候，在android 7.0及以上通过FileProvider获取Uri，不需要文件权限
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            List<String> permissionList = new ArrayList<>(Arrays.asList(NEEDED_PERMISSIONS));
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            NEEDED_PERMISSIONS = permissionList.toArray(new String[0]);
        }

        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
        }
    }

    private void initView() {
        ivImage.setImageResource(R.mipmap.faces);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_CHOOSE_IMAGE) {
            if (data == null || data.getData() == null) {
                Toast.makeText(this, R.string.get_picture_failed, Toast.LENGTH_SHORT).show();
                return;
            }
            mBitmap = ImageUtil.getBitmapFromUri(data.getData(), this);
            if (mBitmap == null) {
                Toast.makeText(this, R.string.get_picture_failed, Toast.LENGTH_SHORT).show();
                return;
            }
            ivImage.setImageBitmap(mBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= grantResult == PackageManager.PERMISSION_GRANTED;
            }
            if (!isAllGranted) {
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @OnClick({R.id.bt_query, R.id.bt_add, R.id.bt_delete, R.id.bt_select, R.id.bt_query_record, R.id.bt_base64})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_query:
                CallQueryTemplate.newQueryTemp("", "");
                break;
            case R.id.bt_add:
                CallInsertTemplate.insertTemplate(bitmaptoString(mBitmap));
                break;
            case R.id.bt_delete:
                CallDeleteTemplate.deleteTemplate("431023199003145114");
                break;
            case R.id.bt_select:
                chooseLocalImage(view);
                break;
            case R.id.bt_query_record:
                CallQueryRecord.queryRecord("李超", "2222222");
                break;
            case R.id.bt_base64:
                CallSettingServer.settingServer("192.168.1.195", "8088", "guns/getAttendance", "guns/heartbeat", "guns/imgreg");
//                ivImage.setImageBitmap(imageProcessHelper.brightenBitmap(mBitmap, 50));
                break;
        }
    }

    /**
     * 权限检测
     *
     * @param neededPermissions 所需的所有权限
     * @return 是否检测通过
     */
    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    /**
     * 从本地选择文件
     *
     * @param view
     */
    public void chooseLocalImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, ACTION_CHOOSE_IMAGE);
    }

    public String bitmaptoString(Bitmap bitmap) {
        //将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Base64转图片(去掉字符串的data:image/png;base64,)
     *
     * @param string
     * @return
     */
    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * base64编码字符集转化成图片文件。
     *
     * @param base64Str
     * @param path      文件存储路径
     * @return 是否成功
     */
    public static boolean base64ToFile(String base64Str, String path) {
        byte[] data = Base64.decode(base64Str, Base64.DEFAULT);
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                //调整异常数据
                data[i] += 256;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            os.write(data);
            os.flush();
            os.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
