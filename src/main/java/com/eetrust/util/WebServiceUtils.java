package com.eetrust.util;

import cn.hutool.http.HttpUtil;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServiceUtils {
    private static final Logger log = LoggerFactory.getLogger(WebServiceUtils.class);


    /**
     * 使用Axis2调用WebService接口
     * @param endpoint WebService地址
     * @param xml 请求参数XML字符串
     * @return 响应结果
     */
    public static String webserviceInvok(String endpoint, String xml, int sleepTime) {

        log.info("webserviceInvok->xml-> {}", xml);
        try {
            // 创建ServiceClient实例
            ServiceClient serviceClient = new ServiceClient();

            // 设置目标地址
            Options options = new Options();
            options.setTo(new EndpointReference(endpoint));
            options.setAction("dataSync"); // 设置SOAPAction
            serviceClient.setOptions(options);

            // 创建命名空间
            OMFactory factory = OMAbstractFactory.getOMFactory();
            OMNamespace namespace = factory.createOMNamespace("http://SyncXmlServiceImpl.com/", "syn");

            // 创建方法元素
            OMElement methodElement = factory.createOMElement("dataSync", namespace);

            // 创建参数元素
            OMElement paramElement = factory.createOMElement("syncXml", null); // 不设置命名空间
            paramElement.setText(xml);

            // 添加参数到方法中
            methodElement.addChild(paramElement);

            // 发送请求并获取响应
            OMElement response = serviceClient.sendReceive(methodElement);

            // 提取return元素的内容
            OMElement returnElement = response.getFirstElement();
            String result = returnElement.getText();
            log.info("webserviceInvok->result-> {}", result);

            Thread.sleep(sleepTime);//睡一下减小商密压力

            return result;
        } catch (Exception e) {
            log.error("webserviceInvok->Exception->", e);
            return "<?xml version=\"1.0\" encoding=\"GBK\"?><returnData><status>0</status><errcode></errcode><errormsg>推送报错 " + e + "</errormsg></returnData>";
        }
    }

    	public static void main(String[] args) throws DocumentException {
		// webservice调用
		String xml="<root><privateKey>UAP_2oSY90</privateKey><srcContent></srcContent><dataContent><syncContent dataType=\"1\" operType=\"1\"><syncUnicode>0410000043</syncUnicode><newContent><baseInfo><deptName>顺丰</deptName><deptUniCode>0410000043</deptUniCode><showNum>9999</showNum><deptStatus>1</deptStatus><isCorp>1</isCorp></baseInfo><parentInfo><parentCode>MRBM</parentCode></parentInfo></newContent></syncContent></dataContent></root>";
//		 xml="<root><privateKey>UAP_2oSY90</privateKey><srcContent></srcContent><dataContent><syncContent dataType=\"2\" operType=\"1\"><syncUnicode>zhangs</syncUnicode><newContent><baseInfo><loginName>zhangs</loginName><userName>张三</userName><secLevel>5</secLevel><accountStatus>1</accountStatus></baseInfo><parentInfo><parentCode>MRBM</parentCode></parentInfo></newContent></syncContent></dataContent></root>";
//		System.out.println(xml);
//		webserviceInvok("http://10.3.46.78:8090/securedoc/clientinterface/syncData/ISyncWebService",xml);

            String a="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:syn=\"http://SyncXmlServiceImpl.com/\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <syn:dataSync>\n" +
                    "         <!--Optional:-->\n" +
                    "         <syncXml><![CDATA["+ xml+"]]></syncXml>\n" +
                    "      </syn:dataSync>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            String body = HttpUtil.createPost("http://10.3.46.78:8090/securedoc/clientinterface/syncData/ISyncWebService").body(a).execute().body();
            System.out.println(body);
            String xmlResult = body.replace("&lt;", "<").replace("&gt;", ">").replace("&quot;","\"").replace("<?xml version=\"1.0\" encoding=\"GBK\"?>","");
            log.info(xmlResult);
            Document doc = DocumentHelper.getDocument(xmlResult);
            String status = doc.getRootElement().element("Body").element("dataSyncResponse").element("return").element("returnData").element("status").getText();
            String errormsg = doc.getRootElement().element("Body").element("dataSyncResponse").element("return").element("returnData").element("errormsg").getText();
            System.out.println("status:"+status+" errormsg:"+errormsg);
            }
}
