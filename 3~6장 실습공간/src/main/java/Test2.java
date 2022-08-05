public class Test2 extends Test{
    @Override
    public synchronized void doSomething() {
        System.out.println("b");
        super.doSomething();
    }


//    public static void main(String[] args) {
//        Test2 test2 = new Test2();
//        test2.doSomething();
//    }
}
