package me.roan.aoc.day2;

import java.util.Scanner;

public class Main{

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day2/input"));
		
		int valid = 0;
		while(in.hasNext()){
			String[] limit = in.next().split("-");
			int min = Integer.valueOf(limit[0]);
			int max = Integer.valueOf(limit[1]);
			
			String line = in.nextLine();
			char policy = line.charAt(1);
			
			//if(oldPolicy(min, max, policy, line)){
			if(newPolicy(min, max, policy, line)){
				valid++;
			}
		}
		System.out.println("Valid: " + valid);
	}
	
	private static boolean newPolicy(int min, int max, char policy, String line){
		return (line.charAt(min + 3) == policy) ^ (line.charAt(max + 3) == policy);
	}
	
	@SuppressWarnings("unused")
	private static boolean oldPolicy(int min, int max, char policy, String line){
		int c = 0;
		for(int i = 4; i < line.length(); i++){
			if(line.charAt(i) == policy){
				c++;
			}
		}
		
		return min <= c && max >= c;
	}
}
