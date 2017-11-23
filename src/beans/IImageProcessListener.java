package beans;

import java.util.EventListener;

public interface IImageProcessListener extends EventListener {
    public abstract void imageValueChanged(ImageEvent ie);
}
