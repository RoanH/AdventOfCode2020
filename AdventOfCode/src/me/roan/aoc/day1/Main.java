package me.roan.aoc.day1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
	
	private static final List<Integer> input = new ArrayList<Integer>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day1/input"));
		while(in.hasNext()){
			input.add(in.nextInt());
		}
		System.out.println("len: " + input.size());
		
		findPair();
	}
	
	private static void findPair(){
		for(int i = 0; i < input.size(); i++){
			for(int j = 0; j < input.size(); j++){
				if(i != j && input.get(i) + input.get(j) == 2020){
					System.out.println(i + " + " + j + " = 2020 | " + (input.get(i) * input.get(j)));
				}
			}
		}
	}
}
