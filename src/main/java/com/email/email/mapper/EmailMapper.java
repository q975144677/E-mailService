package com.email.email.mapper;

import com.email.email.pojo.Email;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
@CacheNamespace(implementation=com.email.email.common.MybatisRedisCache.class)
public interface EmailMapper extends Mapper<Email>, MySqlMapper<Email> {
}