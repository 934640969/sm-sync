package com.eetrust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eetrust.domain.TSyncDataIncre;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Entity com.eetrust.domain.TSyncDataIncre
 */
public interface TSyncDataIncreMapper extends BaseMapper<TSyncDataIncre> {


    @Select({
            "SELECT t.* ",
            "FROM t_sync_data_incre t ",
            "JOIN ( ",
            "    SELECT ID FROM t_sync_data_incre ",
            "    WHERE STATE IN (0, 1) ",
            "    ORDER BY STATE, UPDATE_TIME, ID ",
            "    LIMIT 100 ",
            ") temp ON t.ID = temp.ID"
    })
    List<TSyncDataIncre> selectByStateInAndOrder();
}




