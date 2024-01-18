package com.eetrust.controller;

import com.sf.bdus.dist.client.repository.DataRepository;
import com.sf.bdus.dist.client.service.BdusDistributionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * BDUS数据分发控制器
 *
 */
@RequestMapping("/bdus/distribution")
@Controller
public class BdusDistributionController {

    private static final Logger log = LoggerFactory.getLogger(BdusDistributionController.class);

    /**
     * BDUS数据分发服务
     */
    private BdusDistributionService distributionService;

    /**
     * 初始化
     *
     * @param systemKey
     *            系统唯一码
     * @param repositories
     *            数据仓库列表
     */
    @Autowired
    public BdusDistributionController(@Value("${bdus.systemKey}") String systemKey,
                                      List<DataRepository<?>> repositories) {
        distributionService = new BdusDistributionService(systemKey,
                repositories);
    }

    /**
     * 处理数据分发通知
     *
     * @param request
     *            HTTP请求
     * @param response
     *            HTTP响应
     * @throws IOException
     *             输入输出异常
     */
    @RequestMapping(method = RequestMethod.POST)
    public void distribute(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        log.info("Received BDUS data distribution request");
        distributionService.distribute(request, response);
    }
}