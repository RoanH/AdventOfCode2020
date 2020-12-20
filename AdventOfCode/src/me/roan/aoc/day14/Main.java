package me.roan.aoc.day14;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main{
	
	private static Map<Long, Long> map = new HashMap<Long, Long>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day14/input"));
		
		long mask_ones = 0;
		long mask_zero = 0;
		
		while(in.hasNext()){
			String line = in.nextLine();
			
			if(line.startsWith("mask")){
				mask_ones = 0;
				mask_zero = 0;
				for(int i = 7; i < line.length(); i++){
					switch(line.charAt(i)){
					case '0':
						mask_zero |= 1L << (36 - 1 - (i - 7));
						break;
					case '1':
						mask_ones |= 1L << (36 - 1 - (i - 7));
						break;
					case 'X':
						break;
					}
				}
			}else{//mem
				String[] arr = line.split(" = ");
				long address = Long.parseLong(arr[0].substring(4, arr[0].length() - 1));
				long value = Long.parseLong(arr[1]);
				
				value |= mask_ones;
				value = (~(~value | mask_zero)) & 0xF_FFFF_FFFFL;
				
				map.put(address, value);
			}
		}
		
		//Silver
		System.out.println("sum: " + map.values().stream().mapToLong(Long::longValue).sum());
	}
}
