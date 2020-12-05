package me.roan.aoc.day5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{

	private static List<String> tickets = new ArrayList<String>();
	
	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day5/input"));
		
		while(in.hasNext()){
			tickets.add(in.nextLine());
		}
		
		silverStar();
		goldStar();
	}
	
	private static void goldStar(){
		boolean[] seats = new boolean[858 + 1];
		for(String ticket : tickets){
			seats[computeSeatID(ticket)] = true;
		}
		for(int i = 0; i < seats.length - 2; i++){
			if(seats[i] && !seats[i + 1] && seats[i + 2]){
				System.out.println("Seat: " + (i + 1));
			}
		}
	}
	
	private static void silverStar(){
		int max = 0;
		for(String ticket : tickets){
			max = Math.max(max, computeSeatID(ticket));
		}
		System.out.println("Max: " + max);
	}
	
	private static int computeSeatID(String ticket){
		int min = 0;
		int max = 127;
		for(int i = 0; i < 7; i++){
			if(ticket.charAt(i) == 'F'){
				max -= (max - min + 1) / 2;
			}else{
				min += (max - min + 1) / 2;
			}
		}
		int row = min;
		
		min = 0;
		max = 7;
		for(int i = 7; i < 7 + 3; i++){
			if(ticket.charAt(i) == 'L'){
				max -= (max - min + 1) / 2;
			}else{
				min += (max - min + 1) / 2;
			}
		}
		
		return row * 8 + min;
	}
}
