package me.roan.aoc.day24;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main{
	private static final List<String> instructions = new ArrayList<String>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day24/input"));
		
		while(in.hasNext()){
			instructions.add(in.nextLine());
		}
		
		silverStar();
	}
	
	private static void silverStar(){
		Tile ref = new Tile(0, 0, new HashMap<Integer, Map<Integer, Tile>>());
		
		for(String instruction : instructions){
			Tile current = ref;
			for(int i = 0; i < instruction.length(); i++){
				switch(instruction.charAt(i)){
				case 'e':
					current = current.get("e");
					break;
				case 's':
					i++;
					if(instruction.charAt(i) == 'e'){
						current = current.get("se");
					}else{
						current = current.get("sw");
					}
					break;
				case 'w':
					current = current.get("w");
					break;
				case 'n':
					i++;
					if(instruction.charAt(i) == 'e'){
						current = current.get("ne");
					}else{
						current = current.get("nw");
					}
					break;
				}
			}
			current.flipped = !current.flipped;
		}
		
		System.out.println("Flipped: " + ref.tiles.values().stream().map(Map::values).flatMap(Collection::stream).filter(Tile::isFlipped).count());
	}
	
	private static class Tile{
		private Map<Integer, Map<Integer, Tile>> tiles;
		private Tile e;
		private Tile se;
		private Tile sw;
		private Tile w;
		private Tile nw;
		private Tile ne;
		private int x;
		private int y;
		private boolean flipped = false;
		
		private Tile(int x, int y, Map<Integer, Map<Integer, Tile>> tiles){
			this.tiles = tiles;
			this.x = x;
			this.y = y;
			tiles.computeIfAbsent(x, v->new HashMap<Integer, Tile>()).put(y, this);
		}
		
		public boolean isFlipped(){
			return flipped;
		}
		
		public Tile findTile(int x, int y){
			if(tiles.containsKey(x)){
				Tile tile = tiles.get(x).get(y);
				if(tile != null){
					return tile;
				}
			}
			return new Tile(x, y, tiles);
		}
		
		public Tile get(String dir){
			switch(dir){
			case "e":
				if(e == null){
					e = findTile(x + 2, y);
					e.w = this;
				}
				return e;
			case "se":
				if(se == null){
					se = findTile(x + 1, y + 1);
					se.nw = this;
				}
				return se;
			case "sw":
				if(sw == null){
					sw = findTile(x - 1, y + 1);
					sw.ne = this;
				}
				return sw;
			case "w":
				if(w == null){
					w = findTile(x - 2, y);
					w.e = this;
				}
				return w;
			case "nw":
				if(nw == null){
					nw = findTile(x - 1, y - 1);
					nw.se = this;
				}
				return nw;
			case "ne":
				if(ne == null){
					ne = findTile(x + 1, y - 1);
					ne.sw = this;
				}
				return ne;
			default:
				return null;
			}
		}
	}
}
