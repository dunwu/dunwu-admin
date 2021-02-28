package io.github.dunwu;

import io.github.dunwu.annotation.rest.AnonymousGetMapping;
import io.github.dunwu.generator.DefaultCodeGenerator;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.entity.query.CodeTableConfigQuery;
import io.github.dunwu.modules.generator.service.CodeColumnConfigService;
import io.github.dunwu.modules.generator.service.CodeTableConfigService;
import io.github.dunwu.util.SpringContextHolder;
import io.swagger.annotations.Api;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * 应用启动入口 开启审计功能 -> @EnableJpaAuditing
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-30
 */
@EnableAsync
@RestController
@Api(hidden = true)
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@MapperScan("io.github.dunwu.modules.*.dao.mapper")
public class DunwuAdminApp {

    @Autowired
    private CodeTableConfigService tableConfigService;
    @Autowired
    private CodeColumnConfigService columnConfigService;

    public static void main(String[] args) {
        SpringApplication.run(DunwuAdminApp.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public ServletWebServerFactory webServerFactory() {
        CodeTableConfigQuery tableQuery = new CodeTableConfigQuery();
        tableQuery.setTableName("sys_job_role");
        CodeTableConfigDto tableConfigDto = tableConfigService.find(tableQuery);

        CodeColumnConfigQuery codeColumnConfigQuery = new CodeColumnConfigQuery();
        codeColumnConfigQuery.setTableName(tableConfigDto.getTableName());
        List<CodeColumnConfigDto> columns = columnConfigService.pojoListByQuery(codeColumnConfigQuery);

        String tmpPath = "D:\\Temp\\codes";
        Properties properties = columnConfigService.getConfigs(tmpPath, tableConfigDto, columns);
        new DefaultCodeGenerator(properties).generate();

        // TODO 上面的是临时测试代码
        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
        fa.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return fa;
    }

    /**
     * 访问首页提示
     *
     * @return /
     */
    @AnonymousGetMapping("/")
    public String index() {
        return "Backend service started successfully";
    }

}
