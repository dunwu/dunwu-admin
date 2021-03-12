package io.github.dunwu.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.github.dunwu.autoconfigure.web.DunwuWebMvcConfiguration;
import io.github.dunwu.modules.security.config.DunwuWebSecurityConfiguration;
import io.github.dunwu.modules.security.config.DunwuWebSecurityProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
@Configuration
@ConditionalOnClass({ ApiInfo.class, Docket.class })
@ConditionalOnProperty(name = "dunwu.swagger.enabled", havingValue = "true")
@EnableSwagger2
@AllArgsConstructor
@EnableConfigurationProperties({ DunwuSwaggerProperties.class })
@Import({ DunwuSwaggerDataConfiguration.class })
@AutoConfigureAfter({ DunwuWebMvcConfiguration.class, DunwuWebSecurityConfiguration.class })
public class DunwuSwaggerConfiguration implements BeanFactoryAware {

    private final DunwuSwaggerProperties swaggerProperties;
    private DunwuWebSecurityProperties webSecurityProperties;

    @Autowired(required = false)
    public DunwuSwaggerConfiguration setWebSecurityProperties(
        DunwuWebSecurityProperties webSecurityProperties) {
        this.webSecurityProperties = webSecurityProperties;
        return this;
    }

    private BeanFactory beanFactory;

    @Bean
    @ConditionalOnMissingBean
    public DunwuSwaggerProperties swaggerProperties() {
        return new DunwuSwaggerProperties();
    }

