// package io.github.dunwu.module.sys.config;
//
// import io.github.dunwu.tool.web.log.annotation.EnableOperationLog;
// import io.github.dunwu.tool.web.log.aspect.OperationLogAspect;
// import io.github.dunwu.tool.web.log.service.FunctionService;
// import io.github.dunwu.tool.web.log.service.LogRecordService;
// import io.github.dunwu.tool.web.log.service.ParseFunction;
// import io.github.dunwu.tool.web.log.service.impl.DefaultFunctionServiceImpl;
// import io.github.dunwu.tool.web.log.service.impl.DefaultLogRecordServiceImpl;
// import io.github.dunwu.tool.web.log.service.impl.DefaultParseFunction;
// import io.github.dunwu.tool.web.log.service.impl.ParseFunctionFactory;
// import io.github.dunwu.tool.web.log.support.parse.SpElValueParser;
// import io.github.dunwu.tool.web.security.SecurityService;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.config.BeanDefinition;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.ImportAware;
// import org.springframework.context.annotation.Role;
// import org.springframework.core.annotation.AnnotationAttributes;
// import org.springframework.core.type.AnnotationMetadata;
//
// import java.util.List;
//
// /**
//  * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
//  * @since 2021-09-29
//  */
// @Slf4j
// @Configuration
// public class LogConfiguration implements ImportAware {
//
//     private AnnotationAttributes enableLogRecord;
//
//     @Bean
//     public OperationLogAspect operationLogAspect(LogRecordService logStorage, SecurityService securityService,
//         SpElValueParser spElValueParser) {
//         return new OperationLogAspect(enableLogRecord.getString("appName"),
//             logStorage, securityService, spElValueParser);
//     }
//
//     @Bean
//     @ConditionalOnMissingBean(FunctionService.class)
//     public FunctionService functionService(ParseFunctionFactory parseFunctionFactory) {
//         return new DefaultFunctionServiceImpl(parseFunctionFactory);
//     }
//
//     @Bean
//     public ParseFunctionFactory parseFunctionFactory(@Autowired List<ParseFunction> parseFunctions) {
//         return new ParseFunctionFactory(parseFunctions);
//     }
//
//     @Bean
//     @ConditionalOnMissingBean(ParseFunction.class)
//     public DefaultParseFunction parseFunction() {
//         return new DefaultParseFunction();
//     }
//
//     @Bean
//     @ConditionalOnMissingBean(LogRecordService.class)
//     @Role(BeanDefinition.ROLE_APPLICATION)
//     public LogRecordService recordService() {
//         return new DefaultLogRecordServiceImpl();
//     }
//
//     @Override
//     public void setImportMetadata(AnnotationMetadata importMetadata) {
//         this.enableLogRecord = AnnotationAttributes.fromMap(
//             importMetadata.getAnnotationAttributes(EnableOperationLog.class.getName(), false));
//         if (this.enableLogRecord == null) {
//             log.info("@EnableCaching is not present on importing class");
//         }
//     }
//
//     @Bean
//     @ConditionalOnMissingBean(SpElValueParser.class)
//     public SpElValueParser spElValueParser(FunctionService functionService) {
//         return new SpElValueParser(functionService);
//     }
//
// }
