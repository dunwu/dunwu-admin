package io.github.dunwu.module.security.exception;

import io.github.dunwu.tool.core.constant.Status;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.core.exception.CodeMsgException;

/**
 * 认证异常
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class AuthException extends CodeMsgException {

    private static final long serialVersionUID = -7027578114976830416L;

    public AuthException() {
        super(ResultStatus.DATA_ERROR);
    }

    public AuthException(Status status) {
        super(status.getCode(), status.getMsg());
    }

    public AuthException(int code, String message) {
        super(code, message);
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(String message, Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
