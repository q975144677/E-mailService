package com.email.email.common;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class PageHelperConfig {

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true"); // 这样 startpage 就是 page 而不是 start 了
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true"); // 不会跳转到 超过上限 或者 <0 的页数
        pageHelper.setProperties(properties);
        return pageHelper;
    }

}
