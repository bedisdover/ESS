package cn.edu.nju.info.examInfo;

import java.util.List;

public class ExamsOfCourse {

    public static class ExamInfo {
        private int examId;
        private List<Integer> num;
        private List<Double> marks;

        public ExamInfo() {
        }

        public ExamInfo(int examId, List<Integer> num,
                        List<Double> marks) {
            this.examId = examId;
            this.num = num;
            this.marks = marks;
        }

        public int getExamId() {
            return examId;
        }

        public void setExamId(int examId) {
            this.examId = examId;
        }

        public List<Integer> getNum() {
            return num;
        }

        public void setNum(List<Integer> num) {
            this.num = num;
        }

        public List<Double> getMarks() {
            return marks;
        }

        public void setMarks(List<Double> marks) {
            this.marks = marks;
        }
    }

    private int courseId;
    private List<Integer> maxNum;
    private List<ExamInfo> examInfoList;

    public ExamsOfCourse() {
    }

    public ExamsOfCourse(int courseId, List<Integer> maxNum,
                         List<ExamInfo> examInfoList) {
        this.courseId = courseId;
        this.maxNum = maxNum;
        this.examInfoList = examInfoList;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public List<Integer> getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(List<Integer> maxNum) {
        this.maxNum = maxNum;
    }

    public List<ExamInfo> getExamInfoList() {
        return examInfoList;
    }

    public void setExamInfoList(List<ExamInfo> examInfoList) {
        this.examInfoList = examInfoList;
    }
}
