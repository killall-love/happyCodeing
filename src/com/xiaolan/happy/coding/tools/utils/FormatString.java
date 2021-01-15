package com.xiaolan.happy.coding.tools.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * @ProjectName: formatSQL
 * @Package: com.xiaolan.happy.coding.tools.utils
 * @ClassName: FormatString
 * @Author: 烟花小神
 * @Description:
 * @Date: 2020/12/23 23:51
 * @Version: 1.0
 */
public class FormatString {

    private final static String urlBase = "http://translate.google.cn/translate_a/single?client=gtx&dt=t&dj=1&ie=UTF-8&sl=auto&tl=zh-EN&q=";

    /**
     * 转换字符串
     *
     * @param param
     * @return
     */
    public static String getConversion(String param) {
        String body = "";
        try {
            body = Jsoup.connect(urlBase + param).ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:85.0) Gecko/20100101 Firefox/85.0")
                    .method(Connection.Method.GET).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (body.isEmpty()){
            return "";
        }
        JSONObject json = JSONObject.parseObject(body);
        JSONArray sentences = json.getJSONArray("sentences");
        if (sentences.size() <= 0){
            return "";
        }
        String trans = sentences.getJSONObject(0).getString("trans");
        if (trans.isEmpty()){
            return "";
        }
        body = trans.toLowerCase();
        return underlineToCamel(body);
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     *
     * @param param
     * @return
     */
    private static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == ' ') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
