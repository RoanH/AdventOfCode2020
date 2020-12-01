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
		
		System.out.println("===== Part 1 =====");
		findPair();
		
		System.out.println("===== Part 2 =====");
		findTriplet();
	}
	
	private static void findTriplet(){
		input.sort((x, y)->-Integer.compare(x, y));
		int val;
		for(int i = 0; i < input.size(); i++){
			val = input.get(i);
			for(int j = 0; j < input.size(); j++){
				int n = input.get(j);
				if(val + n < 2020){
					val += n;
					for(int k = 0; k < input.size(); k++){
						if(val + input.get(k) == 2020){
							System.out.println(i + " + " + j + " + " + k + " => 2020 | " + (input.get(i) * input.get(j) * input.get(k)));
						}
					}
					val -= n;
				}
			}
		}
	}

	private static void findPair(){
		for(int i = 0; i < input.size(); i++){
			for(int j = 0; j < input.size(); j++){
				if(i != j && input.get(i) + input.get(j) == 2020){
					System.out.println(i + " + " + j + " => 2020 | " + (input.get(i) * input.get(j)));
				}
			}
		}
	}
}
