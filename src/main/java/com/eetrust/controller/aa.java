package com.eetrust.controller;

import com.eetrust.util.WebServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.eetrust.util.WebServiceUtils.webserviceInvok;

/**
 * @Author huangg
 * @create 2024/1/16 17:04
 */
@RestController
@RequestMapping("/aa")
public class aa {
    private static final Logger log = LoggerFactory.getLogger(aa.class);

    @Value("${bdus.systemKey}")
    private String systemKey;
    @RequestMapping("/bb")

    public String bb(){
        log.info("test>>>");
        return "200";
    }
    @RequestMapping("/cc")
    public String cc(){
        log.info("systemKey->"+systemKey);
        return systemKey;
    }
}
