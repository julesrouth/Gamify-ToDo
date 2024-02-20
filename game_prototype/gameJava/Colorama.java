public class Colorama {
    private static final boolean ENABLE_CLR = true;

    public static void clearScreen() {
        if (ENABLE_CLR) {
            System.out.print("\033c");
        }
    }

    public static void printColoredString(String inputString) {
        for (char character : inputString.toCharArray()) {
            switch (character) {
                case '0':
                    System.out.print("\u001B[32m" + character);
                    break;
                case '1':
                    System.out.print("\u001B[34m" + character);
                    break;
                case '2':
                    System.out.print("\u001B[37m" + character);
                    break;
                default:
                    System.out.print("\u001B[31m" + character);
            }
        }
        // Reset color to default after printing the colored string
        System.out.print("\u001B[0m");
    }

    public static void main(String[] args) {
        System.out.println("Testing Colorama");
        clearScreen();
        printColoredString("012");
        System.out.println();  // Move to the next line after printing the colored string
    }
}
