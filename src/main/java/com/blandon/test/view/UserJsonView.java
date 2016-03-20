package com.blandon.test.view;

import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonStructure;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.blandon.test.bean.User;
import com.google.common.base.Charsets;

public class UserJsonView extends AbstractView{
	
	private UserJsonConverter converter;
	
	public UserJsonView(UserJsonConverter converter){
		this.converter=converter;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		User user = (User)model.get("returnedUser");
		
		JsonStructure json = converter.marshal(user);
		
		JsonWriterFactory factory = createWriterFactory();
		
		JsonWriter writer = factory.createWriter( response.getOutputStream(), Charsets.UTF_8);
		
		writer.write(json);
		
		response.flushBuffer();
		
	}
	
	
    private JsonWriterFactory createWriterFactory() {
    	
        final HashMap<String, Object> writerConfig = new HashMap<String, Object>();

        writerConfig.put(JsonGenerator.PRETTY_PRINTING, true);

        return Json.createWriterFactory(writerConfig);
    }

}
