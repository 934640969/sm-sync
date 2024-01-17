package com.eetrust.controller;

import com.eetrust.util.WebServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.eetrust.util.WebServiceUtils.webserviceInvok;

/**
 * @Author huangg
 * @create 2024/1/16 17:04
 */
@Controller
@RequestMapping("/aa")
public class aa {
    private static final Logger log = LoggerFactory.getLogger(aa.class);
    @RequestMapping("/bb")
    public void bb(){
        String xml="<root><privateKey>UAP_2oSY90</privateKey><srcContent></srcContent><dataContent><syncContent dataType=\"1\" operType=\"1\"><syncUnicode>0410000043</syncUnicode><newContent><baseInfo><deptName>顺丰</deptName><deptUniCode>0410000043</deptUniCode><showNum>9999</showNum><deptStatus>1</deptStatus><isCorp>1</isCorp></baseInfo><parentInfo><parentCode>0410000043</parentCode></parentInfo></newContent></syncContent></dataContent></root>";
        log.info("xml->"+xml);
        System.out.println(xml);
        webserviceInvok("http://10.3.44.33:8090/securedoc/clientinterface/syncData/ISyncWebService",xml);
    }
}
