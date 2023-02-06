import java.util.Scanner;

public class MoreThanSeven {

    public static void main(String[] args) {
        System.out.println("Введите число");
        String input;
        Scanner scanner = new Scanner(System.in);
            try {
                input = scanner.nextLine();
                if (Integer.parseInt(input) > 7) {
                    System.out.println("Привет");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число без пробелов и других символов");
            }
        scanner.close();
    }
}