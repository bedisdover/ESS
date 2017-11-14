package cn.edu.nju.controller;

import cn.edu.nju.config.AccountConfig;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.LevelInfo;
import cn.edu.nju.service.examService.ILevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Controller
public class LevelController {

    private final ILevelService levelService;

    @Autowired
    public LevelController(ILevelService levelService) {
        this.levelService = levelService;
    }

    /**
     * mark should between ExamConfig.MIN_MARK and ExamConfig.MAX_MARK exclusively
     */
    @RequestMapping(value = "/level/config", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo setMarkOfLevel(HttpSession session,
                                     @RequestParam int examId,
                                     @RequestParam int courseId,
                                     @RequestParam double[] marks) {
        Integer userId = (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
        return levelService.setMarkOfLevel(userId, courseId, examId, marks);
    }

    @RequestMapping(value = "/level/list", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getLevelInfoList(@RequestParam int courseId) {
        return levelService.getLevelInfoList(courseId);
    }

    @RequestMapping(value = "/level/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateMarkOfLevel(HttpSession session,
                                        @RequestBody List<LevelInfo> levelInfoList) {
        Integer userId = (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
        return levelService.updateMarkOfLevel(userId, levelInfoList);
    }
}
