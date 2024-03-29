package com.eetrust.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huangg
 * @create 2024/1/16 17:04
 */
@RestController
@RequestMapping("/aa")
public class aa {
    private static final Logger log = LoggerFactory.getLogger(aa.class);
    @RequestMapping("/bb")

    public String bb(){
        String xml="测试接口-------------";
        log.info("xml->"+xml);
        return "200";
    }

}
