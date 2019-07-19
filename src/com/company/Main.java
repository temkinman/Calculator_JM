/* developed by Chegodar Artem */

package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter numbers with operation (example: a+b)");

        try {
            String input = reader.readLine();
            ValidateData data = new ValidateData(input);
            reader.close();
            System.out.println(data.start());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