    @Bean
    public UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder()
                                     .deepLinking(swaggerProperties.getUiConfig().getDeepLinking())
                                     .defaultModelExpandDepth(
                                         swaggerProperties.getUiConfig().getDefaultModelExpandDepth())
                                     .defaultModelRendering(swaggerProperties.getUiConfig().getDefaultModelRendering())
                                     .defaultModelsExpandDepth(
                                         swaggerProperties.getUiConfig().getDefaultModelsExpandDepth())
                                     .displayOperationId(swaggerProperties.getUiConfig().getDisplayOperationId())
                                     .displayRequestDuration(
                                         swaggerProperties.getUiConfig().getDisplayRequestDuration())
                                     .docExpansion(swaggerProperties.getUiConfig().getDocExpansion())
                                     .maxDisplayedTags(swaggerProperties.getUiConfig().getMaxDisplayedTags())
                                     .operationsSorter(swaggerProperties.getUiConfig().getOperationsSorter())
                                     .showExtensions(swaggerProperties.getUiConfig().getShowExtensions())
                                     .tagsSorter(swaggerProperties.getUiConfig().getTagsSorter())
                                     .validatorUrl(swaggerProperties.getUiConfig().getValidatorUrl())
                                     .build();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(UiConfiguration.class)
    @ConditionalOnProperty(name = "dunwu.swagger.enabled", matchIfMissing = true)
    public List<Docket> createRestApi() {

        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<Docket> docketList = new LinkedList<>();

        // 没有分组
        if (swaggerProperties.getDocket().size() == 0) {
            ApiInfo apiInfo = new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .contact(new Contact(swaggerProperties.getContact().getName(),
                    swaggerProperties.getContact().getUrl(),
                    swaggerProperties.getContact().getEmail()))
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .build();

            // base-path处理
            // 当没有配置任何path的时候，解析/**
            if (swaggerProperties.getBasePath().isEmpty()) {
                swaggerProperties.getBasePath().add("/**");
            }
            List<Predicate<String>> basePath = new ArrayList();
            for (String path : swaggerProperties.getBasePath()) {
                basePath.add(PathSelectors.ant(path));
            }

            // exclude-path处理
            List<Predicate<String>> excludePath = new ArrayList<>();
            for (String path : swaggerProperties.getExcludePath()) {
                excludePath.add(PathSelectors.ant(path));
            }

            // 全局配置参数
            List<Parameter> params = new ArrayList<>(
                buildGlobalOperationParametersFromSwaggerProperties(swaggerProperties.getGlobalOperationParameters()));
            // 安全配置
            if (webSecurityProperties != null && webSecurityProperties.isEnabled()) {
                ParameterBuilder builder = new ParameterBuilder();
                builder.name(webSecurityProperties.getJwt().getTokenHeader())
                       .description("token")
                       .modelRef(new ModelRef("string"))
                       .parameterType("header")
                       .defaultValue(webSecurityProperties.getJwt().getTokenStartWith())
                       .required(true)
                       .build();
                params.add(builder.build());
            }

            Docket docketBuilder = new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo)
                .globalOperationParameters(params);

            // 全局响应消息
            if (!swaggerProperties.getApplyDefaultResponseMessages()) {
                buildGlobalResponseMessage(swaggerProperties, docketBuilder);
            }

            Docket docket = docketBuilder.select()
                                         .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                                         .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)),
                                             Predicates.or(basePath))
                                         ).build();

            /* ignoredParameterTypes **/
            Class<?>[] array = new Class[swaggerProperties.getIgnoredParameterTypes().size()];
            Class<?>[] ignoredParameterTypes = swaggerProperties.getIgnoredParameterTypes().toArray(array);
            docket.ignoredParameterTypes(ignoredParameterTypes);

            configurableBeanFactory.registerSingleton("defaultDocket", docket);
            docketList.add(docket);
            return docketList;
        }

        // 分组创建
        for (String groupName : swaggerProperties.getDocket().keySet()) {
            DunwuSwaggerProperties.DocketInfo docketInfo = swaggerProperties.getDocket().get(groupName);

            ApiInfo apiInfo = new ApiInfoBuilder()
                .title(docketInfo.getTitle().isEmpty() ? swaggerProperties.getTitle() : docketInfo.getTitle())
                .description(docketInfo.getDescription().isEmpty() ? swaggerProperties.getDescription()
                    : docketInfo.getDescription())
                .version(docketInfo.getVersion().isEmpty() ? swaggerProperties.getVersion() : docketInfo.getVersion())
                .license(docketInfo.getLicense().isEmpty() ? swaggerProperties.getLicense() : docketInfo.getLicense())
                .licenseUrl(docketInfo.getLicenseUrl().isEmpty() ? swaggerProperties.getLicenseUrl()
                    : docketInfo.getLicenseUrl())
                .contact(
                    new Contact(
                        docketInfo.getContact().getName().isEmpty() ? swaggerProperties.getContact().getName()
                            : docketInfo.getContact().getName(),
                        docketInfo.getContact().getUrl().isEmpty() ? swaggerProperties.getContact().getUrl()
                            : docketInfo.getContact().getUrl(),
                        docketInfo.getContact().getEmail().isEmpty() ? swaggerProperties.getContact().getEmail()
                            : docketInfo.getContact().getEmail()
                    )
                )
                .termsOfServiceUrl(
                    docketInfo.getTermsOfServiceUrl().isEmpty() ? swaggerProperties.getTermsOfServiceUrl()
                        : docketInfo.getTermsOfServiceUrl())
                .build();

            // base-path处理
            // 当没有配置任何path的时候，解析/**
            if (docketInfo.getBasePath().isEmpty()) {
                docketInfo.getBasePath().add("/**");
            }
            List<Predicate<String>> basePath = new ArrayList<>();
            for (String path : docketInfo.getBasePath()) {
                basePath.add(PathSelectors.ant(path));
            }

            // exclude-path处理
            List<Predicate<String>> excludePath = new ArrayList<>();
            for (String path : docketInfo.getExcludePath()) {
                excludePath.add(PathSelectors.ant(path));
            }

            Docket docketForBuilder = new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo)
                // .securityContexts(Collections.singletonList(securityContext()))
                .globalOperationParameters(
                    assemblyGlobalOperationParameters(swaggerProperties.getGlobalOperationParameters(),
                        docketInfo.getGlobalOperationParameters()));

            // 全局响应消息
            if (!swaggerProperties.getApplyDefaultResponseMessages()) {
                buildGlobalResponseMessage(swaggerProperties, docketForBuilder);
            }

            Docket docket = docketForBuilder.groupName(groupName)
                                            .select()
                                            .apis(RequestHandlerSelectors.basePackage(docketInfo.getBasePackage()))
                                            .paths(
                                                Predicates.and(
                                                    Predicates.not(Predicates.or(excludePath)),
                                                    Predicates.or(basePath)
                                                )
                                            )
                                            .build();

            /* ignoredParameterTypes **/
            Class<?>[] array = new Class[docketInfo.getIgnoredParameterTypes().size()];
            Class<?>[] ignoredParameterTypes = docketInfo.getIgnoredParameterTypes().toArray(array);
            docket.ignoredParameterTypes(ignoredParameterTypes);

            configurableBeanFactory.registerSingleton(groupName, docket);
            docketList.add(docket);
        }
        return docketList;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private List<Parameter> buildGlobalOperationParametersFromSwaggerProperties(
        List<DunwuSwaggerProperties.GlobalOperationParameter> globalOperationParameters) {
        List<Parameter> parameters = newArrayList();

        if (Objects.isNull(globalOperationParameters)) {
            return parameters;
        }
        for (DunwuSwaggerProperties.GlobalOperationParameter globalOperationParameter : globalOperationParameters) {
            parameters.add(new ParameterBuilder()
                .name(globalOperationParameter.getName())
                .description(globalOperationParameter.getDescription())
                .modelRef(new ModelRef(globalOperationParameter.getModelRef()))
                .parameterType(globalOperationParameter.getParameterType())
                .required(Boolean.parseBoolean(globalOperationParameter.getRequired()))
                .build());
        }
        return parameters;
    }

    /**
     * 局部参数按照name覆盖局部参数
     *
     * @param globalOperationParameters
     * @param docketOperationParameters
     * @return
     */
    private List<Parameter> assemblyGlobalOperationParameters(
        List<DunwuSwaggerProperties.GlobalOperationParameter> globalOperationParameters,
        List<DunwuSwaggerProperties.GlobalOperationParameter> docketOperationParameters) {

        if (Objects.isNull(docketOperationParameters) || docketOperationParameters.isEmpty()) {
            return buildGlobalOperationParametersFromSwaggerProperties(globalOperationParameters);
        }

        Set<String> docketNames = docketOperationParameters.stream()
                                                           .map(
                                                               DunwuSwaggerProperties.GlobalOperationParameter::getName)
                                                           .collect(Collectors.toSet());

        List<DunwuSwaggerProperties.GlobalOperationParameter> resultOperationParameters = newArrayList();

        if (Objects.nonNull(globalOperationParameters)) {
            for (DunwuSwaggerProperties.GlobalOperationParameter parameter : globalOperationParameters) {
                if (!docketNames.contains(parameter.getName())) {
                    resultOperationParameters.add(parameter);
                }
            }
        }

        resultOperationParameters.addAll(docketOperationParameters);
        return buildGlobalOperationParametersFromSwaggerProperties(resultOperationParameters);
    }

    /**
     * 设置全局响应消息
     *
     * @param dunwuSwaggerProperties swaggerProperties 支持 POST,GET,PUT,PATCH,DELETE,HEAD,OPTIONS,TRACE
     * @param docketForBuilder       swagger docket builder
     */
    private void buildGlobalResponseMessage(DunwuSwaggerProperties dunwuSwaggerProperties, Docket docketForBuilder) {

        DunwuSwaggerProperties.GlobalResponseMessage globalResponseMessages =
            dunwuSwaggerProperties.getGlobalResponseMessage();

        /* POST,GET,PUT,PATCH,DELETE,HEAD,OPTIONS,TRACE 响应消息体 **/
        List<ResponseMessage> postResponseMessages = getResponseMessageList(globalResponseMessages.getPost());
        List<ResponseMessage> getResponseMessages = getResponseMessageList(globalResponseMessages.getGet());
        List<ResponseMessage> putResponseMessages = getResponseMessageList(globalResponseMessages.getPut());
        List<ResponseMessage> patchResponseMessages = getResponseMessageList(globalResponseMessages.getPatch());
        List<ResponseMessage> deleteResponseMessages = getResponseMessageList(globalResponseMessages.getDelete());
        List<ResponseMessage> headResponseMessages = getResponseMessageList(globalResponseMessages.getHead());
        List<ResponseMessage> optionsResponseMessages = getResponseMessageList(globalResponseMessages.getOptions());
        List<ResponseMessage> trackResponseMessages = getResponseMessageList(globalResponseMessages.getTrace());

        docketForBuilder.useDefaultResponseMessages(dunwuSwaggerProperties.getApplyDefaultResponseMessages())
                        .globalResponseMessage(RequestMethod.POST, postResponseMessages)
                        .globalResponseMessage(RequestMethod.GET, getResponseMessages)
                        .globalResponseMessage(RequestMethod.PUT, putResponseMessages)
                        .globalResponseMessage(RequestMethod.PATCH, patchResponseMessages)
                        .globalResponseMessage(RequestMethod.DELETE, deleteResponseMessages)
                        .globalResponseMessage(RequestMethod.HEAD, headResponseMessages)
                        .globalResponseMessage(RequestMethod.OPTIONS, optionsResponseMessages)
                        .globalResponseMessage(RequestMethod.TRACE, trackResponseMessages);
    }

    /**
     * 获取返回消息体列表
     *
     * @param globalResponseMessageBodyList 全局Code消息返回集合
     * @return
     */
    private List<ResponseMessage> getResponseMessageList
    (List<DunwuSwaggerProperties.GlobalResponseMessageBody> globalResponseMessageBodyList) {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        for (DunwuSwaggerProperties.GlobalResponseMessageBody globalResponseMessageBody : globalResponseMessageBodyList) {
            ResponseMessageBuilder responseMessageBuilder = new ResponseMessageBuilder();
            responseMessageBuilder.code(globalResponseMessageBody.getCode())
                                  .message(globalResponseMessageBody.getMessage());

            if (!StringUtils.isEmpty(globalResponseMessageBody.getModelRef())) {
                responseMessageBuilder.responseModel(new ModelRef(globalResponseMessageBody.getModelRef()));
            }
            responseMessages.add(responseMessageBuilder.build());
        }

        return responseMessages;
    }

}
