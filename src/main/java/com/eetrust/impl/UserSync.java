package com.eetrust.impl;

import com.sf.bdus.dist.client.repository.DataRepository;
import com.sf.bdus.dist.common.context.DataContext;
import com.sf.bdus.dist.common.dto.EmpDataDTO;
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
public class UserSync implements DataRepository<EmpDataDTO> {
    @Value("${sm.url}")
    private String smUrl;

    @Override
    public void save(Iterator<EmpDataDTO> iterator, DataContext dataContext) throws IOException {
        final int batchSize = 100; // 每次保存的记录数目，在处理大量数据时，为了避免将全部数据加载到内存，从而导致内存溢出
        List<EmpDataDTO> empDataDTOS = new ArrayList<>(batchSize);
        while (iterator.hasNext()) {
            EmpDataDTO empDataDTO = iterator.next();
            empDataDTOS.add(empDataDTO);
            if (empDataDTOS.size() >= batchSize) {
                // 保存数据
                save(empDataDTOS);
                empDataDTOS.clear();
            }
        }
        if (!empDataDTOS.isEmpty()) {
            // 保存数据
            save(empDataDTOS);
        }
    }

    private void save(List<EmpDataDTO> empDataDTOS) {
        for (EmpDataDTO empDataDTO:empDataDTOS){
            String xml="<root>" +
                    "<privateKey>UAP_2oSY90</privateKey>" +
                    "<srcContent></srcContent>" +
                    "<dataContent>" +
                    "<syncContent dataType=\"2\" operType=\"1\">" +
                    "<syncUnicode>"+empDataDTO.getEmpNum()+"</syncUnicode>" +
                    "<newContent>" +
                    "<baseInfo>" +
                    "<loginName>"+empDataDTO.getEmpNum()+"</loginName>" +
                    "<accountStatus>1</accountStatus>" +
                    "<userName>"+empDataDTO.getEmpName()+"</userName>" +
                    "<secLevel>2</secLevel>" +
                    "</baseInfo>" +
                    "<parentInfo>" +
                    "<parentCode>"+empDataDTO.getOrgId()+"</parentCode>" +
                    "</parentInfo>" +
                    "</newContent></syncContent></dataContent></root>";
            webserviceInvok(smUrl,xml);
        }
    }
}
