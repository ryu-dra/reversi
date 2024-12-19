package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.StackPane;

public class ScreenController {
    
    private static final int BOARD_SIZE = 8; // オセロの盤面サイズ
    
    private boolean isClicked = false;
    private int coord[] = new int[2];
    

    @FXML
    private GridPane board;

    // 初期化処理
    @FXML
    public void initialize() {
        // 盤面を動的に生成
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                StackPane cell = createCell(row, col);
                board.add(cell, col, row); // GridPaneの列、行の順番
            }
        }
    }
    
    
    //座標が押されるイベントを無限ループで待つ。					改善できそう…？
    public ArrayList<Integer> select(){
    	var list = new ArrayList<Integer>(2);
    	while(!isClicked) {							//GridPaneのセルが押されるイベントが起こるまで無限ループ
    		try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	isClicked = false;
    	list.add(coord[0]);
    	list.add(coord[1]);
//    	System.out.println(list.get(0));		デバッグ用
    	return list;
    }

    // セルを作成
    private StackPane createCell(int row, int col) {
        StackPane cell = new StackPane();
        cell.setPrefSize(50, 50); // セルのサイズ
        cell.setStyle("-fx-border-color: black; -fx-background-color: lightgreen;");

        // クリックイベントを設定
        cell.setOnMouseClicked(event -> {			//自身のオブジェクトのローカル変数を直接変更				改善できそう…？
        	coord[0] = row;
        	coord[1] = col;
        	isClicked = true;
        	
        });

        return cell;
    }

    // 石を置く処理
    private void placeStone(StackPane cell, Color color, int row, int col) {
        if (!cell.getChildren().isEmpty()) {
            return; // すでに石が置かれている場合は無視
        }

        // 石を作成
        Circle stone = new Circle(20); // 半径20の円
        stone.setFill(color); // 黒い石（仮）
        // 石をセルに追加
        cell.getChildren().add(stone);
    }
    
    
    
    
    public void show_UI(String[][] board) {			//UIに、文字列boardの情報(盤面)を表示   (石が上書きされるような処理だから、よくない気がする)
    	for(int i=0; i<8; i++) {
        	for(int j=0; j<8; j++) {
        		if(board[i][j].equals("E"))
        			continue;
        		
        		Color color = null;
        		switch(board[i][j]) {
        		case "W":
        			color = Color.WHITE;
        			break;
        		case "B":
        			color = Color.BLACK;
        			break;
        		}
        		
//        		以下デバッグ用        		
//        		Node node = getNodeFromGridPane(i, j);
//        		if (node == null) {
//        		    System.out.println("No node found at position (" + i + ", " + j + ")");
//        		    return;
//        		}
//        		if (!(node instanceof StackPane)) {
//        		    System.out.println("Node at position (" + i + ", " + j + ") is not a StackPane");
//        		    return;
//        		}
        		
        		StackPane cell = (StackPane)getNodeFromGridPane(i, j);
                // 石を作成
                Circle stone = new Circle(20); // 半径20の円
                stone.setFill(color); // 黒い石（仮）
                
//                //デバッグ用
//                if (!javafx.application.Platform.isFxApplicationThread()) {
//                    System.out.println("Not on JavaFX Application Thread");
//                } else {
//                    System.out.println("On JavaFX Application Thread");
//                }
                
                Platform.runLater(() -> {			//UIスレッドに処理を渡す
                    cell.getChildren().add(stone);
                });
        	}
    	}
    }
    
    public void changeStone(String[][] pastBoard, String[][] currentBboard) {			//show_UIに組み込める気がする。しかも石が上書きだからよくない気がする
    	var map = changedMap(pastBoard, currentBboard);

    	
    	for(var e :map.keySet()) {
    		
    		StackPane cell = (StackPane)getNodeFromGridPane(e.get(0), e.get(1));
    		
    		
    		switch(map.get(e)) {			//ラムダ式の制約のため、ローカル変数を使わずに実行している。
    		case "W":
                Platform.runLater(() -> {				
                    this.placeStone(cell, Color.WHITE, BOARD_SIZE, BOARD_SIZE);
                });
    			break;
    		case "B":
    			Platform.runLater(() -> {				
                    this.placeStone(cell, Color.BLACK, BOARD_SIZE, BOARD_SIZE);
                });
    			break;
    		default:
    			System.out.println("An Error is occured");
    		}
    		
    	}
    }
    
    //石をひっくり返す前から変更のあった座標を記録したMapを作成
    private static HashMap<ArrayList<Integer>, String> changedMap(String[][] pastBoard, String[][] currentBoard){
    	var map = new HashMap<ArrayList<Integer>, String>(24);
    	for(int i=0; i<8; i++) {
    		for(int j=0; j<8; j++) {
    			if(!pastBoard[i][j].equals(currentBoard[i][j])) {
    				map.put(new ArrayList<Integer>(Arrays.asList(i,j)), currentBoard[i][j]);
    			}
    		}
    	}
    	return map;
    }
    
    // 行と列のインデックスからセルを取得するヘルパーメソッド
    private Node getNodeFromGridPane(int row, int col) {
        for (Node node : this.board.getChildren()) {
            Integer rIndex = GridPane.getRowIndex(node);
            Integer cIndex = GridPane.getColumnIndex(node);
            // 行と列のインデックスが一致する場合にそのノードを返す
            if (rIndex != null && rIndex == row && cIndex != null && cIndex == col) {
            	
                return node;
            }
        }
        return null;  // セルが見つからない場合
    }
}
