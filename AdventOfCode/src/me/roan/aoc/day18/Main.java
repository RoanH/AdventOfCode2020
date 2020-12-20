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
}
