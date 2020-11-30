package com.dome.mp.server.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.dome.mp.server.constant.DateFormatter;

import java.io.IOException;
import java.util.Date;

/**
 * @author tanxin
 * @date 2019/8/1
 */
public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String date = jsonParser.getText();
        try {
            if (date == null || date.trim().length() == 0) {
                return null;
            }
            return DateFormatter.SDF_DATE_TIME.parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
