public enum Mark {
    NOUGHT("O"),CROSS("X"),EMPTY(" ");

    private String mark;
    Mark(String mark){
        this.mark=mark;
    }

    @Override
    public String toString() {
        return mark;
    }

    public boolean equals(Mark otherMark){
        return mark.toString().equals(otherMark.toString());
    }

}
