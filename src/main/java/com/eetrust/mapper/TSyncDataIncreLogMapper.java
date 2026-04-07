package com.eetrust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eetrust.domain.TSyncDataIncreLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

/**
 * @Entity generator.domain.TSyncDataIncreLog
 */
public interface TSyncDataIncreLogMapper extends BaseMapper<TSyncDataIncreLog> {

    /**
     * 获取当前表中最大的ID
     * @return 最大ID
     */
    @org.apache.ibatis.annotations.Select("SELECT IFNULL(MAX(ID), 0) FROM t_sync_data_incre_log")
    Long getMaxId();

    /**
     * 获取当前表中最小的ID
     * @return 最小ID
     */
    @org.apache.ibatis.annotations.Select("SELECT IFNULL(MIN(ID), 0) FROM t_sync_data_incre_log")
    Long getMinId();

    /**
     * 备份指定ID范围的数据到备份表
     * @param minId 最小ID（包含）
     * @param maxId 最大ID（不包含）
     * @return 影响的行数
     */
    @Insert("INSERT INTO t_sync_data_incre_log_bak (TYPE, UNIQUE_FIELD, PARENT_CODE, NAME, CONTENT, CREATE_TIME, STATE, RESULT) " +
            "SELECT TYPE, UNIQUE_FIELD, PARENT_CODE, NAME, CONTENT, CREATE_TIME, STATE, RESULT FROM t_sync_data_incre_log WHERE ID >= #{minId} AND ID < #{maxId}")
    int backupLogsByIdRange(@org.apache.ibatis.annotations.Param("minId") Long minId, 
                            @org.apache.ibatis.annotations.Param("maxId") Long maxId);

    /**
     * 删除指定ID范围的数据
     * @param minId 最小ID（包含）
     * @param maxId 最大ID（不包含）
     * @return 影响的行数
     */
    @Delete("DELETE FROM t_sync_data_incre_log WHERE ID >= #{minId} AND ID < #{maxId}")
    int deleteLogsByIdRange(@org.apache.ibatis.annotations.Param("minId") Long minId,
                            @org.apache.ibatis.annotations.Param("maxId") Long maxId);
}




