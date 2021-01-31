package org.nami.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${nami.http.version}")
    private String version;

    @GetMapping("/test")
    public String test() {
        log.info("==========request test");
        return "ok";
    }

    @PostMapping("/add")
    public User add(@RequestBody User user) {
        log.info("==========add user,version:{}", version);
        return user;
    }

    @PutMapping("/update")
    public User update(@RequestBody User user) {
        log.info("==========update user,version:{}", version);
        return user;
    }

    @DeleteMapping("/del")
    public void delete(@RequestParam("id") Integer id) {
        log.info("==========delete user,version:{}", version);
    }
}
