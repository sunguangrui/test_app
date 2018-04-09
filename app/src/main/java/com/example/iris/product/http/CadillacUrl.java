package com.example.iris.product.http;

/**
 * Created by bitch-1 on 2017/3/27.
 */

public class CadillacUrl {

//   public static final String BASE_URL="http://192.168.1.104/";


//    public static final String BASE_URL="http://sit.iris.com/";//正式

//    public static final String BASE_URL="http://dev.iris.com/";//正式

    public static final String BASE_URL="http://d1y9114112.imwork.net:10280/";//后来彭困龙给的

    /*登录*/
    public static final String LOGIN_URL = BASE_URL+"api/v1/login.htm";

    /*首页*/
    public static final String HOME_URL=BASE_URL+"liveapp/homepage";
//    public static final String HOME_URL="http://192.168.1.178:8001/homepage";//彭测试

    /*个人中心*/
    public static final String CENT_URL =BASE_URL+"liveapp/customer-center";
//    public static final String CENT_URL ="http://192.168.1.178:8001/customer-center";
}
