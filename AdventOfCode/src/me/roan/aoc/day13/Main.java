package me.roan.aoc.day13;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{

	private static List<Integer> busses = new ArrayList<Integer>();
	private static int[] constraints;
	private static int arrival;
	
	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day13/input"));
		arrival = in.nextInt();
		in.nextLine();
		String[] parts = in.nextLine().split(",");
		constraints = new int[parts.length];
		for(int i = 0; i < parts.length; i++){
			if(!parts[i].equals("x")){
				int bus = Integer.parseInt(parts[i]);
				busses.add(bus);
				constraints[i] = bus;
			}
		}
		
		silverStar();
		goldStar();
	}
	
	private static void goldStar(){
		long n = 1;
		for(int bus : busses){
			n *= bus;
		}
		
		long x = 0;
		for(int i = 0; i < constraints.length; i++){
			if(constraints[i] == 0){
				continue;
			}
			long a = (constraints[i] - (i % constraints[i]));
			System.out.println("x = " + a + " (mod " + constraints[i] + ")");
			
			long nhat = n / constraints[i];
			long v = inverse(nhat, constraints[i]);
			
			x += a * v * nhat;
		}
		
		System.out.println("time: " + (x % n));
	}
	
	public static final long inverse(long pr, long mod) throws IllegalArgumentException, ArithmeticException{
		if(mod <= 0 || pr < 0){
			throw new IllegalArgumentException("Arguments may not be negative");
		}
		
		long px = 1;
		long t = 0;
		long r = mod;
		
		long q = 0;
		long tmp;
		while(r != 0){
			q = pr / r;
			
			tmp = r;
			r = pr - q * r;
			pr = tmp;
			
			tmp = t;
			t = px - q * t;
			px = tmp;
		}
		if(pr > 1){
			throw new ArithmeticException("The given argument is not invertible mod " + mod + " or the modulus is not a prime.");
		}
		return px < 0 ? (px + mod) : px;
	}
	
	private static void silverStar(){
		int minWait = Integer.MAX_VALUE;
		int minBus = -1;
		
		for(int bus : busses){
			int wait = bus - (arrival % bus);
			if(wait < minWait){
				minWait = wait;
				minBus = bus;
			}
		}
		
		System.out.println("Bus: " + minBus + ", wait=" + minWait + ", product=" + (minBus * minWait));
	}
}
