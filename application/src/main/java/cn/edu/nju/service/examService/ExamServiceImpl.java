package cn.edu.nju.service.examService;

import cn.edu.nju.dao.courseDAO.IUserCourseDAO;
import cn.edu.nju.dao.examDAO.IExamDAO;
import cn.edu.nju.dao.examDAO.IQuestionDAO;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.model.examModel.LevelModel;
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

        ResultInfo createResult = examDAO.createExam(new ExamModel(
                0, courseId, 1, num
        ));
        if (!createResult.isSuccess()) {
            return createResult;
        }

        int examId = (int) createResult.getData();
        int level = 1;
        String[] marks = mark.split(",");
        List<LevelModel> levelModels = new ArrayList<>(marks.length);
        for (String str : marks) {
            try {
                double m = Double.parseDouble(str);
                levelModels.add(new LevelModel(0, courseId, level, examId, m));
                level += 1;
            }
            catch (NumberFormatException e) {
                examDAO.deleteExam(examId);
                return new ResultInfo(
                        false, "等级分数应该是由逗号隔开的小数组成", false
                );
            }
        }

        ResultInfo updateResult = questionDAO.updateMarkOfLevelByUniqueKey(levelModels);
        if (!updateResult.isSuccess()) {
            examDAO.deleteExam(examId);
            return updateResult;
        }

        return new ResultInfo(true, "成功创建考试", null);
    }
}
