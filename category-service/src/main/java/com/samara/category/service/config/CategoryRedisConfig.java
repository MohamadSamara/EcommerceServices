//package com.samara.category.service.config;
//
//import com.samara.category.service.model.CategoryEntity;
//import com.samara.shared.config.AbstractRedisConfig;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//
//@Configuration
//public class CategoryRedisConfig extends AbstractRedisConfig<CategoryEntity> {
//
//    @Override
//    public RedisTemplate<String, CategoryEntity> redisTemplate() {
//        return configureTemplate(CategoryEntity.class);
//    }
//}