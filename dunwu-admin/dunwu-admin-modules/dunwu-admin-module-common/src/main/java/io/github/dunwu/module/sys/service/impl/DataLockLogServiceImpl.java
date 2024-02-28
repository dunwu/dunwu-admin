package io.github.dunwu.module.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.module.sys.entity.TableColumnLockLog;
import io.github.dunwu.module.sys.service.TableColumnLockLogService;
import io.github.dunwu.tool.web.log.entity.DataLockLogRecord;
import io.github.dunwu.tool.web.log.service.DataLockLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据锁定日志服务实现类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-01-13
 */
@Service
@RequiredArgsConstructor
public class DataLockLogServiceImpl implements DataLockLogService {

    private final TableColumnLockLogService tableColumnLockLogService;

    @Override
    public boolean batchSave(Collection<DataLockLogRecord> records) {
        List<TableColumnLockLog> list = records.stream()
                                               .map(i -> BeanUtil.toBean(i, TableColumnLockLog.class))
                                               .collect(Collectors.toList());
        return tableColumnLockLogService.saveBatch(list);
    }

}
