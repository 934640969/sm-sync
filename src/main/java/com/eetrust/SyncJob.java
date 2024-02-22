package com.eetrust;

import cn.hutool.http.HttpUtil;
import cn.hutool.setting.dialect.Props;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eetrust.util.WebServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author huangg
 * @create 2024/2/22 11:28
 */
public class SyncJob {

    private static final Logger log = LoggerFactory.getLogger(SyncJob.class);
    private static String smUrl;
    private static String oaUrl;
    private static String type;
    private static String sysCode;

    static {
        Props props = new Props("application.properties","GBK");
        smUrl = props.getProperty("smUrl");
        type = props.getProperty("type");
        if("test".equals(type)){
            oaUrl=props.getProperty("testUrl");
        }else{
            oaUrl=props.getProperty("prodUrl");
        }
        sysCode=props.getProperty("sysCode");
    }


    public void sync(){
        log.info("开始同步");
        String getAllOrgsUrl=oaUrl+"IdealService/getAllOrgs.htm?sysCode="+sysCode;
        log.info("getAllOrgsUrl->"+getAllOrgsUrl);
        String s = HttpUtil.get(getAllOrgsUrl);
        log.info("getAllOrgs->"+s);
        JSONObject jsonObject = JSON.parseObject(s);
        Boolean success = jsonObject.getBoolean("success");
        if (!success){
            String message = jsonObject.getString("message");
            log.info("success=fasle,msg="+message);
            return;
        }
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray dataList = data.getJSONArray("dataList");
        for (int i = 0; i <dataList.size() ; i++) {
            JSONObject jsonObject1 = dataList.getJSONObject(i);
            String orgCode = jsonObject1.getString("orgCode");
            String orgName = jsonObject1.getString("orgName");
            String orderNum = jsonObject1.getString("orderNum");
            String parentOrgCode = jsonObject1.getString("parentOrgCode");

            String xml="<?xml version='1.0' encoding='UTF-8'?>" +
                    "<requestData>" +
                    "<dataType>1</dataType>" +
                    "<operType>1</operType>" +
                    "<uniCode>"+orgCode+"</uniCode>" +
                    "<syncContent>" +
                    "<orgnization>" +
                    "<singleValue>" +
                    "<deptName>"+orgName+"</deptName>" +
                    "<showNum>"+orderNum+"</showNum>" +
                   // "<isRoot>1</isRoot>" +
                    "</singleValue>" +
                    "<parent>" +
                    "<dept>" +
                    "<singleValue>" +
                    "<origCode>"+parentOrgCode+"</origCode>" +
                    "</singleValue></dept></parent></orgnization></syncContent></requestData>";
            WebServiceUtils.webserviceInvok(smUrl,xml);
            getUser(orgCode);
            getDept(orgCode);


        }

    }
    private void getDept(String parentOrgCode) {
        String getAllDepartsUrl=oaUrl+"IdealService/getAllDeparts.htm?sysCode="+sysCode+"&orgCode="+ parentOrgCode;
        log.info("getAllDepartsUrl->"+getAllDepartsUrl);
        String s = HttpUtil.get(getAllDepartsUrl);
        log.info("getAllDeparts->"+s);
        JSONObject jsonObject = JSON.parseObject(s);
        Boolean success = jsonObject.getBoolean("success");
        if (!success){
            String message = jsonObject.getString("message");
            log.info("success=fasle,msg="+message);
            return;
        }
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray dataList = data.getJSONArray("dataList");
        for (int i = 0; i <dataList.size() ; i++) {
            JSONObject jsonObject1 = dataList.getJSONObject(i);
            String orgCode = jsonObject1.getString("orgCode");
            String orgName = jsonObject1.getString("orgName");
            String orderNum = jsonObject1.getString("orderNum");
            String xml="<?xml version='1.0' encoding='UTF-8'?>" +
                    "<requestData>" +
                    "<dataType>1</dataType>" +
                    "<operType>1</operType>" +
                    "<uniCode>"+orgCode+"</uniCode>" +
                    "<syncContent>" +
                    "<orgnization>" +
                    "<singleValue>" +
                    "<deptName>"+orgName+"</deptName>" +
                    "<showNum>"+orderNum+"</showNum>" +
                    // "<isRoot>1</isRoot>" +
                    "</singleValue>" +
                    "<parent>" +
                    "<dept>" +
                    "<singleValue>" +
                    "<origCode>"+parentOrgCode+"</origCode>" +
                    "</singleValue></dept></parent></orgnization></syncContent></requestData>";
            WebServiceUtils.webserviceInvok(smUrl,xml);
            getUser(orgCode);
            getDept(orgCode);
        }
    }

    private void getUser(String parentOrgCode) {
        String getEmployeeListUrl=oaUrl+"IdealService/getEmployeeList.htm?sysCode="+sysCode+"&orgCode="+ parentOrgCode;
        log.info("getEmployeeListUrl->"+getEmployeeListUrl);
        String s = HttpUtil.get(getEmployeeListUrl);
        log.info("getEmployeeListUrl->"+s);
        JSONObject jsonObject = JSON.parseObject(s);
        Boolean success = jsonObject.getBoolean("success");
        if (!success){
            String message = jsonObject.getString("message");
            log.info("success=fasle,msg="+message);
            return;
        }
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray dataList = data.getJSONArray("dataList");
        for (int i = 0; i <dataList.size() ; i++) {
            JSONObject jsonObject1 = dataList.getJSONObject(i);
            String loginName = jsonObject1.getString("loginName");
            String realName = jsonObject1.getString("realName");
            String email = jsonObject1.getString("email");
            String mobilePhone = jsonObject1.getString("mobilePhone");
            String xml="<returnData>" +
                    "<dataType>2</dataType>" +
                    "<operType>1</operType>" +
                    "<uniCode>"+loginName+"</uniCode>" +
                    "<syncContent>" +
                    "<user>" +
                    "<singleValue>" +
                    "<loginName>"+loginName+"</loginName>" +
                    "<userName>"+realName+"</userName>" +
                    "<mail>"+email+"</mail>" +
                    "<userFrom>0</userFrom>" +
                    "<ctPreferredMobile>"+mobilePhone+"</ctPreferredMobile>" +
                    "</singleValue>" +
                    "<principal>" +
                    "<dept>" +
                    "<singleValue>" +
                    "<showNum>10</showNum>" +
                    "<origCode>"+parentOrgCode+"</origCode>" +
                    "</singleValue>" +
                    "</dept>" +
                    "</principal>" +
                    "</user>" +
                    "</syncContent>" +
                    "</returnData>";
            WebServiceUtils.webserviceInvok(smUrl,xml);
        }
    }
}
