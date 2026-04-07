package com.eetrust.impl;

import com.eetrust.contant.syncContant;
import com.eetrust.domain.TSyncDataIncre;
import com.eetrust.domain.TSyncDataIncreBak;
import com.eetrust.domain.TSyncDataIncreLog;
import com.eetrust.mapper.TSyncDataIncreBakMapper;
import com.eetrust.mapper.TSyncDataIncreLogBakMapper;
import com.eetrust.mapper.TSyncDataIncreLogMapper;
import com.eetrust.mapper.TSyncDataIncreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.eetrust.util.xmlUtil.removeXmlSpecialChars;

/**
 * @Author huangg
 * @create 2025/6/26 14:45
 */
@Service
public class SyncDataIncreService {
    private static final Logger log = LoggerFactory.getLogger(SyncDataIncreService.class);

    @Autowired
    private TSyncDataIncreMapper tSyncDataIncreMapper;
    @Autowired
    private TSyncDataIncreLogMapper tSyncDataIncreLogMapper;
    @Autowired
    private TSyncDataIncreBakMapper tSyncDataIncreBakMapper;
    @Autowired
    private TSyncDataIncreLogBakMapper tSyncDataIncreLogBakMapper;


    /**
     * 保存部门数据
     * @param orgId
     * @param orgIdParent
     * @param orgName
     */
    public void saveDept(Long orgId, Long orgIdParent, String orgName) {
        orgName=removeXmlSpecialChars(orgName);
        String xml="<root>" +
                "<privateKey>UAP_2oSY90</privateKey>" +
                "<srcContent></srcContent>" +
                "<dataContent>" +
                "<syncContent dataType=\"1\" operType=\"1\">" +
                "<syncUnicode>"+orgId+"</syncUnicode>" +
                "<newContent>" +
                "<baseInfo>" +
                "<deptName>"+orgName+"</deptName>" +
                "<deptUniCode>"+orgId+"</deptUniCode>" +
                "<deptStatus>1</deptStatus>" +
                "<showNum>10</showNum>"+
                "<isCorp>0</isCorp>" +
                "</baseInfo>" +
                "<parentInfo>" +
                "<parentCode>"+orgIdParent+"</parentCode>" +
                "</parentInfo>" +
                "</newContent></syncContent></dataContent></root>";

        TSyncDataIncre tSyncDataIncre = new TSyncDataIncre();
        tSyncDataIncre.setType(syncContant.SYNC_TYPE_DEPT);
        tSyncDataIncre.setUniqueField(String.valueOf(orgId));
        tSyncDataIncre.setParentCode(String.valueOf(orgIdParent));
        tSyncDataIncre.setName(orgName);
        tSyncDataIncre.setContent(xml);
        Date date = new Date();
        tSyncDataIncre.setCreateTime(date);
        tSyncDataIncre.setUpdateTime(date);
        tSyncDataIncre.setState(syncContant.SYNC_STATUS_PENDING);
        tSyncDataIncre.setErrorCount(0);
        tSyncDataIncreMapper.insert(tSyncDataIncre);
    }

    /**
     *  保存用户数据
     * @param empNum
     * @param empName
     * @param orgId
     */
    public void saveUser(String empNum, String empName, Long orgId) {
        empName=removeXmlSpecialChars(empName);
        String xml="<root>" +
                "<privateKey>UAP_2oSY90</privateKey>" +
                "<srcContent></srcContent>" +
                "<dataContent>" +
                "<syncContent dataType=\"2\" operType=\"1\">" +
                "<syncUnicode>"+ empNum +"</syncUnicode>" +
                "<newContent>" +
                "<baseInfo>" +
                "<loginName>"+ empNum +"</loginName>" +
                "<accountStatus>1</accountStatus>" +
                "<userName>"+empName+"</userName>" +
                "<secLevel>5</secLevel>" +
                "</baseInfo>" +
                "<parentInfo>" +
                "<parentCode>"+ orgId +"</parentCode>" +
                "</parentInfo>" +
                "</newContent></syncContent></dataContent></root>";

        TSyncDataIncre tSyncDataIncre = new TSyncDataIncre();
        tSyncDataIncre.setType(syncContant.SYNC_TYPE_USER);
        tSyncDataIncre.setUniqueField(empNum);
        tSyncDataIncre.setParentCode(String.valueOf(orgId));
        tSyncDataIncre.setName(empName);
        tSyncDataIncre.setContent(xml);
        Date date = new Date();
        tSyncDataIncre.setCreateTime(date);
        tSyncDataIncre.setUpdateTime(date);
        tSyncDataIncre.setState(syncContant.SYNC_STATUS_PENDING);
        tSyncDataIncre.setErrorCount(0);
        tSyncDataIncreMapper.insert(tSyncDataIncre);

    }

