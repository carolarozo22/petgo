package com.peigo.wallet.ms.boilerplate.infraestructure.constants;

public class CodeConstant {

    private CodeConstant(){}

    /**
     * Response codes http requests
     */
    public static final String RESPONSE_CODE_SUCCESS = "200";
    public static final String RESPONSE_CODE_BAD_REQUEST = "400";

    /**
     * Response text http requests
     */
    public static final String RESPONSE_TEXT_SUCCESS = "Success";
    public static final String RESPONSE_TEXT_BAD_REQUEST = "Bad Request";

    /**
     * Response codes for validations
     */
    public static final String VALIDATION_CODE_NOT_EMPTY = "2701";
    public static final String VALIDATION_CODE_NOT_EMAIL_FORMAT = "2702";
    public static final String VALIDATION_CODE_MIN_VALUE = "2703";
    public static final String VALIDATION_CODE_MAX_VALUE = "2704";
    public static final String VALIDATION_CODE_ENUM_NOT_MATCH = "2705";
}
