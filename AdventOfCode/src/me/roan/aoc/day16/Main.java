package me.roan.aoc.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main{
	private static List<Field> fields = new ArrayList<Field>();
	private static int[] own;
	private static List<int[]> tickets = new ArrayList<int[]>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day16/input"));
		
		String line;
		while(!(line = in.nextLine()).isEmpty()){
			fields.add(new Field(line));
		}
		
		in.nextLine();
		own = readTicket(in.nextLine());
		
		in.nextLine();
		in.nextLine();
		while(in.hasNext()){
			tickets.add(readTicket(in.nextLine()));
		}
		
		silverStar();
	}
	
	private static void silverStar(){
		int error = 0;
		for(int[] ticket : tickets){
			values: for(int val : ticket){
				for(Field field : fields){
					if(field.isValid(val)){
						continue values;
					}
				}
				error += val;
			}
		}
		System.out.println("Error rate: " + error);
	}
	
	private static final int[] readTicket(String line){
		return Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
	}
	
	private static class Field{
		private String name;
		private int r0;
		private int r1;
		private int r2;
		private int r3;
		
		private Field(String line){
			String[] args = line.split(": ");
			name = args[0];
			args = args[1].split(" ");
			String[] r01 = args[0].split("-");
			r0 = Integer.parseInt(r01[0]);
			r1 = Integer.parseInt(r01[1]);
			String[] r23 = args[2].split("-");
			r2 = Integer.parseInt(r23[0]);
			r3 = Integer.parseInt(r23[1]);
		}
		
		private boolean isValid(int num){
			return (r0 <= num && num <= r1) || (r2 <= num && num <= r3);
		}
	}
}
