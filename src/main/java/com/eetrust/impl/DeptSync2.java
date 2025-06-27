package com.eetrust.impl;

import com.alibaba.fastjson.JSON;
import com.sf.bdus.dist.client.repository.DataRepository;
import com.sf.bdus.dist.common.context.DataContext;
import com.sf.bdus.dist.common.dto.AggOrgDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.eetrust.util.WebServiceUtils.webserviceInvok;
import static com.eetrust.util.xmlUtil.removeXmlSpecialChars;

/**
 * @Author huangg
 * @create 2024/1/16 14:16
 * 聚合组织主数据
 */
@Repository
public class DeptSync2 implements DataRepository<AggOrgDTO> {
    private static final Logger log = LoggerFactory.getLogger(DeptSync2.class);

    @Value("${rootCode}")
    private Long rootCode;
    @Autowired
    private SyncDataIncreService syncDataIncreService;

    @Override
    public void save(Iterator<AggOrgDTO> iterator, DataContext dataContext) throws IOException {
        log.info("开始同步AggOrgDTO数据");
        final int batchSize = 100; // 每次保存的记录数目，在处理大量数据时，为了避免将全部数据加载到内存，从而导致内存溢出
        List<AggOrgDTO> orgDatas = new ArrayList<>(batchSize);
        while (iterator.hasNext()) {
            AggOrgDTO aggOrgDTO = iterator.next();
            orgDatas.add(aggOrgDTO);
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

    private void baocun(List<AggOrgDTO> orgDatas) {
        log.info("开始保存AggOrgDTO数据");
        for (AggOrgDTO aggOrgDTO:orgDatas){
            String jsonString = JSON.toJSONString(aggOrgDTO);
            log.info("aggOrgDTO->{}", jsonString);
            Long orgIdParent = aggOrgDTO.getOrgIdParent();
            if (aggOrgDTO.getOrgId().equals(rootCode)){
                orgIdParent= rootCode;
            }
            //顺丰存在多个根组织情况 父级为0为根组织
            Long orgId = aggOrgDTO.getOrgId();
            if (orgIdParent.equals(0L)){
                orgIdParent=orgId;
            }
            String orgName = aggOrgDTO.getOrgName();
            syncDataIncreService.saveDept(orgId, orgIdParent, orgName);
        }
    }
}
