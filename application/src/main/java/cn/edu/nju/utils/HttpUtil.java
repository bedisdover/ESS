package cn.edu.nju.utils;

import cn.edu.nju.vo.ResultInfo;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

class HttpUtil {

    static ResultInfo post(String url, List<NameValuePair> params) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        try {
            HttpEntity entity = new UrlEncodedFormEntity(params, Charset.forName("UTF-8"));
            request.setEntity(entity);
            client.execute(request);
            return new ResultInfo(true, "请求发送成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(HttpUtil.class).error(e);
            return new ResultInfo(false, "网络异常", null);
        }
    }
}
