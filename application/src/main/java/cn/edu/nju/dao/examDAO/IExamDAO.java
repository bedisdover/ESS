package cn.edu.nju.dao.examDAO;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.model.examModel.ExamModel;

public interface IExamDAO {

    /**
     * insert an exam record
     * @param model exam information
     * @return ResultInfo.data = exam id
     */
    ResultInfo createExam(ExamModel model);

    ResultInfo deleteExam(int examId);
}
