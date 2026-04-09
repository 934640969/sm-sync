package com.eetrust.impl;

import com.eetrust.contant.syncContant;
import com.eetrust.domain.TSyncDataIncre;
import com.eetrust.mapper.TSyncDataIncreMapper;
import com.eetrust.util.WebServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author huangg
 * @create 2025/6/26 15:58
 */
@Component
public class SyncJob {
    private static final Logger log = LoggerFactory.getLogger(SyncJob.class);

    @Autowired
    private TSyncDataIncreMapper tSyncDataIncreMapper;
    @Autowired
    private SyncDataIncreService syncDataIncreService;

    @Value("${sm.url}")
    private String smUrl;
    @Value("${errorCount}")
    private Integer errorCount;
    @Value("${sleepTime:1000}")
    private  int sleepTime;

    /**
     * 处理待同步数据
     */
    @Scheduled(cron = "${cron}")
    public void sync4pend() {
        log.info("开始处理待同步数据");
        // 获取同步数据
        boolean flag = true;
        while (flag) {
            List<TSyncDataIncre> tSyncDataIncres = tSyncDataIncreMapper.selectByStateInAndOrder();
            for (TSyncDataIncre tSyncDataIncre : tSyncDataIncres) {
                String s = WebServiceUtils.webserviceInvok(smUrl, tSyncDataIncre.getContent(),sleepTime);
                //<?xml version="1.0" encoding="GBK"?><returnData><status>1</status><errcode></errcode><errormsg>用户同步成功</errormsg></returnData>
                //<?xml version="1.0" encoding="GBK"?><returnData><status>0</status><errcode></errcode><errormsg>同步用户的组织机构不存在</errormsg></returnData>
                //<?xml version="1.0" encoding="GBK"?><returnData><status>1</status><errcode></errcode><errormsg>组织机构同步成功</errormsg></returnData>
                //<?xml version="1.0" encoding="GBK"?><returnData><status>0</status><errcode></errcode><errormsg>父级部门不存在,id=9989710065467</errormsg></returnData>
                //<?xml version="1.0" encoding="GBK"?><returnData><status>0</status><errcode></errcode><errormsg>xml解析失败</errormsg></returnData>
                if (s.contains("<status>1</status>")) {
                    syncDataIncreService.saveLog(tSyncDataIncre, s, syncContant.SYNC_STATUS_SUCCESS);
                    tSyncDataIncreMapper.deleteById(tSyncDataIncre);
                } else if (s.contains("<status>0</status>")) {
                    syncDataIncreService.saveLog(tSyncDataIncre, s, syncContant.SYNC_STATUS_FAILED);
                    tSyncDataIncre.setUpdateTime(new Date());
                    tSyncDataIncre.setResult(s);
                    tSyncDataIncre.setState(syncContant.SYNC_STATUS_FAILED);
                    tSyncDataIncre.setErrorCount(tSyncDataIncre.getErrorCount() + 1);
                    if (tSyncDataIncre.getErrorCount() >= errorCount) {
                        // 失败次数达到阈值，直接删除
                        log.info("数据同步失败{}次，直接删除。ID: {}, uniqueField: {}",
                                tSyncDataIncre.getErrorCount(), tSyncDataIncre.getId(), tSyncDataIncre.getUniqueField());
                        syncDataIncreService.deleteFailedIncre(tSyncDataIncre);
                    } else {
                        tSyncDataIncreMapper.updateById(tSyncDataIncre);
                    }
                }
            }
            if (tSyncDataIncres.isEmpty()) {
                flag = false;
            }
        }
        log.info("处理待同步数据结束");
    }

    /**
     * 每月清理旧日志数据（保留月数从配置读取）
     * 每月1号凌晨2点执行
     */
    @Scheduled(cron = "${logBakcron}")
    public void cleanOldLogsMonthly() {
        log.info("开始执行月度日志清理任务");
        try {
            long startTime = System.currentTimeMillis();
            syncDataIncreService.cleanOldLogs();
            long endTime = System.currentTimeMillis();
            log.info("月度日志清理任务执行成功，耗时: {} ms", (endTime - startTime));
        } catch (Exception e) {
            log.error("月度日志清理任务执行失败", e);
        }
    }
}