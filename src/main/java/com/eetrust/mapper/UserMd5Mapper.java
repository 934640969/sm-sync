package com.eetrust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eetrust.domain.UserMd5;
import org.apache.ibatis.annotations.Select;

public interface UserMd5Mapper extends BaseMapper<UserMd5> {

    @Select("SELECT * FROM t_user WHERE emp_num = #{empNum} LIMIT 1")
    UserMd5 selectByEmpNum(String empNum);
}
