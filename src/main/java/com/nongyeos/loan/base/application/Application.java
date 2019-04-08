package com.nongyeos.loan.base.application;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;

@EnableTransactionManagement
@EnableAutoConfiguration(exclude={com.alibaba.dubbo.spring.boot.DubboAutoConfiguration.class})
@SpringBootApplication
//@EnableRedisHttpSession
@ComponentScan(basePackages={"com.nongyeos.loan"})//指定spring要扫描的包
@MapperScan(basePackages = {"com.nongyeos.loan.**.mapper"})//指定mapper文件所在的包
public class Application extends SpringBootServletInitializer{
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
	
	@Bean 
	public EmbeddedServletContainerCustomizer containerCustomizer(){ 
	      return new EmbeddedServletContainerCustomizer() { 
				@Override 
				public void customize(ConfigurableEmbeddedServletContainer container) { 
					container.setSessionTimeout(30, TimeUnit.MINUTES);//单位为S 
				} 
	      };
	}
	
	//配置mybatis的分页插件pageHelper
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }
    
    //配置mybatis的分页插件pageHelper
//    @Bean
//    MultipartConfigElement multipartConfigElement(){
//    	MultipartConfigFactory factory = new MultipartConfigFactory();
//    	factory.setLocation("/data/loan/xlsFile/download");
//    	return factory.createMultipartConfig();
//    }
		
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	/*
	//创建数据源
	@Bean  
    @ConfigurationProperties(prefix = "spring.datasource")//ָ指定数据源的前缀
    public DataSource dataSource() {  
        return new DataSource();  
    }
	
	//创建SqlSessionFactory
	@Bean  
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {  
  
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();  
        sqlSessionFactoryBean.setDataSource(dataSource());  
  
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();  
  
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));//指定mapper文件所在目录  
  
        return sqlSessionFactoryBean.getObject();  
    }  
	
	//创建事务管理器
	@Bean  
    public PlatformTransactionManager transactionManager() {  
        return new DataSourceTransactionManager(dataSource());  
    }
    */
}
