package com.lichao.http;

import android.util.Log;

import com.lichao.utils.Const;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class CallDeleteTemplate {
    private static String TAG = "CallDeleteTemplate";

    /**
     * 删除人脸模板
     * @param workNo
     */
    public static void deleteTemplate(String workNo) {
        OkHttpUtils.post()
                .url(Const.DELETE_TEMP)
                .addParams("workNo", workNo)
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
