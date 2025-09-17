package com.info.demo_batch;

import org.springframework.stereotype.Component;

// User.java
@Component
public class User {
    private String name;
    private int age;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", age=" + age + "]";
    }

    
    // Default Constructor, Getters, Setters, toString (required by ItemReader)
}