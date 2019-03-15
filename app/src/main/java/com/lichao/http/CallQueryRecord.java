package com.lichao.http;

import android.util.Log;

import com.lichao.utils.Const;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class CallQueryRecord {
    private static String TAG = "CallQueryRecord";

    /**
     * 查询访客记录
     * @param workNo
     */
    public static void queryRecord(String name, String workNo) {
        OkHttpUtils.post()
                .url(Const.QUERY_RECORED)
                .addParams("fcmp_name", name)
                .addParams("fcmp_workNo", workNo)
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
