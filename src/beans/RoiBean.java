package beans;

import filters.imageFilters.RoiFilter;
import filters.pmp.interfaces.Readable;

import javax.media.jai.PlanarImage;
import java.io.Serializable;
import java.util.Vector;

public class RoiBean implements Serializable, IImageProcessListener, Readable<PlanarImage> {

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
    roiFilter = new RoiFilter((filters.pmp.interfaces.Readable<PlanarImage>) this, x, y, width, height);
  }

  public void addIImageProcessListener(IImageProcessListener il) {
    listeners.addElement(il);
  }

  public void removeIImageProcessListener(IImageProcessListener il) {
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
      synchronized (this) {
        v = (Vector) listeners.clone();
      }
      for (int i = 0; i < v.size(); i++) {
        IImageProcessListener wl = (IImageProcessListener) v.elementAt(i);
        wl.imageValueChanged(ie2);
      }
    } else {
      throw new Exception("image is null");
    }
  }

  public int getX() {
    return x;
  }

  public void setX(int x) throws Exception {
    this.x = x;
    roiFilter.setX(x);
    if (image != null) {
      processListeners();
    }
  }

  public int getY() {
    return y;
  }

  public void setY(int y) throws Exception {
    this.y = y;
    roiFilter.setY(y);
    if (image != null) {
      processListeners();
    }
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) throws Exception {
    this.height = height;
    roiFilter.setHeight(height);
    if (image != null) {
      processListeners();
    }
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) throws Exception {
    this.width = width;
    roiFilter.setWidth(width);
    if (image != null) {
      processListeners();
    }
  }

  @Override
  public PlanarImage read() throws Exception {
    throw new Exception("Method is not implemented");
  }
}
