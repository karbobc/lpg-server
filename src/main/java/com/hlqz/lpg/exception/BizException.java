package com.hlqz.lpg.exception;

import com.hlqz.lpg.model.enums.RcEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author Karbob
 * @date 2023-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 全局错误码
     */
    private String code;

    public BizException() {
        super(RcEnum.BIZ_ERROR.getMessage());
        this.code = RcEnum.BIZ_ERROR.getCode();
    }

    public BizException(String message) {
        super(message);
        this.code = RcEnum.BIZ_ERROR.getCode();
    }

    public BizException(Throwable cause) {
        super(RcEnum.BIZ_ERROR.getMessage(), cause);
        this.code = RcEnum.BIZ_ERROR.getCode();
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.code = RcEnum.BIZ_ERROR.getCode();
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
