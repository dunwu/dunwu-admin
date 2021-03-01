package io.github.dunwu.generator;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.generator.config.builder.ConfigBuilder;
import io.github.dunwu.tool.io.AnsiColorUtil;

/**
 * 代码生成器
 * <p>
 * （1）如果使用默认代码生成器，可以在 conf/mybatis.properties 中配置 ParamKeyEnum 的 key 来控制代码生成
 * <p>
 * （2）如果需要定制，可以覆写 DefaultCodeGenerator 中的方法来订制更细粒度的配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see CodeGeneratorUtil
 * @since 2019-07-19
 */
public class MyCodeGenerator {

    public static void main(String[] args) {
        ConfigBuilder configBuilder = CodeGeneratorUtil.initConfigBuilder("classpath://conf/mybatis.properties");
        if (configBuilder != null) {
            configBuilder.initTableInfoList();
            CodeGenerator codeGenerator = new CodeGenerator(configBuilder);
            codeGenerator.generate();
            AnsiColorUtil.YELLOW.println(JSONUtil.toJsonStr(configBuilder.getTableInfoList()));
        }
    }

}
