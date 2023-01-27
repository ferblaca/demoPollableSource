package com.example.demo.pollable;

public class Foo {

    private String message;

    public Foo() {
    }

    public Foo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "message='" + message + '\'' +
                '}';
    }
}