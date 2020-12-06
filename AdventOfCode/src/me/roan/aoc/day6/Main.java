package me.roan.aoc.day6;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main{

	public static void mainf(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day6/input"));
		
		int sum = 0;
		
		Set<Character> set = new HashSet<Character>();
		while(in.hasNext()){
			String line = in.nextLine();
			if(line.isEmpty()){
				System.out.println("cler: " + set.size() + " / " + set);
				sum += set.size();
				set.clear();
			}
			
			for(int i = 0; i < line.length(); i++){
				set.add(line.charAt(i));
			}
		}
		sum += set.size();

		
		System.out.println("s: " + sum);
	}
	
	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day6/input"));
		
		int sum = 0;
		
		Set<Character> set = new HashSet<Character>();
		String line = in.nextLine();
		for(int i = 0; i < line.length(); i++){
			set.add(line.charAt(i));
		}
		while(in.hasNext()){
			line = in.nextLine();
			if(line.isEmpty()){
				System.out.println(set.size() + " / " + set);
				sum += set.size();
				set.clear();
				line = in.nextLine();
				for(int i = 0; i < line.length(); i++){
					set.add(line.charAt(i));
				}
				continue;
			}
						
			final String l = line;
			set.removeIf(e->!l.contains(e + ""));
		}
		sum += set.size();

		
		System.out.println("s: " + sum);
	}
}
