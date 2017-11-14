package cn.edu.nju.info.examInfo;

import java.util.List;

public class ExamsOfCourse {

    public static class ExamInfo {
        private int examId;
        private String name;
        private String password;
        private String startTime;
        private String endTime;
        private List<Integer> num;
        private List<Double> marks;
        private List<StudentInfo> studentInfoList;

        public ExamInfo() {
        }

        public ExamInfo(int examId, String name, String password,
                        String startTime, String endTime,
                        List<Integer> num, List<Double> marks,
                        List<StudentInfo> studentInfoList) {
            this.examId = examId;
            this.name = name;
            this.password = password;
            this.startTime = startTime;
            this.endTime = endTime;
            this.num = num;
            this.marks = marks;
            this.studentInfoList = studentInfoList;
        }

        public int getExamId() {
            return examId;
        }

        public void setExamId(int examId) {
            this.examId = examId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
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

        public List<StudentInfo> getStudentInfoList() {
            return studentInfoList;
        }

        public void setStudentInfoList(List<StudentInfo> studentInfoList) {
            this.studentInfoList = studentInfoList;
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
