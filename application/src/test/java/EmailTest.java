import cn.edu.nju.utils.EmailUtil;
import cn.edu.nju.info.ResultInfo;

public class EmailTest {

    public static void main(String[] args) {
        ResultInfo result = EmailUtil.sendVerifyEmail("141250152@smail.nju.edu.cn", "123902930");
        System.out.println(result.getMessage());
    }
}
