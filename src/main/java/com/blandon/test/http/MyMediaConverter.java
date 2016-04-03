package com.blandon.test.http;

import org.springframework.core.convert.converter.Converter;

import com.google.common.net.MediaType;

public class MyMediaConverter implements Converter<String, MediaType> {
	 /** {@inheritDoc} */
    public MediaType convert(String source) {
        return MediaType.parse(source);
    }
}
