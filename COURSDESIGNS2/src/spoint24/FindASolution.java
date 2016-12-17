package spoint24;
/*
 * 通过获取展示卡片的数值，就算出是否存在能够成结果为24点的式子
 * 如果存在返回公式，如果不存在返回no solution
 */
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

public class FindASolution {
	char[]  operator = {'+','-','*','/'};
	String expression;
	public FindASolution(){
		
	}
	
	public String createExpression(int[] noCar){
		LinkedList<Integer> list = new LinkedList<>();
		for (int i = 0; i < noCar.length; i++) {
				noCar[i] = noCar[i] % 13;
				if(noCar[i] == 0)
					noCar[i] = 13;
			list.add(noCar[i]);
		}
		
		int[] newArray = new int[noCar.length];
		
		for (int a = 0; a < 4; a++)
			for (int b = 0; b < 4; b++) {
				if (b == a) {
					continue;
				}

				for (int c = 0; c < 4; c++) {
					if (c == a || c == b) {
						continue;
					}

					for (int d = 0; d < 4; d++) {
						if (d == a || d == b || d == c) {
							continue;
						}

						newArray[a] = noCar[0];
						newArray[b] = noCar[1];
						newArray[c] = noCar[2];
						newArray[d] = noCar[3];
					}
				}
				if(this.SimpleExpression(newArray))
					return expression;
			}
	
		return "no solution";
	}
	
	public boolean SimpleExpression(int[] operand){
		String[] simpleExpression = new String[7];
		//将操作数插入
		simpleExpression[0] = operand[0] + "";
		simpleExpression[2] = operand[1] + "";
		simpleExpression[4] = operand[2] + "";
		simpleExpression[6] = operand[3] + "";
		
		
			for (int j = 0; j < operator.length; j++) {
				simpleExpression[1] = operator[j] + "";
				for(int j1 = 0; j1 < operator.length; j1++){
					simpleExpression[3] = operator[j1] + "";
					for(int j2 = 0; j2 < operator.length; j2++){
						simpleExpression[5] = operator[j2] + "";
						if(this.standardExpression(simpleExpression))
							return true;
					}
				}
		}
		return false;
	}
	
	public boolean standardExpression(String[] sE){
		String[] standardexpression = new String[7];
		standardexpression[0] = sE[0] + sE[1] + sE[2] + sE[3] + sE[4] + sE[5] + sE[6];
		standardexpression[1] = "(" + sE[0] + sE[1] + sE[2] + ")" + sE[3] + sE[4] + sE[5] + sE[6];
		standardexpression[2] = "(" + sE[0] + sE[1] + sE[2] + sE[3] + sE[4] + ")" + sE[5] + sE[6];
		standardexpression[3] =  sE[0] + sE[1] +"("+sE[2] + sE[3] + sE[4] + ")" + sE[5] + sE[6];
		standardexpression[4] = "(" + sE[0] + sE[1] + sE[2] + ")" + sE[3] + "(" + sE[4] + sE[5] + sE[6] + ")";
		standardexpression[5] = "((" + sE[0] + sE[1] + sE[2] + ")" + sE[3] + sE[4]  + ")" + sE[5] + sE[6];
		standardexpression[6] = "(" + sE[0] + sE[1] + "(" +sE[2] + sE[3] + sE[4]  + "))" + sE[5] + sE[6];
		 for (int i = 0; i < standardexpression.length; i++) {
			 System.out.print(standardexpression[i]);
			 if(this.Calculate(standardexpression[i])){
				 this.expression = standardexpression[i];
				 return true;
			 }
		}
		 return false;
	}
	
	public boolean Calculate(String equaltion){
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
      	    		   return false;
      	       } 
      	       operatorStack.push(token.charAt(0));
             }
	         else if(token.charAt(0) == '*' || token.charAt(0) == '/'){
	        	 while (!operatorStack.isEmpty() &&
	        			 (operatorStack.peek() == '*' ||
	        			 operatorStack.peek() == '/')) {
	        		   if(!processAnOperator(operandStack, operatorStack))
	      	    		   return false;
				}
	        	 operatorStack.push(token.charAt(0));
	         }
	         else if(token.trim().charAt(0) == '('){
	        	 operatorStack.push('(');
	         }else if(token.trim().charAt(0) == ')'){
	        	 while(operatorStack.peek() != '('){
	        		   if(!processAnOperator(operandStack, operatorStack))
	      	    		   return false;
	        	 }
	        	 operatorStack.pop();
	         }
	         else{
	        	 operandStack.push(new Double(token));
	         }
	         
		 }
		 while(!operatorStack.isEmpty()){
			 if(!processAnOperator(operandStack, operatorStack))
	    		   return false;
		 }
		 System.out.println(" " + operandStack.peek());
		 return operandStack.pop() == 24;
	  }
		 
		 public  boolean processAnOperator(Stack<Double> operandStack,
					Stack<Character> operatorStack) {
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
