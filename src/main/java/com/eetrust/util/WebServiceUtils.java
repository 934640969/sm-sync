package com.eetrust.util;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.net.URL;

public class WebServiceUtils {
	private static final Logger log = LoggerFactory.getLogger(WebServiceUtils.class);

	public static void webserviceInvok(String endpoint,String xml) {
		log.info("webserviceInvok->xml-> "+xml);
		// 创建Service实例
		Service service = new Service();
		// 通过Service实例创建Call实例
		Call call;
		try {
			call = (Call) service.createCall();
			// 将WebService的服务路径加入到Call实例中，并为Call设置服务的位置
			URL url = new URL(endpoint);
			call.setTargetEndpointAddress(url);
			// 调用WebService方法
			call.setOperationName("orgAndUserSync");
			String password="eetrust";
			String result = String.valueOf(call.invoke(new Object[] {password,xml}));
			log.info("webserviceInvok->result-> "+result);
		} catch (Exception e) {
			log.error("webserviceInvok->Exception->",e);
		}
	}
}
