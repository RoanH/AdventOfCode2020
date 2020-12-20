package me.roan.aoc.day14;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main{
	
	private static Map<Long, Long> mapSilver = new HashMap<Long, Long>();
	private static Map<Long, Long> mapGold = new HashMap<Long, Long>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day14/input"));
		
		long mask_ones = 0;
		long mask_zero = 0;
		long mask_x = 0;
		
		while(in.hasNext()){
			String line = in.nextLine();
			
			if(line.startsWith("mask")){
				mask_ones = 0;
				mask_zero = 0;
				mask_x = 0;
				for(int i = 7; i < line.length(); i++){
					switch(line.charAt(i)){
					case '0':
						mask_zero |= 1L << (36 - 1 - (i - 7));
						break;
					case '1':
						mask_ones |= 1L << (36 - 1 - (i - 7));
						break;
					case 'X':
						mask_x |= 1L << (36 - 1 - (i - 7));
						break;
					}
				}
			}else{//mem
				String[] arr = line.split(" = ");
				long address = Long.parseLong(arr[0].substring(4, arr[0].length() - 1));
				long value = Long.parseLong(arr[1]);
				
				silverStar(address, value, mask_ones, mask_zero);
				goldStar(address, value, mask_ones, mask_x);
			}
		}
		
		System.out.println("sum (silver): " + mapSilver.values().stream().mapToLong(Long::longValue).sum());
		System.out.println("sum (gold): " + mapGold.values().stream().mapToLong(Long::longValue).sum());
	}
	
	private static void goldStar(long address, long value, long mask_ones, long mask_x){
		address |= mask_ones;
		write(address, value, mask_x, 0);
	}
	
	private static void write(long address, long value, long mask_x, int offset){
		for(int i = offset; i < 36; i++){
			if((mask_x & (1L << i)) != 0){
				write(address, value, mask_x, i + 1);
				write(address ^ (1L << i), value, mask_x, i + 1);
				return;
			}
		}
		
		mapGold.put(address, value);
	}
	
	private static void silverStar(long address, long value, long mask_ones, long mask_zero){
		value |= mask_ones;
		value = (~(~value | mask_zero)) & 0xF_FFFF_FFFFL;
		
		mapSilver.put(address, value);
	}
}
