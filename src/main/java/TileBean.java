import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.List;

public class TileBean {

    private Mark mark;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener() {
        Arrays.stream(support.getPropertyChangeListeners()).forEach(x->{
            support.removePropertyChangeListener(x);
        });

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
