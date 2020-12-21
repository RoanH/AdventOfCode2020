package me.roan.aoc.day19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main{
	private static Map<Integer, String> rules = new HashMap<Integer, String>();
	private static List<String> messages = new ArrayList<String>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day19/input"));
		
		String line;
		while(!(line = in.nextLine()).isEmpty()){
			String[] arr = line.split(": ");
			rules.put(Integer.parseInt(arr[0]), arr[1]);
		}
		
		while(in.hasNextLine()){
			messages.add(in.nextLine());
		}
		
		silverStar();
	}
	
	private static void silverStar(){
		Pattern regex = Pattern.compile(buildRegex(rules.get(0)));
		System.out.println("Regex: " + regex);
		
		int total = 0;
		for(String msg : messages){
			if(regex.matcher(msg).matches()){
				total++;
			}
		}
		System.out.println("Total matches: " + total);
	}
	
	private static String buildRegex(String rule){
		if(rule.equals("\"a\"")){
			return "a";
		}else if(rule.equals("\"b\"")){
			return "b";
		}else if(rule.contains("|")){
			String[] parts = rule.split(" \\| ");
			return "(" + buildRegex(parts[0]) + "|" + buildRegex(parts[1]) + ")";
		}else{
			String[] parts = rule.split(" ");
			String str = "";
			for(String part : parts){
				str += buildRegex(rules.get(Integer.parseInt(part)));
			}
			return str;
		}
	}
}
