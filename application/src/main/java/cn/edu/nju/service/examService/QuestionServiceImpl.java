package cn.edu.nju.service.examService;

import cn.edu.nju.dao.examDAO.IQuestionDAO;
import cn.edu.nju.utils.EncryptionUtil;
import cn.edu.nju.utils.ExcelUtil;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.QuestionInfo;
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

    @Autowired
    public QuestionServiceImpl(IQuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    @Override
    public ResultInfo saveQuestion(int courseId, InputStream excelStream) {
        try {
            ByteArrayOutputStream bufferStream = toBAOS(excelStream);
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
            List<QuestionInfo> list = ExcelUtil.extractQuestions(courseId, stream2);
            stream2.close();
            ExcelUtil.print(list);

            excelStream.close();
            return questionDAO.saveQuestions(QuestionInfo.toModelList(list, md5));
        } catch (IOException | BiffException e) {
            e.printStackTrace();
            Logger.getLogger(QuestionServiceImpl.class).error(e);
            return new ResultInfo(false, "文件读写错误", null);
        } catch (ErrorTemplateFormatException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }
    }

    private ByteArrayOutputStream toBAOS(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, len);
        }
        result.flush();
        return result;
    }
}
