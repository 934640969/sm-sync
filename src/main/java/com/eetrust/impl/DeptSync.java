package com.eetrust.impl;

import com.sf.bdus.dist.client.repository.DataRepository;
import com.sf.bdus.dist.common.context.DataContext;
import com.sf.bdus.dist.common.dto.OrgDataDTO;
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
 * 组织同步实现类
 */
@Repository
public class DeptSync implements DataRepository<OrgDataDTO> {
    private static final Logger log = LoggerFactory.getLogger(DeptSync.class);

    @Value("${sm.url}")
    private String smUrl;

    @Override
    public void save(Iterator<OrgDataDTO> iterator, DataContext dataContext) throws IOException {
        log.info("开始同步组织数据");
        final int batchSize = 100; // 每次保存的记录数目，在处理大量数据时，为了避免将全部数据加载到内存，从而导致内存溢出
        List<OrgDataDTO> orgDatas = new ArrayList<>(batchSize);
        while (iterator.hasNext()) {
            OrgDataDTO orgDataDTO = iterator.next();
            orgDatas.add(orgDataDTO);
            if (orgDatas.size() >= batchSize) {
                // 保存数据
                baocun(orgDatas);
                orgDatas.clear();
            }
        }
        if (!orgDatas.isEmpty()) {
            // 保存数据
            baocun(orgDatas);
        }
    }

    private void baocun(List<OrgDataDTO> orgDatas) {
        log.info("开始保存组织数据");
        for (OrgDataDTO orgDataDTO:orgDatas){
            String xml="<root>" +
                    "<privateKey>UAP_2oSY90</privateKey>" +
                    "<srcContent></srcContent>" +
                    "<dataContent>" +
                    "<syncContent dataType=\"1\" operType=\"1\">" +
                    "<syncUnicode>"+orgDataDTO.getOrgId()+"</syncUnicode>" +
                    "<newContent>" +
                    "<baseInfo>" +
                    "<deptName>"+orgDataDTO.getOrgName()+"</deptName>" +
                    "<deptUniCode>"+orgDataDTO.getOrgId()+"</deptUniCode>" +
                    "<deptStatus>1</deptStatus>" +
                    "<isCorp>0</isCorp>" +
                    "</baseInfo>" +
                    "<parentInfo>" +
                    "<parentCode>"+orgDataDTO.getOrgIdParent()+"</parentCode>" +
                    "</parentInfo>" +
                    "</newContent></syncContent></dataContent></root>";
            webserviceInvok(smUrl,xml);
        }
    }
}
