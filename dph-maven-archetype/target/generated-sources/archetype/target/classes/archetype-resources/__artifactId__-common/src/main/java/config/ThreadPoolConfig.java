#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置
 */
@Configuration
public class ThreadPoolConfig {
	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
		threadPool.setCorePoolSize(1);
		threadPool.setMaxPoolSize(16);
		return threadPool;
	}
	
}
