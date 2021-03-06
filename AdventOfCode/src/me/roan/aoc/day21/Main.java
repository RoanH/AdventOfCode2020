package me.roan.aoc.day21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main{
	private static List<Food> foods = new ArrayList<Food>();

	public static void main(String[] args){
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream("me/roan/aoc/day21/input"));
		
		while(in.hasNext()){
			foods.add(new Food(in.nextLine()));
		}
		
		silverStar();
	}
	
	private static void silverStar(){
		List<String> allergens = foods.stream().flatMap(Food::getAllergens).distinct().collect(Collectors.toList());
		Map<String, List<String>> contains = new HashMap<String, List<String>>();
		
		allergens.forEach(a->contains.put(a, new ArrayList<String>()));
		
		for(Food food : foods){
			for(String allergen : food.allergens){
				List<String> possible = contains.get(allergen);
				if(possible.isEmpty()){
					possible.addAll(food.ingredients);
				}else{
					possible.removeIf(a->!food.ingredients.contains(a));
				}
			}
		}
		
		List<String> candidates = contains.values().stream().flatMap(List::stream).distinct().collect(Collectors.toList());
		List<String> safe = foods.stream().flatMap(Food::getIngredients).distinct().filter(i->!candidates.contains(i)).collect(Collectors.toList());
		int total = 0;
		for(String i : safe){
			for(Food food : foods){
				if(food.ingredients.contains(i)){
					total++;
				}
			}
		}
		System.out.println("Safe: " + total);
		
		goldStar(contains);
	}
	
	private static void goldStar(Map<String, List<String>> contains){
		boolean hit;
		do{
			hit = false;
			for(List<String> possible : contains.values()){
				if(possible.size() == 1){
					for(List<String> other : contains.values()){
						if(possible != other){
							other.remove(possible.get(0));
						}
					}
				}else{
					hit = true;
				}
			}
		}while(hit);
		
		System.out.println("List: " + contains.entrySet().stream().sorted(Main::alphabetical).map(Entry::getValue).flatMap(List::stream).reduce("", (res, e)->{
			return res.isEmpty() ? e : (res + "," + e);
		}));
	}
	
	private static int alphabetical(Entry<String, List<String>> ea, Entry<String, List<String>> eb){
		String a = ea.getKey();
		String b = eb.getKey();
		for(int i = 0; i < Math.min(a.length(), b.length()); i++){
			int eq = Character.compare(a.charAt(i), b.charAt(i));
			if(eq != 0){
				return eq;
			}
		}
		return 0;
	}
	
	private static class Food{
		private List<String> ingredients = new ArrayList<String>();
		private List<String> allergens = new ArrayList<String>();
		
		private Food(String line){
			String[] args = line.split(" ");
			boolean contains = false;
			for(String arg : args){
				if(arg.equals("(contains")){
					contains = true;
					continue;
				}
				if(contains){
					allergens.add(arg.substring(0, arg.length() - 1));
				}else{
					ingredients.add(arg);
				}
			}
		}
		
		public Stream<String> getAllergens(){
			return allergens.stream();
		}
		
		public Stream<String> getIngredients(){
			return ingredients.stream();
		}
	}
}
