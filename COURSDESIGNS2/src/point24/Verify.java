package point24;

import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Verify {

	public Verify() {

	}

	public boolean Comform(int[] noCar, String equaltion) {
		//判断几张牌是否和用户输入的几张牌一样
		for (int i = 0; i < noCar.length; i++) {
			noCar[i] = noCar[i] % 13;//首先将牌信息转换成牌上的数值
			if(noCar[i] == 0)
				noCar[i] = 13;
		}
		
		if (isShowNumber(noCar, equaltion))
			return true;
		else
			return false;

	}

	public boolean isShowNumber(int[] noCar, String equaltion) {//实际的分离和比较内容是否相同
		StringTokenizer tokens =  new StringTokenizer(equaltion," (*+/-)",false);
		int[] inputNum = new int[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreElements()) {
			
			inputNum[i++] = Integer.parseInt((String) tokens.nextElement());
		}
		
		
		Arrays.sort(inputNum);
		Arrays.sort(noCar);
		String s1 = "";
		String s2 = "";
		for (int n : inputNum) {
			s1 += n;
		}
		for (int m : noCar) {
			s2 += m;
		}
		
		if (s1.compareTo(s2) == 0)
			return true;
		else
			return false;

	}
	
	public String illegal(String equaltion){
		//判断输入的等式是否合法，同时计算出结果与24比较
	    Stack<Character> operatorStack = new Stack<Character>();
	    Stack<Double> operandStack = new Stack<Double>();
	    
		StringTokenizer tokens =  new StringTokenizer(equaltion," (*+/-)",true);
		
		 while (tokens.hasMoreTokens()) {
			
			String token = tokens.nextToken().trim();
	         if(token.length() == 0)
	        	 continue;
	         else if(token.charAt(0) == '+' ||token.charAt(0) == '-'){
      	       while(!operatorStack.isEmpty() &&
      	    		   (operatorStack.peek() == '+' ||
      	    		   operatorStack.peek() == '-' ||
      	    		   operatorStack.peek() == '*' ||
      	    		   operatorStack.peek() == '/')){
      	    	 if(!processAnOperator(operandStack, operatorStack))
    	    		   return "2";
      	       } 
      	       operatorStack.push(token.charAt(0));
             }
	         else if(token.charAt(0) == '*' || token.charAt(0) == '/'){
	        	 while (!operatorStack.isEmpty() &&
	        			 (operatorStack.peek() == '*' ||
	        			 operatorStack.peek() == '/')) {
	        		 if(!processAnOperator(operandStack, operatorStack))
	      	    		   return "2";
				}
	        	 operatorStack.push(token.charAt(0));
	         }
	         else if(token.trim().charAt(0) == '('){
	        	 operatorStack.push('(');
	         }else if(token.trim().charAt(0) == ')'){
	        	 int num = 0;
	        	 while(operatorStack.peek() != '('){
	        		 if(!processAnOperator(operandStack, operatorStack))
	      	    		   return "2";
	        		 num++;
	        	 }
	        	 if(num == 0){
	        		 return null;
	        	 }
	        	 operatorStack.pop();
	         }
	         else{
	        	 operandStack.push(new Double(token));
	         }
		 }
		
		 while(!operatorStack.isEmpty()){
			 if(!processAnOperator(operandStack, operatorStack))
	    		   return "2";
		 }
	
		 if(operandStack.size() == 1 && operatorStack.size() == 0){
			 
			 return operandStack.pop() == 24?"24":"2";
		 }else{
			 return null;
		 }
		 
	}
	
	public int Calculter(String equaltion){//计算用户输入的情况
		String result = illegal(equaltion);
		if(result == null){
			return -1;
		}else{ 
		  if(result.equals("24")||result.equals("24.0"))
			return 0;
		 else
			return 1;
		}
		
	}
	
	 public  boolean processAnOperator(Stack<Double> operandStack,
				Stack<Character> operatorStack) {//栈内操作符，和数值的简单计算
			// TODO Auto-generated method stub
			char op = operatorStack.pop();
			Double op1 = operandStack.pop();
			Double op2 = operandStack.pop();
			
			if(op == '+'){
				operandStack.push(op2 + op1);
				return true;
			}
			else if(op == '-'){
				operandStack.push(op2 - op1);
				return true;
			}
			else if(op == '*'){
				operandStack.push(op2 * op1);
				return true;
			}
			else if(op == '/' && op1 != 0){
				operandStack.push(op2 / op1);
				return true;
			}
			
			return false;
		}
	

}
