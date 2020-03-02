public enum Player {
    PLAYER_1(Mark.NOUGHT),PLAYER_2(Mark.CROSS);

    private Mark mark;
    Player(Mark mark){
        this.mark=mark;
    }

    public Mark getMark(){
        return mark;
    }
}
