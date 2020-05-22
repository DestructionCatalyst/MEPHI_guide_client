package com.example.mephiguide.ui.telephony;

public class Contact {
    private String name;
    private String number;
    private boolean annotation;

    Contact(String name, String number, boolean annotation){
        this.setName(name);
        this.setNumber(number);
        this.setAnnotation(annotation);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isAnnotation() {
        return annotation;
    }

    public void setAnnotation(boolean annotation) {
        this.annotation = annotation;
    }
}
