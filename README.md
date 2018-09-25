                   == SecurityInJava-6.0.0 doc ==

- [Introduction 介绍](#introduction)
    - [Version 版本](#version)
    - [Changelog](Changelog.md)
- [Supported 兼容性](#supported)
- [Contributing 我要贡献](#contributing)
- [Issues 发现问题](#issues)
- [Installation 安装](#installation)
- [Quick Start 快速上手](#quick-start)
- [Architecture 架构](#architecture)
- [Web Security Web安全性](#web-security)
- [Mobile 手机端](#mobile)
- [Upgrading 更新](#upgrading)
- [References 参考](#references)

# 1. Introduction

SecurityInJava是一个开源项目，核心是以“安全+高效开发”为目标的Java应用开发平台，针对Java经典SSM框架(Spring MVC+Spring+MyBatis，适用于常规企业应用开发，尤其是管理平面的应用)和Java企业级异步开发框架(基于Vert.x，适用于超高并发场景)两大领域，提供专业的安全编码最佳实践，并内置安全模块，包含商用密码算法模块（即国密算法）、基于ABAC的访问控制、认证模块等，以大幅增强Java应用开发的安全性。

由于安全和应用分工不同，造成目前应用开发框架普遍缺失内生安全机制。根据Gartner统计，仅仅OWASP Top 10的安全缺陷就导致了50%的漏洞。我们相信，随着SecurityInJava的完善和普及推广，能够从开发源头显著改善应用系统的安全性，实现“Build Security Into Application”。同时SecurityInJava提供的安全能力是“应用内生的安全”，和后期补漏洞技术相比，具有投入成本极低、安全防护效果极高的特点。按照滑动安全标尺模型，这属于架构安全(Architecture Security)的范畴，SecurityInJava考虑到架构的灵活适应设计，同时能够为后续的被动防御、积极防御等提供良好基础。

安全是发展的保障，失去了安全，我们拥有的业务功能价值越高，风险也会越高，所以对应用进行安全增强刻不容缓。在此，我们呼吁广泛的软件应用同行和安全同行马上行动起来，联手为下一步信息基础设施增加安全保障。Java在应用开发市场常年占有率第一，因此我们先从Java入手开展这项有意义的工作，但是对于Python、PHP、C#、Go等其他应用级开发语言同样也需要内生安全能力，更多的“SecurityInX”项目在我们的工作规划中。

## 1.1 业务功能介绍
### 应用功能优势
* 支持父子表、多对多表的智能关系分析及生成
* JavaEE标准版的代码生成器，可以做企业应用、互联网后端、2B的门户、及网站前台(需要单独设计页面)
* 跨平台的Mobile端的代码生成器(即将发布)，用一套代码通吃iOS各版本、Android各版本、winphone，可用于企业移动端应用、2C的轻应用
* 安全强化JavaEE版的代码生成器(即将发布)，吸纳了“Build Security In” + OWASP + MASS，适用于对安全有要求的应用开发

### 革命性的代码生成器新理念
* 支持本地模板源和远程模板源的扩展，这样能引入模板供应商(Template Provider)，使得代码生成以在线服务的方式提供
* 模板虚拟机的设计，可以扩展为支持多样的Model格式、多样的模板引擎、多样的处理流程


### 架构及技术优势
* 松耦合的设计，不仅支持生成Java工程，也可以生成大多数语言和框架的工程，无需改代码，只需要加模板
* xslt 2.0语法格式的模板，功能强大，前景好（因为html5的xml良构会带来xslt的第二春）
* 纯maven + 纯OSGI的架构，高度组件化，支持一键编译

# 2. 当前项目状态

Current Version: **6.0.0**


### Supported

####Web Browsers
- Chrome (Latest stable version)
- Firefox (Latest released version)
- Safari 7+ (Know problem: required fields in html5 do not work)
- Opera (Latest released version)
- IE 10+

####Java
- Java 8.0 or greater

####Database
- Mysql
- Oracle
- Sql Server
- H2

####Middleware
- Tomcat
- WebSphere Application Server
- Weblogic

### Contributing

If you find this image useful here's how you can help:

- Send a Pull Request with your awesome new features and bug fixes
- Help new users with [Issues](https://github.com/ciphergateway/securityinjava/issues) they may encounter

### Issues

If you found a issue, please post a issue request on the [issues](https://github.com/ciphergateway/securityinjava/issues) page.

In your issue report please make sure you provide the following information:

- The host distribution, environment and release version.
- The log infomations.

# 3. Installation

## 3.1 编译打包
### 一键编译securityinjava-6.0.0 Java工程
		1，git clone https://github.com/ciphergateway/securityinjava
		2，先安装基础jar包和必要组件到$M2_REPO。
			cd securityinjava
			mvn clean package【注：第一次执行需要下载关联maven资源，第二次执行就很快，建议配合本地Nexus使用】
		3，把securutyinjava-ssm/WebContent/目录发布到Tomcat-6.0.0以上或其他JavaEE中间件即可运行。
	

### 一键编译securityinjava-6.0.0 Eclipse插件的方式（含Java工程打包）
eclipse/plugins目录格式，直接复制到Eclipse/links下，安装快。要求是eclipse的JavaEE/jee版，同时Eclipse版本>=3.7

		1，git clone https://github.com/ciphergateway/securityinjava
		2，先安装基础jar包和必要组件到$M2_REPO。
			cd securityinjava/support/build-all
			mvn clean package 【注：第一次执行需要下载大量maven资源，并从Eclipse官方站下载资源，第二次执行可以加上离线参数-o以加快编译，即mvn package -o】
			【注：如果报错，请尝试执行多次mvn package
		3，安装插件包。
			securityinjava/support/build-all/build-securityinjava-ssm/target/eclipse目录，到$ECLIPSE_HOME/links/javasec_6.0.0目录【注：在$ECLIPSE_HOME目录下新建links目录】。
			最终结构如下：$ECLIPSE_HOME/links/securityinjava_6.0.0/eclipse/plugins/...
		4，重启Eclipse即可
		


【备用打包方式】updatesite格式的安装版，安装到Eclipse时较慢。但安装时会自动下载所需的依赖插件，要求Eclipse版本>=3.7

		1，使用Linux下的ln -s(或windows下的junction)，把qb-archetype/quickbundle-rmwebdemo目录软链接到qb-core/eclipse-plugin/quickbundle-gp/t/j1目录。
		2，安装maven-3.0.5，在qb-core目录下，执行mvn install，即可安装到$M2_REPO/org/quickbundle/org.quickbundle.mda.updatesite/4.0.0/org.quickbundle.mda.updatesite-4.0.0.zip。

## 3.2 Quick Start

Download lastest release.


# 4. Architecture

## 4.1 基础jar包和Eclipse插件源代码模块介绍

### 包含基础jar包5个:
		support/java-lib/quickbundle-tools  基础工具jar包，是一些其它quickbundle-xxx.jar的基础
		support/java-lib/quickbundle-core  核心jar包，依赖于Spring
		support/java-lib/quickbundle-mybatis  mybatis扩展
		support/java-lib/quickbundle-springweb  spring mvc扩展
		support/java-lib/quickbundle-struts  struts1 扩展
	
### Eclipse插件
		support/eclipse-plugin/org.quickbundle.mda.libs  osgi下的jar包接入点
		support/eclipse-plugin/quickbundle-gp  项目生成器
		support/eclipse-plugin/quickbundle-mvm  模型虚拟机
		support/eclipse-plugin/quickbundle-gc  代码生成器
		
		support/eclipse-plugin/org.quickbundle.mda.feature  Eclipse插件组合的feature工程
		support/eclipse-plugin/org.quickbundle.mda.updatesite  Eclipse插件的在线/离线安装包的组合工程

### 骨架工程介绍
目前包含：基于SSM的JavaEE标准版。

待整合：Vert.x。

### securityinjava-ssm

* JavaEE 2.5项目骨架，Maven规范
* 主框架是jQuery-1.9 + Html4 + Spring MVC 3.2 + Spring 3.2 + MyBatis 3.2
* 集成了Jackson-2.1(Json) + Apache CXF-2.5(web service) + JFreeChart-1.0(图表) + JasperReport-4.7(报表) + mail-1.4(邮件) + jxl-2.6(Excel) + dom4j-1.6(xml) + slf4j-1.7(日志) + jython-2.7(Python运行库)
* 内置组织权限(设计思想源自Martin Fowler的《分析模式》) + 分布式调度(基于quartz-2.1增强了管理界面) + 编码数据管理 + 附件管理 + 业务日志 + 业务锁
* 待增加：Activiti-5.13(工作流引擎，增强了组织适配器、流程管理器等) + MuleESB-3.4(ESB企业服务总线) + Drools-6.0(规则引擎)

适用场景：企业应用、互联网应用后端。

## 4.2 Web Security
### 安全应用框架

安全强化的Java Web版(后端+PC端)，Mobile版。  
对JavaEE标准版，以“Build Security In”的思想彻底重构之后的注重安全的Java Web应用框架。适用场景：对安全有要求的企业应用、互联网应用后端。

#### 特性
* 以JavaEE标准版(quickbundle-rmwebdemo)为基础 [【引用】](https://github.com/quickbundle/qb-archetype/blob/master/README.md)，以OWASP Java Project[【参考】](https://www.owasp.org/index.php/Category:OWASP_Java_Project)为指导做安全方面的深度重构。
* 集成Spring Security 3.1[【参考】](http://docs.spring.io/spring-security/site/features.html)，兼容数据安全中间件Ralasafe v2[【参考】](http://http://www.ralasafe.cn/)
* 导入ESAPI-2.1的安全规范写法并精心定制，并以WebGoat-5.4[【参考】](https://www.owasp.org/index.php/Webgoat)为样本全面修复可能存在的安全缺陷，并针对OWASP Top 10[【参考】](https://www.owasp.org/index.php/Category:OWASP_Top_Ten_Project)做框架代码安全加固，重点是：

		A1-注入
		A2-失效的身份认证及会话管理
		A3-跨站脚本-CSS
		A4-不安全的直接对象引用
		A7-功能级访问控制缺失
		A8-跨站请求伪造-CSRF
		ESAPI-2.1加固

* 针对设计和开发阶段，整理一套以"Build Security In"为指导的风险分析规范，以及按具体业务做设计的方法论体系。
* 针对集成部署阶段，形成一套混淆编译规范及工具[【参考】](http://tools.pediy.com/decompilers.htm)，形成一套完整的安全实施过程模板，重点是：

		A5-安全配置错误
		A6-敏感信息泄漏
		混淆编译规范及工具
		Linux主机加固、中间件加固
		涵盖SSL、PKI的安全配置模板

* 针对运维阶段，编写一套ruby脚本做自动化的配置漏洞检查，并以主流漏洞扫描器(如AppScan、Acunetix WVS等)做辅助验证。并部署一套重构后的安全应用框架实例到靶机，持续攻防测试并持续改进。
* 将上述应用安全框架成果，提炼到基于MDA的代码生成器模板[【引用】](https://github.com/quickbundle/qb-core/tree/master/eclipse-plugin)中，并发布为Eclipse plugin形式(支持Eclipse3.7及以上)，“逐鹿安全应用框架”的用户(应用开发者)可以快速构建并生成Java应用代码。


# 5. Upgrading



# 6. References


