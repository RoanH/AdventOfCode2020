package me.roan.aoc.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
	
	private static List<String> map = new ArrayList<String>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day3/input"));
		
		while(in.hasNext()){
			map.add(in.nextLine());
		}
		
		
		silverStar();
	}
	
	private static boolean mapIndex(int x, int y){
		if(y >= map.size()){
			System.err.println("Out of bounds");
			return false;
		}else{
			String line = map.get(y);
			return line.charAt(x % line.length()) == '#';
		}
	}
	
	private static void silverStar(){
		int x = 0;
		int y = 0;
		int trees = 0;
		while(y + 1 < map.size()){
			x += 3;
			y += 1;
			System.out.print(x + " | " + y + ": " + map.get(y) + " = ");
			if(mapIndex(x, y)){
				System.out.println('#');
				trees++;
			}else{
				System.out.println('.');
			}
		}
		System.out.println("Trees: " + trees);
	}
}
