package cn.edu.nju.utils;

import cn.edu.nju.controller.QuestionController;
import cn.edu.nju.info.ResultInfo;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

public class HttpUtil {

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

    public static void fileDownload(String fileName,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        final String filePath = request.getSession().getServletContext().getRealPath("/");
        File file = new File(filePath+fileName);
        if(!file.exists()){
            Logger.getLogger(QuestionController.class)
                    .error("No such file: " + file.getName());
            return;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {

            response.setHeader("Content-Disposition", "attachment;Filename=" + fileName);
            byte[] bytes = new byte[2048];
            int len;
            while ((len = fileInputStream.read(bytes))>0){
                outputStream.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(QuestionController.class).error(e);
        }
    }
}
