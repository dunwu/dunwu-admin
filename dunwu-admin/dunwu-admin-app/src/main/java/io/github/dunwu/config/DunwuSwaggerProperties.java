package io.github.dunwu.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Swagger 配置属性
 *
 * @author 翟永超
 * @author <a href="mailto:forbreak@163.com">Dunwu</a>
 * @since 2020-03-17
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties("dunwu.swagger")
public class DunwuSwaggerProperties {

    /**
     * 是否开启swagger
     **/
    private boolean enabled;
    /**
     * 标题
     **/
    private String title = "";
    /**
     * 描述
     **/
    private String description = "";
    /**
     * 版本
     **/
    private String version = "";
    /**
     * 许可证
     **/
    private String license = "";
    /**
     * 许可证URL
     **/
    private String licenseUrl = "";
    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";
    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "";
    /**
     * 联系方式
     */
    private Contact contact = new Contact();
    /**
     * 忽略的参数类型
     **/
    private List<Class<?>> ignoredParameterTypes = new ArrayList<>();
    /**
     * swagger会解析的url规则
     **/
    private List<String> basePath = new ArrayList<>();
    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePath = new ArrayList<>();
    /**
     * 分组文档
     **/
    private Map<String, DocketInfo> docket = new LinkedHashMap<>();
    /**
     * host信息
     **/
    private String host = "";
    /**
     * 全局参数配置
     **/
    private List<GlobalOperationParameter> globalOperationParameters;
    /**
     * 页面功能配置
     **/
    private UiConfig uiConfig = new UiConfig();
    /**
     * 是否使用默认预定义的响应消息 ，默认 true
     **/
    private Boolean applyDefaultResponseMessages = true;
    /**
     * 全局响应消息
     **/
    private GlobalResponseMessage globalResponseMessage;

    @Data
    @NoArgsConstructor
    public static class GlobalOperationParameter {

        /**
         * 参数名
         **/
        private String name;

        /**
         * 描述信息
         **/
        private String description;

        /**
         * 指定参数类型
         **/
        private String modelRef;

        /**
         * 参数放在哪个地方:header,query,path,body.form
         **/
        private String parameterType;

        /**
         * 参数是否必须传
         **/
        private String required;

    }

    @Data
    @NoArgsConstructor
    public static class DocketInfo {

        /**
         * 标题
         **/
        private String title = "";
        /**
         * 描述
         **/
        private String description = "";
        /**
         * 版本
         **/
        private String version = "";
        /**
         * 许可证
         **/
        private String license = "";
        /**
         * 许可证URL
         **/
        private String licenseUrl = "";
        /**
         * 服务条款URL
         **/
        private String termsOfServiceUrl = "";
        /**
         * 联系方式
         */
        private Contact contact = new Contact();
        /**
         * swagger会解析的包路径
         **/
        private String basePackage = "";
        /**
         * swagger会解析的url规则
         **/
        private List<String> basePath = new ArrayList<>();
        /**
         * 在basePath基础上需要排除的url规则
         **/
        private List<String> excludePath = new ArrayList<>();

        private List<GlobalOperationParameter> globalOperationParameters;

        /**
         * 忽略的参数类型
         **/
        private List<Class<?>> ignoredParameterTypes = new ArrayList<>();

    }

    /**
     * 联系方式
     */
    @Data
    @NoArgsConstructor
    public static class Contact {

        /**
         * 联系人
         **/
        private String name = "";
        /**
         * 联系人url
         **/
        private String url = "";
        /**
         * 联系人email
         **/
        private String email = "";

    }

    @Data
    @NoArgsConstructor
    public static class GlobalResponseMessage {

        /**
         * POST 响应消息体
         **/
        List<GlobalResponseMessageBody> post = new ArrayList<>();

        /**
         * GET 响应消息体
         **/
        List<GlobalResponseMessageBody> get = new ArrayList<>();

        /**
         * PUT 响应消息体
         **/
        List<GlobalResponseMessageBody> put = new ArrayList<>();

        /**
         * PATCH 响应消息体
         **/
        List<GlobalResponseMessageBody> patch = new ArrayList<>();

        /**
         * DELETE 响应消息体
         **/
        List<GlobalResponseMessageBody> delete = new ArrayList<>();

        /**
         * HEAD 响应消息体
         **/
        List<GlobalResponseMessageBody> head = new ArrayList<>();

        /**
         * OPTIONS 响应消息体
         **/
        List<GlobalResponseMessageBody> options = new ArrayList<>();

        /**
         * TRACE 响应消息体
         **/
        List<GlobalResponseMessageBody> trace = new ArrayList<>();

    }

    @Data
    @NoArgsConstructor
    public static class GlobalResponseMessageBody {

        /**
         * 响应码
         **/
        private int code;

        /**
         * 响应消息
         **/
        private String message;

        /**
         * 响应体
         **/
        private String modelRef;

    }

    @Data
    @NoArgsConstructor
    public static class UiConfig {

        private String apiSorter = "alpha";

        /**
         * 是否启用json编辑器
         **/
        private Boolean jsonEditor = false;
        /**
         * 是否显示请求头信息
         **/
        private Boolean showRequestHeaders = true;
        /**
         * 支持页面提交的请求类型
         **/
        private String submitMethods = "get,post,put,delete,patch";
        /**
         * 请求超时时间
         **/
        private Long requestTimeout = 10000L;

        private Boolean deepLinking;
        private Boolean displayOperationId;
        private Integer defaultModelsExpandDepth;
        private Integer defaultModelExpandDepth;
        private ModelRendering defaultModelRendering;

        /**
         * 是否显示请求耗时，默认false
         */
        private Boolean displayRequestDuration = true;
        /**
         * 可选 none | list
         */
        private DocExpansion docExpansion;
        /**
         * Boolean=false OR String
         */
        private Object filter;
        private Integer maxDisplayedTags;
        private OperationsSorter operationsSorter;
        private Boolean showExtensions;
        private TagsSorter tagsSorter;

        /**
         * Network
         */
        private String validatorUrl;

    }

}


