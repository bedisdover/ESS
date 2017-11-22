package cn.edu.nju.service.examService;

import cn.edu.nju.config.ExamConfig;
import cn.edu.nju.dao.courseDAO.IUserCourseDAO;
import cn.edu.nju.dao.examDAO.ILevelDAO;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.LevelInfo;
import cn.edu.nju.model.examModel.LevelModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service("levelService")
public class LevelServiceImpl implements ILevelService {

    private final ILevelDAO levelDAO;

    private final IUserCourseDAO userCourseDAO;

    @Autowired
    public LevelServiceImpl(ILevelDAO levelDAO,
                            IUserCourseDAO userCourseDAO) {
        this.levelDAO = levelDAO;
        this.userCourseDAO = userCourseDAO;
    }

    @Override
    public ResultInfo setMarkOfLevel(Integer userId, int courseId,
                                     int examId, double[] marks) {
        if (!userCourseDAO.doesUserHaveCourse(userId, courseId)) {
            return new ResultInfo(
                    false, "只有该门课的老师才能设置等级分数", null
            );
        }

        if (!areMarksValid(marks)) {
            return new ResultInfo(
                    false, "分数应该在" + ExamConfig.MIN_MARK
                    + "和" + ExamConfig.MAX_MARK + "之间", null
            );
        }

        try {
            List<Double> markList = Arrays.stream(marks).boxed().collect(Collectors.toList());
            levelDAO.setMarkOfLevel(courseId, examId, markList);
            return new ResultInfo(true, "成功设置等级分数", null);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(QuestionServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResultInfo getLevelInfoList(int courseId) {
        List<LevelModel> list = levelDAO.getLevelModelList(courseId);
        return new ResultInfo(
                true, "成功获取等级信息",
                LevelModel.toInfoList(list)
        );
    }

    @Override
    public ResultInfo updateMarkOfLevel(Integer userId, List<LevelInfo> levelInfoList) {
        if (levelInfoList.isEmpty()) {
            return new ResultInfo(true, "等级分数修改成功", null);
        }

        LevelInfo levelInfo = levelInfoList.get(0);
        int courseId = levelInfo.getCourseId();
        if (!userCourseDAO.doesUserHaveCourse(userId, courseId)) {
            return new ResultInfo(
                    false, "只有该门课的老师才能修改等级分数", null
            );
        }

        try {
            int examId= levelInfo.getExamId();
            int size = levelInfoList.size();
            List<Double> marks = new ArrayList<>(size);
            levelInfoList.forEach(level -> marks.add(level.getMark()));
            levelDAO.setMarkOfLevel(courseId, examId, marks);
            return new ResultInfo(true, "成功修改等级对应的分数", null);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(QuestionServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", false);
        }
    }

    private boolean areMarksValid(double[] marks) {
        for (double m : marks) {
            if (m <= ExamConfig.MIN_MARK || m >= ExamConfig.MAX_MARK) {
                return false;
            }
        }
        return true;
    }
}
