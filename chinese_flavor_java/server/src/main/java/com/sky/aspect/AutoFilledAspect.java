package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFilledAspect {
    /**}
     * 切面表达式重用方式定义切面表达式
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void pointCut(){}

    @Before("pointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("公共字段自动填充");

        //获取被拦截的方法的数据库操作类型
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = annotation.value();

        //拿到被拦截的方法的参数
        Object[] args = joinPoint.getArgs();
        Object entity = args[0];

        if(entity == null){
            return;
        }

        //准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();

        //填充相应的公共字段
        try {
            Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

            if(operationType == OperationType.INSERT){
                setCreateTime.invoke(entity,now);
                setCreateUser.invoke(entity,id);

                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,id);
            }else if(operationType == OperationType.UPDATE){
                setUpdateTime.invoke(entity,now);

                setUpdateUser.invoke(entity,id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
