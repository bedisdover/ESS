package cn.edu.nju.dao.courseDAO;

import cn.edu.nju.dao.SessionFactory;
import cn.edu.nju.mapper.IdMapper;
import cn.edu.nju.mapper.courseMapper.CourseMapper;
import cn.edu.nju.vo.ResultInfo;
import cn.edu.nju.vo.courseVO.CourseListResult;
import cn.edu.nju.po.coursePO.CourseModel;
import cn.edu.nju.vo.courseVO.CourseInfo;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "courseDAO")
public class CourseDAOImpl implements ICourseDAO {

    private static final Logger logger = Logger.getLogger(CourseDAOImpl.class);

    @Transactional
    @Override
    public ResultInfo addCourse(int userId, CourseInfo info) {
        try(SqlSession session = SessionFactory.getInstance().openSession()) {
            CourseMapper courseMapper = session.getMapper(CourseMapper.class);
            boolean isRecordExist = courseMapper.getRemovedCourseNum(info.getId()) > 0;
            if (isRecordExist) {
                courseMapper.recoverRemovedCourse(info.getId());
            }
            else {
                courseMapper.addCourse(new CourseModel(info));
                IdMapper idMapper = session.getMapper(IdMapper.class);
                int courseId = idMapper.getLastInsertId();
                courseMapper.addUserCourseRecord(userId, courseId);
            }
            return new ResultInfo(true, "添加课程成功");
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return new ResultInfo(false, "添加课程失败");
        }
    }

    @Override
    public ResultInfo updateCourse(CourseInfo info) {
        try(SqlSession session = SessionFactory.getInstance().openSession()) {
            CourseMapper courseMapper = session.getMapper(CourseMapper.class);
            courseMapper.updateCourse(new CourseModel(info));
            return new ResultInfo(true, "课程信息修改成功");
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return new ResultInfo(false, "系统异常");
        }
    }

    @Override
    public ResultInfo enrollCourse(int userId, int courseId) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            CourseMapper mapper = session.getMapper(CourseMapper.class);
            boolean isRecordExist = mapper.getRemovedRecordNum(userId, courseId) > 0;
            if (isRecordExist) {
                mapper.recoverRemovedCourse(userId, courseId);
            }
            else {
                mapper.addUserCourseRecord(userId, courseId);
            }
            return new ResultInfo(true, "选课成功");
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return new ResultInfo(false, "系统异常");
        }
    }

    @Override
    public ResultInfo quitCourse(int userId, int courseId) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            CourseMapper mapper = session.getMapper(CourseMapper.class);
            mapper.removeUserCourseRecord(userId, courseId);
            return new ResultInfo(true, "退课成功");
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return new ResultInfo(false, "系统异常");
        }
    }

    @Override
    public int getCourseUserRecordNum(int courseId, int userId) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            CourseMapper mapper = session.getMapper(CourseMapper.class);
            return mapper.getCourseUserRecordNum(userId, courseId);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return -1;
        }
    }

    @Override
    public String getCourseKeyById(int courseId) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            CourseMapper mapper = session.getMapper(CourseMapper.class);
            return mapper.getCourseKeyById(courseId);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return "";
        }
    }

    @Override
    public CourseListResult getCourseList(int userId, int num) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            CourseMapper mapper = session.getMapper(CourseMapper.class);
            List<CourseModel> list = mapper.getCourseListBySize(userId, num);
            return new CourseListResult(
                    new ResultInfo(true, "成功获取课程信息列表"),
                    toCourseInfoList(list)
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return new CourseListResult(
                    new ResultInfo(false, "系统异常"), null
            );
        }
    }

    @Override
    public CourseListResult getCourseListById(int userId) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            CourseMapper mapper = session.getMapper(CourseMapper.class);
            List<CourseModel> list = mapper.getCourseListById(userId);
            return new CourseListResult(
                    new ResultInfo(true, "成功获取课程信息列表"),
                    toCourseInfoList(list)
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return new CourseListResult(
                    new ResultInfo(false, "系统异常"), null
            );
        }
    }

    private List<CourseInfo> toCourseInfoList(List<CourseModel> list) {
        List<CourseInfo> result = new ArrayList<>(list.size());
        for (CourseModel model : list) {
            result.add(model.toCourseInfo());
        }
        return result;
    }
}
