public class Main {

    public static void main(String[] args) {
        if (args.length >= 2) {
            int a = Integer.parseInt(args[0]);
            int b = Integer.parseInt(args[1]);
            System.out.println("结果：" + (a + b));
        } else {
            System.out.println("请提供两个整数作为参数");
        }
    }
}
