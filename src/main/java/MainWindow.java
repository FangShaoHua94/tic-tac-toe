import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class MainWindow extends AnchorPane {

    private static final int DIMENSION=3;
    private static final int NODE_DIMENSION =98;
    private Tile[][] board = new Tile[DIMENSION][DIMENSION];
    private boolean isPlayerOneTurn;

    @FXML
    private Button resetButton;

    @FXML
    private GridPane boardPane;

    @FXML
    public void initialize(){
        isPlayerOneTurn=true;
        for(int i=0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                board[i][j]=new Tile(i,j);
            }
        }
    }

    @FXML
    public void reset(){
        for(int i=0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                boardPane.getChildren().removeAll(board[i][j].getButton(),board[i][j].getLabel());
            }
        }
        initialize();
    }

    public void changePlayer(){
        isPlayerOneTurn= !isPlayerOneTurn;
    }

    public void checkEndGame(){
        if(checkBoard().equals(State.NONE)){
            return;
        }
        if(checkBoard().equals(State.PLAYER_1)){
            System.out.println("player 1 win");
        }else if(checkBoard().equals(State.PLAYER_2)){
            System.out.println("player 2 win");
        }
        for(int i=0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                board[i][j].disable();
            }
        }
    }

    private State checkBoard(){
        // check row
        for(int i=0;i<DIMENSION;i++){
            if(checkTriplets(board[i][0],board[i][1],board[0][2])){
                return board[i][0].state;
            }
        }

        // check col
        for(int i=0;i<DIMENSION;i++){
            if(checkTriplets(board[0][i],board[1][i],board[2][i])){
                return board[i][0].state;
            }
        }

        // check diagonal
        if(checkTriplets(board[0][0],board[1][1],board[2][2])){
            return board[0][0].state;
        }

        if(checkTriplets(board[0][2],board[1][1],board[2][0])){
            return board[0][2].state;
        }

        return State.NONE;
    }

    private boolean checkTriplets(Tile a,Tile b, Tile c){
        return !a.isUnmark() && a.equals(b) && b.equals(c);
    }

    public void print(){
        for(int i=0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                if(board[i][j].state.equals(State.PLAYER_1)){
                    System.out.print("O");
                }else if(board[i][j].state.equals(State.PLAYER_2)){
                    System.out.print("X");
                }else{
                    System.out.print("#");
                }
            }
            System.out.println();
        }
        System.out.println("----------------------------");
    }

    enum State{
        NONE,PLAYER_1,PLAYER_2
    }

    class Tile{

        private static final String PLAYER_1="  O";
        private static final String PLAYER_2="  X";

        private Button button;
        private Label label;
        private State state;

        public Tile(int rowIndex, int colIndex){
            label = addLabel(rowIndex,colIndex);
            button = addButton(rowIndex,colIndex);
            state = State.NONE;
        }

        private Label addLabel(int rowIndex,int colIndex){
            Label label = new Label();
            label.setPrefSize(NODE_DIMENSION, NODE_DIMENSION);
            label.setMinSize(NODE_DIMENSION, NODE_DIMENSION);
            label.setMaxSize(NODE_DIMENSION, NODE_DIMENSION);
            label.getStylesheets().add(getClass().getResource("/view/Label.css").toExternalForm());
            boardPane.add(label,colIndex,rowIndex);
            return label;
        }

        private Button addButton(int rowIndex,int colIndex){
            Button button = new Button();
            button.setPrefSize(NODE_DIMENSION, NODE_DIMENSION);
            button.setMinSize(NODE_DIMENSION, NODE_DIMENSION);
            button.setMaxSize(NODE_DIMENSION, NODE_DIMENSION);

            button.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    mark();
                    checkEndGame();
                    print();
                    changePlayer();
                }
            });
            boardPane.add(button,colIndex,rowIndex);
            return button;
        }

        private void mark(){
            if(isPlayerOneTurn){
                state=State.PLAYER_1;
            }else{
                state=State.PLAYER_2;
            }
            button.setDisable(true);
            display(state);
        }

        private void display(State state){
            if(state.equals(State.PLAYER_1)){
                label.setText(PLAYER_1);
            }else{
                label.setText(PLAYER_2);
            }
        }

        public boolean isUnmark(){
            return state.equals(State.NONE);
        }

        public Button getButton(){
            return button;
        }

        public Label getLabel(){
            return label;
        }

        public void disable(){
            button.setDisable(true);
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof Tile // instanceof handles nulls
                    && state.equals(((Tile) other).state)); // state check
        }
    }





}
