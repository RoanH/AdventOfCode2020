package me.roan.aoc.day19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
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
		goldStar();
	}
	
	private static void goldStar(){
		//Rule 8 and 11 are only used by rule 0
		
		//New 8: 42 | 42 8
		Pattern rule8 = Pattern.compile("(" + buildRegex(rules.get(42)) + ")+");
		
		//New 11: 42 31 | 42 11 31
		Pattern rule42 = Pattern.compile("^" + buildRegex(rules.get(42)));
		Pattern rule31 = Pattern.compile(buildRegex(rules.get(31)) + "$");
		
		int total = 0;
		for(String msg : messages){
			//Match rule 42 and 31 as often as possible but in pairs and abusing
			//the fact that rule 11 is just a repeat of the start of rule 11
			boolean found = false;
			while(true){
				Matcher m = rule31.matcher(msg);
				if(m.find()){
					String str = m.replaceFirst("");
					m = rule42.matcher(str);
					if(m.find()){
						found = true;
						msg = m.replaceFirst("");
						continue;
					}
				}
				break;
			}
			
			//We require at least one match of rule 11
			if(!found){
				continue;
			}
			
			//Match the remainder against rule 8
			if(rule8.matcher(msg).matches()){
				total++;
			}
		}
		System.out.println("Total matches: " + total);
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
