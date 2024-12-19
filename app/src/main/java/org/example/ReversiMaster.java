package org.example;

import static org.example.Board.*;

import java.util.function.BiConsumer;

public class ReversiMaster{
	private Board board;
	private Player p1;
	private Player p2;
	private BiConsumer<String[][], String[][]> con;	//盤面を表示させるための関数(様々な表し方に対応するため、ラムダ式にしている。)
	private String[][] past,current;
	
	public ReversiMaster(Board board, Player p1, Player p2, BiConsumer<String[][], String[][]> con) {
		this.board = board;
		this.p1 = p1;
		this.p2 = p2;
		this.con = con;
		past = board.getBoard();
		current = board.getBoard();
	}
	
	public void gameStart() {
		board.show_board();
		con.accept(past, current);
	}
	
	public int turn_p1() {
		
		if(board.num_of_stone(E)==0 || board.num_of_stone(B)==0 || board.num_of_stone(W)==0) {		//完全敗北orゲーム終了まで
			return 0;
		}
			
		System.out.println("p1's turn.");
		if(!board.turnable_pos_list(p1.get_color()).isEmpty()) {		//させる場所があるか判定
			p1.put_on(board);
			current = board.getBoard();
			board.show_board();		//標準出力
			con.accept(past, current);		//UI出力など
		}else {
			System.out.println("pass.");
		}
			
		return 1;	
	}
	
	public int turn_p2() {				//ほぼp1と同じ
		
		if(board.num_of_stone(E)==0 || board.num_of_stone(B)==0 || board.num_of_stone(W)==0) {
			return 0;
		}
			
		System.out.println("p2's turn");		//ここが違う
		if(!board.turnable_pos_list(p2.get_color()).isEmpty()) {
			p2.put_on(board);
			past = current;
			current = board.getBoard();
			board.show_board();
			con.accept(past, current);
		}else {
			System.out.println("pass.");
		}
		
		System.out.println("turn end.");		//ここが違う
		
		return 1;
	}
	
	public void finish(){
		
		System.out.println("finished!");
		board.show_board();
		past = current;
		current = board.getBoard();
		con.accept(past, current);
		System.out.println(p1.get_color()+": "+board.num_of_stone(p1.get_color()));
		System.out.println(p2.get_color()+": "+board.num_of_stone(p2.get_color()));
		if(board.num_of_stone(p1.get_color())>board.num_of_stone(p2.get_color())) {
			System.out.println("p1 win!");
		}else if(board.num_of_stone(p1.get_color())<board.num_of_stone(p2.get_color())){
			System.out.println("p2 win!");
		}else {
			System.out.println("draw");
		}
	}
}