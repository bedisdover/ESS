package cn.edu.nju.vo.courseVO;

import cn.edu.nju.vo.ResultInfo;

import java.util.List;

public class CourseListResult {

    private ResultInfo result;
    private List<CourseInfo> list;

    public CourseListResult() {
    }

    public CourseListResult(ResultInfo result, List<CourseInfo> list) {
        this.result = result;
        this.list = list;
    }

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    public List<CourseInfo> getList() {
        return list;
    }

    public void setList(List<CourseInfo> list) {
        this.list = list;
    }
}
