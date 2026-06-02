package com.eetrust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eetrust.domain.DeptMd5;
import org.apache.ibatis.annotations.Select;

public interface DeptMd5Mapper extends BaseMapper<DeptMd5> {

    @Select("SELECT * FROM t_dept WHERE org_id = #{orgId} LIMIT 1")
    DeptMd5 selectByOrgId(String orgId);
}
