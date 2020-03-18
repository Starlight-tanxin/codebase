package com.zyb.mini.mall.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.zyb.mini.mall.constant.DateFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * DateTime Config
 *
 * @author tanxin
 * @date 2019/10/26
 */

@Configuration
public class DateTimeConfig {

    @Bean
    public ObjectMapper getObjectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateFormatter.DATE_TIME));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateFormatter.DATE));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateFormatter.TIME));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateFormatter.DATE_TIME));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateFormatter.DATE));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateFormatter.TIME));

        javaTimeModule.addSerializer(Date.class, new DateSerializer(false, DateFormatter.SDF_DATE_TIME));
        javaTimeModule.addDeserializer(Date.class, new CustomJsonDateDeserializer());

        // setSerializationInclusion(JsonInclude.Include.NON_NULL) json返回对象时，忽略null的字段（null值的字段不会返回）
        // registerModule(javaTimeModule) 注册时间转换模型
        ObjectMapper objectMapper = new ObjectMapper();
        // objectMapper.setDateFormat(DateFormatter.SDF_DATE_TIME);
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;

    }

    // *** 注册转换器  **//
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return LocalDateTime.parse(source, DateFormatter.DATE_TIME);
            }
        };
    }

    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source, DateFormatter.DATE);
            }
        };
    }

    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                return LocalTime.parse(source, DateFormatter.TIME);
            }
        };
    }

}
