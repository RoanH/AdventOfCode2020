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
	
		long target = silverStar();
		goldStar(target);
	}
	
	private static void goldStar(long target){
		int start = 0;
		int end = 0;
		long sum = numbers.get(0);
		
		while(end + 1 < numbers.size() && start + 1 < numbers.size()){
			if(sum < target){
				end++;
				sum += numbers.get(end);
			}
			if(sum > target){
				sum -= numbers.get(start);
				start++;
			}
			if(sum == target){
				long min = Long.MAX_VALUE;
				long max = 0;
				for(int i = start; i <= end; i++){
					min = Math.min(min, numbers.get(i));
					max = Math.max(max, numbers.get(i));
				}
				System.out.println("Found: " + start + " ~ " + end + ", min=" + min + ", max=" + max + ", sum=" + (min + max));
				break;
			}
		}
	}
	
	private static long silverStar(){
		for(int i = 25; i < numbers.size(); i++){
			if(!sum(i - 25, numbers.get(i))){
				System.out.println("Num: " + numbers.get(i));
				return numbers.get(i);
			}
		}
		return -1L;
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
