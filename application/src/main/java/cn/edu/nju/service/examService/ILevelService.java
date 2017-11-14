package cn.edu.nju.service.examService;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.LevelInfo;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface ILevelService {

    ResultInfo setMarkOfLevel(Integer userId, int courseId, int examId, double[] marks);

    ResultInfo getLevelInfoList(int courseId);

    ResultInfo updateMarkOfLevel(Integer userId, List<LevelInfo> levelInfoList);
}
