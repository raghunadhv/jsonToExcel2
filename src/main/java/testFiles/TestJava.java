package testFiles;

public class TestJava {
    private void testMethod() {
        int a=10;
        int b=20;
        MyData myData=new MyData();
        if (a>b) {
            myData.setResult("a is big");
            if (a==1) {
                System.out.println("a==1");
                int i=1;
                while ( i <= 10) {
                    System.out.println("inside loop");
                            if (i==2) {
                                System.out.println("if inside while");
                            } else {
                                System.out.println("else inside while");

                            }

                }
            }else {
                System.out.println("a!=1");
            }
        }else if (b>a) {
            myData.setResult("b is big");
            if (b==1) {
                System.out.println("b==1");
            }
        } else {
            myData.setResult("both are same");
        }
    }
}
