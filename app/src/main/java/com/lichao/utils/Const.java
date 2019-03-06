package com.lichao.utils;

public class Const {

    /**
     * 服务器地址
     */
    private static String SERVER_IP = "http://192.168.1.129:8088/";

    /**
     * 查询模板
     */
    public static String QUERY_TEMPLATE =  SERVER_IP + "newQueryTemplate?";

    /**
     * 添加模板
     */
    public static String INSERT_TEMP =  SERVER_IP + "newInsertTemplate?";

    /**
     * 添加模板
     */
    public static String DELETE_TEMP =  SERVER_IP + "newDeleteTemplate?";

    /**
     * 服务器地址配置
     */
    public static String SETTING_SERVER =  SERVER_IP + "updateServerConfig?";
}
