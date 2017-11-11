package cn.edu.nju.utils;

import java.util.Random;

/**
 * Created by Jiayiwu on 17/11/11.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class RandomUtil {

    private static final String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int BASE_LEN = base.length();

    private static final int RAND_STRING_LEN = 15;

    public static String randomString() {
        StringBuilder builder = new StringBuilder(RAND_STRING_LEN);
        Random random = new Random();
        for (int i = 0; i < RAND_STRING_LEN; ++i) {
            int index = random.nextInt(BASE_LEN);
            builder.append(base.charAt(index));
        }
        return builder.toString();
    }
}
