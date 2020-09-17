package com.mini.cloud.common.auth.intercepter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class WebHttpServletRequestWrapper extends HttpServletRequestWrapper{

	
	private final byte[] body;
	
	private String content;
	
	 
    public WebHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.content= HttpRequestUtil.getBodyContent(request);
        body = this.content.getBytes(Charset.forName("UTF-8"));
    }
    
    
    public String getBodyContent() {
    	return this.content;
    }
    
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
 
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }
	
}
