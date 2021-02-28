package io.github.dunwu.generator;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import io.github.dunwu.generator.config.*;
import io.github.dunwu.generator.config.po.TableInfo;
import io.github.dunwu.generator.config.rules.NamingStrategy;
import io.github.dunwu.generator.engine.FreemarkerTemplateEngine;
import io.github.dunwu.generator.engine.TemplateContent;
import io.github.dunwu.tool.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * 默认代码生成器
 * <p>
 * 基于 mybatis plus 代码生成器，做了定制修改。
 * <ul>
 * <li>生成 Mapper xml</li>
 * <li>生成 Entity类</li>
 * <li>生成 Dao类</li>
 * <li>生成 Controller类（Controller做了深层定制，自动生成继承自BaseController的增删改查REST接口）</li>
 * </ul>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href= "https://mybatis.plus/guide/generator.html#%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B">mybatis plus 代码生成器</a>
 * @since 2019-04-15
 */
public class DefaultCodeGenerator implements ICodeGenerator {

    private final Logger log = LoggerFactory.getLogger(DefaultCodeGenerator.class);
    private Properties properties;

    /**
     * 加载默认加载配置文件，尝试读取其中的属性
     */
    public DefaultCodeGenerator() {
        // 默认加载配置文件
        this("classpath://conf/mybatis.properties", "classpath://application.properties");
    }

    /**
     * 加载指定路径的配置文件，尝试读取其中的属性
     */
    public DefaultCodeGenerator(String... configFiles) {
        loadProperties(configFiles);
    }

    /**
     * 直接读取传入的属性，尝试读取其中的属性
     */
    public DefaultCodeGenerator(Properties properties) {
        this.properties = properties;
    }

    /**
     * 生成代码到本地
     */
    @Override
    public void generate() {
        AutoGenerator generator = newAutoGenerator();
        if (generator != null) {
            generator.execute();
        }
    }

    /**
     * 获取生成代码的预览内容
     */
    @Override
    public List<TemplateContent> getPreviewList() {
        AutoGenerator generator = newAutoGenerator();
        if (generator != null) {
            return generator.preview();
        }
        return new ArrayList<>();
    }

    /**
     * 加载指定路径的配置文件，尝试读取其中的属性
     */
    @Override
    public void loadProperties(String... files) {
        this.properties = new Properties();
        if (ArrayUtil.isEmpty(files)) {
            return;
        }

        for (String file : files) {
            try {
                this.properties.putAll(PropertiesUtil.loadFromFile(file));
            } catch (Exception e) {
                log.debug("未检索到 {} 文件", file);
            }
        }
    }

    /**
     * 从 {@link Properties} 中尝试读取代码生成器的全局配置 {@link GlobalConfig}
     *
     * @param properties Properties
     * @return /
     */
    @Override
    public GlobalConfig getGlobalConfig(Properties properties) {
        // 全局配置
        boolean enableSwagger = PropertiesUtil.getBoolean(properties,
            MybatisPlusGenProps.GC_ENABLE_SWAGGER.getKey(), false);
        String outputDir = getPropertie(properties, MybatisPlusGenProps.OUTPUT_DIR);
        String authorName = getPropertie(properties, MybatisPlusGenProps.GC_AUTHOR_NAME);

        String xmlName = getPropertie(properties, MybatisPlusGenProps.GC_XML_NAME);
        String mapperName = getPropertie(properties, MybatisPlusGenProps.GC_MAPPER_NAME);
        String daoName = getPropertie(properties, MybatisPlusGenProps.GC_DAO_NAME);
        String daoImplName = getPropertie(properties, MybatisPlusGenProps.GC_DAO_IMPL_NAME);
        String serviceName = getPropertie(properties, MybatisPlusGenProps.GC_SERVICE_NAME);
        String serviceImplName = getPropertie(properties, MybatisPlusGenProps.GC_SERVICE_IMPL_NAME);
        String controllerName = getPropertie(properties, MybatisPlusGenProps.GC_CONTROLLER_NAME);

        GlobalConfig config = new GlobalConfig();
        config.setOpen(false)
              .setFileOverride(true)
              .setActiveRecord(false)
              .setOutputDir(outputDir)
              .setXmlName(xmlName)
              .setMapperName(mapperName)
              .setDaoName(daoName)
              .setDaoImplName(daoImplName)
              .setServiceName(serviceName)
              .setServiceImplName(serviceImplName)
              .setControllerName(controllerName)
              .setSwagger2(enableSwagger);

        if (StrUtil.isNotBlank(authorName)) {
            config.setAuthor(authorName);
        }
        return config;
    }

    /**
     * 从 {@link Properties} 中数据源配置 {@link DataSourceConfig}
     *
     * @param properties Properties
     * @return /
     */
    @Override
    public DataSourceConfig getDataSourceConfig(Properties properties) {
        String url = getPropertie(properties, MybatisPlusGenProps.SPRING_DATASOURCE_URL);
        String driverName = getPropertie(properties, MybatisPlusGenProps.SPRING_DATASOURCE_DRIVER);
        String username = getPropertie(properties, MybatisPlusGenProps.SPRING_DATASOURCE_USERNAME);
        String password = getPropertie(properties, MybatisPlusGenProps.SPRING_DATASOURCE_PASSWORD);
        DataSourceConfig config = new DataSourceConfig();
        config.setUrl(url).setDriverName(driverName).setUsername(username).setPassword(password);
        return config;
    }

