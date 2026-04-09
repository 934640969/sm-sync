package com.eetrust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eetrust.domain.TSyncDataIncreLog;
import org.apache.ibatis.annotations.Delete;

import java.util.Date;

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
     * 删除指定日期之前的日志数据（限制每次删除数量）
     * @param cutoffDate 截止日期
     * @param limit 每次删除的最大数量
     * @return 影响的行数
     */
    @Delete("DELETE FROM t_sync_data_incre_log WHERE CREATE_TIME < #{cutoffDate} ORDER BY CREATE_TIME ASC LIMIT #{limit}")
    int deleteLogsBeforeDateWithLimit(@org.apache.ibatis.annotations.Param("cutoffDate") Date cutoffDate,
                                      @org.apache.ibatis.annotations.Param("limit") int limit);
}
