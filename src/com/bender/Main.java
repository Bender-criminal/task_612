package com.bender;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    private static PrintStream out = System.out;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Ввод N и S + валидация
        out.println("Введите N и S через пробел:");
        byte n = Byte.parseByte(scanner.next());
        int s = scanner.nextInt();

        if (n < 2 || n > 24) {
            out.println("Неверный ввод: 2 <= N <= 24");
            printNoSolution();
        }

        if (s < -1_000_000_000 || s > 1_000_000_000) {
            out.println("Неверный ввод: - 1 000 000 000 <= S <= 1 000 000 000");
            printNoSolution();
        }

        //Ввод чисел + валидация
        out.println("Введите N чисел через пробел:");
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = Integer.parseInt(scanner.next());
            if (numbers[i] < 0 || numbers[i] > 50_000_000) {
                out.println("Неверный ввод: 0 <= X[i] <= 50 000 000");
                printNoSolution();
            }
        }

        //проверяем простым переборм всех возможных вариантов
        for (int i = 0; i < Math.pow(2, n - 1); i++) {
            boolean[] variant = getVariant(n - 1, i);
            int sum = summator(numbers, variant);
            if (sum == s) {
                printResult(numbers, variant, s);
            }
        }

        printNoSolution();
    }


    /**
     * сумматор
     *
     * @param numbers    - числа для суммирования
     * @param operations - операции в виде булинов
     * @return сумма
     */
    private static int summator(int[] numbers, boolean[] operations) {
        int result = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            if (operations[i - 1]) {
                result += numbers[i];
            } else {
                result -= numbers[i];
            }
        }

        return result;
    }

    /**
     * метод получения варианта операций
     *
     * @param size - длина массива(количество операций)
     * @param n    - номер варианта
     * @return вариант массива операций
     */
    private static boolean[] getVariant(int size, int n) {
        //дополняем стоку нулями до нужной точности
        String variantStr = String.format("%0" + size + "d", Integer.valueOf(toBinary(n)));

        boolean[] result = new boolean[size];
        for (int i = 0; i < variantStr.length(); i++) {
            result[i] = variantStr.charAt(i) == '1';
        }
        return result;
    }

    /**
     * преобразователь десятичного числа в двоичное(строковое отображение)
     *
     * @param n - десятичное число
     * @return двоичное число в виде строки
     */
    public static String toBinary(int n) {
        if (n <= 1) {
            return String.valueOf(n);
        }
        return toBinary(n / 2) + (n % 2);
    }

    /**
     * метод печатает, что нет решения и стопорит прогу
     */
    private static void printNoSolution() {
        out.println("No solution");
        System.exit(0);
    }

    /**
     * метод печатает результат
     *
     * @param numbers    - числа для суммирования
     * @param operations - операции в виде булинов
     * @param s          - число S из условия
     */
    private static void printResult(int[] numbers, boolean[] operations, int s) {
        String result = String.valueOf(numbers[0]);
        for (int i = 1; i < numbers.length; i++) {
            String operationStr = operations[i - 1] ? " + " : " - ";
            result += operationStr + numbers[i];
        }
        result += " = " + s;

        out.println(result);
        System.exit(0);
    }
}
