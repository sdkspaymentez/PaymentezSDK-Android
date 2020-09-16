package ar.com.fennoma.paymentezsdk.models;

import android.text.TextUtils;

public enum ErrorMessage {

    INVALID_SESSION("invalid_session"),
    INVALID_DATA("Los datos ingresados son incorrectos."),
    INVALID_LOGIN("El usuario o la clave son incorrectos."),
    SINISTER_CREATE_INVALID_ZIPCODE("Debe indicar un código postal valido."),
    UNEXPECTED("unexpected");

    public static final String GENERIC_ERROR_IDENTIFIER = "generic error";

    ErrorMessage(String errorCode) {
        this.errorCode = errorCode;
    }

    private String errorCode;
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static ErrorMessage getError(String errorCode) {
        if (errorCode == null) {
            return null;
        }
        for (ErrorMessage key : values()) {
            if (errorCode.equalsIgnoreCase(key.getErrorCode())) {
                return key;
            }
        }
        return UNEXPECTED;
    }

    public boolean isInvalidSession() {
        return !TextUtils.isEmpty(errorCode) && errorCode.equals(INVALID_SESSION.errorCode);
    }
}