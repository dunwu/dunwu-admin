package io.github.dunwu.aspect;

import cn.hutool.core.util.ArrayUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Mybatis CRUD AOP 处理器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MybatisCrudAspect {

    private final MybatisHelper mybatisHelper;

    // ==================================================================== 插入和更新处理切点

    @Pointcut("execution(* io.github.dunwu.tool.data.mybatis.IDao.insert(..))")
    public void insertPointcut() { }

    @Pointcut("execution(* io.github.dunwu.tool.data.mybatis.IDao.update*(..))")
    public void updatePointcut() { }

    @Pointcut("execution(* io.github.dunwu.tool.data.mybatis.IDao.save*(..))")
    public void savePointcut() { }

    @Before("insertPointcut() || updatePointcut() || savePointcut()")
    public void prepareBeforeSaveAndUpdate(JoinPoint joinPoint) throws IllegalAccessException {
        // 获取方法信息
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        if (log.isTraceEnabled()) {
            log.trace("[MybatisCrudAspect] 尝试处理方法：{}", className + "#" + methodName);
        }

        // 获取所有参数值
        Object[] argValues = joinPoint.getArgs();
        if (ArrayUtil.isEmpty(argValues)) {
            return;
        }

        for (Object obj : argValues) {
            mybatisHelper.completeUserToEntity(obj, methodName);
        }
    }

}
