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
        disableButton();
    }

    public void disableButton(){
        button.setDisable(true);
    }

    public Mark getMark(){
        return mark;
    }




}
