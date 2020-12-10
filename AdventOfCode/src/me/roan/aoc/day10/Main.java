package me.roan.aoc.day10;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
	
	private static List<Integer> jolts = new ArrayList<Integer>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day10/input"));
		
		while(in.hasNext()){
			jolts.add(in.nextInt());
		}
		
		silverStar();
	}
	
	private static void silverStar(){
		jolts.sort(null);
		
		int oneDiff = 1;//account for outlet
		int threeDiff = 1;//account for phone
		for(int i = 0; i < jolts.size() - 1; i++){
			int diff = jolts.get(i + 1) - jolts.get(i);
			if(diff == 1){
				oneDiff++;
			}else if(diff == 3){
				threeDiff++;
			}
		}
		
		System.out.println("Product: " + (oneDiff * threeDiff));
	}
}
