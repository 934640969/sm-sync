package com.eetrust.impl;

import com.sf.bdus.dist.client.repository.DataRepository;
import com.sf.bdus.dist.common.context.DataContext;
import com.sf.bdus.dist.common.dto.KexOrgDTO;
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
 * KEX组织主数据（泰国）
 */
@Repository
public class DeptSync3 implements DataRepository<KexOrgDTO> {
    private static final Logger log = LoggerFactory.getLogger(DeptSync3.class);

    @Value("${sm.url}")
    private String smUrl;
    @Value("${rootCode}")
    private Long rootCode;

    @Override
    public void save(Iterator<KexOrgDTO> iterator, DataContext dataContext) throws IOException {
        log.info("开始同步KexOrgDTO数据");
        final int batchSize = 100; // 每次保存的记录数目，在处理大量数据时，为了避免将全部数据加载到内存，从而导致内存溢出
        List<KexOrgDTO> orgDatas = new ArrayList<>(batchSize);
        while (iterator.hasNext()) {
            KexOrgDTO kexOrgDTO = iterator.next();
            orgDatas.add(kexOrgDTO);
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

    private void baocun(List<KexOrgDTO> orgDatas) {
        log.info("开始保存KexOrgDTO数据");
        for (KexOrgDTO kexOrgDTO:orgDatas){
            Long orgIdParent = kexOrgDTO.getParentOrgId();
            if (kexOrgDTO.getOrgId().equals(rootCode)){
                orgIdParent= rootCode;
            }
            String xml="<root>" +
                    "<privateKey>UAP_2oSY90</privateKey>" +
                    "<srcContent></srcContent>" +
                    "<dataContent>" +
                    "<syncContent dataType=\"1\" operType=\"1\">" +
                    "<syncUnicode>"+kexOrgDTO.getOrgId()+"</syncUnicode>" +
                    "<newContent>" +
                    "<baseInfo>" +
                    "<deptName>"+kexOrgDTO.getOrgName()+"</deptName>" +
                    "<deptUniCode>"+kexOrgDTO.getOrgId()+"</deptUniCode>" +
                    "<deptStatus>1</deptStatus>" +
                    "<showNum>10</showNum>"+
                    "<isCorp>0</isCorp>" +
                    "</baseInfo>" +
                    "<parentInfo>" +
                    "<parentCode>"+orgIdParent+"</parentCode>" +
                    "</parentInfo>" +
                    "</newContent></syncContent></dataContent></root>";
            webserviceInvok(smUrl,xml);
        }
    }
}
