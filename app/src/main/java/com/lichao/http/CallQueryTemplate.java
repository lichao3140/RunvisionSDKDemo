package com.lichao.http;

import android.util.Log;
import com.lichao.utils.Const;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;

public class CallQueryTemplate {
    private static String TAG = "CallQueryTemplate";

    /**
     * 获取人脸模
     * @param name
     * @param workNo
     */
    public static void newQueryTemp(String name, String workNo) {
        OkHttpUtils.post()
                .url(Const.QUERY_TEMPLATE)
                .addParams("ff_name", name)
                .addParams("ff_job_number", workNo)
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
