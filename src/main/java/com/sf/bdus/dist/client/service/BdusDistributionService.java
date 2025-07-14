//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.sf.bdus.dist.client.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.bdus.dist.client.repository.DataRepository;
import com.sf.bdus.dist.client.task.BdusDistributionTask;
import com.sf.bdus.dist.common.context.DataContext;
import com.sf.bdus.dist.common.util.SignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class BdusDistributionService {
    private static final Logger logger = LoggerFactory.getLogger(BdusDistributionService.class);
    private ObjectMapper mapper;
    private String systemKey;
    private Map<Class<?>, DataRepository<?>> dataRepositoryMap;

    public BdusDistributionService(String systemKey, Iterable<DataRepository<?>> dataRepositories) {
        this(systemKey, buildDataRepositoryMap(dataRepositories));
    }

    public BdusDistributionService(String systemKey, Map<Class<?>, DataRepository<?>> dataRepositoryMap) {
        if (!isNotEmpty(systemKey)) {
            throw new IllegalArgumentException("请指定系统唯一码");
        } else if (dataRepositoryMap == null) {
            throw new IllegalArgumentException("请指定数据仓库字典");
        } else {
            this.systemKey = systemKey;
            this.dataRepositoryMap = dataRepositoryMap;
            this.mapper = new ObjectMapper();
            this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
    }

    public void distribute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int statusCode;
        String content;
        try {
            DataContext context = (DataContext)this.mapper.readValue(request.getInputStream(), DataContext.class);
            logger.info("BdusDistributionService.distribute: 分发通知请求参数, context = {}", context);
            this.checkDataContext(context);
            Map<String, String> parameterMap = getParameterMap(request);
            logger.info("BdusDistributionService.distribute: 分发通知请求参数, parameters = {}", parameterMap);
            context.setParameters(parameterMap);
            DataRepository<?> dataRepository = this.getDataRepository(context.buildDataType());
            BdusDistributionTask task = new BdusDistributionTask(this.systemKey, dataRepository, context);
            Thread thread = new Thread(task);
            thread.start();
            statusCode = 200;
            content = "";
        } catch (Exception exception) {
            logger.error("分发通知出错", exception);
            statusCode = 400;
            content = exception.getMessage();
        }

        response.setStatus(statusCode);
        response.setContentType("application/json; charset=utf-8");
        if (content != null) {
            PrintWriter writer = response.getWriter();
            writer.write(content);
        }

        logger.info("BdusDistributionService.distribute: 分发通知响应结果, status code = {}, content = {}", statusCode, content);
    }

    private void checkDataContext(DataContext context) throws Exception {
        checkArgument(context != null, "无效分发通知参数");
        checkArgument(context.getDataType() != null, "数据类型参数不能为空");
        checkArgument(isNotEmpty(context.getDataUrl()), "数据下载地址不能为空");
        checkArgument(isNotEmpty(context.getFeedbackUrl()), "分发结果反馈地址不能为空");
        checkArgument(isNotEmpty(context.getSign()), "签名不能为空");
        String sign = SignUtils.computeSign(context, this.systemKey);
        checkArgument(sign.equals(context.getSign()), "签名不正确, systemKey: " + (new StringBuilder(this.systemKey)).replace(0, this.systemKey.length() * 2 / 3, "***"));
    }

    private static void checkArgument(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    private static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    private static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> parameterMap = new HashMap();
        Enumeration<String> parameterNames = request.getParameterNames();

        while(parameterNames.hasMoreElements()) {
            String parameterName = (String)parameterNames.nextElement();
            String parameter = request.getParameter(parameterName);
            parameterMap.put(parameterName, parameter);
        }

        return parameterMap;
    }

    private DataRepository<?> getDataRepository(Class<?> dataType) {
        DataRepository<?> dataRepository = (DataRepository)this.dataRepositoryMap.get(dataType);
        if (dataRepository == null) {
            for(Map.Entry<Class<?>, DataRepository<?>> entry : this.dataRepositoryMap.entrySet()) {
                if (((Class)entry.getKey()).isAssignableFrom(dataType)) {
                    dataRepository = (DataRepository)entry.getValue();
                    break;
                }
            }
        }

        if (dataRepository == null) {
            throw new IllegalArgumentException("未找到对应的数据仓库，数据类型  = " + dataType);
        } else {
            return dataRepository;
        }
    }

    private static Map<Class<?>, DataRepository<?>> buildDataRepositoryMap(Iterable<DataRepository<?>> dataRepositories) {
        Map<Class<?>, DataRepository<?>> dataRepositoryMap = new HashMap();

        for(DataRepository<?> dataRepository : dataRepositories) {

          //  Class<?> dataType = getDataType(dataRepository.getClass());
            Class<?> dataType = getDataType(AopUtils.getTargetClass(dataRepository));
            dataRepositoryMap.put(dataType, dataRepository);
        }

        return dataRepositoryMap;
    }

    private static Class<?> getDataType(Class<?> dataRepositoryClass) {
        Type[] interfaceTypes = dataRepositoryClass.getGenericInterfaces();

        for(Type interfaceType : interfaceTypes) {
            if (interfaceType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType)interfaceType;
                if (parameterizedType.getRawType() == DataRepository.class) {
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    return (Class)typeArguments[0];
                }
            }
        }

        return null;
    }
}
