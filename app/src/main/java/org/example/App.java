package org.example;

import java.util.concurrent.CompletableFuture;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
    	    	
      try {
            //FXMLからのシーングラフの読み込み
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
            Parent root = loader.load();
            ScreenController sController = loader.getController();

            //シーングラフのルートノードを設定したシーンの作成
            Scene scene = new Scene(root, 1000, 600);
            //ステージへのシーンの設定
            primaryStage.setScene(scene);
            primaryStage.setTitle("Reversi Game");
            primaryStage.show();
            
            //リバーシ実行のためにオブジェクトを宣言。
            var board = new Board();
            //Playerオブジェクトの作成
            var p1 = new Player("W", ()->sController.select());			
//            var p1 = new NPC_random_Player("W");				
            var p2 = new NPC_random_Player("B");			//ここで対戦相手を変更する。
            String str[][] = board.getBoard();
            var gm = new ReversiMaster(board,p1,p2, (past, current)-> sController.show_UI(str));	
           
            
            //UIスレッドと非同期でリバーシの処理を実行。(各ターンで実行待ちになったときにもUIが動くようにするために)
            CompletableFuture.runAsync(() -> {
            	
            	 gm.gameStart();
                 while(true) {
                 	if(gm.turn_p1() == 0) {
                 		break;
                 	}
                 	if(gm.turn_p2() == 0) {
                 		break;
                 	}
                 }
                 gm.finish();
                 
            });
            
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    

}
