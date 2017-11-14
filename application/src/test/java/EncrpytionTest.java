import cn.edu.nju.utils.EncryptionUtil;

public class EncrpytionTest {

    public static void main(String[] args) {
        String base = "abc";
        String encode = EncryptionUtil.base64Encode(base);
        System.out.println("base64 encode: " + encode);
        System.out.println("base64 decode: " + EncryptionUtil.base64Decode(encode));
    }
}
