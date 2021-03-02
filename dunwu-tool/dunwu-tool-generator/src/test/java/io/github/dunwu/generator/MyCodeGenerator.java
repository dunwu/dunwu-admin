package io.github.dunwu.generator;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.generator.config.*;
import io.github.dunwu.generator.config.builder.ConfigBuilder;
import io.github.dunwu.generator.config.po.TableField;
import io.github.dunwu.generator.config.po.TableInfo;
import io.github.dunwu.tool.io.AnsiColorUtil;

import java.util.Collection;

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
        generateByConfig();
        // generateByProperties();
    }

    public static void generateByConfig() {
        String url =
            "jdbc:mysql://localhost:3306/eladmin?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
        DataSourceConfig dataSourceConfig = new DataSourceConfig(url, "com.mysql.cj.jdbc.Driver", "root", "root");
        PackageConfig packageConfig = new PackageConfig("io.github.dunwu.modules", "generator");
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor("dunwu").setOutputDir("E:\\Temp\\codes");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude("code_column_config");
        TemplateConfig templateConfig = new TemplateConfig();

        ConfigBuilder builder = new ConfigBuilder(dataSourceConfig, globalConfig, packageConfig, strategyConfig,
            templateConfig);

        Collection<TableInfo> tableInfoList = builder.queryTableInfoList();
        for (TableInfo table : tableInfoList) {
            for (TableField field : table.getFields()) {
                if (field.getName().equals("rating")) {
                    field.setFrontQueryType("Between");
                    field.setFrontFormType("Date");
                } else {
                    field.setFrontQueryType("Equals");
                    field.setFrontFormType("Input");
                }
            }
        }

        builder.setTableInfoList(tableInfoList);
        CodeGenerator codeGenerator = new CodeGenerator(builder);
        codeGenerator.generate();
        AnsiColorUtil.YELLOW.println(JSONUtil.toJsonStr(builder.getTableInfoList()));
    }

    public static void generateByProperties() {
        ConfigBuilder builder = CodeGeneratorUtil.initConfigBuilder("classpath://conf/mybatis.properties");
        // builder.initTableInfoList();
        if (builder != null) {
            Collection<TableInfo> tableInfoList = builder.queryTableInfoList();
            for (TableInfo table : tableInfoList) {
                for (TableField field : table.getFields()) {
                    if (field.getName().equals("rating")) {
                        field.setFrontQueryType("Between");
                        field.setFrontFormType("Date");
                    } else {
                        field.setFrontQueryType("Equals");
                        field.setFrontFormType("Input");
                    }
                }
            }
            builder.setTableInfoList(tableInfoList);
            CodeGenerator codeGenerator = new CodeGenerator(builder);
            codeGenerator.generate();
            AnsiColorUtil.YELLOW.println(JSONUtil.toJsonStr(builder.getTableInfoList()));
        }
    }

}
