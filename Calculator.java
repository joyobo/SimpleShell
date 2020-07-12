import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import java.util.regex.*;
import lib.*;

public class Calculator {
    private String input;
    private ArrayList<String> tok;
    private String output;
    private LinkedList<String> rpn;  // shunting yard algorithm output (Reverse Polish Notation/Postfix Notation)

    Calculator(String s) {
        this.input = s;
    }

    public LinkedList<String> evaluate() throws InvalidMathExpression {
        // 1. tokenise
        this.tok = this.tokeniser();
        // 1b. handle negative numbers eg, (-9.0) -> -9.0
        this.tok = this.handleNeg();
        // 2. shunting yard algorithm
        this.rpn = this.SYA();
        return this.rpn;
    }

    private ArrayList<String> tokeniser() throws InvalidMathExpression {
        // get rid of all whitespaces
        String s1 = this.input.replaceAll("\\s", "");
        // split into tokens of either +,-,*,/,^,\d+,(,)
        ArrayList<String> allMatches = new ArrayList<String>();
        Pattern p = Pattern.compile("(\\(-[0-9.]+\\))|([0-9.]+)|(-)|(\\*)|(/)|([(])|([)])|([+])|(\\^)");
        Matcher m = p.matcher(s1);
        // System.out.println(m.groupCount());
        while (m.find()) {
            allMatches.add(m.group());
        }
        // check for any invalid characters
        String tokenisedStr = String.join("", allMatches);
        if (!tokenisedStr.equals(s1))
            throw new InvalidMathExpression(
                    "Invalid Character detected. Supported characters are +,-,*,/,(,),digits and decimal points(.) .");
        return allMatches;
    }

    private ArrayList<String> handleNeg(){
        ArrayList<String> prc = new ArrayList<>();
        for(String ele: this.tok){
            if (StringUtils.contains(ele,"(")&&StringUtils.contains(ele,")")){ 
            System.out.println("in");
            prc.add(ele.substring(1,ele.length()-1));
            }
            else{
                prc.add(ele);
            }
        }
        return prc;
    }

    private LinkedList<String> SYA() throws InvalidMathExpression{
        HashMap<String, Integer> pcd = new HashMap<>() {{ put("+", 1); put("-", 1); put("*", 2); put("/", 2); put("^", 3);}};
        Stack<String> stack = new Stack<>(); // Stack: LIFO
        LinkedList<String> queue = new LinkedList<>(); // Queue: FIFO
        for (String ele: this.tok) {
            System.out.println("Main token: "+ele);
            System.out.println("Stack: "+Arrays.toString(stack.toArray()));
            System.out.println("Queue: "+Arrays.toString(queue.toArray()));

            // number
            if (NumberUtils.isCreatable(ele)) queue.add(ele);
            // operator
            else if (StringUtils.contains("+-/*^", ele)) {
                if(stack.size()==0) stack.push(ele);  // empty stack
                else{  // stack not empty
                    int imp_cur = pcd.get(ele);
                    while(stack.size()!=0){  // as long as stack isnt empty
                        //System.out.println(stack.peek());
                        if(stack.peek().equals("(")){
                            break;
                        }
                        if(pcd.get(stack.peek())>=imp_cur){
                            queue.add(stack.peek()); 
                            stack.pop();
                        }
                        else{
                            break;
                        }
                    }
                    stack.push(ele);
                } 
            }
            // (
            else if(ele.equals("(")) stack.push(ele);
            // )
            else if(ele.equals(")")){
                boolean found = false;
                while(stack.size()!=0){
                    if(stack.peek().equals("(")){
                        stack.pop();
                        found = true;
                        break;
                    }
                    queue.add(stack.pop());
                }
                if(!found) throw new InvalidMathExpression("Missing Parenthesis.");
            }
            else{
                throw new InvalidMathExpression("Invalid Character detected: "+ele+" . Supported characters are +,-,*,/,(,),digits and decimal points(.) .");
            }
        }
        
        System.out.println(Arrays.toString(stack.toArray()));
        // end of all input tokens
        while(stack.size()!=0){
            if(stack.peek().equals("(")||stack.peek().equals(")")) throw new InvalidMathExpression("Mismatched Parenthesis.");
            queue.add(stack.pop());
        }
        return queue;
    }

    public static void main(String[] args) {
        // System.out.println(NumberUtils.isCreatable("-9"));
        // System.out.println(Double.parseDouble("-6"));
        // System.out.println(StringUtils.contains("+-/*^", "+"));
        // System.out.println(StringUtils.contains("+-/*^", "="));
        String s = "99*( ( 33.5+45*(-2))+35+(-9.0))-7/(2^2)(5)";
        Calculator c = new Calculator(s);
        try{        
            System.out.println(c.evaluate());
        }catch (InvalidMathExpression e){
            System.out.println(e.getMessage());
        }
    }
}

class InvalidMathExpression extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidMathExpression(String message) {
        super(message);
    }
}
