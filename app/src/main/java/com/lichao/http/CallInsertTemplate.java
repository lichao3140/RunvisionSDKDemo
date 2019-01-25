package com.lichao.http;

import android.util.Log;

import com.lichao.utils.Const;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class CallInsertTemplate {
    private static String TAG = "CallQueryTemplate";

    /**
     * 添加人脸模
     * @param bitmap
     */
    public static void insertTemplate(String bitmap) {
        OkHttpUtils.post()
                .url(Const.INSERT_TEMP)
                .addParams("name", "李超")
                .addParams("workNo", "431023199003145114")
                .addParams("img", bitmap)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i(TAG, "网络错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "success:" + response.toString());
                    }
                });
    }
}
