package me.roan.aoc.day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main{
	
	private static char[][] original;

	public static void main(String[] args) throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day11/input")));
		List<String> lines = in.lines().collect(Collectors.toList());
		in.close();
		
		original = new char[lines.size()][];
		for(int i = 0; i < lines.size(); i++){
			original[i] = lines.get(i).toCharArray();
		}
		
		silverStar();
		goldStar();
	}
	
	public static void goldStar(){
		char[][] state = new char[original.length][];
		char[][] next = new char[original.length][original[0].length];
		for(int i = 0; i < state.length; i++){
			state[i] = Arrays.copyOf(original[i], original[i].length);
		}
		
		while(!Arrays.deepEquals(state, next)){
			for(int i = 0; i < state.length; i++){
				for(int j = 0; j < state[0].length; j++){
					next[i][j] = nextStateGold(state, i, j);
				}
			}
			
			char[][] tmp = state;
			state = next;
			next = tmp;
		}
		
		int occupied = 0;
		for(int i = 0; i < state.length; i++){
			for(int j = 0; j < state[0].length; j++){
				if(state[i][j] == '#'){
					occupied++;
				}
			}
		}
		
		System.out.println("Occupied: " + occupied);
	}
	
	public static void silverStar(){
		char[][] state = new char[original.length][];
		char[][] next = new char[original.length][original[0].length];
		for(int i = 0; i < state.length; i++){
			state[i] = Arrays.copyOf(original[i], original[i].length);
		}
		
		while(!Arrays.deepEquals(state, next)){
			for(int i = 0; i < state.length; i++){
				for(int j = 0; j < state[0].length; j++){
					next[i][j] = nextState(state, i, j);
				}
			}
			
			char[][] tmp = state;
			state = next;
			next = tmp;
		}
		
		int occupied = 0;
		for(int i = 0; i < state.length; i++){
			for(int j = 0; j < state[0].length; j++){
				if(state[i][j] == '#'){
					occupied++;
				}
			}
		}
		
		System.out.println("Occupied: " + occupied);
	}
	
	private static char nextState(char[][] state, int i, int j){
		if(state[i][j] == '.'){
			return '.';
		}else if(state[i][j] == 'L' && !getAdjacent(state, i, j).anyMatch(c->c == '#')){
			return '#';
		}else if(state[i][j] == '#' && getAdjacent(state, i, j).filter(c->c == '#').count() >= 4){
			return 'L';
		}else{
			return state[i][j];
		}
	}
	
	private static char nextStateGold(char[][] state, int i, int j){
		if(state[i][j] == '.'){
			return '.';
		}else if(state[i][j] == 'L' && !getVisible(state, i, j).anyMatch(c->c == '#')){
			return '#';
		}else if(state[i][j] == '#' && getVisible(state, i, j).filter(c->c == '#').count() >= 5){
			return 'L';
		}else{
			return state[i][j];
		}
	}
	
	private static Stream<Character> getVisible(char[][] state, int i, int j){
		return Stream.of(
			find(state, i, j, -1, -1),
			find(state, i, j, -1, 0),
			find(state, i, j, -1, 1),
			find(state, i, j, 0, -1),
			find(state, i, j, 0, 1),
			find(state, i, j, 1, -1),
			find(state, i, j, 1, 0),
			find(state, i, j, 1, 1)
		).filter(Optional::isPresent).map(Optional::get);
	}

	private static Optional<Character> find(char[][] state, int i, int j, int di, int dj){
		Optional<Character> seat;
		do{
			seat = get(state, i += di, j += dj);
		}while(seat.filter(c->c == '.').isPresent());
		return seat;
	}
	
	private static Stream<Character> getAdjacent(char[][] state, int i, int j){
		return Stream.of(
			get(state, i - 1, j - 1),
			get(state, i - 1, j),
			get(state, i - 1, j + 1),
			get(state, i, j - 1),
			get(state, i, j + 1),
			get(state, i + 1, j - 1),
			get(state, i + 1, j),
			get(state, i + 1, j + 1)
		).filter(Optional::isPresent).map(Optional::get);
	}
	
	private static Optional<Character> get(char[][] state, int i, int j){
		if(i >= 0 && j >= 0 && i < state.length && j < state[0].length){
			return Optional.of(state[i][j]);
		}else{
			return Optional.empty();
		}
	}
}
