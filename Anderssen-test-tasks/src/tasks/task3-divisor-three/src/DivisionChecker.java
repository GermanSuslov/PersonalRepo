import java.util.ArrayList;

public class DivisionChecker {
    private final Integer divisor;

    public DivisionChecker(Integer divisor) {
        this.divisor = divisor;
    }

    public void printDivisibleNumbers(String input) {
        try {
            String[] inputArray = input.split(" ");
            ArrayList<Integer> outputList = new ArrayList<>();
            for (int i = 0; i < inputArray.length; i++) {
                Integer number = Integer.parseInt(inputArray[i]);
                if(number % divisor == 0) {
                    outputList.add(number);
                }
            }
            System.out.println(outputList);
        } catch (NumberFormatException e) {
            System.out.println("Количество пробелов между числами не должно быть больше одного");
        }
    }
}
