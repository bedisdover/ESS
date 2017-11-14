package cn.edu.nju.utils;

import cn.edu.nju.info.examInfo.StudentInfo;
import cn.edu.nju.service.examService.ErrorTemplateFormatException;
import cn.edu.nju.info.examInfo.OptionInfo;
import cn.edu.nju.info.examInfo.QuestionInfo;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    public static List<QuestionInfo> extractQuestions(InputStream excelStream)
            throws IOException, BiffException, ErrorTemplateFormatException {
        List<QuestionInfo> result = new ArrayList<>();
        Workbook workbook = Workbook.getWorkbook(excelStream);
        Sheet sheet = workbook.getSheet(0);

        // check if template is modified
        int col = sheet.getColumns();
        Cell[] header = sheet.getRow(0);
        if (col < 4 || !header[0].getContents().trim().equals("试题内容")
                || !header[1].getContents().trim().equals("正确答案")
                || !header[2].getContents().trim().equals("试题难度等级")
                || !header[3].getContents().trim().equals("选项")) {
            throw new ErrorTemplateFormatException(
                    "请不要修改模板的标题,从下一行开始填入题目信息"
            );
        }

        int row = sheet.getRows();
        for (int i = 1; i < row; ++i) {
            Cell[] cells = sheet.getRow(i);
            if (cells.length == 0 || cells[0].getContents().trim().isEmpty()) {
                break;
            }

            QuestionInfo vo = new QuestionInfo();
            result.add(vo);

            // add content
            String content = cells[0].getContents().trim();
            if (content.isEmpty()) {
                throw new ErrorTemplateFormatException(
                        "第" + (i + 1) + "行题目内容不能为空,请参照模板demo"
                );
            }
            vo.setContent(content);

            // add answer
            String answer = cells[1].getContents().trim();
            if (answer.isEmpty()) {
                throw new ErrorTemplateFormatException(
                        "第" + (i + 1) + "行题目答案不能为空,请参照模板demo"
                );
            }
            String[] strings = answer.split(",");
            for (String str : strings) {
                try {
                    int lv = Integer.parseInt(str);
                    if (lv < 1) {
                        throw new ErrorTemplateFormatException(
                                "第" + (i + 1) + "行题目答案中的数字应该为大于0的整数,请参照模板demo"
                        );
                    }
                }
                catch (NumberFormatException e) {
                    throw new ErrorTemplateFormatException(
                            "第" + (i + 1) + "行题目答案应该是由逗号分隔的数字组成,请参照模板demo"
                    );
                }
            }
            vo.setAnswer(answer);

            // add level
            String lvStr = cells[2].getContents().trim();
            if (!StringUtils.isNumeric(lvStr)) {
                throw new ErrorTemplateFormatException(
                        "第" + (i + 1) + "行题目难度等级应该为整数,请参照模板demo"
                );
            }
            int level = Integer.parseInt(lvStr);
            vo.setLevel(level);

            // add option
            List<OptionInfo> options = new ArrayList<>();
            for (int j = 3; j < cells.length; ++j) {
                String opt = cells[j].getContents().trim();
                if (opt.isEmpty()) break;
                options.add(new OptionInfo(j - 2, opt));
            }
            if (options.isEmpty()) {
                throw new ErrorTemplateFormatException(
                        "第" + (i + 1) + "行选项不应该为空,请参照模板demo"
                );
            }
            vo.setOptions(options);
        }

        return result;
    }

    public static List<StudentInfo> extractStudents(InputStream excelStream)
            throws IOException, BiffException, ErrorTemplateFormatException {
        List<StudentInfo> result = new ArrayList<>();
        Workbook workbook = Workbook.getWorkbook(excelStream);
        Sheet sheet = workbook.getSheet(0);

        // check if template is modified
        int col = sheet.getColumns();
        Cell[] header = sheet.getRow(0);
        if (col < 3 || !header[0].getContents().trim().equals("邮箱")
                || !header[1].getContents().trim().equals("姓名")
                || !header[2].getContents().trim().equals("班级")) {
            throw new ErrorTemplateFormatException(
                    "请不要修改模板的标题,从下一行开始填入题目信息"
            );
        }

        int row = sheet.getRows();
        for (int i = 1; i < row; ++i) {
            Cell[] cells = sheet.getRow(i);
            if (cells.length == 0 || cells[0].getContents().trim().isEmpty()) {
                break;
            }

            StudentInfo info = new StudentInfo();

            String email = cells[0].getContents().trim();
            if (email.isEmpty()) {
                throw new ErrorTemplateFormatException(
                        "第" + (i + 1) + "行邮箱不能为空"
                );
            }
            info.setEmail(email);

            String name = cells[1].getContents().trim();
            if (name.isEmpty()) {
                throw new ErrorTemplateFormatException(
                        "第" + (i + 1) + "行姓名不能为空"
                );
            }
            info.setName(name);

            String classStr = cells[2].getContents().trim();
            try {
                int cls = Integer.parseInt(classStr);
                if (cls < 1 || cls > 4) {
                    throw new NumberFormatException();
                }
                info.setCls(cls);
            } catch (NumberFormatException e) {
                throw new ErrorTemplateFormatException(
                        "第" + (i + 1) + "行班级应该为1~4的整数"
                );
            }

            result.add(info);
        }

        return result;
    }


//    public static void print(List<QuestionInfo> list) {
//        String gap = "\t\t\t";
//        System.out.println("题目" + gap + "答案" + gap + "等级" + gap + "选项");
//        for (QuestionInfo vo : list) {
//            System.out.print(vo.getContent() + gap);
//            System.out.print(vo.getAnswer() + gap);
//            System.out.print(vo.getLevel() + gap);
//            for (OptionInfo opt : vo.getOptions()) {
//                System.out.print(opt.getOptionId() + "." + opt.getContent() + gap);
//            }
//            System.out.println();
//        }
//    }
}
