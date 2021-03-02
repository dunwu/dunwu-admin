/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.dunwu.generator.config.po;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.dunwu.generator.config.StrategyConfig;
import io.github.dunwu.generator.config.rules.DbColumnType;
import io.github.dunwu.generator.config.rules.IColumnType;
import io.github.dunwu.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 表字段信息
 *
 * @author YangHu
 * @since 2016-12-03
 */
@Data
@Accessors(chain = true)
public class TableField {

    /** 字段名称（表字段） */
    private String name;
    /** 字段数据类型 */
    private String type;
    /** 字段展示名称（实体字段） */
    private String propertyName;
    /** 字段类型（Java 类型） */
    private IColumnType columnType;
    /** 字段注释 */
    private String comment;
    private String fill;
    /** 字段名称是否经过了转换 */
    private boolean convert;
    /** 是否为Key */
    private boolean keyFlag = false;
    /** Key类型 */
    private String keyType;
    /** 是否为自增主键 */
    private boolean keyIdentityFlag = true;
    /** 是否不允许为空 */
    private boolean notNull = true;
    /** 是否出现在表单 */
    private boolean enableForm = true;
    /** 是否出现在列表 */
    private boolean enableList = true;
    /** 是否出现在搜索 */
    private boolean enableQuery = true;
    /** 表单控件类型 */
    private String formType;
    /** 表单控件类型 */
    private String queryType;
    /** 字典名称 */
    private String dictName;
    /** 是否允许排序 */
    private boolean enableSort = false;
    /** 排序类型 */
    private String sortType;

    /**
     * 自定义查询字段列表
     */
    private Map<String, Object> customMap;

    public TableField setConvert(boolean convert) {
        this.convert = convert;
        return this;
    }

    public TableField setPropertyName(StrategyConfig strategyConfig, String propertyName) {
        this.propertyName = propertyName;
        this.setConvert(strategyConfig);
        return this;
    }

    protected TableField setConvert(StrategyConfig strategyConfig) {
        if (strategyConfig.isEntityTableFieldAnnotationEnable()) {
            this.convert = true;
            return this;
        }
        if (strategyConfig.isCapitalModeNaming(name)) {
            this.convert = false;
        } else {
            // 转换字段
            if (NamingStrategy.underline_to_camel == strategyConfig.getColumnNaming()) {
                // 包含大写处理
                if (StringUtils.containsUpperCase(name)) {
                    this.convert = true;
                }
            } else if (!name.equals(propertyName)) {
                this.convert = true;
            }
        }
        return this;
    }

    public String getPropertyType() {
        if (null != columnType) {
            return columnType.getType();
        }
        return null;
    }

    /**
     * 按JavaBean规则来生成get和set方法
     */
    public String getCapitalName() {
        if (propertyName.length() <= 1) {
            return propertyName.toUpperCase();
        }
        String setGetName = propertyName;
        if (DbColumnType.BASE_BOOLEAN.getType().equalsIgnoreCase(columnType.getType())) {
            setGetName = StringUtils.removeIsPrefixIfBoolean(setGetName, Boolean.class);
        }
        // 第一个字母 小写、 第二个字母 大写 ，特殊处理
        String firstChar = setGetName.substring(0, 1);
        if (Character.isLowerCase(firstChar.toCharArray()[0])
            && Character.isUpperCase(setGetName.substring(1, 2).toCharArray()[0])) {
            return firstChar.toLowerCase() + setGetName.substring(1);
        }
        return firstChar.toUpperCase() + setGetName.substring(1);
    }

}
