import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static int check;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пример: ");
        String exam = scanner.nextLine();
        System.out.println(calc(exam));
    }

    public static String calc(String input){
        regExAr(input);
        if(check == 1){
            return ar(input);
        }
        return ri(input);
    }

    public static boolean regExAr(String str){
        Pattern pattern = Pattern.compile("^\\d\\s?[+-/*]\\s?\\d$");
        Matcher matcher = pattern.matcher(str);
        boolean result = matcher.matches();
        if(result){
            check = 1;
            return true;
        } else if(!regExRi(str)) {
            throw new IllegalArgumentException();
        }
        return true;
    }

    public static boolean regExRi(String str){
        Pattern pattern = Pattern.compile("^(I{1,3}|IV|V|VI{1,3}|IX)\\s[+*-/]\\s(I{1,3}|IV|V|VI{1,3}|IX)$");
        Matcher matcher = pattern.matcher(str);
        boolean result = matcher.matches();
        if(!result){
            throw new IllegalArgumentException();
        }
        check = 2;
        return true;
    }

    public static String ar(String str){
        String[] expression = str.split("\\s+");
        char symbol = expression[1].charAt(0);
        int one = Integer.parseInt(expression[0]);
        int two = Integer.parseInt(expression[2]);
        int time = result(one,two,symbol);
        return String.valueOf(time);
    }

    public static String ri(String str){
        Pattern pattern = Pattern.compile("[IVX]+");
        Matcher matcher = pattern.matcher(str);
        String[] symbol = new String[2];
        int i = 0;
        while(matcher.find()){
            symbol[i] = matcher.group();
            i++;
        }
        int one = calculator(symbol[0]);
        int two = calculator(symbol[1]);
        char ch = str.replaceAll("[IVX]+\\s","").charAt(0);
        int time = result(one,two,ch);
        if(time <= 0){
            throw new IllegalArgumentException("Результат отрицательный,что неудопустимо в римской системе исчисления");
        }
        return reverse(time);
    }

    public static int calculator(String str){
        int result = 0;
        if(str.startsWith("V")){
            result = 5 + str.length() - 1;
        }
        else if(str.endsWith("V")){
            result = 4;
        }
        else if(str.endsWith("X")){
            result = 9;
        }
        else{
            result = str.length();
        }
        return result;
    }

    public static int result(int one, int two,char symbol){
        int time = 0;
        switch (symbol){
            case '+' -> time = one + two;
            case '-' -> time = one - two;
            case '/' -> time =(int) one / two;
            case '*' -> time = one * two;
        }
        return time;
    }

    public static String reverse(int result){
        StringBuilder sb = new StringBuilder();
        int[] values = {50,40,10,9,5,4,1};
        String[] numerals = {"L","XL","X","IX","V","IV","I"};
        for(int i = 0; i < values.length; i++){
            while(result >= values[i]){
                result -= values[i];
                sb.append(numerals[i]);
            }
        }
        return sb.toString();
    }
}
