import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;


public class Board {

    public static Player player=Player.PLAYER_1;
    private static final int DIMENSION=3;
    private Tile[][] board=new Tile[DIMENSION][DIMENSION];
    private List<TileBean> tileBeans=new ArrayList<>();

    public Board(GridPane gridPane){
        List<Node> buttons=gridPane.getChildren();
        for(int i=0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                Button button=(Button)buttons.get(i*DIMENSION+j);
                button.setDisable(false);
                button.setText(Mark.EMPTY.toString());
                TileBean tileBean=new TileBean();
                tileBeans.add(tileBean);
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
        for(int i=0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                board[i][j].disableButton();
            }
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
                System.out.println("1111");
                gameOver();
                return;
            }

            // vertical
            mark_1=board[0][i].getMark();
            mark_2=board[1][i].getMark();
            mark_3=board[2][i].getMark();
            if(mark_1.equals(mark_2) && mark_2.equals(mark_3) && !mark_1.equals(Mark.EMPTY)){
                System.out.println("2222");
                gameOver();
                return;
            }
        }

        // diagonal
        mark_1=board[0][0].getMark();
        mark_2=board[1][1].getMark();
        mark_3=board[2][2].getMark();
        if(mark_1.equals(mark_2) && mark_2.equals(mark_3) && !mark_1.equals(Mark.EMPTY)){
            System.out.println("33333");
            gameOver();
            return;
        }

        //diagonal
        mark_1=board[0][2].getMark();
        mark_2=board[1][1].getMark();
        mark_3=board[2][0].getMark();
        if(mark_1.equals(mark_2) && mark_2.equals(mark_3) && !mark_1.equals(Mark.EMPTY)){
            System.out.println("44444");
            gameOver();
        };
    }

    private static void changePlayer(){
        if(player.equals(Player.PLAYER_1)){
            player=Player.PLAYER_2;
        }else{
            player=Player.PLAYER_1;
        }
    }

}
