package cn.edu.nju.utils;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.OptionInfo;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    public static String toJson(ResultInfo info) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("success", info.isSuccess());
        result.put("message", info.getMessage());
        result.put("data", info.getData());
        return result.toString();
    }

    public static String toJson(List<OptionInfo> list) {
        JSONArray arr = new JSONArray();
        for (OptionInfo info : list) {
            Map<String, String> map = new HashMap<>();
            map.put("optionId", String.valueOf(info.getOptionId()));
            map.put("content", info.getContent());
            arr.put(map);
        }
        return arr.toString();
    }

    public static List<OptionInfo> toOptionInfoList(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, OptionInfo.class);
        return mapper.readValue(json, type);
    }
}
