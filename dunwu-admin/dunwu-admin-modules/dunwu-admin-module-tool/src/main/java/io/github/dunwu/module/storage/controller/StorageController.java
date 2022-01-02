package io.github.dunwu.module.storage.controller;

import io.github.dunwu.module.storage.entity.dto.FileInfoDto;
import io.github.dunwu.module.storage.entity.dto.UploadFileDto;
import io.github.dunwu.module.storage.entity.query.FileInfoQuery;
import io.github.dunwu.module.storage.service.StorageService;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.PageResult;
import io.github.dunwu.tool.web.ServletUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 存储服务 Controller
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/tool/storage")
@Api(tags = "文件上传下载服务")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @ApiOperation("添加一条 FileInfo 记录")
    @PostMapping("upload")
    public DataResult<FileInfoDto> upload(HttpServletRequest request, UploadFileDto fileDto)
        throws IOException {
        String ip = ServletUtil.getRealRemoteAddr(request);
        boolean isOk = storageService.allowAccess(ip);
        if (!isOk) {
            return DataResult.fail(ResultStatus.IO_ERROR.getCode(), "上传请求过于频繁，请稍后再尝试");
        }

        if (fileDto == null) {
            return DataResult.fail();
        }

        return DataResult.ok(storageService.upload(fileDto));
    }

    @ApiOperation("根据 id 列表批量删除 FileInfo 记录")
    @PreAuthorize("@exp.check('mnt:app:del')")
    @PostMapping("del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(storageService.deleteBatchByIds(ids));
    }

    @GetMapping("image/{namespace}/{tag}/{originName:.+}")
    @ApiOperation(value = "查看图片文件")
    public void imageByName(HttpServletResponse response, @PathVariable("namespace") String namespace,
        @PathVariable("tag") String tag, @PathVariable("originName") String originName)
        throws IOException {

        FileInfoQuery fileQuery = new FileInfoQuery();
        fileQuery.setNamespace(namespace);
        fileQuery.setTag(tag);
        fileQuery.setOriginName(originName);
        FileInfoDto fileInfoDto = storageService.getOne(fileQuery);
        if (fileInfoDto == null) {
            response.setStatus(404);
            return;
        }

        ServletUtil.setFileShowHeader(response);
        IOUtils.write(fileInfoDto.getContent(), response.getOutputStream());
    }

    @GetMapping("/download/{namespace}/{tag}/{originName:.+}")
    @ApiOperation(value = "下载文件")
    public void downloadByName(HttpServletRequest request, HttpServletResponse response,
        @PathVariable("namespace") String namespace, @PathVariable("tag") String tag,
        @PathVariable("originName") String originName) throws IOException {
        FileInfoQuery fileQuery = new FileInfoQuery();
        fileQuery.setNamespace(namespace);
        fileQuery.setTag(tag);
        fileQuery.setOriginName(originName);
        FileInfoDto fileInfoDto = storageService.getOne(fileQuery);
        if (fileInfoDto == null) {
            response.setStatus(404);
            return;
        }

        ServletUtil.setFileDownloadHeader(request, response, fileInfoDto.getOriginName(), fileInfoDto.getContent());
        IOUtils.write(fileInfoDto.getContent(), response.getOutputStream());
    }

    @ApiOperation("根据 FileInfoQuery 和 Pageable 分页查询 FileInfoDto 列表")
    @GetMapping("page")
    public PageResult<FileInfoDto> page(FileInfoQuery query, Pageable pageable) {
        return PageResult.ok(storageService.page(query, pageable));
    }

}
