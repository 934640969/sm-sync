package com.eetrust.impl;

import com.alibaba.fastjson.JSON;
import com.sf.bdus.dist.client.repository.DataRepository;
import com.sf.bdus.dist.common.context.DataContext;
import com.sf.bdus.dist.common.dto.KexEmpDTO;
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
 * KEX人员主数据（泰国）
 */
@Repository
public class UserSync4 implements DataRepository<KexEmpDTO> {
    private static final Logger log = LoggerFactory.getLogger(UserSync4.class);

    @Autowired
    private SyncDataIncreService syncDataIncreService;

    @Override
    public void save(Iterator<KexEmpDTO> iterator, DataContext dataContext) throws IOException {
        log.info("开始同步KexEmpDTO数据");
        final int batchSize = 100; // 每次保存的记录数目，在处理大量数据时，为了避免将全部数据加载到内存，从而导致内存溢出
        List<KexEmpDTO> kexEmpDTOS = new ArrayList<>(batchSize);
        while (iterator.hasNext()) {
            KexEmpDTO kexEmpDTO = iterator.next();
            kexEmpDTOS.add(kexEmpDTO);
            if (kexEmpDTOS.size() >= batchSize) {
                // 保存数据
                baocun(kexEmpDTOS);
                kexEmpDTOS.clear();
            }
        }
        if (!kexEmpDTOS.isEmpty()) {
            // 保存数据
            baocun(kexEmpDTOS);
        }
    }

    private void baocun(List<KexEmpDTO> kexEmpDTOS) {
        log.info("开始保存KexEmpDTO数据");
        for (KexEmpDTO kexEmpDTO:kexEmpDTOS){
            String jsonString = JSON.toJSONString(kexEmpDTO);
            log.info("kexEmpDTO->{}", jsonString);
            String empName = kexEmpDTO.getEmpName();
            String empNum = kexEmpDTO.getEmpNum();
            Long orgId = kexEmpDTO.getOrgId();
            syncDataIncreService.saveUser(empNum,empName,orgId);
        }
    }
}
