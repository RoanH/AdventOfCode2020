package me.roan.aoc.day20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main{
	private static List<Tile> tiles = new ArrayList<Tile>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day20/input"));
		
		while(in.hasNextLine()){
			String line = in.nextLine();
			if(line.startsWith("Tile ")){
				Tile tile = new Tile();
				tile.id = Integer.parseInt(line.substring(5, line.length() - 1));
				for(int i = 0; i < 10; i++){
					line = in.nextLine();
					for(int c = 0; c < line.length(); c++){
						tile.data[i][c] = line.charAt(c) == '#';
					}
				}
				tiles.add(tile);
			}
		}
		
		silverStar();
	}
	
	private static void silverStar(){
		long product = 1;
		main: for(int i = 0; i < tiles.size(); i++){
			Tile t = tiles.get(i);
			
			int match = 0;
			sides: for(int s = 1; s <= 4; s++){
				for(int j = 0; j < tiles.size(); j++){
					if(j != i && t.canMatch(s, tiles.get(j))){
						match++;
						if(match >= 3){
							continue main;
						}else{
							continue sides;
						}
					}
				}
			}
			
			if(match <= 2){
				System.out.println("Corner tile: " + t.id + " m:" + match);
				product *= t.id;
			}
		}
		System.out.println(product);
	}
	
	private static class Tile{
		private int id;
		private boolean[][] data = new boolean[10][10];
		
		public boolean canMatch(int side, Tile other){
			for(int s = 1; s <= 4; s++){
				if(canMatch(side, s, other)){
					return true;
				}
			}
			return false;
		}
		
		public boolean canMatch(int side1, int side2, Tile other){
			boolean[] s1 = getSide(side1);
			boolean[] s2 = other.getSide(side2);
			
			if(Arrays.equals(s1, s2)){
				return true;
			}else{
				for(int i = 0; i < s1.length; i++){
					if(s1[i] != s2[9 - i]){
						return false;
					}
				}
				return true;
			}
		}
		
		public boolean[] getSide(int s){
			switch(s){
			case 1:
				return data[0];
			case 2:
				return data[9];
			case 3:
				return new boolean[]{
					data[0][0],
					data[1][0],
					data[2][0],
					data[3][0],
					data[4][0],
					data[5][0],
					data[6][0],
					data[7][0],
					data[8][0],
					data[9][0]
				};
			case 4:
				return new boolean[]{
					data[0][9],
					data[1][9],
					data[2][9],
					data[3][9],
					data[4][9],
					data[5][9],
					data[6][9],
					data[7][9],
					data[8][9],
					data[9][9]
				};
			default:
				throw new RuntimeException("Bad side");
			}
		}
		
		private String rowString(boolean[] data){
			return (data[0] ? "#" : ".")
				+ (data[1] ? "#" : ".")
				+ (data[2] ? "#" : ".")
				+ (data[3] ? "#" : ".")
				+ (data[4] ? "#" : ".")
				+ (data[5] ? "#" : ".")
				+ (data[6] ? "#" : ".")
				+ (data[7] ? "#" : ".")
				+ (data[8] ? "#" : ".")
				+ (data[9] ? "#" : ".");
		}
		
		@Override
		public String toString(){
			return rowString(data[0])
				+ "\n" + rowString(data[1])
				+ "\n" + rowString(data[2])
				+ "\n" + rowString(data[3])
				+ "\n" + rowString(data[4])
				+ "\n" + rowString(data[5])
				+ "\n" + rowString(data[6])
				+ "\n" + rowString(data[7])
				+ "\n" + rowString(data[8])
				+ "\n" + rowString(data[9]);
		}
	}
}
