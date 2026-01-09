import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Введите первое число:");
        int number1 = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число:");
        int number2 = new Scanner(System.in).nextInt();
        int sum = number1 + number2;
        int diff = number1 - number2;
        int multiply = number1 * number2;
        double quotient = (double) number1 / number2;
        System.out.println("Сумма чисел равна: " + sum);
        System.out.println("Разность чисел равна: " + diff);
        System.out.println("Поизведение чисел равно: " + multiply);
        System.out.println("Частное чисел равно: " + quotient);
    }
}