package com.shopverse.gateway.route;

public final class RouteConstants {

    public static final String AUTH_SERVICE = "AUTH-SERVICE";
    public static final String USER_SERVICE = "USER-SERVICE";
    public static final String PRODUCT_SERVICE = "PRODUCT-SERVICE";
    public static final String ORDER_SERVICE = "ORDER-SERVICE";
    public static final String PAYMENT_SERVICE = "PAYMENT-SERVICE";

    public static final String AUTH_API = "/api/auth/**";
    public static final String USER_API = "/api/users/**";
    public static final String PRODUCT_API = "/api/products/**";
    public static final String ORDER_API = "/api/orders/**";
    public static final String PAYMENT_API = "/api/payments/**";

    private RouteConstants() {
    }
}
