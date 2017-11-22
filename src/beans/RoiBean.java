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
    private int width;
    private int height;
    private RoiFilter roiFilter;
    private PlanarImage image;
    private Vector listeners;

    public RoiBean() {
        x = 55;
        y = 50;
        width = 447;
        height = 70;
        listeners = new Vector();
        roiFilter = new RoiFilter( (filters.pmp.interfaces.Readable<PlanarImage>) this, x, y, width, height);
    }

    public void addImageProcessListener(ImageProcessListener il) {
        listeners.addElement(il);
    }

    public void removeImageProcessListener(ImageProcessListener il) {
        listeners.removeElement(il);
    }

    @Override
    public void imageValueChanged(ImageEvent ie) {
        try {
            image = ie.getValue();
            processListeners();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processListeners() throws Exception {
        if (image != null) {
            PlanarImage roiImage = roiFilter.process(image);
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
        }else{
            throw new Exception("image is null");
        }
    }
/*
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        roiFilter.setX(x);
        if (image != null) {
            processAndNotifyListeners();
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        roiFilter.setY(y);
        if (image != null) {
            processAndNotifyListeners();
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        roiFilter.setHeight(height);
        if (image != null) {
            processAndNotifyListeners();
        }
    }
*/
    @Override
    public PlanarImage read() throws Exception {
        throw new Exception("Method is not implemented");
    }
}
