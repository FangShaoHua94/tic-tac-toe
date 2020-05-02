import com.sun.tools.javac.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

public class MainWindow extends AnchorPane {

    private static final int FLICKING = 10;
    private static final long FLICKING_DELAY = 200;
    private static final long CONVERSION_DELAY = 500;
    private static final int DIMENSION = 3;
    private static final int NODE_DIMENSION = 90;
    private Tile[][] board = new Tile[DIMENSION][DIMENSION];
    private boolean isPlayerOneTurn;

    @FXML
    private Button resetButton;

    @FXML
    private GridPane boardPane;

    /**
     * sets the board.
     */
    @FXML
    public void initialize() {
        isPlayerOneTurn = true;
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                board[i][j] = new Tile(i, j);
            }
        }
    }

    /**
     * resets the board.
     */
    @FXML
    public void reset() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                boardPane.getChildren().removeAll(board[i][j].getButton(), board[i][j].getLabel());
            }
        }
        initialize();
    }

    public void changePlayer() {
        isPlayerOneTurn = !isPlayerOneTurn;
    }

    /**
     * Checks if the game has end condition.
     */
    public void checkEndGame() {
        ArrayList<Tile> winningTiles = checkBoard();
        if (winningTiles.isEmpty()) {
            return;
        }
        State state = winningTiles.get(0).getState();
        flickingEffect(winningTiles, state);
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                board[i][j].disable();
            }
        }
        endEffect(state);
    }

    private void flickingEffect(ArrayList<Tile> tiles, State state) {
        Timeline timer = new Timeline(
                new KeyFrame(Duration.millis(FLICKING_DELAY), e ->
                        tiles.forEach(Tile::removeLabelGraphic)),
                new KeyFrame(Duration.millis(FLICKING_DELAY + FLICKING_DELAY), e ->
                        tiles.forEach(tile -> tile.display(state))));
        timer.setCycleCount(FLICKING);
        timer.play();
    }

    private void endEffect(State state) {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (!board[i][j].getState().equals(state)) {
                    tiles.add(board[i][j]);
                }
            }
        }
        Timeline timer = new Timeline();
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            timer.getKeyFrames().add(new KeyFrame(Duration.millis(CONVERSION_DELAY * (i + 1)), e ->
                    tile.display(state)));
        }
        timer.play();
    }

    private ArrayList<Tile> checkBoard() {
        ArrayList<Tile> winningTiles = new ArrayList<>();

        // check row
        for (int i = 0; i < DIMENSION; i++) {
            winningTiles.addAll(Arrays.asList(board[i]).subList(0, DIMENSION));
            if (checkTriplets(winningTiles)) {
                return winningTiles;
            } else {
                winningTiles.clear();
            }
        }

        // check col
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                winningTiles.add(board[j][i]);
            }
            if (checkTriplets(winningTiles)) {
                return winningTiles;
            } else {
                winningTiles.clear();
            }
        }

        // check diagonal
        for (int i = 0; i < DIMENSION; i++) {
            winningTiles.add(board[i][i]);
        }
        if (checkTriplets(winningTiles)) {
            return winningTiles;
        } else {
            winningTiles.clear();
        }

        winningTiles.addAll(List.of(board[0][2], board[1][1], board[2][0]));
        if (checkTriplets(winningTiles)) {
            return winningTiles;
        } else {
            winningTiles.clear();
        }

        return winningTiles;
    }

    private boolean checkTriplets(ArrayList<Tile> tiles) {
        Tile tile = tiles.get(0);
        if (tile.isUnmark()) {
            return false;
        }
        return tiles.stream().allMatch(t -> t.equals(tile));
    }

    enum State {
        NONE, PLAYER_1, PLAYER_2
    }

    class Tile {

        private static final String PLAYER_1 = "/image/O.png";
        private static final String PLAYER_2 = "/image/X.png";

        private Button button;
        private Label label;
        private State state;

        public Tile(int rowIndex, int colIndex) {
            label = addLabel(rowIndex, colIndex);
            button = addButton(rowIndex, colIndex);
            state = State.NONE;
        }

        private Label addLabel(int rowIndex, int colIndex) {
            Label label = new Label();
            label.setPrefSize(NODE_DIMENSION, NODE_DIMENSION);
            label.setMinSize(NODE_DIMENSION, NODE_DIMENSION);
            label.setMaxSize(NODE_DIMENSION, NODE_DIMENSION);
            label.getStylesheets().add(getClass().getResource("/view/Label.css").toExternalForm());
            boardPane.add(label, colIndex, rowIndex);
            return label;
        }

        private Button addButton(int rowIndex, int colIndex) {
            Button button = new Button();
            button.setPrefSize(NODE_DIMENSION, NODE_DIMENSION);
            button.setMinSize(NODE_DIMENSION, NODE_DIMENSION);
            button.setMaxSize(NODE_DIMENSION, NODE_DIMENSION);

            button.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    mark();
                    checkEndGame();
                    changePlayer();
                }
            });
            boardPane.add(button, colIndex, rowIndex);
            return button;
        }

        private void mark() {
            if (isPlayerOneTurn) {
                state = State.PLAYER_1;
            } else {
                state = State.PLAYER_2;
            }
            button.setDisable(true);
            display(state);
        }

        private void display(State state) {
            if (state.equals(State.PLAYER_1)) {
                markLabel(PLAYER_1);
            } else {
                markLabel(PLAYER_2);
            }
        }

        public void markLabel(String imagePath) {
            ImageView image = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
            image.setPreserveRatio(true);
            image.fitHeightProperty().bind(label.widthProperty());
            image.fitHeightProperty().bind(label.heightProperty());
            label.setGraphic(image);
        }

        public void removeLabelGraphic() {
            label.setGraphic(null);
        }

        public boolean isUnmark() {
            return state.equals(State.NONE);
        }

        public Button getButton() {
            return button;
        }

        public Label getLabel() {
            return label;
        }

        public State getState() {
            return state;
        }

        public void disable() {
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
