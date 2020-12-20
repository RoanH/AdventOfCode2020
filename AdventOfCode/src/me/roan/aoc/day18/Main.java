package me.roan.aoc.day18;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main{
	private static List<String> problems = new ArrayList<String>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day18/input"));
		
		while(in.hasNextLine()){
			problems.add(in.nextLine().replace(" ", ""));
		}
		
		silverStar();
		goldStar();
	}
	
	private static void goldStar(){
		long sum = 0;
		for(String problem : problems){
			System.out.print(problem);
			System.out.print("=");
			long ans = solveEq(problem);
			System.out.println(ans);
			sum += ans;
		}
		System.out.println("Sum: " + sum);
	}
	
	private static long solveEq(String eq){
		LinkedList<Symbol> data = new LinkedList<Symbol>();
		for(int i = 0; i < eq.length(); i++){
			data.add(new Symbol(eq.charAt(i)));
		}
		return solveEq(data).value;
	}
	
	private static Symbol solveEq(LinkedList<Symbol> symbols){
		if(symbols.contains(Symbol.PARENTHESIS_OPEN)){
			for(int start = 0; start < symbols.size(); start++){
				if(symbols.get(start).equals(Symbol.PARENTHESIS_OPEN)){
					int ignore = 0;
					LinkedList<Symbol> subEq = new LinkedList<Symbol>();
					
					Symbol s;
					while(true){
						s = symbols.remove(start + 1);
						if(s.equals(Symbol.PARENTHESIS_OPEN)){
							ignore++;
						}else if(s.equals(Symbol.PARENTHESIS_CLOSE)){
							if(ignore == 0){
								symbols.set(start, solveEq(subEq));
								break;
							}else{
								ignore--;
							}
						}
						subEq.addLast(s);
					}
				}
			}
		}
		
		for(int i = 0; i < symbols.size(); i++){
			if(symbols.get(i).equals(Symbol.ADD)){
				symbols.set(i - 1, new Symbol(symbols.get(i - 1).value + symbols.get(i + 1).value));
				symbols.remove(i);
				symbols.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < symbols.size(); i++){
			if(symbols.get(i).equals(Symbol.MUL)){
				symbols.set(i - 1, new Symbol(symbols.get(i - 1).value * symbols.get(i + 1).value));
				symbols.remove(i);
				symbols.remove(i);
				i--;
			}
		}

		return symbols.get(0);
	}
		
	private static void silverStar(){
		long result = 0;
		for(String problem : problems){
			System.out.print(problem);
			System.out.print("=");
			long ans = solveEquation(problem);
			System.out.println(ans);
			result += ans;
		}
		System.out.println("Total sum: " + result);
	}
	
	private static long solveEquation(String eq){
		//Assuming all single digit numbers
		LinkedList<Character> symbols = new LinkedList<Character>();
		for(int i = 0; i < eq.length(); i++){
			symbols.addLast(eq.charAt(i));
		}
		symbols.addLast(')');
		
		return solveParen(symbols);
	}

	private static long solveParen(LinkedList<Character> symbols){
		Long first = null;
		char op = 0;
		while(!symbols.isEmpty()){
			char sym = symbols.removeFirst();
			switch(sym){
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				if(first == null){
					first = (long)(sym - '0');
				}else{
					if(op == '+'){
						first += (long)(sym - '0');
					}else{
						first *= (long)(sym - '0');
					}
				}
				break;
			case '+':
			case '*':
				op = sym;
				break;
			case '(':
				if(first == null){
					first = solveParen(symbols);
				}else{
					if(op == '+'){
						first += solveParen(symbols);
					}else{
						first *= solveParen(symbols);
					}
				}
				break;
			case ')':
				return first;
			}
		}
		throw new RuntimeException("Malformed input");
	}
	
	private static class Symbol{
		private static final Symbol PARENTHESIS_OPEN = new Symbol('(');
		private static final Symbol PARENTHESIS_CLOSE = new Symbol(')');
		private static final Symbol MUL = new Symbol('*');
		private static final Symbol ADD = new Symbol('+');
		private char sym;
		private long value;
		
		private Symbol(long value){
			this.value = value;
		}
		
		private Symbol(char ch){
			sym = ch;
			if('0' <= ch && ch <= '9'){
				value = ch - '0';
			}
		}
		
		@Override
		public boolean equals(Object other){
			return other instanceof Symbol && ((Symbol)other).sym == sym;
		}
		
		@Override
		public String toString(){
			return "Symbol[sym=" + sym + ",value=" + value + "]";
		}
	}
}
