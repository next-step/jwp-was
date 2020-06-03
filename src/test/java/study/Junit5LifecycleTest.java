package study;

import org.junit.jupiter.api.*;

public class Junit5LifecycleTest {

    @BeforeAll
    static void setup(){
        System.out.println("\n=========== @BeforeAll  ===========\n");
    }

    @BeforeEach
    void setupThis(){
        System.out.println("\n=========== @BeforeEach ===========");
    }

    @Test
    void test1() {
        System.out.println("              Test1              ");
    }

    @Test
    void test2() {
        System.out.println("              Test2              ");
    }

    @AfterEach
    void tearThis(){
        System.out.println("=========== @AfterEach  ===========\n");
    }

    @AfterAll
    static void tear(){
        System.out.println("\n=========== @AfterAll   ===========\n");
    }

}
