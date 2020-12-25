package me.roan.aoc.day24;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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
			current.next = true;
		}
		
		System.out.println("Flipped: " + ref.tiles.values().stream().map(Map::values).flatMap(Collection::stream).filter(Tile::isFlipped).count());
	
		goldStar(ref);
	}
	
	private static void goldStar(Tile ref){
		for(int i = 0; i < 100; i++){
			//Put white tile around all flipped tiles
			ref.tiles.values().stream().map(Map::values).flatMap(Collection::stream).filter(Tile::isFlipped).collect(Collectors.toList()).forEach(Tile::ensureNeighbors);
			
			//Compute next state
			ref.tiles.values().stream().map(Map::values).flatMap(Collection::stream).forEach(Tile::computeNext);
			
			//Apply next state
			ref.tiles.values().stream().map(Map::values).flatMap(Collection::stream).forEach(Tile::applyNext);

			System.out.println("Day " + (i + 1) + ": " + ref.tiles.values().stream().map(Map::values).flatMap(Collection::stream).filter(Tile::isFlipped).count());
		}
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
		private boolean next = false;
		
		private Tile(int x, int y, Map<Integer, Map<Integer, Tile>> tiles){
			this.tiles = tiles;
			this.x = x;
			this.y = y;
			tiles.computeIfAbsent(x, v->new HashMap<Integer, Tile>()).put(y, this);
		}
		
		public void applyNext(){
			flipped = next;
		}
		
		public void computeNext(){
			int black = 0;
			if(e != null && e.flipped){
				black++;
			}
			if(se != null && se.flipped){
				black++;
			}
			if(ne != null && ne.flipped){
				black++;
			}
			if(w != null && w.flipped){
				black++;
			}
			if(sw != null && sw.flipped){
				black++;
			}
			if(nw != null && nw.flipped){
				black++;
			}
			
			if(flipped){
				next = !(black == 0 || black > 2);
			}else{
				next = black == 2;
			}
		}
		
		public void ensureNeighbors(){
			get("e");
			get("se");
			get("ne");
			get("w");
			get("sw");
			get("nw");
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
