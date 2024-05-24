package com.samara.discount.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.samara.discount.service.model.DiscountEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, DiscountEntity> categoryRedisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, DiscountEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        ObjectMapper objectMapper = createObjectMapperWithLocalDateTime();

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<DiscountEntity> valueSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, DiscountEntity.class);

        // Configure serializers for key and value types
        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);

        // After serialization configuration, initialize the template
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

    public ObjectMapper createObjectMapperWithLocalDateTime() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}