package org.example;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Supplier;

public class Player {
	private String color;
	private Supplier<ArrayList<Integer>> sup;			//指す場所を指定するメソッド
	
	public Player(String color, Supplier<ArrayList<Integer>> sup) {
		this.color = color;
		this.sup = sup;
	}
	
	public void put_on(Board board) {
		System.out.println("現在おくことができる座標は以下の通りです。");
		board.turnable_pos_list(color).stream().forEach(v -> System.out.print("("+v[0]+","+v[1]+") "));
		System.out.println();
		System.out.println("input coordinate.");
		
		inputCorrectCoordinate:
		while(true){
			ArrayList<Integer> list = (ArrayList<Integer>) sup.get();
//			System.out.println("list :"+list.get(0));		//デバッグ用
			int x = list.get(0);
			int y = list.get(1);
			for(var ele : board.turnable_pos_list(color)) {		//指定した座標が、「ひっくり返せるリスト」に入っているかか判定。有効なら処理をしてbreak
				if(x==ele[0] && y==ele[1]) {
					board.flip(color, x, y);
					break inputCorrectCoordinate;
				}
			}
			System.out.println("You can't put on this field.");
			
		}
	}
	
	ArrayList<Integer> put() {			//標準入力用メソッド
		var scn = new Scanner(System.in);
		var list = new ArrayList<Integer>(2);
		list.add(scn.nextInt());
		list.add(scn.nextInt());
		scn.close();
		return list;
	}
 
	public String get_color() {
		return color;
	}
}
