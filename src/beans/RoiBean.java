package beans;

import filters.imageFilters.RoiFilter;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Vector;
import filters.pmp.interfaces.Readable;

public class RoiBean implements Serializable, ImageProcessListener, Readable<PlanarImage> {

    private int x;
    private int y;
    private int height;
    private Vector listeners;
    private Rectangle rectangle;

    private RoiFilter roiFilter;

    public RoiBean() {
        listeners = new Vector();
        rectangle = new Rectangle();
    }

    public void addImageProcessListener(ImageProcessListener il) {
        listeners.addElement(il);
    }

    public void removeImageProcessListener(ImageProcessListener il) {
        listeners.removeElement(il);
    }

    @Override
    public void imageValueChanged(ImageEvent ie) {
        roiFilter = new RoiFilter( (Readable<PlanarImage>) ie.getSource(), rectangle);
        PlanarImage roiImage = roiFilter.process(ie.getValue());
        ImageEvent ie2 = new ImageEvent(this, roiImage);

        // Listener benachrichtigen
        Vector v;
        synchronized(this) {
            v = (Vector)listeners.clone();
        }
        for(int i = 0; i < v.size(); i++) {
            ImageProcessListener wl = (ImageProcessListener)v.elementAt(i);
            wl.imageValueChanged(ie2);
        }
    }

    @Override
    public PlanarImage read() throws StreamCorruptedException {
        return null;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
