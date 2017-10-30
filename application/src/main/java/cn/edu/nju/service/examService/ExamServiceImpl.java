package cn.edu.nju.service.examService;

import cn.edu.nju.dao.courseDAO.IUserCourseDAO;
import cn.edu.nju.dao.examDAO.IExamDAO;
import cn.edu.nju.dao.examDAO.IQuestionDAO;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.model.examModel.LevelModel;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "examService")
public class ExamServiceImpl implements IExamService {

    private final IUserCourseDAO userCourseDAO;

    private final IQuestionDAO questionDAO;

    private final IExamDAO examDAO;

    @Autowired
    public ExamServiceImpl(IUserCourseDAO userCourseDAO,
                           IQuestionDAO questionDAO,
                           IExamDAO examDAO) {
        this.userCourseDAO = userCourseDAO;
        this.questionDAO = questionDAO;
        this.examDAO = examDAO;
    }

    @Override
    public ResultInfo createExam(int userId, int courseId,
                                 String num, String mark) {
        if (!userCourseDAO.doesUserHaveCourse(userId, courseId)) {
            return new ResultInfo(
                    false, "只有该门课的老师才能生成该门课的考试", null
            );
        }

        // add an exam record to database
        int examId;
        try {
            examId = examDAO.createExam(new ExamModel(0, courseId, 1, num));
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(ExamServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }

        // extract mark of level
        int level = 1;
        String[] marks = mark.split(",");
        List<LevelModel> levelModels = new ArrayList<>(marks.length);
        try {
            for (String str : marks) {
                double m = Double.parseDouble(str);
                levelModels.add(new LevelModel(0, courseId, level, examId, m));
                level += 1;
            }
            questionDAO.updateMarkOfLevelByUniqueKey(levelModels);
            return new ResultInfo(true, "成功创建考试", null);
        }
        catch (NumberFormatException e) {
            try {
                examDAO.deleteExam(examId);
                return new ResultInfo(
                        false, "等级分数应该是由逗号隔开的小数组成", null
                );
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(ExamServiceImpl.class).error(e);
                return new ResultInfo(false, "系统异常", null);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger logger = Logger.getLogger(ExamServiceImpl.class);
            logger.error(e);
            try {
                examDAO.deleteExam(examId);
            } catch (Exception e1) {
                e1.printStackTrace();
                logger.error(e1);
            }
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo updateExam(int userId, int examId, String num, String mark) {
        int courseId = examDAO.getCourseIdByExamId(examId);
        if (!userCourseDAO.doesUserHaveCourse(userId, courseId)) {
            return new ResultInfo(
                    false, "只有该门课的老师才能修改该门课的考试信息", null
            );
        }

        try {
            examDAO.updateNumOfQuestions(examId, num);

            int level = 1;
            String[] marks = mark.split(",");
            List<LevelModel> levelModels = new ArrayList<>(marks.length);
            for (String str : marks) {
                double m = Double.parseDouble(str);
                levelModels.add(new LevelModel(0, courseId, level, examId, m));
                level += 1;
            }
            questionDAO.updateMarkOfLevelByUniqueKey(levelModels);
            return new ResultInfo(true, "成功修改考试信息", null);
        }
        catch (NumberFormatException e) {
            return new ResultInfo(
                    false, "等级分数应该是由逗号隔开的小数组成", null
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(ExamServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }
}
