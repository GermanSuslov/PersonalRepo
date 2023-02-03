import java.util.ArrayList;
import java.util.Scanner;

public class DivisorThree {

    public static void main(String[] args) {
        System.out.println("Введите массив чисел через пробел");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        input.trim();
        DivisionChecker divisionChecker = new DivisionChecker(3);
        divisionChecker.printDivisibleNumbers(input);
        scanner.close();
    }
}