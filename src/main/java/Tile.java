import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;

import javafx.scene.input.MouseEvent;

public class Tile {

    private Mark mark=Mark.EMPTY;
    private TileBean tileBean;
    private Button button;

    public Tile(Button button,TileBean tileBean){
        this.button=button;
        this.tileBean=tileBean;
        setUpButton();
        reset();
    }

    private void setUpButton(){
        button.addEventFilter(MouseEvent.MOUSE_PRESSED,e->{
            if (e.getButton() == MouseButton.PRIMARY) {
                markTile();
                System.out.println("qqqqqqqqqq");
                tileBean.setMark(mark);
            }
        });
    }

    private void markTile(){
        mark=Board.player.getMark();
        button.setText(Board.player.getMark().toString());
        button.setDisable(true);
    }

    private void reset(){
        button.setVisible(true);
        button.setDisable(false);
    }

    public void disableButton(){
        button.setVisible(false);
        button.setDisable(true);
    }

    public Mark getMark(){
        return mark;
    }




}
