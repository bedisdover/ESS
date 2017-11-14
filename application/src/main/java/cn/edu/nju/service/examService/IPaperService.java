package cn.edu.nju.service.examService;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.AnsweredPaperInfo;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface IPaperService {

    ResultInfo generatePaper(int examId, String email, String password);

    ResultInfo submitPaper(AnsweredPaperInfo paper);

    ResultInfo deletePaper(int paperId);
}
