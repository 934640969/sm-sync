package com.eetrust.impl;

import com.eetrust.contant.syncContant;
import com.eetrust.domain.TSyncDataIncre;
import com.eetrust.domain.TSyncDataIncreLog;
import com.eetrust.mapper.TSyncDataIncreLogMapper;
import com.eetrust.mapper.TSyncDataIncreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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
    @Value("${logRetentionMonths:3}")
    private Integer logRetentionMonths;
    @Value("${logBatchDeleteSize:1000}")
    private Integer logBatchDeleteSize;
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
     * 直接删除失败的业务数据（不再备份）
     * @param tSyncDataIncre 待删除的数据
     */
    public void deleteFailedIncre(TSyncDataIncre tSyncDataIncre) {
        log.info("删除失败数据。ID: {}, uniqueField: {}, errorCount: {}", 
                tSyncDataIncre.getId(), tSyncDataIncre.getUniqueField(), tSyncDataIncre.getErrorCount());
        tSyncDataIncreMapper.deleteById(tSyncDataIncre.getId());
    }

    /**
     * 定时删除旧日志数据（保留月数从配置读取，分批删除）
     */
    public void cleanOldLogs() {
        try {
            // 计算指定月数前的日期
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -logRetentionMonths);
            Date cutoffDate = calendar.getTime();
            
            log.info("开始清理{}之前的日志数据（保留{}个月，每批删除{}条）", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cutoffDate), logRetentionMonths, logBatchDeleteSize);
            
            int totalDeletedCount = 0;
            int batchNum = 0;
            int deletedCount;
            
            // 循环分批删除，直到没有数据可删
            do {
                batchNum++;
                deletedCount = tSyncDataIncreLogMapper.deleteLogsBeforeDateWithLimit(cutoffDate, logBatchDeleteSize);
                totalDeletedCount += deletedCount;
                
                if (deletedCount > 0) {
                    log.info("第{}批删除完成，本批删除: {}条，累计删除: {}条", batchNum, deletedCount, totalDeletedCount);
                }
                
            } while (deletedCount > 0);
            
            if (totalDeletedCount > 0) {
                log.info("日志清理任务执行成功，共处理{}批，总删除数量: {}", batchNum, totalDeletedCount);
            } else {
                log.info("日志清理任务执行完成，无需清理的数据");
            }
            
        } catch (Exception e) {
            log.error("日志清理任务执行失败", e);
            throw new RuntimeException("日志清理失败", e);
        }
    }

}

