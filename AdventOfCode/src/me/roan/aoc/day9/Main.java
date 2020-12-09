package me.roan.aoc.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
	
	private static List<Long> numbers = new ArrayList<Long>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day9/input"));
		
		while(in.hasNext()){
			numbers.add(in.nextLong());
		}
	
		silverStar();
	}
	
	private static void silverStar(){
		for(int i = 25; i < numbers.size(); i++){
			if(!sum(i - 25, numbers.get(i))){
				System.out.println("Num: " + numbers.get(i));
			}
		}
	}
	
	private static boolean sum(int start, long target){
		for(int i = start; i < start + 25; i++){
			long num = numbers.get(i);
			if(num < target){
				for(int j = start; j < start + 25; j++){
					if(i != j && num + numbers.get(j) == target){
						return true;
					}
				}
			}
		}
		return false;
	}
}
