package me.roan.aoc.day25;

import java.util.Scanner;

public class Main{
	private static long pubkey1;
	private static long pubkey2;

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day25/input"));
		
		pubkey1 = in.nextLong();
		pubkey2 = in.nextLong();
		
		silverStar();
	}
	
	private static void silverStar(){
		long loop1 = findLoopSize(7, pubkey1);
		long value = 1;
		for(int i = 0; i < loop1; i++){
			value *= pubkey2;
			value %= 20201227L;
		}
		
		System.out.println("Encryption key: " + value);
	}
	
	private static int findLoopSize(long subject, long result){
		int i = 0;
		long value = 1;
		while(value != result){
			i++;
			value *= subject;
			value %= 20201227L;
		}
		return i;
	}
}
