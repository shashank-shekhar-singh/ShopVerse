package com.shopverse.productservice.exception;

import com.shopverse.common.exception.BaseException;
import com.shopverse.common.exception.ErrorCode;

public class ProductNotFoundException extends BaseException {

    public ProductNotFoundException(Long productId) {
        super("Product not found with id: " + productId, ErrorCode.PRODUCT_NOT_FOUND);
    }
}
