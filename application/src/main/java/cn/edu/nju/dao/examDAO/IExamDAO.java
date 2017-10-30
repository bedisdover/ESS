package cn.edu.nju.dao.examDAO;

import cn.edu.nju.model.examModel.ExamModel;

public interface IExamDAO {

    /**
     * insert an exam record
     * @param model exam information
     * @return exam id
     */
    int createExam(ExamModel model) throws Exception;

    void deleteExam(int examId) throws Exception;
}
