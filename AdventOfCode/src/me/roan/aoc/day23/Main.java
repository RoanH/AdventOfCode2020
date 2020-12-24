package me.roan.aoc.day23;

public class Main{
	private static int[] input = new int[]{3, 2, 6, 5, 1, 9, 4, 7, 8};
	
	public static void main(String[] args){
		silverStar(false);
		silverStar(true);
	}
	
	private static void silverStar(boolean gold){
		//Cyclic linked list with array backing for indexing
		Node[] index = new Node[(gold ? 1_000_000 : 9) + 1];
		Node head = new Node(input[0]);
		index[input[0]] = head;
		Node tail = head;
		for(int i = 1; i < input.length; i++){
			tail = new Node(tail, input[i]);
			index[tail.value] = tail;
		}
		if(gold){
			for(int i = 10; i <= 1_000_000; i++){
				tail = new Node(tail, i);
				index[tail.value] = tail;
			}
		}
		head.prev = tail;
		tail.next = head;
		
		Node current = head;
		for(int move = 0; move < (gold ? 10_000_000 : 100); move++){
			//Mark picked
			Node start = current.next;
			start.picked = true;
			start.next.picked = true;
			Node end = start.next.next;
			end.picked = true;
			
			//Find dest
			int dest = current.value;
			do{
				dest = dest == 0 ? (gold ? 1_000_000 : 9) : dest - 1;
			}while(dest == 0 || index[dest].picked);
			Node destIdx = index[dest];
			
			//Move range
			current.next = end.next;
			end.next.prev = current;
			
			end.next = destIdx.next;
			destIdx.next.prev = end;
			
			destIdx.next = start;
			start.prev = destIdx;
			
			//Mark unpicked
			start.picked = false;
			start.next.picked = false;
			end.picked = false;
			
			current = current.next;
		}
		
		if(gold){
			System.out.print("Gold: ");
			while(current.value != 1){
				current = current.next;
			}
			long n1 = current.next.value;
			long n2 = current.next.next.value;
			System.out.println(n1 + " * " + n2 + " = " + (n1 * n2));
		}else{
			System.out.print("Silver: ");
			while(current.value != 1){
				current = current.next;
			}
			while(current.next.value != 1){
				current = current.next;
				System.out.print(current.value);
			}
			System.out.println();
		}
	}
	
	private static class Node{
		private int value;
		private Node next;
		private Node prev;
		private boolean picked = false;
		
		private Node(int value){
			this.value = value;
		}
		
		private Node(Node prev, int value){
			this.prev = prev;
			this.value = value;
			prev.next = this;
		}
		
		@Override
		public String toString(){
			return prev.value + "<-" + String.valueOf(value) + "->" + next.value;
		}
	}
}