    /**
     *  保存用户数据
     * @param jobNumber
     * @param name
     * @param colDeptid
     */
    public void saveUser(String jobNumber, String name, String colDeptid) {
        name=removeXmlSpecialChars(name);
        String xml="<root>" +
                "<privateKey>UAP_2oSY90</privateKey>" +
                "<srcContent></srcContent>" +
                "<dataContent>" +
                "<syncContent dataType=\"2\" operType=\"1\">" +
                "<syncUnicode>"+ jobNumber +"</syncUnicode>" +
                "<newContent>" +
                "<baseInfo>" +
                "<loginName>"+ jobNumber +"</loginName>" +
                "<accountStatus>1</accountStatus>" +
                "<userName>"+name+"</userName>" +
                "<secLevel>5</secLevel>" +
                "</baseInfo>" +
                "<parentInfo>" +
                "<parentCode>"+ colDeptid +"</parentCode>" +
                "</parentInfo>" +
                "</newContent></syncContent></dataContent></root>";

        TSyncDataIncre tSyncDataIncre = new TSyncDataIncre();
        tSyncDataIncre.setType(syncContant.SYNC_TYPE_USER);
        tSyncDataIncre.setUniqueField(jobNumber);
        tSyncDataIncre.setParentCode(colDeptid);
        tSyncDataIncre.setName(name);
        tSyncDataIncre.setContent(xml);
        Date date = new Date();
        tSyncDataIncre.setCreateTime(date);
        tSyncDataIncre.setUpdateTime(date);
        tSyncDataIncre.setState(syncContant.SYNC_STATUS_PENDING);
        tSyncDataIncre.setErrorCount(0);
        tSyncDataIncreMapper.insert(tSyncDataIncre);
    }
    public void saveLog(TSyncDataIncre tSyncDataIncre, String result, int syncStatus){
        TSyncDataIncreLog tSyncDataIncreLog = new TSyncDataIncreLog();
        tSyncDataIncreLog.setType(tSyncDataIncre.getType());
        tSyncDataIncreLog.setUniqueField(tSyncDataIncre.getUniqueField());
        tSyncDataIncreLog.setParentCode(tSyncDataIncre.getParentCode());
        tSyncDataIncreLog.setName(tSyncDataIncre.getName());
        tSyncDataIncreLog.setContent(tSyncDataIncre.getContent());
        tSyncDataIncreLog.setCreateTime(new Date());
        tSyncDataIncreLog.setState(syncStatus);
        tSyncDataIncreLog.setResult(result);
        tSyncDataIncreLogMapper.insert(tSyncDataIncreLog);
    }

    /**
     * 备份同步数据到备份表并删除原数据
     * @param tSyncDataIncre 待备份的数据
     */
    public void backupAndDeleteIncre(TSyncDataIncre tSyncDataIncre) {
        TSyncDataIncreBak bak = new TSyncDataIncreBak();
        bak.setType(tSyncDataIncre.getType());
        bak.setUniqueField(tSyncDataIncre.getUniqueField());
        bak.setParentCode(tSyncDataIncre.getParentCode());
        bak.setName(tSyncDataIncre.getName());
        bak.setContent(tSyncDataIncre.getContent());
        bak.setCreateTime(tSyncDataIncre.getCreateTime());
        bak.setUpdateTime(tSyncDataIncre.getUpdateTime());
        bak.setState(tSyncDataIncre.getState());
        bak.setResult(tSyncDataIncre.getResult());
        bak.setErrorCount(tSyncDataIncre.getErrorCount());
        tSyncDataIncreBakMapper.insert(bak);
        tSyncDataIncreMapper.deleteById(tSyncDataIncre.getId());
    }

    /**
     * 安全备份日志表数据（基于ID范围，避免数据丢失）
     * 采用分批策略，每批处理固定数量的数据，避免大数据量导致的性能问题
     */
    @Transactional(rollbackFor = Exception.class)
    public void backupAndClearLogSafe() {
        try {
            // 步骤1：获取当前表中的最小ID和最大ID
            Long minId = tSyncDataIncreLogMapper.getMinId();
            Long maxId = tSyncDataIncreLogMapper.getMaxId();
            
            if (minId == null || maxId == null || minId == 0 || maxId == 0 || minId > maxId) {
                log.info("日志表为空，无需备份");
                return;
            }
            
            long totalRecords = maxId - minId + 1;
            log.info("开始备份日志数据，ID范围: [{} - {}], 预计处理数量: {}", minId, maxId, totalRecords);
            
            // 步骤2：分批处理，每批1000条
            int batchSize = 1000;
            long currentMinId = minId;
            long totalBackupCount = 0;
            long totalDeleteCount = 0;
            int batchNum = 0;
            
            while (currentMinId <= maxId) {
                batchNum++;
                long currentMaxId = Math.min(currentMinId + batchSize, maxId + 1);
                
                log.info("处理第{}批，ID范围: [{} - {})", batchNum, currentMinId, currentMaxId);
                
                // 备份当前批次
                int backupCount = tSyncDataIncreLogMapper.backupLogsByIdRange(currentMinId, currentMaxId);
                totalBackupCount += backupCount;
                log.info("第{}批备份完成，备份数量: {}", batchNum, backupCount);
                
                if (backupCount > 0) {
                    // 删除已备份的数据
                    int deleteCount = tSyncDataIncreLogMapper.deleteLogsByIdRange(currentMinId, currentMaxId);
                    totalDeleteCount += deleteCount;
                    log.info("第{}批删除完成，删除数量: {}", batchNum, deleteCount);
                    
                    // 验证数据一致性
                    if (backupCount != deleteCount) {
                        log.error("第{}批数据不一致！备份数量: {}, 删除数量: {}", batchNum, backupCount, deleteCount);
                        throw new RuntimeException(String.format(
                            "第%d批数据不一致，备份:%d, 删除:%d", batchNum, backupCount, deleteCount));
                    }
                }
                
                // 移动到下一批
                currentMinId = currentMaxId;
            }
            
            log.info("日志备份任务执行成功，共处理{}批，备份总数: {}, 删除总数: {}", 
                    batchNum, totalBackupCount, totalDeleteCount);
            
        } catch (Exception e) {
            log.error("日志备份任务执行失败", e);
            throw new RuntimeException("日志备份失败", e);
        }
    }



}


