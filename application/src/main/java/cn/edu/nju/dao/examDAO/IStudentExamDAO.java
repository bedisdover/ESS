package cn.edu.nju.dao.examDAO;

import cn.edu.nju.model.examModel.StudentModel;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface IStudentExamDAO {

    void joinInExam(int examId, List<String> emails) throws Exception;

}
