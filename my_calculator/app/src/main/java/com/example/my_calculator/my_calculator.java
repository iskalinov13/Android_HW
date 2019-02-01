package com.example.my_calculator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
public class my_calculator {
    public String infixToPostfix(String infixexpr){
        Map<String, Integer> prec = new HashMap<String, Integer>();
        prec.put("*",3);
        prec.put("/",3);
        prec.put("+",2);
        prec.put("-",2);
        prec.put("(",1);
        CharSequence topCharacter;
        CharSequence second;
        CharSequence first;
        Stack<CharSequence> operationStack = new Stack<CharSequence>();
        ArrayList<CharSequence> postfixList = new ArrayList<CharSequence>();
        CharSequence[] characterList = infixexpr.split(" ");


        for (CharSequence character : characterList) {
            if(isNumeric(character)){
                if((character.charAt(0)+"").equals("_")){
                    character = "-"+character.subSequence(1, character.length());
                    postfixList.add(character);
                }
                else{
                    postfixList.add(character);
                }
            }
            else if(character.equals("(")){
                operationStack.push(character);
            }
            else if(character.equals(")")){
                topCharacter = operationStack.pop();
                while(!topCharacter.equals("(")){
                    postfixList.add(topCharacter);
                    topCharacter = operationStack.pop();
                }
            }
            else{

                while(!operationStack.empty()&&(prec.get(operationStack.peek())>=prec.get(character))){
                    postfixList.add(operationStack.pop());
                }
                operationStack.push(character);
            }
        }

        while(!operationStack.isEmpty()){
            postfixList.add(operationStack.pop());
        }
        String result = Arrays.toString(postfixList.toArray());
        return result.substring(1,result.length()-1);


    }
    public static boolean isNumeric(CharSequence str){
        try{
            if((str.charAt(0)+"").equals("_")){
                double d = Double.parseDouble(str.toString().substring(1));
            }
            else{
                double d = Double.parseDouble(str.toString());
            }
        }
        catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }
    public static String postfixEval(String postfixExpr){
        Stack<Double> operandStack = new Stack<Double>();
        CharSequence[] characterList = postfixExpr.split(", ");

        for (CharSequence character : characterList) {
            if(isNumeric(character)){
                operandStack.push(Double.parseDouble(character.toString()));
            }
            else{
                double second_operand = operandStack.pop();
                double first_operand = operandStack.pop();
                double result = doMath(character,first_operand,second_operand);
                operandStack.push(result);
            }
        }
        String result = operandStack.pop().toString();
        System.out.println(result);
        if(result.equals("Infinity")||result.equals("-Infinity")){
            return result;
        }
        else if(isInteger(Double.parseDouble(result))&&!result.equals("Infinity")){
            if(result.contains("E")){
                System.out.println("From here1");
                return new BigDecimal((Double.parseDouble(result))) + "";
            }
            else{
                System.out.println("From here3");
                return ((int)(Double.parseDouble(result)))+"";

            }
        }
        else{
            System.out.println("From here2");
            if(result.contains("E-")){
                String[] array = result.split("E-");
                int multiplyer =Integer.parseInt(array[1]);
                if(multiplyer>10){
                    multiplyer =9;
                }
                System.out.println(multiplyer+"M");
                String x ="0.";
                for(int i= 1;i<multiplyer;i++){
                    x=x+"0";
                }
                for(int i= 0;i<array[0].length();i++){
                    if(!(array[0].charAt(i)+"").equals(".")){
                        x=x+array[0].charAt(i);
                    }
                }

                System.out.println(x+"S");
                System.out.println(result+"HAYOO");
                int indexOf = (result).indexOf("E");
                result = x;
                return result.substring(0,11);
            }
            else{
                BigDecimal a = new BigDecimal(result);
                BigDecimal roundOff = a.setScale(10, BigDecimal.ROUND_HALF_EVEN);
                int index = (roundOff+"").length()-1;
                result=roundOff+"";
                while((result.charAt(index)+"").equals("0")){
                    index--;
                }
                return result.substring(0,index+1);

            }

        }
    }

    public static double doMath(CharSequence operand, double first_operand, double second_operand){
        if(operand.equals("*")){
            return first_operand * second_operand;
        }
        else if(operand.equals("/")){
            return first_operand / second_operand;
        }
        if(operand.equals("+")){
            return first_operand + second_operand;
        }
        else{
            return first_operand - second_operand;
        }
    }

    public static String doSplit(String expression){

        for(int i = 2; i<expression.length();i++){
            if((expression.charAt(i-2)+"").equals("(")&&(expression.charAt(i-1)+"").equals("-")&&(expression.charAt(i)+"").equals("(")){
                expression =expression.substring(0, i-1)  + "-1*" + expression.substring(i);
            }
        }
        for(int i = 2; i<expression.length();i++){
            if(isNumeric(expression.charAt(i)+"")&&(expression.charAt(i-2)+"").equals("(")&&(expression.charAt(i-1)+"").equals("-")){
                expression =expression.substring(0, i-2)+ expression.substring(i-2, i-1) + "_" + expression.substring(i);
            }
        }

        String[] splitted = expression.split("(?=[-+*/()])|(?<=[-+*/()])");

        String result = "";
        for (CharSequence character : splitted){
            result+=character+" ";
        }
        if((result.charAt(0)+"").equals(" ")){
            return  result.substring(1,result.length());
        }
        else{
            return result.substring(0,result.length());
        }

    }

    public static boolean isInteger(double d){
        if(d > Long.MAX_VALUE || d < Long.MIN_VALUE){
            return true;
        } else if((long)d == d){
            return true;
        } else {
            return false;
        }
    }
}
