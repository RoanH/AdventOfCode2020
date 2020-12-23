package me.roan.aoc.day23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main{
	private static int[] input = new int[]{3, 2, 6, 5, 1, 9, 4, 7, 8};
	
	public static void main(String[] args){
		silverStar();
	}
	
	private static void silverStar(){
		LinkedList<Integer> data = new LinkedList<Integer>();
		Arrays.stream(input).forEach(data::add);
		int currentIdx = 0;
		
		for(int move = 0; move < 100; move++){
			List<Integer> sub = new ArrayList<Integer>(3);
			int current = data.get(currentIdx);
			for(int i = 0; i < 3; i++){
				if(currentIdx + 1 < data.size()){
					sub.add(data.remove(currentIdx + 1));
				}else{
					sub.add(data.remove());
				}
			}
			
			int dest = current;
			int destIdx = -1;
			do{
				dest = dest == 0 ? 9 : dest - 1;
			}while((destIdx = data.indexOf(dest)) == -1);
			
			if(destIdx == data.size() - 1){
				data.addAll(sub);
			}else{
				data.addAll(destIdx + 1, sub);
			}
			
			currentIdx = (data.indexOf(current) + 1) % data.size();
		}
		
		for(int i = data.indexOf(1) + 1; data.get(i % data.size()) != 1; i++){
			System.out.print(data.get(i % data.size()));
		}
		System.out.println();
	}
}
