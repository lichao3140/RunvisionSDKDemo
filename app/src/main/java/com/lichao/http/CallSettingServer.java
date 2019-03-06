package com.lichao.http;

import android.util.Log;

import com.lichao.utils.Const;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class CallSettingServer {
    private static String TAG = "CallSettingServer";

    /**
     * 设置服务器
     * @param ip
     * @param port
     * @param attend
     * @param heart
     * @param img
     */
    public static void settingServer(String ip, String port, String attend, String heart, String img) {
        OkHttpUtils.post()
                .url(Const.SETTING_SERVER)
                .addParams("serverIp", ip)
                .addParams("serverPort", port)
                .addParams("serverAttend", attend)
                .addParams("serverHeart", heart)
                .addParams("serverImg", img)
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
