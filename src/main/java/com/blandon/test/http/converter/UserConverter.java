package com.blandon.test.http.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.blandon.test.bean.User;

public class UserConverter extends AbstractHttpMessageConverter<User>{

	@Override
	protected boolean supports(Class<?> clazz) {
		return com.blandon.test.bean.User.class.equals(clazz);
	}

	@Override
	protected User readInternal(Class<? extends User> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void writeInternal(User user, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		
		oos.writeObject(user);
		
		baos.writeTo(outputMessage.getBody());
		
		oos.close();
	}

}
