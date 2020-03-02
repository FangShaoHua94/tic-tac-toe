import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TileBean {

    private Mark mark;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark newMark) {
        Mark oldMark = this.mark;
        this.mark = newMark;
        support.firePropertyChange("mark", oldMark, newMark);
    }
}
