package com.shopverse.productservice.exception;

import com.shopverse.common.exception.BaseException;
import com.shopverse.common.exception.ErrorCode;

public class ProductPurchaseException extends BaseException {

    public ProductPurchaseException(String message) {
        super(message, ErrorCode.INSUFFICIENT_STOCK);
    }
}
