package io.github.zaragozamartin91;

public class InvalidPriceException extends IllegalStateException {

    private String price;

    public String getPrice() {
        return price;
    }

    public InvalidPriceException(String string, IllegalArgumentException e, String price) {
        super(string, e);
        this.price = price;
    }
    
}
