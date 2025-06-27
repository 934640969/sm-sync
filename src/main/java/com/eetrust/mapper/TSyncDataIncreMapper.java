package com.eetrust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eetrust.domain.TSyncDataIncre;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Entity com.eetrust.domain.TSyncDataIncre
 */
public interface TSyncDataIncreMapper extends BaseMapper<TSyncDataIncre> {


    @Select("SELECT * FROM t_sync_data_incre WHERE STATE IN (0, 1) ORDER BY UPDATE_TIME ASC,ID ASC limit 100")
    List<TSyncDataIncre> selectByStateInAndOrder();
}




