package ru.bobrov.vyacheslav.minijunit.tests;

import ru.bobrov.vyacheslav.minijunit.annotations.Test;

import static java.lang.System.out;

public class TestRun {
    @Test
    public void test1(){
        out.println("Test 1 is ok");
    }

    @Test
    public void test2(){
        out.println("Test 2 is ok");
    }

    @Test
    public void wrongTest1(String arg){
        out.println("Test with args - fail");
    }

    @Test
    public static void wrongTest2(){
        out.println("Test static method - fail");
    }

    @Test
    private void wrongTest3(){
        out.println("Test private method - fail");
    }
}
