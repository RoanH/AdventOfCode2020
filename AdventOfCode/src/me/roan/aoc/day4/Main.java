package me.roan.aoc.day4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main{

	private static List<Map<String, String>> map = new ArrayList<Map<String, String>>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day4/input"));

		Map<String, String> entry = null;
		while(in.hasNextLine()){
			if(entry == null){
				entry = new HashMap<String, String>();
			}
			
			String line = in.nextLine();
			if(line.isEmpty()){
				map.add(entry);
				entry = null;
			}else{
				String[] arr = line.split(" ");
				for(String s : arr){
					String[] pair = s.split(":", 2);
					entry.put(pair[0], pair[1]);
				}
			}
		}
		map.add(entry);
		
		
		silverStar();
	}
	
	private static void silverStar(){
		int valid = 0;
		for(Map<String, String> entry : map){
			if(entry.containsKey("byr") && entry.containsKey("iyr") && entry.containsKey("eyr") && entry.containsKey("hgt") && entry.containsKey("hcl") && entry.containsKey("ecl") && entry.containsKey("pid")){
				valid++;
			}
		}
		System.out.println("Valid: " + valid);
	}
}
