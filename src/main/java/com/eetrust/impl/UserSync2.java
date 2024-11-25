package com.eetrust.impl;

import com.sf.bdus.dist.client.repository.DataRepository;
import com.sf.bdus.dist.common.context.DataContext;
import com.sf.bdus.dist.common.dto.AggEmpDTO;
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
 * 人员同步实现类
 */
@Repository
public class UserSync2 implements DataRepository<AggEmpDTO> {
    private static final Logger log = LoggerFactory.getLogger(UserSync2.class);

    @Value("${sm.url}")
    private String smUrl;

    @Override
    public void save(Iterator<AggEmpDTO> iterator, DataContext dataContext) throws IOException {
        log.info("开始同步聚合人员数据");
        final int batchSize = 100; // 每次保存的记录数目，在处理大量数据时，为了避免将全部数据加载到内存，从而导致内存溢出
        List<AggEmpDTO> aggEmpDTOS = new ArrayList<>(batchSize);
        while (iterator.hasNext()) {
            AggEmpDTO aggEmpDTO = iterator.next();
            aggEmpDTOS.add(aggEmpDTO);
            if (aggEmpDTOS.size() >= batchSize) {
                // 保存数据
                baocun(aggEmpDTOS);
                aggEmpDTOS.clear();
            }
        }
        if (!aggEmpDTOS.isEmpty()) {
            // 保存数据
            baocun(aggEmpDTOS);
        }
    }

    private void baocun(List<AggEmpDTO> aggEmpDTOS) {
        log.info("开始保存聚合人员数据");
        for (AggEmpDTO aggEmpDTO:aggEmpDTOS){
            String xml="<root>" +
                    "<privateKey>UAP_2oSY90</privateKey>" +
                    "<srcContent></srcContent>" +
                    "<dataContent>" +
                    "<syncContent dataType=\"2\" operType=\"1\">" +
                    "<syncUnicode>"+aggEmpDTO.getEmpNum()+"</syncUnicode>" +
                    "<newContent>" +
                    "<baseInfo>" +
                    "<loginName>"+aggEmpDTO.getEmpNum()+"</loginName>" +
                    "<accountStatus>1</accountStatus>" +
                    "<userName>"+aggEmpDTO.getEmpName()+"</userName>" +
                    "<secLevel>0</secLevel>" +
                    "</baseInfo>" +
                    "<parentInfo>" +
                    "<parentCode>"+aggEmpDTO.getOrgId()+"</parentCode>" +
                    "</parentInfo>" +
                    "</newContent></syncContent></dataContent></root>";
            webserviceInvok(smUrl,xml);
        }
    }
}
