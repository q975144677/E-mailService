package com.email.email.mapper;

import com.email.email.pojo.AuthorityUser;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface AuthorityUserMapper extends Mapper<AuthorityUser>, MySqlMapper<AuthorityUser> {
}