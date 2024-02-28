package io.github.dunwu.config;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * Swagger 数据转换配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
@Configuration
@ConditionalOnClass({ ApiInfo.class, Docket.class })
@ConditionalOnProperty(name = "dunwu.web.swagger.enabled", havingValue = "true")
public class SwaggerDataConfiguration {

    @Bean
    public AlternateTypeRuleConvention pageableConvention(final TypeResolver resolver) {
        return new AlternateTypeRuleConvention() {
            @Override
            public int getOrder() {
                return Ordered.HIGHEST_PRECEDENCE;
            }

            @Override
            public List<AlternateTypeRule> rules() {
                return newArrayList(newRule(resolver.resolve(Pageable.class), resolver.resolve(Page.class)));
            }
        };
    }

    @Data
    @ApiModel
    private static class Page {

        @ApiModelProperty("当前页码 [0, N]")
        private Integer page;
        @ApiModelProperty("每页显示的记录数")
        private Integer size;
        @ApiModelProperty("以下列格式排序标准：property[,asc | desc]。 默认排序顺序为升序。支持多种排序条件：如：id,asc")
        private List<String> sort;

    }

}
