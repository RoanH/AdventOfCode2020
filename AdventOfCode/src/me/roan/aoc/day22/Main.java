package me.roan.aoc.day22;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class Main{
	private static Deque<Integer> player1 = new LinkedList<Integer>();
	private static Deque<Integer> player2 = new LinkedList<Integer>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day22/input"));
	
		in.nextLine();
		String line;
		while(!(line = in.nextLine()).isEmpty()){
			player1.addLast(Integer.parseInt(line));
		}
		
		in.nextLine();
		while(in.hasNext()){
			player2.addLast(Integer.parseInt(in.nextLine()));
		}
		
		silverStar();
	}
	
	public static void silverStar(){
		while(!player1.isEmpty() && !player2.isEmpty()){
			if(player1.peekFirst() > player2.peekFirst()){
				player1.addLast(player1.removeFirst());
				player1.addLast(player2.removeFirst());
			}else{
				player2.addLast(player2.removeFirst());
				player2.addLast(player1.removeFirst());
			}
		}
		
		Deque<Integer> winner = player1.isEmpty() ? player2 : player1;
		int total = 0;
		for(int i = 1; !winner.isEmpty(); i++){
			total += winner.removeLast() * i;
		}
		System.out.println("Winner score: " + total);
	}
}
