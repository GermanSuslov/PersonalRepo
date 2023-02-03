import java.util.ArrayList;
import java.util.List;

public class BracketsValidator {
    ArrayList<String> bracketsList;

    public BracketsValidator(String[] inputArray) {
        this.bracketsList = new ArrayList<>(List.of(inputArray));
    }

    public boolean bracketsValidated() {
        boolean validated = true;
        String[] brackets = {"(", ")", "{", "}", "[", "]"};
        ArrayList<String> bracketsSymbolsList = new ArrayList<>(List.of(brackets));
        for (int i = 0; i < bracketsList.size(); i++) {
            if (!bracketsSymbolsList.contains(bracketsList.get(i))) {
                validated = false;
                break;
            }
        }
        return validated;
    }

    public void bracketsSubsequenceValidator() {
        boolean subsequenceCorrect = true;
        subsequenceCorrect = squareBracketValidation(subsequenceCorrect);
        subsequenceCorrect = parenthesesValidation(subsequenceCorrect);
        subsequenceCorrect = bracesValidation(subsequenceCorrect);
        if (subsequenceCorrect) {
            System.out.println("Последовательность скобок правильная");
        }
    }

    private boolean squareBracketValidation(boolean subsequenceCorrect) {
        int squareBracketOpenCount = 0;
        for (int i = 0; i < bracketsList.size(); i++) {
            if (bracketsList.get(i).equals("]") && squareBracketOpenCount == 0) {
                subsequenceCorrect = false;
                int index = i + 1;
                System.out.println("Необходимо добавить открывающую квадратную скобку для " + index + " скобки");
                continue;
            }
            if (bracketsList.get(i).equals("[")) {
                squareBracketOpenCount++;
                continue;
            }
            if (bracketsList.get(i).equals("]")) {
                squareBracketOpenCount--;
            }
        }
        if (squareBracketOpenCount != 0) {
            subsequenceCorrect = false;
            if (squareBracketOpenCount < 2) {
                System.out.println("Необходимо закрыть " + squareBracketOpenCount + " квадратную скобку");
            } else {
                System.out.println("Необходимо закрыть " + squareBracketOpenCount + " квадратные скобки");
            }
        }
        return subsequenceCorrect;
    }

    private boolean parenthesesValidation(boolean subsequenceCorrect) {
        int parenthesesOpenCount = 0;
        for (int i = 0; i < bracketsList.size(); i++) {
            if (bracketsList.get(i).equals(")") && parenthesesOpenCount == 0) {
                subsequenceCorrect = false;
                int index = i + 1;
                System.out.println("Необходимо добавить открывающую круглую скобку для " + index + " скобки");
                continue;
            }
            if (bracketsList.get(i).equals("(")) {
                parenthesesOpenCount++;
                continue;
            }
            if (bracketsList.get(i).equals(")")) {
                parenthesesOpenCount--;
            }
        }
        if (parenthesesOpenCount != 0) {
            subsequenceCorrect = false;
            if (parenthesesOpenCount < 2) {
                System.out.println("Необходимо закрыть " + parenthesesOpenCount + " круглую скобку");
            } else {
                System.out.println("Необходимо закрыть " + parenthesesOpenCount + " круглые скобки");
            }
        }
        return subsequenceCorrect;
    }

    private boolean bracesValidation(boolean subsequenceCorrect) {
        int bracesOpenCount = 0;
        for (int i = 0; i < bracketsList.size(); i++) {
            if (bracketsList.get(i).equals("}") && bracesOpenCount == 0) {
                subsequenceCorrect = false;
                int index = i + 1;
                System.out.println("Необходимо добавить открывающую фигурную скобку для " + index + " скобки");
                continue;
            }
            if (bracketsList.get(i).equals("{")) {
                bracesOpenCount++;
                continue;
            }
            if (bracketsList.get(i).equals("}")) {
                bracesOpenCount--;
            }
        }
        if (bracesOpenCount != 0) {
            subsequenceCorrect = false;
            if (bracesOpenCount < 2) {
                System.out.println("Необходимо закрыть " + bracesOpenCount + " фигурную скобку");
            } else {
                System.out.println("Необходимо закрыть " + bracesOpenCount + " фигурные скобки");
            }
        }
        return subsequenceCorrect;
    }

}
