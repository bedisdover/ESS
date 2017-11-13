package cn.edu.nju.service.examService;

import cn.edu.nju.info.ResultInfo;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface IPaperService {

    ResultInfo generatePaper(int examId);

    ResultInfo deletePaper(int paperId);
}
