package cn.edu.nju.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;

public class JsonUtil {

    public static<T> T toCollection(String json,
                                    Class<?> collectionClass,
                                    Class<?> ...elementClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructParametricType(
                collectionClass, elementClass
        );
        return mapper.readValue(json, type);
    }

    public static<T> T toObject(String json, Class<T> cls) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, cls);
    }

    public static<T> String toJson(T object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
