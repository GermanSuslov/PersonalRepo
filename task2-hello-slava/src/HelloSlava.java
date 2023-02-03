import java.util.Scanner;

public class HelloSlava {
    public static void main(String[] args) {
        System.out.println("Введите имя:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("Вячеслав")) {
            System.out.println("Привет, Вячеслав");
        } else {
            System.out.println("Нет такого имени");
        }
        scanner.close();
    }
}