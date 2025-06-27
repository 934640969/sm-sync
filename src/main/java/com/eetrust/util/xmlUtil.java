package com.eetrust.util;

/**
 * @Author huangg
 * @create 2025/6/17 10:28
 */
public class xmlUtil {

    public static String removeXmlSpecialChars(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }
        return content.replaceAll("[<>&\"']", "");
    }
}
