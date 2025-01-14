package com.eetrust.impl;

import com.sf.bdus.dist.client.repository.DataRepository;
import com.sf.bdus.dist.common.context.DataContext;
import com.sf.bdus.dist.common.dto.AggEmpDTO;
import com.sf.bdus.dist.common.dto.UcUserInfoDataDTO;
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
    public void save(Iterator<UcUserInfoDataDTO> iterator, DataContext dataContext) throws IOException {
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
            String xml="<root>" +
                    "<privateKey>UAP_2oSY90</privateKey>" +
                    "<srcContent></srcContent>" +
                    "<dataContent>" +
                    "<syncContent dataType=\"2\" operType=\"1\">" +
                    "<syncUnicode>"+ucUserInfoDataDTO.getJobNumber()+"</syncUnicode>" +
                    "<newContent>" +
                    "<baseInfo>" +
                    "<loginName>"+ucUserInfoDataDTO.getJobNumber()+"</loginName>" +
                    "<accountStatus>1</accountStatus>" +
                    "<userName>"+ucUserInfoDataDTO.getName()+"</userName>" +
                    "<secLevel>5</secLevel>" +
                    "</baseInfo>" +
                    "<parentInfo>" +
                    "<parentCode>"+ucUserInfoDataDTO.getAccounts().get(0).getAuthority()+"</parentCode>" +
                    "</parentInfo>" +
                    "</newContent></syncContent></dataContent></root>";
            webserviceInvok(smUrl,xml);
        }
    }
}
