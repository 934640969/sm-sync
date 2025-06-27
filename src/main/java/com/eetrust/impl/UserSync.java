package com.eetrust.impl;

import com.alibaba.fastjson.JSON;
import com.sf.bdus.dist.client.repository.DataRepository;
import com.sf.bdus.dist.common.context.DataContext;
import com.sf.bdus.dist.common.dto.EmpDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author huangg
 * @create 2024/1/16 14:16
 * 人员同步实现类
 */
@Repository
public class UserSync implements DataRepository<EmpDataDTO> {
    private static final Logger log = LoggerFactory.getLogger(UserSync.class);


    @Autowired
    private SyncDataIncreService syncDataIncreService;

    @Override
    public void save(Iterator<EmpDataDTO> iterator, DataContext dataContext) throws IOException {
        log.info("开始同步EmpDataDTO数据");
        final int batchSize = 100; // 每次保存的记录数目，在处理大量数据时，为了避免将全部数据加载到内存，从而导致内存溢出
        List<EmpDataDTO> empDataDTOS = new ArrayList<>(batchSize);
        while (iterator.hasNext()) {
            EmpDataDTO empDataDTO = iterator.next();
            empDataDTOS.add(empDataDTO);
            if (empDataDTOS.size() >= batchSize) {
                // 保存数据
                baocun(empDataDTOS);
                empDataDTOS.clear();
            }
        }
        if (!empDataDTOS.isEmpty()) {
            // 保存数据
            baocun(empDataDTOS);
        }
    }

    private void baocun(List<EmpDataDTO> empDataDTOS) {
        log.info("开始保存EmpDataDTO数据");
        for (EmpDataDTO empDataDTO:empDataDTOS){
            String jsonString = JSON.toJSONString(empDataDTO);
            log.info("empDataDTO->{}", jsonString);
            String empName = empDataDTO.getEmpName();
            String empNum = empDataDTO.getEmpNum();
            Long orgId = empDataDTO.getOrgId();
            syncDataIncreService.saveUser(empNum,empName,orgId);
        }
    }
}
