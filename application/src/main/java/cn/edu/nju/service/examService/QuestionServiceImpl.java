package cn.edu.nju.service.examService;

import cn.edu.nju.config.ExamConfig;
import cn.edu.nju.dao.courseDAO.IUserCourseDAO;
import cn.edu.nju.dao.examDAO.IQuestionDAO;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.LevelInfo;
import cn.edu.nju.info.examInfo.QuestionInfo;
import cn.edu.nju.model.examModel.LevelModel;
import cn.edu.nju.model.examModel.QuestionModel;
import cn.edu.nju.utils.EncryptionUtil;
import cn.edu.nju.utils.ExcelUtil;
import cn.edu.nju.utils.IOUtil;
import jxl.read.biff.BiffException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service("questionService")
public class QuestionServiceImpl implements IQuestionService {

    private final IQuestionDAO questionDAO;

    private final IUserCourseDAO userCourseDAO;

    @Autowired
    public QuestionServiceImpl(IQuestionDAO questionDAO,
                               IUserCourseDAO userCourseDAO) {
        this.questionDAO = questionDAO;
        this.userCourseDAO = userCourseDAO;
    }

    @Override
    public ResultInfo saveQuestion(int userId, int courseId, InputStream excelStream) {
        if (!userCourseDAO.doesUserHaveCourse(userId, courseId)) {
            return new ResultInfo(
                    false, "只有该门课的老师才能上传试题", null
            );
        }

        try {
            ByteArrayOutputStream bufferStream = IOUtil.toByteArrayOutputStream(excelStream);
            byte[] data = bufferStream.toByteArray();

            InputStream stream1 = new ByteArrayInputStream(data);
            String md5 = EncryptionUtil.md5(stream1);
            stream1.close();
            if (questionDAO.isMD5Exist(md5)) {
                return new ResultInfo(
                        false, "文件已经上传过了,无需重新上传", null
                );
            }

            InputStream stream2 = new ByteArrayInputStream(data);
            List<QuestionInfo> list = ExcelUtil.extractQuestions(
                    new ByteArrayInputStream(data)
            );
            list.forEach(info -> info.setCourseId(courseId));
            stream2.close();

            excelStream.close();
            questionDAO.saveQuestions(QuestionInfo.toModelList(list, md5));
            return new ResultInfo(true, "成功添加问题", null);
        } catch (IOException | BiffException e) {
            e.printStackTrace();
            Logger.getLogger(QuestionServiceImpl.class).error(e);
            return new ResultInfo(false, "文件读写错误", null);
        } catch (ErrorTemplateFormatException e) {
            return new ResultInfo(false, e.getMessage(), null);
        } catch (Exception e) {
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResultInfo getAllQuestions(Integer page, Integer size) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 10;
        }
        int num = (long)page * size > Integer.MAX_VALUE ?
                Integer.MAX_VALUE : page * size;

        List<QuestionModel> list = questionDAO.getAllQuestions(num);
        List<QuestionInfo> questions;
        try {
            questions = QuestionModel.toInfoList(list);
            return new ResultInfo(true, "成功获取问题列表", questions);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(QuestionServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo deleteQuestions(int userId, List<Integer> questionIdList) {
        if (questionIdList.isEmpty()) {
            return new ResultInfo(true, "成功删除试题", null);
        }

        int questionId = questionIdList.get(0);
        int courseId = questionDAO.getCourseIdByQuestionId(questionId);
        if (!userCourseDAO.doesUserHaveCourse(userId, courseId)) {
            return new ResultInfo(
                    false, "只有该门课的老师才能删除试题", null
            );
        }

        try {
            questionDAO.deleteQuestions(courseId, questionIdList);
            return new ResultInfo(true, "成功删除问题", null);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(QuestionServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
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
            questionDAO.setMarkOfLevel(courseId, examId, marks);
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
        List<LevelModel> list = questionDAO.getLevelModelList(courseId);
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

        int courseId = levelInfoList.get(0).getCourseId();
        if (!userCourseDAO.doesUserHaveCourse(userId, courseId)) {
            return new ResultInfo(
                    false, "只有该门课的老师才能修改等级分数", null
            );
        }

        try {
            questionDAO.updateMarkOfLevelById(
                    LevelInfo.toModelList(levelInfoList)
            );
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
