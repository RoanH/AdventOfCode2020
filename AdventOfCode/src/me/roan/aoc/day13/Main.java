package me.roan.aoc.day13;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{

	private static List<Integer> busses = new ArrayList<Integer>();
	private static int arrival;
	
	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day13/input"));
		arrival = in.nextInt();
		in.nextLine();
		String[] parts = in.nextLine().split(",");
		for(String str : parts){
			if(!str.equals("x")){
				busses.add(Integer.parseInt(str));
			}
		}
		
		silverStar();
	}
	
	private static void silverStar(){
		int minWait = Integer.MAX_VALUE;
		int minBus = -1;
		
		for(int bus : busses){
			int wait = bus - (arrival % bus);
			if(wait < minWait){
				minWait = wait;
				minBus = bus;
			}
		}
		
		System.out.println("Bus: " + minBus + ", wait=" + minWait + ", product=" + (minBus * minWait));
	}
}
