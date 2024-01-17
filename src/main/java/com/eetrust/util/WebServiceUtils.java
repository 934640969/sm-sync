package com.eetrust.util;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.net.URL;

public class WebServiceUtils {
	private static final Logger log = LoggerFactory.getLogger(WebServiceUtils.class);
	public static void main(String[] args) {
		// webservice调用
		String xml="<root><privateKey>UAP_2oSY90</privateKey><srcContent></srcContent><dataContent><syncContent dataType=\"1\" operType=\"1\"><syncUnicode>0410000043</syncUnicode><newContent><baseInfo><deptName>顺丰</deptName><deptUniCode>0410000043</deptUniCode><showNum>9999</showNum><deptStatus>1</deptStatus><isCorp>1</isCorp></baseInfo><parentInfo><parentCode>0410000043</parentCode></parentInfo></newContent></syncContent></dataContent></root>";
		System.out.println(xml);
		webserviceInvok("http://10.3.44.33:8090/securedoc/clientinterface/syncData/ISyncWebService",xml);
	}

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
			call.setOperationName(new QName("http://SyncXmlServiceImpl.com/", "dataSync"));
			// 调用WebService传入参数
			call.addParameter("syncXml", org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			String result = String.valueOf(call.invoke(new Object[] {xml}));
			log.info("webserviceInvok->result-> "+result);
		} catch (Exception e) {
			log.error("webserviceInvok->Exception->",e);
		}
	}
}
