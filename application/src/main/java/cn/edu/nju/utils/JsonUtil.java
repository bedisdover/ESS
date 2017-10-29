package cn.edu.nju.utils;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.OptionInfo;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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

    public static String toJson(List<OptionInfo> list) {
        JSONObject object = new JSONObject();
        for (OptionInfo info : list) {
            try {
                object.put(String.valueOf(info.getOptionId()), info.getContent());
            } catch (JSONException e) {
                e.printStackTrace();
                Logger.getLogger(JsonUtil.class).error(e);
            }
        }
        return object.toString();
    }
}
