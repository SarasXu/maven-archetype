package com.dph.temp.service;

import com.dph.common.utils.core.exception.BizError;
import com.dph.common.utils.enums.Status;
import com.dph.common.utils.service.BaseOrder;
import com.dph.common.utils.service.BaseResult;
import com.dph.common.utils.utils.Reflections;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * description:
 * 业务切面预处理器，业务基础骨架
 *
 * @author saras_xu@163.com
 * @date 2018-01-22 10:13 创建
 */
@Aspect
@Component
public class ServiceSectionHandler {

    private final static Logger logger = LoggerFactory.getLogger(ServiceSectionHandler.class);
    private final static String DEFAULT_EXCEPTION_MESSAGE = "系统繁忙，请稍后尝试";
    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Pointcut("@annotation(com.dph.sale.service.ServiceHandler)")
    public void servicePoint() {

    }

    /**
     * 业务预处理器
     *
     * @param proceedingJoinPoint
     */
    @Around("servicePoint()")
    public Object invoke(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        ServiceHandler serviceHandler = method.getAnnotation(ServiceHandler.class);
        if (null == serviceHandler) {
            //为空时去获取实现类方法上的注解
            String methodName = proceedingJoinPoint.getSignature().getName();
            Class<?> classTarget = proceedingJoinPoint.getTarget().getClass();
            Class<?>[] par = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterTypes();
            Method implMethod = classTarget.getMethod(methodName, par);
            serviceHandler = implMethod.getAnnotation(ServiceHandler.class);
        }
        Object[] params = proceedingJoinPoint.getArgs();
        for (Object object : params) {
            logger.info("{}收到业务请求，订单：{}。", method.getDeclaringClass().getName() + "#" + method.getName(), object);
            if (object instanceof BaseOrder) {
                setMDC((String) Reflections.invokeGetter(object, "serialNo"));
            }
        }

        Class<?> returnType = method.getReturnType();
        boolean isBaseResult = BaseResult.class.isAssignableFrom(returnType);
        Object result;
        try {
            //检查参数
            if (serviceHandler.isCheckOrder()) {
                for (Object object : params) {
                    if (object instanceof BaseOrder) {
                        ((BaseOrder) object).check();
                    }
                }
            }
            //业务实现
            if (serviceHandler.isTransaction()) {
                logger.info("业务处理开始[事务开启]");
                AppBiz biz = new AppBiz(proceedingJoinPoint);
                result = transactionTemplate.execute(biz);
            } else {
                logger.info("业务处理开始[事务未开启]");
                result = proceedingJoinPoint.proceed();
            }
            if (isBaseResult) {
                if (null == Reflections.invokeGetter(result, "status")) {
                    Reflections.invokeSetter(result, "status", Status.SUCCESS);
                    Reflections.invokeSetter(result, "message", "业务处理成功");
                }
            }
        } catch (BizError bizError) {
            logger.warn("处理业务发生错误:[{}]", bizError.getMessage());
            if (isBaseResult) {
                result = returnType.newInstance();
                Reflections.invokeSetter(result, "status", Status.FAIL);
                Reflections.invokeSetter(result, "code", bizError.getCode());
                Reflections.invokeSetter(result, "message", bizError.getMessage());
            } else {
                throw bizError;
            }
        }
        logger.info("{}业务请求处理完成，结果：{}。", method.getDeclaringClass().getName() + "#" + method.getName(), result);
        return result;
    }


    /**
     * 设置MDC
     *
     * @param serialNo
     */
    private void setMDC(String serialNo) {
        if (StringUtils.isNotBlank(serialNo)) {
            if (StringUtils.isNotBlank(MDC.get("serialNo"))) {
                MDC.remove("serialNo");
            }
            MDC.put("serialNo", "-[serialNo:" + serialNo + "]");
        }
    }

    /**
     * 编程式事务
     */
    private class AppBiz implements TransactionCallback<Object> {
        private ProceedingJoinPoint proceedingJoinPoint;

        AppBiz(ProceedingJoinPoint proceedingJoinPoint) {
            this.proceedingJoinPoint = proceedingJoinPoint;
        }

        @Override
        public Object doInTransaction(TransactionStatus status) {
            try {
                //业务执行
                return proceedingJoinPoint.proceed();
            } catch (Throwable e) {
                status.setRollbackOnly();
                if (e instanceof BizError) {
                    logger.warn("系统内部发生业务异常：{}", e.getMessage());
                    throw (BizError) e;
                } else if (e instanceof NullPointerException) {
                    logger.error("系统内部发生空指针异常：", e);
                    throw new BizError(DEFAULT_EXCEPTION_MESSAGE);
                } else if (e instanceof SQLException) {
                    logger.error("系统内部发生数据库异常：", e);
                    throw new BizError(DEFAULT_EXCEPTION_MESSAGE);
                } else {
                    logger.error("系统内部发生未知异常：", e);
                    throw new BizError(DEFAULT_EXCEPTION_MESSAGE);
                }
            }
        }
    }
}
