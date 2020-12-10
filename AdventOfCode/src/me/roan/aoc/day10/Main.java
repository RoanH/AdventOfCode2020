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
		goldStar();
	}
	
	private static void goldStar(){
		jolts.sort(null);
		
		int[][] next = new int[jolts.size()][3];
		
		for(int i = 0; i < jolts.size() - 1; i++){
			int idx = 0;
			if(jolts.get(i + 1) - jolts.get(i) <= 3){
				next[i][idx++] = i + 1;
			}
			if(i < jolts.size() - 2 && jolts.get(i + 2) - jolts.get(i) <= 3){
				next[i][idx++] = i + 2;
			}
			if(i < jolts.size() - 3 && jolts.get(i + 3) - jolts.get(i) <= 3){
				next[i][idx] = i + 3;
			}
		}
		
		long[] branches = new long[jolts.size()];
		branches[jolts.size() - 1] = 1;
		
		for(int i = jolts.size() - 2; 0 <= i; i--){
			long val = 0;
			if(next[i][0] != 0){
				val += branches[next[i][0]];
			}
			if(next[i][1] != 0){
				val += branches[next[i][1]];
			}
			if(next[i][2] != 0){
				val += branches[next[i][2]];
			}
			branches[i] = val;
		}
		
		long total = 0;
		for(int i = 0; i < jolts.size(); i++){
			if(jolts.get(i) <= 3){
				total += branches[i];
			}
		}
		
		System.out.println("Branches: " + total);
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
