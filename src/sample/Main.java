package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private boolean turnX = true;
    private boolean playable = true;
    private Tile[][] board = new Tile[3][3];
    private List<Combo> combos = new ArrayList<>();

    private Parent createContent(){
        GridPane root = new GridPane();
        root.setPrefSize(600, 650);
        //root.autosize();
        //root.setGridLinesVisible(true);

        for (int i= 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);

                root.getChildren().add(tile);


                board[j][i] = tile;
            }
        }
        //root.add(new Button("Restart"), 0,0);
        //horizontal
        for (int k = 0; k < 3; k++){
            combos.add(new Combo(board[0][k], board[1][k], board[2][k]));
        }
        //vertical
        for (int l = 0; l < 3; l++) {
            combos.add(new Combo(board[l][0], board[l][1], board[l][2]));
        }
        //diagonal
        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("TicTackToe");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private void checkState(){
        for (Combo combo : combos){
            if (combo.isComplete()){
                playable = false;
                break;
            }
        }
    }

    private class Combo{
        private Tile[] tiles;
        public  Combo(Tile... tiles){
            this.tiles = tiles;
        }
        public boolean isComplete(){
            if (tiles[0].getValue().isEmpty()){
                return false;
            }
            return  tiles[0].getValue().equals(tiles[1].getValue()) && tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

    private class Tile extends StackPane{
        private Text text = new Text();

        public Tile(){
            Rectangle border = new Rectangle(200, 200);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(72));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(event ->{
                if(!playable){
                    return;
                }

                if(event.getButton() == MouseButton.PRIMARY){
                    if (!turnX){
                        return;
                    }
                    drawX();
                    turnX = false;
                    checkState();
                } else if (event.getButton() == MouseButton.SECONDARY){
                    if (turnX){
                        return;
                    }
                    drawO();
                    turnX = true;
                    checkState();
                }
            });
        }

        public String getValue(){
            return text.getText();
        }

        private void drawX(){
            text.setText("X");
        }
        private void drawO(){
            text.setText("O");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
