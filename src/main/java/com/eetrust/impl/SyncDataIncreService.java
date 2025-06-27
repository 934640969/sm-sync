package com.eetrust.impl;

import com.eetrust.contant.syncContant;
import com.eetrust.domain.TSyncDataIncre;
import com.eetrust.domain.TSyncDataIncreLog;
import com.eetrust.mapper.TSyncDataIncreLogMapper;
import com.eetrust.mapper.TSyncDataIncreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.eetrust.util.xmlUtil.removeXmlSpecialChars;

/**
 * @Author huangg
 * @create 2025/6/26 14:45
 */
@Service
public class SyncDataIncreService {

    @Autowired
    private TSyncDataIncreMapper tSyncDataIncreMapper;
    @Autowired
    private TSyncDataIncreLogMapper tSyncDataIncreLogMapper;


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



}
