package org.easley.consumer.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.easley.common.entity.Account;
import org.easley.common.service.TransMoneyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class TestController {

    @DubboReference
    private TransMoneyService transMoneyService;

    @PostMapping("findAccountByUserId")
    public Account findAccountByUserId(@RequestBody Long userId) {
        return transMoneyService.findAccountByUserId(userId);
    }

    @PostMapping("transUsd")
    public Boolean transUsd(@RequestBody Map<String, Object> map) {
        long userId = (Integer) map.get("userId");
        BigDecimal money = BigDecimal.valueOf((Double) map.get("money"));
        Account trans = new Account();
        trans.setUsd(money);
        return transMoneyService.transMoney(userId, trans);
    }

    @PostMapping("transCny")
    public Boolean transCny(@RequestBody Map<String, Object> map) {
        long userId = (Integer) map.get("userId");
        BigDecimal money = BigDecimal.valueOf((Double) map.get("money"));
        Account trans = new Account();
        trans.setCny(money);
        return transMoneyService.transMoney(userId, trans);
    }
}
