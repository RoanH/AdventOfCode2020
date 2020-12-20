package me.roan.aoc.day17;

import java.util.Scanner;

public class Main{
	private static final int mx = 8 + 6 * 2 + 2;
	private static final int my = 8 + 6 * 2 + 2;
	private static final int mz = 1 + 6 * 2 + 2;
	private static final int mw = 1 + 6 * 2 + 2;
	private static boolean[][][] dimSilver = new boolean[mx][my][mz];
	private static boolean[][][][] dimGold = new boolean[mx][my][mz][mw];
	
	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day17/input"));
		
		int y = 7;
		while(in.hasNextLine()){
			String line = in.nextLine();
			for(int i = 0; i < line.length(); i++){
				dimSilver[7 + i][y][7] = line.charAt(i) == '#';
				dimGold[7 + i][y][7][7] = line.charAt(i) == '#';
			}
			y++;
		}
		
		silverStar();
		goldStar();
	}
	
	private static void goldStar(){
		boolean[][][][] state = dimGold;
		
		for(int i = 0; i < 6; i++){
			boolean[][][][] next = new boolean[mx][my][mz][mw];
			for(int x = 1; x < mx - 1; x++){
				for(int y = 1; y < my - 1; y++){
					for(int z = 1; z < mz - 1; z++){
						for(int w = 1; w < mw - 1; w++){
							next[x][y][z][w] = nextState4D(x, y, z, w, state);
						}
					}
				}
			}
			state = next;
		}
		
		int active = 0;
		for(int x = 0; x < mx; x++){
			for(int y = 0; y < my; y++){
				for(int z = 0; z < mz; z++){
					for(int w = 0; w < mw; w++){
						if(state[x][y][z][w]){
							active++;
						}
					}
				}
			}
		}
		
		System.out.println("Active (gold): " + active);
	}
	
	private static void silverStar(){
		boolean[][][] state = dimSilver;
		
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
						next[x][y][z] = nextState3D(x, y, z, state);
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
		
		System.out.println("Active (silver): " + active);
	}
	
	private static boolean nextState4D(int x, int y, int z, int w, boolean[][][][] state){
		int total = 0;
		for(int dx = -1; dx <= 1; dx++){
			for(int dy = -1; dy <= 1; dy++){
				for(int dz = -1; dz <= 1; dz++){
					for(int dw = -1; dw <= 1; dw++){
						if(!(dx == 0 && dy == 0 && dz == 0 && dw == 0)){
							if(state[x + dx][y + dy][z + dz][w + dw]){
								total++;
							}
						}
					}
				}
			}
		}
		
		return state[x][y][z][w] ? (total == 2 || total == 3) : (total == 3);
	}
	
	private static boolean nextState3D(int x, int y, int z, boolean[][][] state){
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
