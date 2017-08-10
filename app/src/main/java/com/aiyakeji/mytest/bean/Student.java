package com.aiyakeji.mytest.bean;

import java.util.ArrayList;

/**
 * 学生bean
 */

public class Student {

    private String name;

    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

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


    public static ArrayList<Student> getSampleData(int count) {
        ArrayList<Student> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new Student("学生" + i, i));
        }
        return list;
    }
}
