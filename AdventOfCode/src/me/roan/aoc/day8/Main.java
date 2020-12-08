package me.roan.aoc.day8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
	
	private static List<Instruction> instructions = new ArrayList<Instruction>();

	private static int acc;
	
	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day8/input"));
		
		while(in.hasNext()){
			String[] arr = in.nextLine().split(" ");
			instructions.add(new Instruction(arr[0], Integer.parseInt(arr[1])));
		}
		
		silverStar();
		goldStar();
	}
	
	private static void goldStar(){
		for(int i = 0; i < instructions.size(); i++){
			Instruction code = instructions.get(i);
			if(!code.name.equals("acc")){
				String old = code.name;
				code.name = code.name.equals("nop") ? "jmp" : "nop";
				acc = 0;
				if(silverStar()){
					return;
				}
				code.name = old;
			}
		}
	}
	
	private static boolean silverStar(){
		boolean normalTermination = false;
		boolean[] seen = new boolean[700];
		int idx = 0;
		while(!seen[idx]){
			if(normalTermination = idx == instructions.size()){
				break;
			}
			seen[idx] = true;
			Instruction i = instructions.get(idx);
			System.out.println(i.name + " " + i.value);
			switch(i.name){
			case "nop":
				idx++;
				break;
			case "acc":
				acc += i.value;
				idx++;
				break;
			case "jmp":
				idx += i.value;
				break;
			}
		}
		System.out.println("acc: " + acc);
		return normalTermination;
	}
	
	private static class Instruction{
		private String name;
		private int value;
		
		private Instruction(String name, int value){
			this.name = name;
			this.value = value;
		}
	}
}
