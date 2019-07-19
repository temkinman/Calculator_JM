package com.company;

public class RomanConverter {
    int numbers[]  = {1, 4, 5, 9, 10, 50, 100, 500, 1000 };
    String letters[]  = {"I", "IV", "V", "IX", "X", "L", "C", "D", "M"};
    Boolean negative = false;

    public int convertRomanToInt(String romanNumeral)
    {
        negative = false;
        int integerValue = 0;
        int last = 4000;
        int x = 0;

        if( romanNumeral.charAt(0) == '-') {
            negative = true;
        }

        for (int i = 0; i < romanNumeral.length(); i++)
        {
            char ch;
            if(negative & x <= 0) {
                ch = romanNumeral.charAt(++i);
                x++;
            }
            else ch = romanNumeral.charAt(i);

            int number = letterToNumber(ch);

            if ( number == -1){
                throw new NumberFormatException("Invalid format");
            }
            if (last < number){
                integerValue -= last;
                integerValue += number - last;
            }
            else{
                integerValue += number;
                last = number;
            }
        }
        integerValue = isNegative(integerValue);

        return integerValue;
    }

    private int isNegative(int number){
        if(negative) return 0-number;
        else return number;
    }

    private int letterToNumber(char letter)
    {
        switch (letter) {
            case 'I':  return 1;
            case 'V':  return 5;
            case 'X':  return 10;
            case 'L':  return 50;
            case 'C':  return 100;
            case 'D':  return 500;
            case 'M':  return 1000;
            default:   return -1;
        }
    }

    public String convertIntegerToRoman(Integer number){
        String res = "";
        Integer digit = Math.abs(number);
        int i = numbers.length - 1,
            j = letters.length - 1;

        if (number < 0) negative = true;

        while (digit > 0){
            while (digit >= numbers[i]){
                digit -= numbers[i];
                res += letters[j];
            }
            j--; i--;
        }

        if (negative) return  "-" + res;
        else return res;
    }
}
