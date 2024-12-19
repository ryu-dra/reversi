package org.example;

import java.util.Random;

public class NPC_random_Player extends Player{
	
	public NPC_random_Player(String color) {
		super(color,()->{return null;});
	}
	
	@Override
	public void put_on(Board board) {
		
		var list = board.turnable_pos_list(get_color()); 
		var rnd = new Random();
		if(list.size() == 0) {
			System.out.println("err");
		}else {
			System.out.println(list.size());
			var r_element = list.get(rnd.nextInt(list.size()));
			board.flip(get_color(), r_element[0], r_element[1]);
		}
		
	}
	
}
