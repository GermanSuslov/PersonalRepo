import java.util.Scanner;

public class Brackets {

    public static void main(String[] args) {
        System.out.println("Введите скобочную последовательность");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] inputArray = input.split("");
        BracketsValidator validator = new BracketsValidator(inputArray);
        if (!validator.bracketsValidated()) {
            System.out.println("Массив должен содержать только скобки");
        } else {
            validator.bracketsSubsequenceValidator();
        }
        scanner.close();
    }

}


