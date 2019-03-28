package com.email.email.mapper;

import com.email.email.pojo.Authority;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
@CacheNamespace
public interface AuthorityMapper extends Mapper<Authority>, MySqlMapper<Authority> {
}