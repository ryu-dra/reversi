package org.example;

import java.util.Scanner;

public class Player {
	private String color;
	
	public Player(String color) {
		this.color = color;
	}
	
	public void put_on(Board board) {
		System.out.println("現在おくことができる座標は以下の通りです。");
		board.turnable_pos_list(color).stream().forEach(v -> System.out.print("("+v[0]+","+v[1]+") "));
		System.out.println();
		scan:
		while(true){
			System.out.println("おきたい座標を入力してください。");
			var scn = new Scanner(System.in);
			int x = scn.nextInt();
			int y = scn.nextInt();
			for(var ele : board.turnable_pos_list(color)) {
				if(x==ele[0] && y==ele[1]) {
					board.flip(color, x, y);
					break scan;
				}
			}
			System.out.println("その場所にはおけません。");
			
		}
	}
 
	public String get_color() {
		return color;
	}
}
