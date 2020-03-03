import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.beans.PropertyChangeListener;
import java.util.List;

public class Board {

    private static final int DIMENSION=3;
    public static Player player;

    private Tile[][] board=new Tile[DIMENSION][DIMENSION];
    private TileBean tileBean=new TileBean();
    private boolean isGameOver=false;

    public Board(GridPane gridPane){
        player=Player.PLAYER_1;
        List<Node> buttons=gridPane.getChildren();
        for(int i=0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                Button button=(Button)buttons.get(i*DIMENSION+j);
                button.setText(Mark.EMPTY.toString());
                tileBean.addPropertyChangeListener(e->{
                    checkBoard();
                    changePlayer();
                });
                Tile tile=new Tile(button,tileBean);
                board[i][j]=tile;
            }
        }
    }

    private void gameOver(){
        if(!isGameOver) {
            for (int i = 0; i < DIMENSION; i++) {
                for (int j = 0; j < DIMENSION; j++) {
                    board[i][j].disableButton();
                }
            }
            isGameOver=true;
            tileBean.removePropertyChangeListener();
        }
//        drawLine
//        displayWinner
    }

    private void checkBoard(){
        Mark mark_1,mark_2,mark_3;

        for(int i=0;i<DIMENSION;i++){

            // horizontal
            mark_1=board[i][0].getMark();
            mark_2=board[i][1].getMark();
            mark_3=board[i][2].getMark();
            if(mark_1.equals(mark_2) && mark_2.equals(mark_3) && !mark_1.equals(Mark.EMPTY)){
                System.out.println("horizontal");
                gameOver();
                return;
            }

            // vertical
            mark_1=board[0][i].getMark();
            mark_2=board[1][i].getMark();
            mark_3=board[2][i].getMark();
            if(mark_1.equals(mark_2) && mark_2.equals(mark_3) && !mark_1.equals(Mark.EMPTY)){
                System.out.println("vertical");
                gameOver();
                return;
            }
        }

        // diagonal
        mark_1=board[0][0].getMark();
        mark_2=board[1][1].getMark();
        mark_3=board[2][2].getMark();
        if(mark_1.equals(mark_2) && mark_2.equals(mark_3) && !mark_1.equals(Mark.EMPTY)){
            System.out.println("diagonal");
            gameOver();
            return;
        }

        //diagonal
        mark_1=board[0][2].getMark();
        mark_2=board[1][1].getMark();
        mark_3=board[2][0].getMark();
        if(mark_1.equals(mark_2) && mark_2.equals(mark_3) && !mark_1.equals(Mark.EMPTY)){
            System.out.println("diagonal");
            gameOver();
        };
    }

    //
    private static void changePlayer(){
        if(player.equals(Player.PLAYER_1)){
            player=Player.PLAYER_2;
        }else{
            player=Player.PLAYER_1;
        }
    }

}
