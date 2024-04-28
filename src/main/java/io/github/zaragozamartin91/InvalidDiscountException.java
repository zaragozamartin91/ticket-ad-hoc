package io.github.zaragozamartin91;

public class InvalidDiscountException extends IllegalStateException {

    private String price;

    public String getPrice() {
        return price;
    }

    public InvalidDiscountException(String string, IllegalArgumentException e, String price) {
        super(string, e);
        this.price = price;
    }
    
}
