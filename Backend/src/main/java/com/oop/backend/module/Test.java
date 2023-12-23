package com.oop.backend.module;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        File file = new File("\\New");
        boolean bool = file.mkdir();
        System.out.print(bool);
    }
}
