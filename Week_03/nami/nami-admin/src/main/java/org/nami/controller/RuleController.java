package org.nami.controller;

import org.nami.dto.ChangeStatusDTO;
import org.nami.dto.RuleDTO;
import org.nami.service.RuleService;
import org.nami.vo.Result;
import org.nami.vo.RuleVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * RuleController
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Controller
@RequestMapping("/rule")
public class RuleController {

    @Resource
    private RuleService ruleService;

    /**
     * add new route rule
     *
     * @param ruleDTO
     * @return
     */
    @ResponseBody
    @PostMapping("")
    public Result add(@Validated RuleDTO ruleDTO){
        ruleService.add(ruleDTO);
        return Result.success();
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        ruleService.delete(id);
        return Result.success();
    }

    @GetMapping("/list")
    public String list(ModelMap map, @RequestParam(name = "appName", required = false) String appName) {
        List<RuleVO> ruleVOs = ruleService.queryList(appName);
        map.put("ruleVOS", ruleVOs);
        map.put("appName", appName);
        return "rule";
    }

    @ResponseBody
    @PutMapping("/status")
    public Result changeStatus(@RequestBody ChangeStatusDTO statusDTO) {
        ruleService.changeStatus(statusDTO);
        return Result.success();
    }
}
