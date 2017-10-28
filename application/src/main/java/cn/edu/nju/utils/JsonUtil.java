package cn.edu.nju.utils;

import cn.edu.nju.vo.ResultInfo;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

    public static String toJson(ResultInfo info) {
        JSONObject result = new JSONObject();
        try {
            result.put("success", info.isSuccess());
            result.put("message", info.getMessage());
            result.put("data", info.getData());
        } catch (JSONException e) {
            Logger.getLogger(JsonUtil.class).error(e);
            e.printStackTrace();
        }
        return result.toString();
    }
}
