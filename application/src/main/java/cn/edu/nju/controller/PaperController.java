package cn.edu.nju.controller;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.AnsweredPaperInfo;
import cn.edu.nju.service.examService.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Controller
public class PaperController {

    private final IPaperService paperService;

    @Autowired
    public PaperController(IPaperService paperService) {
        this.paperService = paperService;
    }

    @RequestMapping(value = "/paper/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo generatePaper(@RequestParam int examId,
                                    @RequestParam String email,
                                    @RequestParam String password) {
        return paperService.generatePaper(examId, email, password);
    }

    @RequestMapping(value = "/paper/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo submitPaper(@RequestBody AnsweredPaperInfo paper) {
        return paperService.submitPaper(paper);
    }

    @RequestMapping(value = "/paper/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deletePaper(@RequestParam int paperId) {
        return paperService.deletePaper(paperId);
    }
}