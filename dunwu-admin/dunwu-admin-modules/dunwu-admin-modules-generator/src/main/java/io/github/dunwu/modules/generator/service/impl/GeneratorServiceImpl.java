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
package io.github.dunwu.modules.generator.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ZipUtil;
import io.github.dunwu.exception.BadRequestException;
import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.service.CodeColumnConfigService;
import io.github.dunwu.modules.generator.service.GeneratorService;
import io.github.dunwu.modules.generator.service.TableService;
import io.github.dunwu.modules.generator.util.GenUtil;
import io.github.dunwu.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zheng Jie
 * @date 2019-01-02
 */
@Service
@RequiredArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private static final Logger log = LoggerFactory.getLogger(GeneratorServiceImpl.class);

    private final TableService tableService;
    private final CodeColumnConfigService columnConfigService;

    @Override
    public List<CodeColumnConfigDto> getColumns(String tableName) {
        CodeColumnConfigQuery params = new CodeColumnConfigQuery();
        params.setTableName(tableName);
        List<CodeColumnConfigDto> columnInfos = columnConfigService.pojoListByQuery(params);
        if (CollectionUtil.isNotEmpty(columnInfos)) {
            return columnInfos;
        } else {
            columnInfos = tableService.getColumns(tableName);
            List<CodeColumnConfig> entites = columnInfos.stream()
                                                        .map(columnConfigService::dtoToDo)
                                                        .collect(Collectors.toList());
            columnConfigService.saveBatch(entites);
            return columnInfos;
        }
    }

    @Override
    public void generator(CodeTableConfigDto tableConfig, List<CodeColumnConfigDto> columns) {
        if (tableConfig.getId() == null) {
            throw new BadRequestException("请先配置生成器");
        }
        try {
            GenUtil.generatorCode(columns, tableConfig);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException("生成失败，请手动处理已生成的文件");
        }
    }

    @Override
    public ResponseEntity<Object> preview(CodeTableConfigDto tableConfig, List<CodeColumnConfigDto> columns) {
        if (tableConfig.getId() == null) {
            throw new BadRequestException("请先配置生成器");
        }
        List<Map<String, Object>> genList = GenUtil.preview(columns, tableConfig);
        return new ResponseEntity<>(genList, HttpStatus.OK);
    }

    @Override
    public void download(CodeTableConfigDto tableConfig, List<CodeColumnConfigDto> columns, HttpServletRequest request,
        HttpServletResponse response) {
        if (tableConfig.getId() == null) {
            throw new BadRequestException("请先配置生成器");
        }
        try {
            File file = new File(GenUtil.download(columns, tableConfig));
            String zipPath = file.getPath() + ".zip";
            ZipUtil.zip(file.getPath(), zipPath);
            FileUtil.downloadFile(request, response, new File(zipPath), true);
        } catch (IOException e) {
            throw new BadRequestException("打包失败");
        }
    }

}
