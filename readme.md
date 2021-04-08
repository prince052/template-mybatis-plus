# Getting Started

### 参考文档

* [mybatis-plus使用说明](https://baomidou.com/)
* [hutool工具类使用说明](https://www.hutool.cn/docs/#/)

### 简介

* 此工程为springboot快速开发搭建基础架构脚手架*mybatis plus版*，现整合了
    
    1. swagger（生产测试环境分隔待完善）
    2. mybatis-generator
    3. 统一返回体
    4. 统一错误定义
    5. 统一错误处理
    6. 统一日志处理，多环境配置（分布式日志收集待完善）
    7. 健康检测埋点
    8. 跨域处理
    9. hutool工具类
    10.分页插件
       。。。
       
### 项目初始化

1. checkout此项目
2. 配置application-{}文件,尤其是spring.datasource数据库连接，mpg需要依赖此连接生成代码
3. 运行Mpg.java的main函数，在控制台依次输入包名（本项目包名为com.example）,需要生成的表面，回车完成
5. 运行app.java
6. 打开localhost:9066/doc.html会进入swagger页面展示你的所有接口

#### 开始愉快的开发吧


    
    

