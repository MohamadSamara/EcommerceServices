//package com.samara.shared.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//public abstract class AbstractRedisConfig<T> {
//
//    @Bean
//    public abstract RedisTemplate<String, T> redisTemplate();
//
//    protected RedisTemplate<String, T> configureTemplate(Class<T> entityClass) {
//        RedisTemplate<String, T> template = new RedisTemplate<>();
//
//        StringRedisSerializer keySerializer = new StringRedisSerializer();
//        ObjectMapper objectMapper = createObjectMapperWithLocalDateTime();
//        Jackson2JsonRedisSerializer<T> valueSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, entityClass);
//
//        template.setKeySerializer(keySerializer);
//        template.setValueSerializer(valueSerializer);
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
//}
