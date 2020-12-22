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
		goldStar();
	}
	
	private static void goldStar(){
		System.out.println("Tile count: " + tiles.size());
		
		System.out.println("Tile layout:");
		tiles.forEach(t->System.out.println(t.id + " | " + t.neighborString()));
		
		Tile topLeft = tiles.stream().filter(Tile::isCorner).findFirst().get();

		if(topLeft.neighbor[0] == null && topLeft.neighbor[3] == null){
			topLeft.rotate(90);
		}else if(topLeft.neighbor[3] == null && topLeft.neighbor[1] == null){
			topLeft.rotate(180);
		}else if(topLeft.neighbor[1] == null && topLeft.neighbor[2] == null){
			topLeft.rotate(270);
		}
		
		//Align tiles
		System.out.println("Tile grid:");
		boolean[][] data = new boolean[8 * (int)Math.sqrt(tiles.size())][8 * (int)Math.sqrt(tiles.size())];
		int y = 0;
		int x = 0;
		Tile last = topLeft;
		Tile tile;
		while(true){
			System.out.print(last.id + " ");
			Tile left = last;
			last.write(x, y, data);
			x += 8;
			while((tile = left.neighbor[3]) != null){
				System.out.print(tile.id + " ");
				tile.alignToRightOf(left);
				left = tile;
				tile.write(x, y, data);
				x += 8;
			}
			
			System.out.println();
			if(last.neighbor[1] == null){
				break;
			}
			
			tile = last.neighbor[1];
			tile.alignToBottomOf(last);
			last = tile;

			y += 8;
			x = 0;
		}
		
		System.out.println("Reassembled image:");
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[0].length; j++){
				System.out.print(data[i][j] ? '#' : '.');
			}
			System.out.println();
		}

		//Define nessie
		boolean[][][] nessie = new boolean[][][]{
			new boolean[][]{
				new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
				new boolean[]{true,  false, false, false, false, true,  true,  false, false, false, false, true,  true,  false, false, false, false, true,  true,  true},
				new boolean[]{false, true,  false, false, true,  false, false, true,  false, false, true,  false, false, true,  false, false, true,  false, false, false}
			},
			new boolean[3][20],
			new boolean[20][3],
			new boolean[20][3]
		};
		nessie[1][0] = nessie[0][2];
		nessie[1][1] = nessie[0][1];
		nessie[1][2] = nessie[0][0];
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 3; j++){
				nessie[2][i][j] = nessie[0][j][i];
				nessie[3][i][2 - j] = nessie[0][j][i];
			}
		}
		
		//Find nessie
		int nessieTiles = 0;
		for(boolean[][] variant : nessie){
			for(int i = 0; i < data.length - variant[0].length; i++){
				inner: for(int j = 0; j < data.length - variant.length; j++){
					for(int lx = 0; lx < variant[0].length; lx++){
						for(int ly = 0; ly < variant.length; ly++){
							if(variant[ly][lx] && !data[j + ly][i + lx]){
								continue inner;
							}
						}
					}
					System.out.println("Found nessie");
					nessieTiles += 15;
				}
			}
		}
		
		//Total '#' count
		int total = 0;
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data.length; j++){
				if(data[j][i]){
					total++;
				}
			}
		}
		
		System.out.println("Water roughness: " + (total - nessieTiles));
	}
	
	private static void silverStar(){
		long product = 1;
		for(int i = 0; i < tiles.size(); i++){
			Tile t = tiles.get(i);
			
			int match = 0;
			sides: for(int s = 1; s <= 4; s++){
				for(int j = 0; j < tiles.size(); j++){
					if(j != i && t.canMatch(s, tiles.get(j))){
						match++;
						t.neighbor[s - 1] = tiles.get(j);
						continue sides;
					}
				}
			}
			
			if(match <= 2){
				System.out.println("Corner tile: " + t.id + " m:" + match);
				product *= t.id;
			}
		}
		System.out.println("Corner product: " + product);
	}
	
	//s: 1 = top, 2 = bottom, 3 = left, 4 = right
	private static class Tile{
		private int id;
		private boolean[][] data = new boolean[10][10];
		private Tile[] neighbor = new Tile[4];
		
		public void write(int x, int y, boolean[][] data){
			for(int i = 1; i < this.data.length - 1; i++){
				for(int j = 1; j < this.data[0].length - 1; j++){
					data[y + i - 1][x + j - 1] = this.data[i][j];
				}
			}
		}
		
		public void alignToRightOf(Tile other){
			if(neighbor[0] == other){
				rotate(90);
			}else if(neighbor[1] == other){
				rotate(270);
			}else if(neighbor[3] == other){
				rotate(180);
			}
			
			if(!Arrays.equals(getSide(3), other.getSide(4))){
				flipTopDown();
			}
		}
		
		public void alignToBottomOf(Tile other){
			if(neighbor[1] == other){
				rotate(180);
			}else if(neighbor[2] == other){
				rotate(270);
			}else if(neighbor[3] == other){
				rotate(90);
			}
			
			if(!Arrays.equals(getSide(1), other.getSide(2))){
				flipLeftRight();
			}
		}
		
		public void flipTopDown(){
			for(int i = 0; i < 5; i++){
				boolean[] tmp = data[i];
				data[i] = data[9 - i];
				data[9 - i] = tmp;
			}
			Tile tmp = neighbor[0];
			neighbor[0] = neighbor[1];
			neighbor[1] = tmp;
		}
		
		public void flipLeftRight(){
			for(int r = 0; r < 10; r++){
				for(int i = 0; i < 5; i++){
					boolean tmp = data[r][i];
					data[r][i] = data[r][9 - i];
					data[r][9 - i] = tmp;
				}
			}
			Tile tmp = neighbor[2];
			neighbor[2] = neighbor[3];
			neighbor[3] = tmp;
		}
		
		//it's either 90, 180 or 270, anything else doesn't exist
		public void rotate(int deg){
			boolean[][] rot = new boolean[10][10];
			Tile[] rel = new Tile[4];
			switch(deg){
			case 90:
				for(int i = 0; i < 10; i++){
					for(int j = 0; j < 10; j++){
						rot[j][i] = data[i][9 - j];
					}
				}
				rel[0] = neighbor[3];
				rel[1] = neighbor[2];
				rel[2] = neighbor[0];
				rel[3] = neighbor[1];
				break;
			case 180:
				for(int i = 0; i < 10; i++){
					for(int j = 0; j < 10; j++){
						rot[9 - i][j] = data[i][9 - j];
					}
				}
				rel[0] = neighbor[1];
				rel[1] = neighbor[0];
				rel[2] = neighbor[3];
				rel[3] = neighbor[2];
				break;
			case 270:
				for(int i = 0; i < 10; i++){
					for(int j = 0; j < 10; j++){
						rot[j][9 - i] = data[i][j];
					}
				}
				rel[0] = neighbor[2];
				rel[1] = neighbor[3];
				rel[2] = neighbor[1];
				rel[3] = neighbor[0];
				break;
			case 360:
			case 0:
				rot = data;
				rel = neighbor;
				break;
			default:
				throw new RuntimeException("Don't rotate by that");
			}
			data = rot;
			neighbor = rel;
		}
		
		public boolean isCorner(){
			int n = 0;
			if(neighbor[0] == null){
				n++;
			}
			if(neighbor[1] == null){
				n++;
			}
			if(neighbor[2] == null){
				n++;
			}
			if(neighbor[3] == null){
				n++;
			}
			return n == 2;
		}
		
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
		
		private String neighborString(){
			return "[1=" + (neighbor[0] == null ? "null" : neighbor[0].id)
				+ ",2=" + (neighbor[1] == null ? "null" : neighbor[1].id)
				+ ",3=" + (neighbor[2] == null ? "null" : neighbor[2].id)
				+ ",4=" + (neighbor[3] == null ? "null" : neighbor[3].id) + "]";
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
