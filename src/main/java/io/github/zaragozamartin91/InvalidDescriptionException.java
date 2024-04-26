package io.github.zaragozamartin91;

public class InvalidDescriptionException extends IllegalArgumentException {

    private String description;

    public String getDescription() {
        return description;
    }

    public InvalidDescriptionException(String string, Exception e, String description) {
        super(string, e);
        this.description = description;
    }

}
