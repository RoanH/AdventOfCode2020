package me.roan.aoc.day17;

import java.util.Scanner;

public class Main{
	private static final int mx = 8 + 6 * 2 + 2;
	private static final int my = 8 + 6 * 2 + 2;
	private static final int mz = 1 + 6 * 2 + 2;
	private static boolean[][][] dim = new boolean[mx][my][mz];//xyz
	
	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day17/input"));
		
		int y = 7;
		while(in.hasNextLine()){
			String line = in.nextLine();
			for(int i = 0; i < line.length(); i++){
				dim[7 + i][y][7] = line.charAt(i) == '#';
			}
			y++;
		}
		
		silverStar();
	}
	
	private static void silverStar(){
		boolean[][][] state = dim;
		
		for(int y = 0; y < my; y++){
			for(int x = 0; x < mx; x++){
				System.out.print(state[x][y][6] ? '#' : '.');
			}
			System.out.println();
		}
		
		for(int i = 0; i < 6; i++){
			boolean[][][] next = new boolean[mx][my][mz];
			for(int x = 1; x < mx - 1; x++){
				for(int y = 1; y < my - 1; y++){
					for(int z = 1; z < mz - 1; z++){
						next[x][y][z] = nextState(x, y, z, state);
					}
				}
			}
			state = next;
			System.out.println(
				"================================================================="
				+ "==============================================================="
				+ "==============================================================="
				+ "==============================================================="
				+ "==============================================================="
				+ "========================================================="
			);
			for(int y = 0; y < my; y++){
				for(int z = 0; z < mz; z++){
					for(int x = 0; x < mx; x++){
						System.out.print(state[x][y][z] ? '#' : '.');
					}
					System.out.print(" | ");
				}
				System.out.println();
			}
		}
		
		int active = 0;
		for(int x = 0; x < mx; x++){
			for(int y = 0; y < my; y++){
				for(int z = 0; z < mz; z++){
					if(state[x][y][z]){
						active++;
					}
				}
			}
		}
		
		System.out.println("Active: " + active);
	}
	
	private static boolean nextState(int x, int y, int z, boolean[][][] state){
		int total = 0;
		for(int dx = -1; dx <= 1; dx++){
			for(int dy = -1; dy <= 1; dy++){
				for(int dz = -1; dz <= 1; dz++){
					if(!(dx == 0 && dy == 0 && dz == 0)){
						if(state[x + dx][y + dy][z + dz]){
							total++;
						}
					}
				}
			}
		}
		
		return state[x][y][z] ? (total == 2 || total == 3) : (total == 3);
	}
}
