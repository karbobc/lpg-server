package com.hlqz.lpg.util;

import jakarta.validation.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * @author Karbob
 * @date 2024-01-05
 */
public class ValidationUtils {

    private static volatile Validator validator;

    private ValidationUtils() {
    }

    /**
     * 获取原生 Validator 对象
     */
    @NonNull
    public static Validator getValidator() {
        if (validator == null) {
            synchronized (ValidationUtils.class) {
                if (validator == null) {
                    try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()){
                        validator = factory.getValidator();
                    }
                }
            }
        }
        return validator;
    }

    /**
     * 手动校验对象
     *
     * @param bean 校验对象
     * @param groups 校验组
     * @throws ConstraintViolationException 如果校验失败则抛出异常
     */
    public static <T> void validate(T bean, Class<?>... groups) {
        Validator validator = getValidator();
        // 校验对象
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(bean, groups);
        if (!CollectionUtils.isEmpty(constraintViolationSet)) {
            // 如果包含错误, 则抛出异常
            throw new ConstraintViolationException(constraintViolationSet);
        }
    }
}