    /**
     * 从 {@link Properties} 中读取代码生成器的 package 配置 {@link PackageConfig}
     *
     * @param properties Properties
     * @return /
     */
    @Override
    public PackageConfig getPackageConfig(Properties properties) {
        String moduleName = getPropertie(properties, MybatisPlusGenProps.PC_MODULE_NAME);
        String packageName = getPropertie(properties, MybatisPlusGenProps.PC_PACKAGE_NAME);
        String mapperPackageName = getPropertie(properties, MybatisPlusGenProps.PC_MAPPER_NAME);
        String daoPackageName = getPropertie(properties, MybatisPlusGenProps.PC_DAO_NAME);
        String daoImplPackageName = getPropertie(properties, MybatisPlusGenProps.PC_DAO_IMPL_NAME);
        String servicePackageName = getPropertie(properties, MybatisPlusGenProps.PC_SERVICE_NAME);
        String serviceImplPackageName = getPropertie(properties, MybatisPlusGenProps.PC_SERVICE_IMPL_NAME);
        String xmlPackageName = getPropertie(properties, MybatisPlusGenProps.PC_XML_NAME);
        PackageConfig config = new PackageConfig();
        if (StrUtil.isNotBlank(moduleName)) {
            config.setModuleName(moduleName);
        }
        if (StrUtil.isNotBlank(packageName)) {
            config.setParent(packageName);
        }
        config.setMapper(mapperPackageName)
              .setDao(daoPackageName)
              .setDaoImpl(daoImplPackageName)
              .setService(servicePackageName)
              .setServiceImpl(serviceImplPackageName)
              .setXml(xmlPackageName);
        return config;
    }

    /**
     * 从 {@link Properties} 中读取代码生成器的策略配置 {@link StrategyConfig}
     *
     * @param properties Properties
     * @return /
     */
    @Override
    public StrategyConfig getStrategyConfig(Properties properties, PackageConfig pc) {
        String tableName = getPropertie(properties, MybatisPlusGenProps.SC_TABLE_NAME);
        String superEntity = getPropertie(properties, MybatisPlusGenProps.SC_SUPER_ENTITY);
        String superDao = getPropertie(properties, MybatisPlusGenProps.SC_SUPER_DAO);
        String superDaoImpl = getPropertie(properties, MybatisPlusGenProps.SC_SUPER_DAO_IMPL);

        StrategyConfig config = new StrategyConfig();
        config.setEntityLombokModel(true)
              .setRestControllerStyle(true)
              .setControllerMappingHyphenStyle(false)
              .setNaming(NamingStrategy.underline_to_camel)
              .setColumnNaming(NamingStrategy.underline_to_camel)
              .setSuperDaoClass(superDao)
              .setSuperDaoImplClass(superDaoImpl);
        if (StrUtil.isNotBlank(tableName)) {
            tableName = tableName.trim();
            config.setInclude(tableName.split(","));
        } else {
            config.setInclude(scanner("表名"));
        }

        if (StrUtil.isNotBlank(superEntity)) {
            config.setSuperEntityClass(superEntity);
        }
        return config;
    }

    /**
     * 从 {@link Properties} 中读取代码生成器的注入配置 {@link InjectionConfig}
     *
     * @param properties Properties
     * @return /
     */
    @Override
    public InjectionConfig getInjectionConfig(Properties properties, PackageConfig pc) {
        String resourcesDir = getPropertie(properties, MybatisPlusGenProps.OUTPUT_DIR) + "/src/main/resources";

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/backend/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                StringBuilder sb = new StringBuilder(resourcesDir);
                sb.append("/").append(pc.getXml()).append("/");

                if (StrUtil.isNotEmpty(pc.getModuleName())) {
                    sb.append(pc.getModuleName()).append("/");
                }
                sb.append(tableInfo.getEntityName());
                sb.append("Mapper");
                sb.append(StringPool.DOT_XML);
                return sb.toString();
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * 获取代码生成器的策略配置 {@link TemplateConfig}
     *
     * @return /
     */
    @Override
    public TemplateConfig getTemplateConfig() {
        return new TemplateConfig();
    }

    private AutoGenerator newAutoGenerator() {
        if (MapUtil.isEmpty(this.properties)) {
            return null;
        }

        // 数据源配置
        DataSourceConfig dsc = getDataSourceConfig(properties);

        // 全局配置
        GlobalConfig gc = getGlobalConfig(properties);

        // 包名配置
        PackageConfig pc = getPackageConfig(properties);

        // 策略配置
        StrategyConfig sc = getStrategyConfig(properties, pc);

        // 自定义配置
        // InjectionConfig cfg = getInjectionConfig(properties, pc);

        // 模板配置
        TemplateConfig tc = getTemplateConfig();

        // 将配置项注入 AutoGenerator
        AutoGenerator generator = new AutoGenerator();
        generator.setTemplateEngine(new FreemarkerTemplateEngine())
                 .setGlobalConfig(gc)
                 .setDataSource(dsc)
                 .setPackageInfo(pc)
                 .setStrategy(sc)
                 // .setCfg(cfg)
                 .setTemplate(tc);
        return generator;
    }

    /**
     * 属性文件中未检索到的配置项，通过控制台输入方式读取
     */
    private String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        sb.append("请输入" + tip + "：");
        System.out.println(sb.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StrUtil.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public String getPropertie(Properties properties, MybatisPlusGenProps key) {
        return PropertiesUtil.getString(properties, key.getKey(), key.getValue());
    }

}
