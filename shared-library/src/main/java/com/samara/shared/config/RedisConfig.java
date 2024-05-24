//package com.samara.shared.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.samara.shared.bo.category.CategoryEntity;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//public class RedisConfig {
//
//    @Bean("categoryRedisTemplate")
//    public RedisTemplate<String, CategoryEntity> categoryRedisTemplate(LettuceConnectionFactory connectionFactory) {
//        RedisTemplate<String, CategoryEntity> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//
//        ObjectMapper objectMapper = createObjectMapperWithLocalDateTime();
//
//        StringRedisSerializer keySerializer = new StringRedisSerializer();
//        Jackson2JsonRedisSerializer<CategoryEntity> valueSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, CategoryEntity.class);
//
//        // Configure serializers for key and value types
//        template.setKeySerializer(keySerializer);
//        template.setValueSerializer(valueSerializer);
//
//        // After serialization configuration, initialize the template
//        template.afterPropertiesSet();
//        return template;
//    }
//
//    @Bean
//    public LettuceConnectionFactory lettuceConnectionFactory() {
//        return new LettuceConnectionFactory("localhost", 6379);
//    }
//
//    public ObjectMapper createObjectMapperWithLocalDateTime() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        return objectMapper;
//    }
//
//}
