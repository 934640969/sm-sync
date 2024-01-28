package com.eetrust.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eetrust.util.WebServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 宁夏电信文档安全数据同步
 *
 * @author huangg
 */

@RestController
@RequestMapping("/sync")
public class SyncController {
    private static final Logger log = LoggerFactory.getLogger(SyncController.class);

    @Value("${sm.url}")
    private String smUrl;


    @RequestMapping("/orgSync")
    public String orgSync(@RequestBody String json){
        log.info("orgSync json->{}",json);
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            String type = jsonObject.getString("type");
            JSONObject org = jsonObject.getJSONObject("org");
            String parentOrganizationCode = org.getString("parentOrganizationCode");
            String name = org.getString("name");
            String code = org.getString("code");
            String orderNum = org.getString("orderNum");
            String xml="<returnData>" +
                    "<dataType>1</dataType>" +
                    "<operType>1</operType>" +
                    "<uniCode>"+code+"</uniCode>" +
                    "<syncContent>" +
                    "<orgnization>" +
                    "<singleValue>" +
                    "<deptName>"+name+"</deptName>" +
                    "<showNum>"+orderNum+"</showNum>" +
                    "</singleValue>" +
                    "<parent>" +
                    "<dept>" +
                    "<singleValue>" +
                    "<origCode>"+parentOrganizationCode+"</origCode>" +
                    "</singleValue>" +
                    "</dept>" +
                    "</parent>" +
                    "</orgnization>" +
                    "</syncContent>" +
                    "</returnData>";
            String xml2="<returnData>" +
                    "<dataType>1</dataType>" +
                    "<operType>2</operType>" +
                    "<uniCode>"+code+"</uniCode>" +
                    "</returnData>";
            if ("3".equals(type)){
                WebServiceUtils.webserviceInvok(smUrl,xml2);
            }else{
                WebServiceUtils.webserviceInvok(smUrl,xml);
            }
        } catch (Exception e) {
            log.error("orgSync error->",e);
        }
        return "{\"data\":{},\"code\":\"1\",\"msg\":\"成功\"}";
    }
    @RequestMapping("/userSync")
    public String userSync(@RequestBody String json){
        log.info("userSync json->{}",json);
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            String type = jsonObject.getString("type");
            JSONObject user = jsonObject.getJSONObject("user");
            String jtLoginName = user.getString("jtLoginName");
            String orgCode = user.getString("orgCode");
            JSONObject employee = user.getJSONObject("employee");
            String realName = employee.getString("realName");
            JSONObject contact = employee.getJSONObject("contact");
            String mobilePhone1 = contact.getString("mobilePhone1");
            String email = contact.getString("email");
            String xml="<returnData>" +
                    "<dataType>2</dataType>" +
                    "<operType>1</operType>" +
                    "<uniCode>"+jtLoginName+"</uniCode>" +
                    "<syncContent>" +
                    "<user>" +
                    "<singleValue>" +
                    "<loginName>"+jtLoginName+"</loginName>" +
                    "<userName>"+realName+"</userName>" +
                    "<mail>"+email+"</mail>" +
                    "<userFrom>0</userFrom>" +
                    "<ctPreferredMobile>"+mobilePhone1+"</ctPreferredMobile>" +
                    "</singleValue>" +
                    "<principal>" +
                    "<dept>" +
                    "<singleValue>" +
                    "<showNum>10</showNum>" +
                    "<origCode>"+orgCode+"</origCode>" +
                    "</singleValue>" +
                    "</dept>" +
                    "</principal>" +
                    "</user>" +
                    "</syncContent>" +
                    "</returnData>";
            String xml2="<returnData>" +
                    "<dataType>2</dataType>" +
                    "<operType>2</operType>" +
                    "<uniCode>"+jtLoginName+"</uniCode>" +
                    "</returnData>";
            if ("3".equals(type)){
                WebServiceUtils.webserviceInvok(smUrl,xml2);
            }else{
                WebServiceUtils.webserviceInvok(smUrl,xml);
            }
        } catch (Exception e) {
            log.error("userSync error->",e);
        }
        return "{\"data\":{},\"code\":\"1\",\"msg\":\"成功\"}";
    }

}