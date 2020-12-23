package me.roan.aoc.day22;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class Main{
	private static Deque<Integer> deck1 = new LinkedList<Integer>();
	private static Deque<Integer> deck2 = new LinkedList<Integer>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day22/input"));
	
		in.nextLine();
		String line;
		while(!(line = in.nextLine()).isEmpty()){
			deck1.addLast(Integer.parseInt(line));
		}
		
		in.nextLine();
		while(in.hasNext()){
			deck2.addLast(Integer.parseInt(in.nextLine()));
		}
		
		silverStar();
		goldStar();
	}
	
	public static void goldStar(){
		LinkedList<Integer> player1 = new LinkedList<Integer>(deck1);
		LinkedList<Integer> player2 = new LinkedList<Integer>(deck2);
		
		if(subGame(player1, player2)){
			System.out.println("Player 1 won: " + scoreDeck(player1));
		}else{
			System.out.println("Player 2 won: " + scoreDeck(player2));
		}
	}
	
	@SuppressWarnings("unchecked")
	public static boolean subGame(LinkedList<Integer> player1, LinkedList<Integer> player2){
		List<Entry<List<Integer>, List<Integer>>> history = new ArrayList<Entry<List<Integer>, List<Integer>>>();

		while(!player1.isEmpty() && !player2.isEmpty()){
			for(Entry<List<Integer>, List<Integer>> item : history){
				if(item.getKey().equals(player1) && item.getValue().equals(player2)){
					//Player 1 wins by default
					return true;
				}
			}
			history.add(new SimpleEntry<List<Integer>, List<Integer>>((List<Integer>)player1.clone(), (List<Integer>)player2.clone()));
			
			if(player1.peekFirst() <= player1.size() - 1 && player2.peekFirst() <= player2.size() - 1){
				if(subGame(new LinkedList<Integer>(player1.subList(1, player1.peekFirst() + 1)), new LinkedList<Integer>(player2.subList(1, player2.peekFirst() + 1)))){
					player1.addLast(player1.removeFirst());
					player1.addLast(player2.removeFirst());
				}else{
					player2.addLast(player2.removeFirst());
					player2.addLast(player1.removeFirst());
				}
			}else if(player1.peekFirst() > player2.peekFirst()){
				player1.addLast(player1.removeFirst());
				player1.addLast(player2.removeFirst());
			}else{
				player2.addLast(player2.removeFirst());
				player2.addLast(player1.removeFirst());
			}
		}
		
		return !player1.isEmpty();
	}
	
	private static int scoreDeck(Deque<Integer> deck){
		int total = 0;
		for(int i = 1; !deck.isEmpty(); i++){
			total += deck.removeLast() * i;
		}
		return total;
	}
	
	public static void silverStar(){
		Deque<Integer> player1 = new LinkedList<Integer>(deck1);
		Deque<Integer> player2 = new LinkedList<Integer>(deck2);
		
		while(!player1.isEmpty() && !player2.isEmpty()){
			if(player1.peekFirst() > player2.peekFirst()){
				player1.addLast(player1.removeFirst());
				player1.addLast(player2.removeFirst());
			}else{
				player2.addLast(player2.removeFirst());
				player2.addLast(player1.removeFirst());
			}
		}
		
		System.out.println("Winner score: " + scoreDeck(player1.isEmpty() ? player2 : player1));
	}
}
