package com.eetrust.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sf.bdus.dist.client.repository.DataRepository;
import com.sf.bdus.dist.common.context.DataContext;
import com.sf.bdus.dist.common.dto.UcUserAccountDataDTO;
import com.sf.bdus.dist.common.dto.UcUserInfoDataDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.eetrust.util.WebServiceUtils.webserviceInvok;

/**
 * @Author huangg
 * @create 2024/1/16 14:16
 * 人员主数据
 */
@Repository
public class UserSync3 implements DataRepository<UcUserInfoDataDTO> {
    private static final Logger log = LoggerFactory.getLogger(UserSync3.class);

    @Value("${sm.url}")
    private String smUrl;

    @Override
    public void save(Iterator<UcUserInfoDataDTO> iterator, DataContext dataContext) {
        log.info("开始同步UcUserInfoDataDTO数据");
        final int batchSize = 100; // 每次保存的记录数目，在处理大量数据时，为了避免将全部数据加载到内存，从而导致内存溢出
        List<UcUserInfoDataDTO> ucUserInfoDataDTOS = new ArrayList<>(batchSize);
        while (iterator.hasNext()) {
            UcUserInfoDataDTO ucUserInfoDataDTO = iterator.next();
            ucUserInfoDataDTOS.add(ucUserInfoDataDTO);
            if (ucUserInfoDataDTOS.size() >= batchSize) {
                // 保存数据
                baocun(ucUserInfoDataDTOS);
                ucUserInfoDataDTOS.clear();
            }
        }
        if (!ucUserInfoDataDTOS.isEmpty()) {
            // 保存数据
            baocun(ucUserInfoDataDTOS);
        }
    }

    private void baocun(List<UcUserInfoDataDTO> ucUserInfoDataDTOS) {
        log.info("开始保存UcUserInfoDataDTO数据");
        for (UcUserInfoDataDTO ucUserInfoDataDTO:ucUserInfoDataDTOS){
            String jobNumber = ucUserInfoDataDTO.getJobNumber();
            String name = ucUserInfoDataDTO.getName();
            List<UcUserAccountDataDTO> accounts = ucUserInfoDataDTO.getAccounts();
            if (StringUtils.isEmpty(jobNumber)){
                continue;
            }
            if (StringUtils.isEmpty(name)){
                continue;
            }
            if (accounts==null){
                continue;
            }
            String authority = accounts.get(0).getAuthority();
            if (authority==null||"null".equals(authority)||"".equals(authority)){
                continue;
            }
            String jsonString = JSON.toJSONString(ucUserInfoDataDTO);
            log.info("jsonString->"+jsonString);
            log.info("authority->"+authority);
            JSONObject jsonObject = JSON.parseObject(authority);
            String colDeptid = jsonObject.getString("colDeptid");
            if (StringUtils.isEmpty(colDeptid)){
                continue;
            }
            String xml="<root>" +
                    "<privateKey>UAP_2oSY90</privateKey>" +
                    "<srcContent></srcContent>" +
                    "<dataContent>" +
                    "<syncContent dataType=\"2\" operType=\"1\">" +
                    "<syncUnicode>"+jobNumber+"</syncUnicode>" +
                    "<newContent>" +
                    "<baseInfo>" +
                    "<loginName>"+jobNumber+"</loginName>" +
                    "<accountStatus>1</accountStatus>" +
                    "<userName>"+name+"</userName>" +
                    "<secLevel>5</secLevel>" +
                    "</baseInfo>" +
                    "<parentInfo>" +
                    "<parentCode>"+colDeptid+"</parentCode>" +
                    "</parentInfo>" +
                    "</newContent></syncContent></dataContent></root>";
            webserviceInvok(smUrl,xml);
        }
    }
}
