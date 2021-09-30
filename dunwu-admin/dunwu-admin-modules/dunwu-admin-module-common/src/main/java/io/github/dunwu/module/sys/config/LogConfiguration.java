package io.github.dunwu.module.sys.config;

import io.github.dunwu.tool.web.log.LogStorage;
import io.github.dunwu.tool.web.log.aspect.AppLogAspect;
import io.github.dunwu.tool.web.security.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-29
 */
@Configuration
public class LogConfiguration {

    @Bean
    public AppLogAspect appLogAspect(LogStorage logStorage, SecurityService securityService) {
        return new AppLogAspect(logStorage, securityService);
    }

}
