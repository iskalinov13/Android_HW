package com.example.my_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    TextView display;
    my_calculator calculator;
    boolean operation_is_pressed = false;
    boolean digit_is_pressed = false;
    boolean parenthesis_is_opened = true;
    boolean plus_minus_pressed = false;
    boolean plus_minus_pressed_by_button = false;
    boolean dot_is_pressed = false;
    boolean zero_is_pressed = false;
    boolean deleteOnce = false;
    int length_after_dot_is_pressed = 0;
    int last_operation_index = 0;
    int last_operation_index_in_memory = 0;
    int count_of_symbols = 0;
    int length_of_digit = 0;
    int index_of_plus_minus = 0;
    int last_digit_index = 0;
    int index_of_equal = 0;
    String last_digit;
    String answer = "";
    Stack<CharSequence> parenthesisStack = new Stack<CharSequence>();
    Stack<Integer> lastOperationIndexStack = new Stack<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = (TextView)findViewById(R.id.textView);
        calculator = new my_calculator();
    }

    public void digitPressed(View v){
        if(count_of_symbols<100){
            if(display.getText().toString().contains("=")){
                if(!answer.equals("Неверный формат")&&!answer.equals("Недоп.функция")){
                    display.setText(answer);
                }
                else{
                    ClearPressed(v);
                }

            }
            if(dot_is_pressed){
                if(length_after_dot_is_pressed<10) {
                    Button target = (Button) v;
                    digit_is_pressed = true;
                    operation_is_pressed = false;
                    String text = display.getText().toString()  + target.getText().toString();
                    System.out.println(length_after_dot_is_pressed+"HERE");
                    length_after_dot_is_pressed++;
                    display.setText(text);
                    last_digit_index = display.getText().length();

                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Максимальное количество цифр после запятой: (10)", Toast.LENGTH_LONG).show();
                }
            }

            else if(length_of_digit<15&&dot_is_pressed==false){
                Button target  = (Button) v;
                if(length_of_digit==0 && target.getText().toString().equals("0")){
                    if(checkLastChar().equals(")")){
                        last_operation_index = display.getText().toString().length();
                        String text = display.getText().toString() +"×" + target.getText().toString();
                        display.setText(text);
                        zero_is_pressed = true;
                    }
                    else{
                        last_operation_index = display.getText().toString().length()-1; //MYna zher
                        String text = display.getText().toString() + target.getText().toString();
                        display.setText(text);
                        zero_is_pressed = true;
                    }
                }
                if(!target.getText().toString().equals("0")&&zero_is_pressed){//OSY ZHERDI OZGERRTIIM
                    zero_is_pressed = false;
                    String text = display.getText().toString().substring(0,display.getText().length()-1);
                    display.setText(text);

                }
                if(zero_is_pressed==false){
                    if(checkLastChar().equals(")")){
                        last_operation_index = display.getText().toString().length();
                        String text = display.getText().toString() +"×" + target.getText().toString();
                        display.setText(text);
                        if(target.getText().toString().equals("0")){zero_is_pressed = true;}
                    }
                    else{
                        String text = display.getText().toString() + target.getText().toString();
                        display.setText(text);
                    }
                }
                length_of_digit++;
                last_digit_index = display.getText().length();
                digit_is_pressed = true;
                operation_is_pressed = false;
            }
            else{
                Toast.makeText(getApplicationContext(),
                        "Превышено максимальное число цифр (16)", Toast.LENGTH_LONG).show();
            }
            controlTextView();
            count_of_symbols=display.getText().length();
            deleteOnce = false;
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Максимальное число знаков (100)", Toast.LENGTH_LONG).show();
        }
        controlColor();
    }

    public void MultiplicationPressed(View v){
        if(count_of_symbols<100){
            if(display.getText().toString().contains("=")){
                if(!answer.equals("Неверный формат")&&!answer.equals("Недоп.функция")){
                    //ClearPressed(v);
                    display.setText(answer);
                }
                else{
                    ClearPressed(v);
                }
            }
            else{
                length_of_digit = 0;
                dot_is_pressed = false;
                zero_is_pressed = false;
                length_after_dot_is_pressed = 0;

            }

            Button target  = (Button) v;
            if(plus_minus_pressed&&digit_is_pressed&&plus_minus_pressed_by_button){
                parenthesisStack.pop();
                display.setText(display.getText().toString()+")" + target.getText().toString());
                plus_minus_pressed = false;
                digit_is_pressed = false;
                operation_is_pressed = true;
                plus_minus_pressed_by_button = false;
            }
            else if(plus_minus_pressed&&!digit_is_pressed&&!operation_is_pressed){
                String text = display.getText().toString().substring(0,display.getText().toString().length()-1);
                display.setText(text);
                plus_minus_pressed = false;
                operation_is_pressed = false;
            }
            if(!display.getText().equals("")&&digit_is_pressed&&!operation_is_pressed){
                String text = display.getText().toString() + target.getText().toString();
                display.setText(text);
                last_operation_index = display.getText().toString().length()-1;
                operation_is_pressed = true;
                digit_is_pressed = false;
            }
            else if(!display.getText().equals("")&&!digit_is_pressed&&operation_is_pressed){
                String text = display.getText().toString().substring(0,display.getText().toString().length()-1) + target.getText().toString();
                display.setText(text);
                last_operation_index = display.getText().toString().length()-1;
                operation_is_pressed = true;
                digit_is_pressed = false;
            }
            last_operation_index = display.getText().length()-1;
            lastOperationIndexStack.push(last_operation_index);
            controlTextView();
            deleteOnce = false;
            count_of_symbols = display.getText().length();
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Максимальное число знаков (100)", Toast.LENGTH_LONG).show();
        }
        controlColor();

    }
    public void DivisionPressed(View v){
        if(count_of_symbols<100){
            if(display.getText().toString().contains("=")){
                if(!answer.equals("Неверный формат")&&!answer.equals("Недоп.функция")){
                    display.setText(answer);
                }
                else{
                    ClearPressed(v);
                }
            }
            else{
                dot_is_pressed = false;;
                zero_is_pressed = false;
                length_of_digit = 0;
                length_after_dot_is_pressed = 0;
            }
            Button target  = (Button) v;

            if(plus_minus_pressed&&digit_is_pressed&&plus_minus_pressed_by_button ){
                parenthesisStack.pop();
                display.setText(display.getText().toString()+")" + target.getText().toString());
                plus_minus_pressed = false;
                digit_is_pressed = false;
                operation_is_pressed = true;
                plus_minus_pressed_by_button = false;
            }
            else if(plus_minus_pressed&&!digit_is_pressed&&!operation_is_pressed){
                String text = display.getText().toString().substring(0,display.getText().toString().length()-1);
                display.setText(text);
                plus_minus_pressed = false;
                operation_is_pressed = false;
            }
            if(!display.getText().equals("")&&digit_is_pressed&&!operation_is_pressed){
                String text = display.getText().toString() + target.getText().toString();
                display.setText(text);
                last_operation_index = display.getText().toString().length()-1;
                operation_is_pressed = true;
                digit_is_pressed = false;
            }
            else if(!display.getText().equals("")&&!digit_is_pressed&&operation_is_pressed){
                String text = display.getText().toString().substring(0,display.getText().toString().length()-1) + target.getText().toString();
                display.setText(text);
                last_operation_index = display.getText().toString().length()-1;
                operation_is_pressed = true;
                digit_is_pressed = false;
            }
            last_operation_index = display.getText().length()-1;
            lastOperationIndexStack.push(last_operation_index);
            controlTextView();
            deleteOnce = false;
            count_of_symbols = display.getText().length();
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Максимальное число знаков (100)", Toast.LENGTH_LONG).show();
        }
        controlColor();

    }
    public void AdditionPressed(View v){
        if(count_of_symbols<100){
            if(display.getText().toString().contains("=")){
                if(!answer.equals("Неверный формат")&&!answer.equals("Недоп.функция")){
                    display.setText(answer);
                }
                else{
                    ClearPressed(v);
                }

            }
            else{
                dot_is_pressed = false;
                zero_is_pressed = false;
                length_of_digit = 0;
                length_after_dot_is_pressed = 0;
            }
            Button target  = (Button) v;

            if(plus_minus_pressed&&digit_is_pressed&&plus_minus_pressed_by_button ){
                parenthesisStack.pop();
                display.setText(display.getText().toString()+")" + target.getText().toString());
                plus_minus_pressed = false;
                digit_is_pressed = false;
                operation_is_pressed = true;
                plus_minus_pressed_by_button = false;
            }
            else if(plus_minus_pressed&&!digit_is_pressed&&!operation_is_pressed){
                String text = display.getText().toString().substring(0,display.getText().toString().length()-1);
                display.setText(text);
                plus_minus_pressed = false;
                operation_is_pressed = false;
            }
            if(!display.getText().equals("")&&digit_is_pressed&&!operation_is_pressed){
                String text = display.getText().toString() + target.getText().toString();
                display.setText(text);
                last_operation_index = display.getText().toString().length()-1;
                operation_is_pressed = true;
                digit_is_pressed = false;
            }
            else if(!display.getText().equals("")&&!digit_is_pressed&&operation_is_pressed){
                String text = display.getText().toString().substring(0,display.getText().toString().length()-1) + target.getText().toString();
                display.setText(text);
                last_operation_index = display.getText().toString().length()-1;
                operation_is_pressed = true;
                digit_is_pressed = false;
            }
            last_operation_index = display.getText().length()-1;
            lastOperationIndexStack.push(last_operation_index);
            controlTextView();
            deleteOnce = false;
            count_of_symbols = display.getText().length();
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Максимальное число знаков (100)", Toast.LENGTH_LONG).show();
        }
        controlColor();
    }

    public void SubtractionPressed(View v){
        if(count_of_symbols<100){
            if(display.getText().toString().contains("=")){
                if(!answer.equals("Неверный формат")&&!answer.equals("Недоп.функция")){
                    display.setText(answer);
                }
                else{
                    ClearPressed(v);
                }

            }
            else{
                dot_is_pressed = false;
                zero_is_pressed = false;
                length_of_digit = 0;
                length_after_dot_is_pressed = 0;
            }
            Button target  = (Button) v;

            if(plus_minus_pressed&&digit_is_pressed&&plus_minus_pressed_by_button ){
                parenthesisStack.pop();
                display.setText(display.getText().toString()+")" + target.getText().toString());
                plus_minus_pressed = false;
                digit_is_pressed = false;
                operation_is_pressed = true;
                plus_minus_pressed_by_button = false;
            }

            if(!display.getText().equals("")&&digit_is_pressed&&!operation_is_pressed){
                String text = display.getText().toString() + target.getText().toString();
                display.setText(text);
                last_operation_index = display.getText().toString().length()-1;
                operation_is_pressed = true;
                digit_is_pressed = false;
            }
            else if(!display.getText().equals("")&&!digit_is_pressed&&operation_is_pressed){
                String text = display.getText().toString().substring(0,display.getText().toString().length()-1) + target.getText().toString();
                display.setText(text);
                last_operation_index = display.getText().toString().length()-1;
                operation_is_pressed = true;
                digit_is_pressed = false;
            }
            else if(checkLastChar().equals("(")){
                String text = display.getText().toString() + target.getText().toString();
                display.setText(text);
                index_of_plus_minus = display.getText().toString().length()-2;
                last_operation_index = display.getText().toString().length()-1;
                digit_is_pressed = false;
                operation_is_pressed = false;
                plus_minus_pressed =true;
            }
            last_operation_index = display.getText().length()-1;
            lastOperationIndexStack.push(last_operation_index);
            deleteOnce = false;
            controlTextView();
            count_of_symbols = display.getText().length();
        }else{
            Toast.makeText(getApplicationContext(),
                    "Максимальное число знаков (100)", Toast.LENGTH_LONG).show();
        }
        controlColor();

    }

    public void EqualPressed(View v){
        if(display.getText().toString().contains("=")){
            if(!answer.equals("Неверный формат")&&!answer.equals("Недоп.функция")){
                display.setText(answer);
            }else{
                ClearPressed(v);
            }
        }
        String text = display.getText().toString();

        if(!text.equals("")){
            if(!parenthesisStack.empty()){
                do{
                    text+=")";
                    parenthesisStack.pop();
                    System.out.println("Closed!!");
                }
                while(parenthesisStack.size()>0);
            }
            String result = text;
            System.out.println(text);
            for(int i = 0;i<text.length();i++){
                if((text.charAt(i)+"").equals("×")){
                    text = text.substring(0,i) + "*" +text.substring(i+1);
                }
                if((text.charAt(i)+"").equals("÷")){
                    text = text.substring(0,i) + "/" +text.substring(i+1);
                }
            }
            ClearPressed(v);
            try{

                text = calculator.postfixEval(calculator.infixToPostfix(calculator.doSplit(text)));
                answer = text;
                if((text.charAt(0)+"").equals("-")&&!text.equals("-Infinity")){
                    display.setText(Html.fromHtml(result+"<font color = #16C368><br>"+"=("+text+")"+"</font>"));
                    answer = "("+ answer +")";
                    index_of_equal = text.length();
                    digit_is_pressed = true;
                }
                else if(text.equals("Infinity")||text.equals("-Infinity")){
                    answer = "Недоп.функция";
                    index_of_equal = text.length();
                    display.setText(Html.fromHtml(result+"<font color = #16C368><br>"+"=Недоп.функция"+"</font>"));

                }
                else{
                    index_of_equal = text.length();
                    display.setText(Html.fromHtml(result+"<font color = #16C368><br>"+"="+text+"</font>"));
                    if(text.contains(".")){
                        dot_is_pressed = true;
                        length_after_dot_is_pressed = text.substring(text.indexOf(".")+1).length();
                    }
                    else{
                        digit_is_pressed = true;
                        length_of_digit = text.length();
                    }

                }
            }
            catch(Exception ex){
                index_of_equal = text.length();
                answer = "Неверный формат";
                display.setText(Html.fromHtml(result+"<font color = #16C368><br>"+"=Неверный формат"+"</font>"));
            }
        }
        controlTextView();
    }
    public void DeletePressed(View v){
        if(display.getText().toString().contains("=")){
            if(!answer.equals("Неверный формат")&&!answer.equals("Недоп.функция")){
                display.setText(answer);
            }
            else{
                ClearPressed(v);
            }
        }
        else{
            parenthesis_is_opened = false;
            plus_minus_pressed = false;
            plus_minus_pressed_by_button = false;
            index_of_plus_minus = 0;
        }


        if(!display.getText().equals("")&&display.getText().toString().length()!=0){
            String deletedChar = display.getText().charAt(display.getText().toString().length()-1)+"";
            display.setText(display.getText().toString().substring(0,display.getText().length()-1));
            if(!display.getText().equals("")){
                String currentChar = display.getText().charAt(display.getText().toString().length()-1)+"";
                String text = display.getText().toString();
                if(deletedChar.equals(")")){
                    parenthesisStack.push("(");
                }
                else if(deletedChar.equals("(")){
                    parenthesisStack.pop();
                }
                for(int i = 0;i<text.length();i++){
                    if((text.charAt(i)+"").equals("×")){
                        text = text.substring(0,i) + "*" +text.substring(i+1);
                    }
                    if((text.charAt(i)+"").equals("÷")){
                        text = text.substring(0,i) + "/" +text.substring(i+1);
                    }
                }
                for(int i = 0;i<text.length();i++){
                    if("-+*/()".contains(text.charAt(i)+"")){
                        last_operation_index = i;
                    }
                }
                System.out.println(text);
                String[] splitted2 = text.split("([-+*/()])");
                if(splitted2.length>0){
                    int splitted_size = splitted2.length;
                    last_digit = splitted2[splitted_size-1];
                    if(splitted_size==1&&"0123456789.".contains(currentChar)){
                        last_operation_index = 0;
                    }
                }

                if("0123456789.".contains(currentChar)){
                    if(last_digit.contains(".")){
                        if(!last_digit.substring(last_digit.indexOf(".")+1).equals("")){
                            String[] splitted_by_dot = last_digit.split("[.]");
                            length_after_dot_is_pressed = splitted_by_dot[1].length();
                            System.out.println(splitted_by_dot[0]);
                            System.out.println(splitted_by_dot[1]);
                            dot_is_pressed = true;
                            digit_is_pressed = true;
                            operation_is_pressed = false;
                            System.out.println("Delete1");
                        }
                        else{
                            dot_is_pressed = true;
                            digit_is_pressed = true;
                            operation_is_pressed = false;
                            length_after_dot_is_pressed = 0;
                            System.out.println("Delete2");
                        }

                    }
                    else{
                        if(last_digit.length()==1&&last_digit.equals("0")){
                            zero_is_pressed = true;
                            length_of_digit = 1;
                            digit_is_pressed = true;
                            dot_is_pressed = false;
                            System.out.println("Delete3");
                        }
                        else{
                            dot_is_pressed = false;
                            length_of_digit = last_digit.length();
                            digit_is_pressed = true;
                            System.out.println("Delete4");
                        }

                    }
                }
                else{
                    if(currentChar.equals("(")){
                        operation_is_pressed = false;
                        digit_is_pressed = false;
                        length_of_digit = 0;
                        zero_is_pressed = false;
                        dot_is_pressed = false;
                    }
                    else if(currentChar.equals(")")) {
                        digit_is_pressed = true;
                        operation_is_pressed = false;
                        length_of_digit = 0;
                        zero_is_pressed = false;
                        dot_is_pressed = false;
                    }
                    else {
                        operation_is_pressed = true;
                        digit_is_pressed = false;
                        length_of_digit = 0;
                        zero_is_pressed = false;
                        dot_is_pressed = false;
                    }
                }
            }
            else{
                ClearPressed(v);
            }
        }
        else{
                ClearPressed(v);
        }
        count_of_symbols = display.getText().length();
        controlColor();
    }
    public void ClearPressed(View v){

        display.setText("");
        digit_is_pressed = false;
        operation_is_pressed = false;
        parenthesis_is_opened = false;
        plus_minus_pressed = false;
        plus_minus_pressed_by_button = false;
        parenthesisStack.clear();
        lastOperationIndexStack.clear();;
        last_operation_index = 0;
        length_of_digit =0;
        index_of_plus_minus = 0;
        last_operation_index_in_memory = 0;
        length_after_dot_is_pressed = 0;
        count_of_symbols = 0;
        dot_is_pressed = false;
        zero_is_pressed  = false;
        deleteOnce=false;
        last_digit="";
        answer="";
        index_of_equal = 0;
        controlTextView();

    }
    public void DotPressed(View v){

        if(count_of_symbols<100){
            if(display.getText().toString().contains("=")){
                if(!answer.equals("Неверный формат")&&!answer.equals("Недоп.функция")){
                    display.setText(answer);
                }
                else{
                    ClearPressed(v);
                }

            }
            if(dot_is_pressed==false){
                if (checkLastChar().equals(")")) {
                    last_operation_index = display.getText().toString().length();
                    String text = display.getText().toString() + "×" + "0.";
                    display.setText(text);
                }
                else {
                    if(digit_is_pressed){
                        String text = display.getText().toString()  + ".";
                        display.setText(text);
                    }
                    else{
                        String text = display.getText().toString()  + "0.";
                        display.setText(text);
                    }

                }
            }
            //if(length_after_dot_is_pressed>=10){
            //    length_after_dot_is_pressed = 0;
            //}

            //length_of_digit = 0;
            operation_is_pressed = false;
            zero_is_pressed = false;
            dot_is_pressed = true;
            digit_is_pressed  =true;
            deleteOnce = false;
            count_of_symbols = display.getText().length();
            controlTextView();

        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Максимальное число знаков (100)", Toast.LENGTH_LONG).show();
        }
        controlColor();

    }
    public void ParenthesisPressed(View v){

        if(count_of_symbols<100){
            if(display.getText().toString().contains("=")){
                if(!answer.equals("Неверный формат")&&!answer.equals("Недоп.функция")){
                    ClearPressed(v);
                    display.setText(answer);
                }
                else{
                    ClearPressed(v);
                }
            }
            else{
                dot_is_pressed = false;
                zero_is_pressed = false;
                length_after_dot_is_pressed = 0;
            }

            if(digit_is_pressed&&parenthesisStack.empty()){
                parenthesisStack.push("(");
                String text = display.getText().toString() + "×(";
                display.setText(text);
                last_operation_index = display.getText().toString().length()-1;
                digit_is_pressed = false;
                operation_is_pressed = false;
                plus_minus_pressed_by_button = false;
                length_of_digit = 0;
                length_after_dot_is_pressed = 0;
                System.out.println("()1");
            }
            else if(digit_is_pressed&&!parenthesisStack.empty()){
                parenthesisStack.pop();
                String text = display.getText().toString() + ")";
                length_of_digit = 0;
                length_after_dot_is_pressed = 0;
                display.setText(text);
                last_operation_index = display.getText().toString().length()-1;
                operation_is_pressed = false;
                plus_minus_pressed_by_button = false;
                System.out.println("()2");
            }
            else if(!digit_is_pressed||display.getText().toString().equals("")||plus_minus_pressed){
                parenthesisStack.push("(");
                String text = display.getText().toString() + "(";
                length_of_digit = 0;
                length_after_dot_is_pressed = 0;
                display.setText(text);
                last_operation_index = display.getText().toString().length()-1;
                operation_is_pressed = false;
                plus_minus_pressed_by_button = false;
                System.out.println("()3");

            }
            controlTextView();
            lastOperationIndexStack.push(last_operation_index);
            count_of_symbols = display.getText().length();
            deleteOnce = false;
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Максимальное число знаков (100)", Toast.LENGTH_LONG).show();
        }
        controlColor();


    }
    public void PlusMinusPressed(View v){

        if(count_of_symbols<100){
            if(display.getText().toString().contains("=")){
                if(!answer.equals("Неверный формат")&&!answer.equals("Недоп.функция")){
                    //ClearPressed(v);
                    display.setText(answer);
                }
                else{
                    ClearPressed(v);
                }
            }
            if (last_operation_index < 1 && plus_minus_pressed_by_button==false) {
                parenthesisStack.push("(");
                String text = "(-" + display.getText().toString();
                plus_minus_pressed =true;
                display.setText(text);
                plus_minus_pressed_by_button = true;
                last_operation_index_in_memory=last_operation_index;

            }
            else if(plus_minus_pressed_by_button==false&&last_operation_index >=1){
                if(!checkLastChar().equals(")")){
                    parenthesisStack.push(")");
                    String text = display.getText().toString().substring(0,last_operation_index+1) + "(-" + display.getText().toString().substring(last_operation_index+1);
                    plus_minus_pressed =true;
                    display.setText(text);
                    plus_minus_pressed_by_button = true;
                    last_operation_index_in_memory=last_operation_index;
                }
            }
            else if(plus_minus_pressed_by_button==true&&last_operation_index_in_memory <1 ){
                parenthesisStack.pop();
                String text = display.getText().toString().substring(2);
                plus_minus_pressed =false;
                display.setText(text);
                plus_minus_pressed_by_button = false;
            }
            else if(plus_minus_pressed_by_button==true&&last_operation_index_in_memory >=1){
                parenthesisStack.pop();
                String text = display.getText().toString().substring(0,last_operation_index_in_memory +1) + display.getText().toString().substring(last_operation_index_in_memory +3);
                plus_minus_pressed =false;
                display.setText(text);
                plus_minus_pressed_by_button = false;
            }
            controlTextView();
            count_of_symbols = display.getText().length();
            deleteOnce = false;
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Максимальное число знаков (100)", Toast.LENGTH_LONG).show();
        }
        controlColor();
    }
    public String checkLastChar(){
        if(display.getText().toString().equals("")){
            return "";
        }
        else{
            return display.getText().toString().charAt(display.getText().toString().length()-1)+"";
        }

    }

    public void controlTextView(){
        int length = display.getText().toString().length();

        if(length>=13 && length<20){
            display.setTextSize(30);
        }
        if(length>=20&&length<80){
            display.setTextSize(25);
        }

        if(length>=80&&length<90){
            display.setTextSize(22);
        }
        if(length>90){
            display.setTextSize(22);
        }
        if(length<=13){
            display.setTextSize(35);
        }

    }

    public void controlColor(){
        String result="";
        for(int i = 0; i<display.getText().toString().length();i++){
            if("×÷+-".contains(display.getText().toString().charAt(i)+"")){
                result = result + "<font color=#953AC6>" + display.getText().toString().charAt(i) + "</font>";
            }
            else{
                result = result + display.getText().toString().charAt(i);
            }
        }
        display.setText(Html.fromHtml(result));

    }





}
