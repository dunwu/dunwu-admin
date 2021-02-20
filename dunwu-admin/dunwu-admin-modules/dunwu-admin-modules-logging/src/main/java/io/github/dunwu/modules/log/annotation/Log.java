package io.github.dunwu.modules.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 * <p>
 * {@link io.github.dunwu.modules.log.aspect.LogAspect} 是 {@link Log} 注解的处理器
 * <p>
 * 被标记的方法被调用时，会将调用信息写入 DB
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2020-04-09
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    String value() default "";

}
