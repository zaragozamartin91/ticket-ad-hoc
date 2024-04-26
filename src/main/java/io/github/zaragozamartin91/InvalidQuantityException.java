package io.github.zaragozamartin91;

public class InvalidQuantityException extends IllegalArgumentException {

    private String quantityInput;

    public String getQuantityInput() {
        return quantityInput;
    }

    public InvalidQuantityException(String message, Throwable cause, String quantityInput) {
        super(message, cause);
        this.quantityInput = quantityInput;
    }

}
