/*
developed by Chegodar Artem
* */
package com.company;

import com.sun.xml.internal.ws.util.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ValidateData {
    private String input, operation;
    private boolean arabNumber, romamNumber, validData;
    private Integer numberA, numberB, result;
    private HashMap<String, String> operations = new HashMap<String, String>();

    ValidateData(String input){
        this.input = input.toUpperCase();
    }

    //проверяем что ввёл пользователь арабские или римские и проверяем корректен ли ввод
    public boolean validateInput(){
        String numeral = "(^\\-?[0-9]+)" +
                         "(([ ]+)?[\\+\\-\\*\\/]{1})" +
                         "(([ ]+)?\\-?[0-9 ]+)";

        String roman = "(^\\-?[IVXLCDM]+)" +
                       "(([ ]+)?[\\+\\-\\*\\/]{1})" +
                       "(([ ]+)?\\-?[IVXLCDM ]+)";

        if(input.matches(roman)){
            romamNumber = true;
            validData = true;
            return true;
        }
        else if(input.matches(numeral)){
            arabNumber = true;
            validData = true;
            return true;
        }
        else {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Input data not valid");
                System.exit(1);
            }
            return false;
        }
    }

    //узнаем какую операцию надо выполнить над числами
    public void operationName() {
        String plus = "",
               minus = "",
               multiply = "",
               divide = "",
               kindofNumber = "";
        operations.put("plus","\\+");
        operations.put("minus","-");
        operations.put("multiply","\\*");
        operations.put("divide","/");

        if(validData){
            if(arabNumber) kindofNumber = "\\d+";
            if(romamNumber) kindofNumber = "[IVXLCDM]*";

            plus = "(\\-?" + kindofNumber + "([ ]+)?)" +
                   "(\\+)" +
                   "(([ ]+)?\\-?" + kindofNumber + ")";

            minus = "(\\-?" + kindofNumber + "([ ]+)?)" +
                    "(\\-)" +
                    "(([ ]+)?\\-?" + kindofNumber + ")";

            multiply = "(\\-?" + kindofNumber + "([ ]+)?)" +
                       "(\\*)" +
                       "(([ ]+)?\\-?" + kindofNumber + ")";

            divide = "(\\-?" + kindofNumber + "([ ]+)?)" +
                     "(/)" +
                     "(([ ]+)?\\-?" + kindofNumber + ")";
        }

        if (validData) {
            if (input.matches(plus)) operation = "plus";
            if (input.matches(minus)) operation = "minus";
            if (input.matches(multiply)) operation = "multiply";
            if (input.matches(divide)) operation = "divide";
        }
    }

    private ArrayList<String> operwithMinus(){
        String numStrA = "", numStrB = "";
        ArrayList<String> nums = new ArrayList<>();
        int count = input.split("-",-1).length-1;

        if(operation == "minus" && (count == 2 || count == 3)){
            if( input.startsWith("-")){
                int secondminus = input.indexOf("-",2);
                numStrA = input.substring(0, secondminus).trim();
                numStrB = input.substring(secondminus + 1).trim();

            }
            else{
                int firstminus = input.indexOf("-",1);
                numStrA = input.substring(0, firstminus).trim();
                numStrB = input.substring(firstminus + 1).trim();
            }
        }
        nums.add(numStrA);
        nums.add(numStrB);

        return nums;
    }

    public void extNumbers(){
        if(validData){
            String[] strArray = input.split(operations.get(operation));
            int count = input.split("-",-1).length-1;

            if (arabNumber) {

                if(operation == "minus" && (count == 2 || count == 3)){
                    numberA = Integer.parseInt(operwithMinus().get(0));
                    numberB = Integer.parseInt(operwithMinus().get(1));
                }
                else {
                    numberA = Integer.parseInt(strArray[0].trim());
                    numberB = Integer.parseInt(strArray[1].trim());
                }
            }
            else {
                RomanConverter roman = new RomanConverter();
                if(operation == "minus" && (count == 2 || count == 3)){
                    String numstrA = "", numstrB = "";
                    numstrA = operwithMinus().get(0);
                    numstrB = operwithMinus().get(1);
                    numberA = roman.convertRomanToInt(numstrA);
                    numberB = roman.convertRomanToInt(numstrB);
                }
                else {
                    numberA = roman.convertRomanToInt(strArray[0].trim());
                    numberB = roman.convertRomanToInt(strArray[1].trim());
                }
            }
        }
    }

    public Integer calculate(Integer numberA, Integer numberB, String operation){
        if(operation != null && numberA != null && numberB != null) {
            switch (operation){
                case "plus": result = numberA +  numberB;
                                break;
                case "minus": result = numberA -  numberB;
                                break;
                case "multiply": result = numberA *  numberB;
                                break;
                case "divide": {
                    try {
                        result = numberA /  numberB;
                    }
                    catch (ArithmeticException e) {
                        System.out.println("Divide by Zero");
                        System.exit(1);
                    }
                }
                    break;
            }
        }
        return result;
    }

    public String start(){
        RomanConverter roman = new RomanConverter();
        if (validateInput()){
            operationName();
            extNumbers();
        }
        if (calculate(numberA,numberB,operation) != null){
            if (arabNumber) return result.toString();
            if(romamNumber) return roman.convertIntegerToRoman(result);
        }
        return null;
    }
}
