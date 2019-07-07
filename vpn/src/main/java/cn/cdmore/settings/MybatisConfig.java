package cn.cdmore.settings;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;

/**
 * 配置数据源、事务
 * 
 * @author xieke
 *
 */
@Configuration
@MapperScan(basePackages = "cn.cdmore.**.dao")
public class MybatisConfig {

	private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

	@Autowired
	private DruidDataSourceSettings druidDataSourceSettings;

	/**
	 * 配置数据源
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		logger.debug("druidDataSourceSettings" + druidDataSourceSettings);
		// 加载配置文件属性
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName(druidDataSourceSettings.getDriverClassName());
		ds.setUsername(druidDataSourceSettings.getUsername());
		ds.setPassword(druidDataSourceSettings.getPassword());
		ds.setUrl(druidDataSourceSettings.getUrl());
		ds.setMaxActive(druidDataSourceSettings.getMaxActive());
		ds.setValidationQuery(druidDataSourceSettings.getValidationQuery());
		ds.setTestOnBorrow(druidDataSourceSettings.isTestOnBorrow());
		ds.setTestOnReturn(druidDataSourceSettings.isTestOnReturn());
		ds.setTestWhileIdle(druidDataSourceSettings.isTestWhileIdle());
		ds.setTimeBetweenEvictionRunsMillis(druidDataSourceSettings.getTimeBetweenEvictionRunsMillis());
		ds.setMinEvictableIdleTimeMillis(druidDataSourceSettings.getMinEictableIdleTimeMillis());
		ds.setPoolPreparedStatements(druidDataSourceSettings.isPoolPreparedStatements());
		ds.setMaxOpenPreparedStatements(druidDataSourceSettings.getMaxOpenPreparedStatements());
		try {
			ds.setFilters(druidDataSourceSettings.getFilters());
		} catch (SQLException e) {
			logger.error("设置druid失败", e);
		}
		return ds;
	}

	/**
	 * 配置SqlSessionFactory和分页
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		logger.debug("--> sqlSessionFactory");
		final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		sqlSessionFactory.setFailFast(true);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
		// 分页插件
		PageHelper pageHelper = new PageHelper();
		Properties props = new Properties();
		props.setProperty("reasonable", "false");
		props.setProperty("supportMethodsArguments", "true");
		props.setProperty("returnPageInfo", "check");
		props.setProperty("params", "count=countSql");
		pageHelper.setProperties(props);
		// 添加插件
		sqlSessionFactory.setPlugins(new Interceptor[] { pageHelper });
		return sqlSessionFactory.getObject();
	}

	/**
	 * 配置事务管理器
	 * 
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		logger.debug("> transactionManager");
		return new DataSourceTransactionManager(dataSource());
	}
}