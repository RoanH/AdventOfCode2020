package me.roan.aoc.day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class Main{
	
	private static List<String> instructions;
	
	public static void main(String[] args) throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day12/input")));
		instructions = in.lines().collect(Collectors.toList());
		in.close();
		
		silverStar();
		goldStar();
	}
	
	private static void goldStar(){
		int x = 0;
		int y = 0;
		int dx = 10;
		int dy = 1;
		int tmp;
		
		for(String instruction : instructions){
			char code = instruction.charAt(0);
			int value = Integer.parseInt(instruction.substring(1));
			
			switch(code){
			case 'N':
				dy += value;
				break;
			case 'S':
				dy -= value;
				break;
			case 'E':
				dx += value;
				break;
			case 'W':
				dx -= value;
				break;
			case 'L':
				tmp = dx * cos(360 - value) + dy * sin(360 - value);
				dy = dy * cos(360 - value) - dx * sin(360 - value);
				dx = tmp;
				break;
			case 'R':
				tmp = dx * cos(value) + dy * sin(value);
				dy = dy * cos(value) - dx * sin(value);
				dx = tmp;
				break;
			case 'F':
				x += value * dx;
				y += value * dy;
				break;
			}
		}
		
		System.out.println("Gold: " + x + " | " + y + " | " + (Math.abs(x) + Math.abs(y)));
	}
	
	private static void silverStar(){
		int x = 0;
		int y = 0;
		int dx = 1;
		int dy = 0;
		int tmp;
		
		for(String instruction : instructions){
			char code = instruction.charAt(0);
			int value = Integer.parseInt(instruction.substring(1));
			
			switch(code){
			case 'N':
				y += value;
				break;
			case 'S':
				y -= value;
				break;
			case 'E':
				x += value;
				break;
			case 'W':
				x -= value;
				break;
			case 'L':
				tmp = dx * cos(360 - value) + dy * sin(360 - value);
				dy = dy * cos(360 - value) - dx * sin(360 - value);
				dx = tmp;
				break;
			case 'R':
				tmp = dx * cos(value) + dy * sin(value);
				dy = dy * cos(value) - dx * sin(value);
				dx = tmp;
				break;
			case 'F':
				x += value * dx;
				y += value * dy;
				break;
			}
		}
		
		System.out.println("Silver: " + x + " | " + y + " | " + (Math.abs(x) + Math.abs(y)));
	}
	
	//To avoid any floating point related issues
	private static int cos(int value){
		return value == 0 ? 1 : (value == 180 ? -1 : 0);
	}
	
	private static int sin(int value){
		return value == 90 ? 1 : (value == 270 ? -1 : 0);
	}
}
