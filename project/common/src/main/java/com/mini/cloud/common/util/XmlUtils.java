package com.mini.cloud.common.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class XmlUtils {

	
	/**
	 * 对象转XML
	 * @param obj:
	 * @param load:
	 * @param isPretty:
	 * @return String
	 * */
	public static String toStr(Object obj,Class<?> load,boolean isPretty) throws JAXBException {
		return XmlUtils.toStr(obj, load, "utf-8", isPretty,false);
	}
	
	/**
	 * 对象转XML
	 * */
	public static String toStr(Object obj,Class<?> load,boolean isPretty,boolean isHead) throws JAXBException {
		return XmlUtils.toStr(obj, load, "utf-8", isPretty,isHead);
	}
	
	/**
	 * 对象转XML
	 * */
	public static String toStr(Object obj,Class<?> load) throws JAXBException {
		return XmlUtils.toStr(obj, load, "utf-8", false,false);
	}
	/**
	 * 对象转XML
	 * */
	public static String toStr(Object obj, Class<?> load,String charset,boolean isPretty,boolean isHead) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(load);
		Marshaller marshaller = context.createMarshaller();
		//是否格式化生成的xml串 false:不格式
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, isPretty);
		//编码格式
		marshaller.setProperty(Marshaller.JAXB_ENCODING, charset);
		//是否省略xml头信息 false:不去除头信息
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, isHead);
		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString();
	}

	
	/**
	 * XML转对象
	 * */
	public static Object toObj(String xml,Class<?> load) throws JAXBException {
		JAXBContext context=JAXBContext.newInstance(load);
		Unmarshaller unmarshaller = context.createUnmarshaller(); 
		StringReader reader =new StringReader(xml);
		Object object = unmarshaller.unmarshal(reader);
		return object;
	}
	
	
}
