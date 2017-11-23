package beans;

import filters.imageFilters.ReadSourcePicture;
import filters.pmp.interfaces.Readable;

import javax.media.jai.PlanarImage;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Vector;

public class ReadSourcePictureBean implements Serializable, Readable<PlanarImage> {

    private String imagePath;
    private PlanarImage value;
    private Vector listeners;
    private ReadSourcePicture readSourcePicture;

    public ReadSourcePictureBean() {
        imagePath = "C:\\Users\\Elisabeth\\IdeaProjects\\BeanBoxIntelliJ\\resources\\loetstellen.jpg";
        listeners = new Vector();
        readSourcePicture = new ReadSourcePicture(imagePath);
    }


    public void addIImageProcessListener(IImageProcessListener il) {
        listeners.addElement(il);
    }

    public void removeIImageProcessListener(IImageProcessListener il) {
        listeners.removeElement(il);
    }

    protected synchronized void fireImageEvent() {
        Vector vectorListeners;
        synchronized (this) {
            vectorListeners = (Vector) listeners.clone();
        }
        ImageEvent ie = new ImageEvent(this, value);
        for (int i = 0; i < vectorListeners.size(); i++) {
            IImageProcessListener IImageProcessListener = (IImageProcessListener) vectorListeners.elementAt(i);
            IImageProcessListener.imageValueChanged(ie);
        }
    }

    @Override
    public PlanarImage read() throws StreamCorruptedException {
        try {
            PlanarImage image = readSourcePicture.read();
            if (image != null) {
                value = image;
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
        return value;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String path) {
        imagePath = path;
        readSourcePicture.setImagePath(imagePath);
        try {
            value = read();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
        fireImageEvent();
    }
}
