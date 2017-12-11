package cn.edu.nju.utils;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/12.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class StringUtil {

    public static String stringify(List<?> list, String separator) {
        if (list == null) return "";

        int size = list.size();
        if (size == 0) return "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size - 1; ++i) {
            builder.append(list.get(i)).append(separator);
        }
        builder.append(list.get(size - 1));
        return builder.toString();
    }
}
