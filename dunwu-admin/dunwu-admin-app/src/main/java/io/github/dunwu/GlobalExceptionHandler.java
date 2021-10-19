package io.github.dunwu;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.exception.DataException;
import io.github.dunwu.tool.web.constant.WebConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.file.AccessDeniedException;
import java.util.Set;
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

/**
 * 系统全局异常统一处理接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href= "https://blog.csdn.net/hao_kkkkk/article/details/80538955">Springboot项目全局异常统一处理</a>
 * @since 2019-09-11
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String DEFAULT_APP = "dunwu";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("app", DEFAULT_APP);
    }

    /**
     * 统一处理请求参数校验异常(普通传参)
     *
     * @param e ConstraintViolationException
     * @return {@link DataResult}
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ ConstraintViolationException.class })
    public DataResult<?> handleConstraintViolationException(final ConstraintViolationException e) {
        log.error("ConstraintViolationException", e);
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StrUtil.split(path.toString(), ".");
            sb.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        sb = new StringBuilder(sb.substring(0, sb.length() - 1));
        return DataResult.fail(ResultStatus.HTTP_BAD_REQUEST.getCode(), sb.toString());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ HttpClientErrorException.class })
    public DataResult<?> handleBadRequestException(final HttpClientErrorException e) {
        log.error("HttpClientErrorException", e);
        return DataResult.fail(ResultStatus.HTTP_BAD_REQUEST.getCode(), e.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ DataException.class })
    public DataResult<?> handleDataException(final DataException e) {
        log.error("DataException", e);
        return DataResult.fail(ResultStatus.DATA_ERROR.getCode(), e.getLocalizedMessage());
    }

    // ------------------------------------------------------------------------------
    // 认证、授权异常
    // ------------------------------------------------------------------------------

    /**
     * 处理认证异常
     *
     * @param e AuthenticationException
     * @return {@link DataResult}
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public DataResult<?> handleAuthException(final AuthenticationException e) {
        log.error("认证失败，方法: {}, message: {}", e.getClass().getCanonicalName(), e.getLocalizedMessage());
        return DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), e.getLocalizedMessage());
    }

    /**
     * 处理认证异常
     *
     * @param e AuthException
     * @return {@link DataResult}
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    public DataResult<?> handleAuthException(final AuthException e) {
        log.error("认证失败，方法: {}, message: {}", e.getClass().getCanonicalName(), e.getLocalizedMessage());
        return DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), e.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public DataResult<?> handleAccessDeniedException(final AccessDeniedException e) {
        log.error("权限不足，方法: {}, message: {}", e.getClass().getCanonicalName(), e.getLocalizedMessage());
        return DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), e.getLocalizedMessage());
    }

    /**
     * 处理所有不可知的异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public DataResult<?> handleException(Throwable e) {
        log.error("未知异常", e);
        return DataResult.fail(ResultStatus.HTTP_SERVER_ERROR.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param e MethodArgumentNotValidException
     * @return {@link DataResult}
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    private DataResult<?> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        StringBuilder sb = new StringBuilder();
        sb.append("参数错误：\n");
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            sb.append(((FieldError) error).getField() + " ");
            sb.append(error.getDefaultMessage());
            sb.append("\n");
        }
        return DataResult.fail(ResultStatus.SYSTEM_ERROR_PARAM.getCode(), sb.toString());
    }

    private WebConstant.ResponseType getResponseMode(HttpServletRequest request) {
        String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        String accept = request.getHeader(HttpHeaders.ACCEPT);

        if (StrUtil.isNotBlank(contentType)
            && contentType.contains(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
            return WebConstant.ResponseType.HTTP_REPONSE;
        }

        if (StrUtil.isNotBlank(accept)
            && accept.contains(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
            return WebConstant.ResponseType.HTTP_REPONSE;
        }

        return WebConstant.ResponseType.MODEL_AND_VIEW;
    }

}
