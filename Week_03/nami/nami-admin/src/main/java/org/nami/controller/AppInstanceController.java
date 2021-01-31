package org.nami.controller;

import org.nami.dto.UpdateWeightDTO;
import org.nami.service.AppInstanceService;
import org.nami.vo.InstanceVO;
import org.nami.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AppInstanceController
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Controller
@RequestMapping("/app/instance")
public class AppInstanceController {

    @Autowired
    private AppInstanceService instanceService;

    @GetMapping("/list")
    public String list(@RequestParam("appId") Integer appId, ModelMap map) {
        List<InstanceVO> instanceVOS = instanceService.queryList(appId);
        map.put("instanceVOS", instanceVOS);
        return "instance";
    }

    @ResponseBody
    @PutMapping("")
    public Result updateWeight(@RequestBody @Validated UpdateWeightDTO updateWeightDTO){
        instanceService.updateWeight(updateWeightDTO);
        return Result.success();
    }
}
