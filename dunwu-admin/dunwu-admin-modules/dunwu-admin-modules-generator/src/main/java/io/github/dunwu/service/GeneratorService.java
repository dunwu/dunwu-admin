/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.github.dunwu.service;

import io.github.dunwu.domain.GenConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zheng Jie
 * @date 2019-01-02
 */
public interface GeneratorService {

    /**
     * 得到数据表的元数据
     *
     * @param name 表名
     * @return /
     */
    List<CodeColumnConfigDto> getColumns(String name);

    /**
     * 代码生成
     *
     * @param genConfig 配置信息
     * @param columns   字段信息
     */
    void generator(GenConfig genConfig, List<CodeColumnConfigDto> columns);

    /**
     * 预览
     *
     * @param genConfig 配置信息
     * @param columns   字段信息
     * @return /
     */
    ResponseEntity<Object> preview(GenConfig genConfig, List<CodeColumnConfigDto> columns);

    /**
     * 打包下载
     *
     * @param genConfig 配置信息
     * @param columns   字段信息
     * @param request   /
     * @param response  /
     */
    void download(GenConfig genConfig, List<CodeColumnConfigDto> columns, HttpServletRequest request,
        HttpServletResponse response);

}
