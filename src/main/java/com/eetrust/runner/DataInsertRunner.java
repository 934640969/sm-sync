//package com.eetrust.runner;
//
//import com.eetrust.domain.TSyncDataIncre;
//import com.eetrust.mapper.TSyncDataIncreMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * Spring Boot 启动后自动运行的数据插入测试类
// */
//@Component
//public class DataInsertRunner implements CommandLineRunner {
//
//    @Autowired
//    private TSyncDataIncreMapper mapper;
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("开始插入测试数据...");
//
//        for (int i = 1; i <= 50; i++) {
//            TSyncDataIncre entity = new TSyncDataIncre();
//            entity.setType(1); // 组织
//            entity.setUniqueField("UNIQUE_" + i);
//            entity.setParentCode("PARENT_" + (i % 10)); // 模拟父级
//            entity.setName("组织名称_" + i);
//            entity.setContent("<data>示例XML内容_" + i + "</data>");
//            Date date = new Date();
//            entity.setCreateTime(date);
//            entity.setUpdateTime(date);
//            entity.setState(0); // 待同步
//            entity.setErrorCount(0);
//
//            mapper.insert(entity);
//            System.out.println("已插入第 " + i + " 条数据");
//        }
//
//        System.out.println("插入完成！");
//    }
//}
