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
		goldStar();
	}
	
	private static void silverStar(){
		int valid = 0;
		for(Map<String, String> entry : map){
			if(entry.containsKey("byr") && entry.containsKey("iyr") && entry.containsKey("eyr") && entry.containsKey("hgt") && entry.containsKey("hcl") && entry.containsKey("ecl") && entry.containsKey("pid")){
				valid++;
			}
		}
		System.out.println("Valid (silver): " + valid);
	}
	
	private static void goldStar(){
		int valid = 0;
		for(Map<String, String> entry : map){
			if(entry.containsKey("byr") && entry.containsKey("iyr") && entry.containsKey("eyr") && entry.containsKey("hgt") && entry.containsKey("hcl") && entry.containsKey("ecl") && entry.containsKey("pid")){
				try{
					int byr = Integer.parseInt(entry.get("byr"));
					int iyr = Integer.parseInt(entry.get("iyr"));
					int eyr = Integer.parseInt(entry.get("eyr"));
					if(byr >= 1920 && byr <= 2002 && iyr >= 2010 && iyr <= 2020 && eyr >= 2020 && eyr <= 2030){
						String hgt = entry.get("hgt");
						if(hgt.endsWith("cm")){
							int num = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
							if(num < 150 || num > 193){
								continue;
							}
						}else if(hgt.endsWith("in")){
							int num = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
							if(num < 59 || num > 76){
								continue;
							}
						}else{
							continue;
						}
						
						if(!entry.get("hcl").matches("#[0-9a-f]{6}")){
							continue;
						}
						
						String ecl = entry.get("ecl");
						if(ecl.equals("amb") || ecl.equals("blu") || ecl.equals("brn") || ecl.equals("gry") || ecl.equals("grn") || ecl.equals("hzl") || ecl.equals("oth")){
							if(entry.get("pid").matches("[0-9]{9}")){
								valid++;
								continue;
							}
						}
					}
				}catch(Exception e){
				}
			}
		}
		System.out.println("Valid (gold): " + valid);
	}
}
