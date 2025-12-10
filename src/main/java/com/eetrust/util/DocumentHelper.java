package com.eetrust.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @ClassName DocumentHelper
 * @Description 无漏洞获取Domcument
 * @Author baigq
 * @Date 2023/3/9 17:49
 * @Version 1.0
 **/
public class DocumentHelper {

    public static Document getDocument(String str) throws DocumentException {
        Document doc = null;
        SAXReader reader = new SAXReader();

        try{
            reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
            reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        }


        catch (SAXException e) {
            e.printStackTrace();
        }

        String encoding = getEncoding(str);

        InputSource source = new InputSource(new StringReader(str));
        source.setEncoding(encoding);

        doc = reader.read(source);
        if (doc.getXMLEncoding() == null) {
            doc.setXMLEncoding(encoding);
        }
        return doc;
    }
    private static String getEncoding(String text)
    {
        String result = null;

        String xml = text.trim();
        if (xml.startsWith("<?xml"))
        {
            int end = xml.indexOf("?>");
            String sub = xml.substring(0, end);
            StringTokenizer tokens = new StringTokenizer(sub, " =\"'");
            while (tokens.hasMoreTokens())
            {
                String token = tokens.nextToken();
                if ("encoding".equals(token))
                {
                    if (!tokens.hasMoreTokens()) {
                        break;
                    }
                    result = tokens.nextToken(); break;
                }
            }
        }
        return result;
    }
    public static void removeValeNUllNode(Document document){
        Element rootElement = document.getRootElement();
        getElementList(rootElement);
        System.out.println(document.asXML());
    }
    /**
     * 递归遍历方法
     *
     * @param element
     */
    public static void getElementList(Element element) {
        List elements = element.elements();
        if (elements.size() == 0) {
            //没有子元素
            String value = element.getTextTrim();
            if (value.contains("${")&& value.contains("}")){
                element.setText("");
            }
        } else {
            //有子元素
            for (Iterator it = elements.iterator(); it.hasNext();) {
                Element elem = (Element) it.next();
                //递归遍历
                getElementList(elem);
            }
        }
    }
}
