package me.roan.aoc.day15;

import java.util.HashMap;
import java.util.Map;

public class Main{
	private static final int[] input = new int[]{11, 0, 1, 10, 5, 19};

	public static void main(String[] args){
		silverStar();
	}
	
	private static void silverStar(){
		Map<Integer, Integer> last = new HashMap<Integer, Integer>();
		for(int i = 1; i < input.length; i++){
			last.put(input[i - 1], i);
		}
		
		int turn = input.length;
		int prev = input[input.length - 1];
		
		do{
			turn++;
			int num;
			if(last.containsKey(prev)){
				num = turn - 1 - last.get(prev);
			}else{
				num = 0;
			}
			last.put(prev, turn - 1);
			prev = num;
		}while(turn != 2020);
		
		System.out.println("Num 2020 is " + prev);
	}
}
