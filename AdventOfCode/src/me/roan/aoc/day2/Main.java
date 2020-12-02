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
			
			int c = 0;
			for(int i = 4; i < line.length(); i++){
				if(line.charAt(i) == policy){
					c++;
				}
			}
			
			if(min <= c && max >= c){
				valid++;
			}
		}
		System.out.println("Valid: " + valid);
	}
}
