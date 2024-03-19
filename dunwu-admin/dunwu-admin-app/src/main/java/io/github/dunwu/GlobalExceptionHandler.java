package io.github.dunwu;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.core.constant.enums.ResultCode;
import io.github.dunwu.tool.core.exception.CodeMsgException;
import io.github.dunwu.tool.data.exception.DataException;
import io.github.dunwu.tool.data.response.DataResult;
import io.github.dunwu.tool.data.response.Result;
import io.github.dunwu.tool.web.constant.WebConstant;
import io.jsonwebtoken.JwtException;
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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    public Result handleConstraintViolationException(final ConstraintViolationException e) {
        log.error("ConstraintViolationException", e);
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StrUtil.splitToArray(path.toString(), ".");
            sb.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        sb = new StringBuilder(sb.substring(0, sb.length() - 1));
        return new Result(ResultCode.REQUEST_ERROR.getCode(), sb.toString());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ HttpClientErrorException.class })
    public Result handleBadRequestException(final HttpClientErrorException e) {
        log.error("HttpClientErrorException", e);
        return new Result(ResultCode.REQUEST_ERROR.getCode(), e.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ DataException.class })
    public Result handleDataException(final DataException e) {
        log.error("DataException", e);
        return new Result(ResultCode.DATA_ERROR.getCode(), e.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ CodeMsgException.class })
    public Result handleCodeMsgException(final CodeMsgException e) {
        log.error("DataException", e);
        return new Result(ResultCode.DATA_ERROR.getCode(), e.getLocalizedMessage());
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
    public Result handleAuthException(final AuthenticationException e) {
        log.error("认证失败，方法: {}, message: {}", e.getClass().getCanonicalName(), e.getLocalizedMessage());
        return new Result(ResultCode.AUTH_ERROR.getCode(), e.getLocalizedMessage());
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
    public Result handleAuthException(final AuthException e) {
        log.error("认证失败，方法: {}, message: {}", e.getClass().getCanonicalName(), e.getLocalizedMessage());
        return new Result(ResultCode.AUTH_ERROR.getCode(), e.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Result handleAccessDeniedException(final AccessDeniedException e) {
        log.error("权限不足，方法: {}, message: {}", e.getClass().getCanonicalName(), e.getLocalizedMessage());
        return new Result(ResultCode.AUTH_ERROR.getCode(), e.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(JwtException.class)
    public Result handleJwtException(final JwtException e) {
        log.error("令牌失效，方法: {}, message: {}", e.getClass().getCanonicalName(), e.getLocalizedMessage());
        return new Result(ResultCode.AUTH_ERROR.getCode(), e.getLocalizedMessage());
    }

    /**
     * 处理所有不可知的异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public Result handleException(Throwable e) {
        log.error("未知异常", e);
        return new Result(ResultCode.SERVER_ERROR.getCode(), e.getMessage());
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
    private Result handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        StringBuilder sb = new StringBuilder();
        sb.append("参数错误：\n");
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            sb.append(((FieldError) error).getField() + " ");
            sb.append(error.getDefaultMessage());
            sb.append("\n");
        }
        return new Result(ResultCode.REQUEST_ERROR.getCode(), sb.toString());
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
