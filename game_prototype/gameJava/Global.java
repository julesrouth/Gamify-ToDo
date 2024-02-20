public class  Global {
    public static final int LINELENGTH = 80;

    public static void clearScreen() {
        System.out.print("\033c");
        System.out.flush();
    }
}