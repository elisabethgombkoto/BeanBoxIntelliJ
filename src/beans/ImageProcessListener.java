package beans;

import java.util.EventListener;

public interface ImageProcessListener extends EventListener {
    public abstract void imageValueChanged(ImageEvent ie);
}
