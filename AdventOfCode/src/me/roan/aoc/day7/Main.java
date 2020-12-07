package me.roan.aoc.day7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main{
	
	private static final Map<String, List<Bag>> rules = new HashMap<String, List<Bag>>(); 

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day7/input"));
		
		while(in.hasNext()){
			String[] arr = in.nextLine().split(" bags contain ");
			
			List<Bag> content = rules.computeIfAbsent(arr[0], k->new ArrayList<Bag>());
			for(String type : arr[1].split(",")){
				type = type.substring(0, type.lastIndexOf("bag")).trim();
				String[] parts = type.split(" ", 2);
				content.add(new Bag(parts[0], parts[1]));
			}
		}
		
		silverStar();
	}
	
	private static void silverStar(){
		int count = 0;
		for(String key : rules.keySet()){
			if(key.equals("shiny gold")){
				continue;
			}
			
			System.out.print(key + ": ");
			if(containsGold(key)){
				count++;
				System.out.println("true");
			}else{
				System.out.println("false");
			}
		}
		System.out.println("Num: " + count);
	}
	
	private static boolean containsGold(String bag){
		boolean retVal = false;
		for(Bag b : rules.get(bag)){
			if(b.name.equals("shiny gold")){
				return true;
			}if(b.count == -1){
				continue;
			}else{
				retVal = retVal || containsGold(b.name);
			}
		}
		return retVal;
	}
	
	private static class Bag{
		private String name;
		private int count;
		
		private Bag(String count, String name){
			if(count.equals("no") && name.equals("other")){
				this.count = - 1;
				this.name = "no" + name;
			}else{
				this.count = Integer.parseInt(count);
				this.name = name;
			}
		}
	}
}
