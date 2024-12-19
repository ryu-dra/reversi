package org.example;

import java.util.ArrayList;

//盤面を生成。ひっくり返す。盤面の状況(各プレイヤーがひっくり返せる場所、各石の数)の把握

public class Board {
	static final String W = "W";
	static final String B = "B";
	static final String E = "E";
	
	private String[][] board;
	
	public Board() {
		this.board = new String[8][8];
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				board[i][j] = E;
			}
		}
		board[3][3] = B;
		board[3][4] = W;
		board[4][3] = W;
		board[4][4] = B;
	}
	
	public String[][] getBoard(){
		return this.board;
	}
	
	public void show_board() {
		System.out.print("  ");
		for(int y=0; y<8; y++) {
			System.out.printf(" %s", y);
		}
		System.out.println();
		for(int x=0; x<8; x++) {
			System.out.printf("%s: ", x);
			for(int y=0; y<8; y++) {
				System.out.printf("%s ",board[x][y]);
			}
			System.out.println();
		}
	}
	

	
	public ArrayList<int[]> turnable_pos_list(String color){
		var turnable_list = new ArrayList<int[]>();
		
		for(int x=0; x<8; x++) {
			for(int y=0; y<8; y++) {
				if(is_turnable(color, x, y)) {
					turnable_list.add(new int[]{x,y});
				}
			}
		}
		
		return turnable_list;
	}
	
	public int num_of_stone(String color) {
		int num = 0;
		for(var row :board) {
			for(var ele : row) {
				if(ele.equals(color)) {
					num++;
				}
			}
		}
		return num;
	}
	
	public void flip(String color, int x, int y) {			//ひっくり返せるかどうかは、Main側で判定すること。
		String opo_color = null;
		switch(color) {
		case B:
			opo_color = W;
			break;
		case W:
			opo_color = B;
		}
		
		if(is_turnable(color,x,y)) {
			board[x][y] = color;
			
			for(int deg = 0; deg<360; deg+=45) {
				if(turnable_line(color,x,y,deg)) {
					double rad = Math.toRadians(deg);
					int x_vec = (int)(Math.cos(rad)*1.5);		//8方向をうまく調べるために
					int y_vec = (int)(Math.sin(rad)*1.5);
					x+=x_vec;
					y+=y_vec;
					while(board[x][y].equals(opo_color)) {
						board[x][y] = color;
						x+=x_vec;
						y+=y_vec;
					}
				}
			}
		}else {
			System.out.println("can't flip");
		}
	}
	
	public boolean is_turnable(String color, int x, int y) {
		if(!board[x][y].equals(E)) {
			return false;
		}
		
		for(int deg = 0; deg < 360; deg+=45) {
			if(turnable_line(color,x,y,deg)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean turnable_line(String color, int x, int y, int deg) {	//下方向を0度として、8方向考える。
		String opo_color = null;
		boolean res = false;
		boolean opo_exist = false;
		
		switch(color) {
			case B:
				opo_color = W;
				break;
			case W:
				opo_color = B;
		}
		
		double rad = Math.toRadians(deg);
		int x_vec = (int)(Math.cos(rad)*1.5);
		int y_vec = (int)(Math.sin(rad)*1.5);
		
		x+=x_vec;
		y+=y_vec;
		while(x>=0 && x<8 && y>=0 && y<8) {
			if(board[x][y].equals(color)) {
				if(opo_exist) {
					res = true;
					break;
				}
			}else if(board[x][y].equals(opo_color)) {
				opo_exist = true;
			}else {
				break;
			}
			
			x+=x_vec;
			y+=y_vec;
		}
		
		return res;
	}
	

	
}




