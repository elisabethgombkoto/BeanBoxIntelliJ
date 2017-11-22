package beans;

import filters.imageFilters.MedianFilter;
import filters.pmp.interfaces.Readable;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Vector;

/**
 * Created by Elisabeth on 22.11.2017.
 */
public class MedianBean implements Serializable, ImageProcessListener, Readable<PlanarImage> {

  private MedianFilterShape medianFilterShape;
  private int size;
  private MedianFilter medianFilter;
  private PlanarImage image;
  private Vector listeners;

  public MedianBean(){
    medianFilterShape = MedianFilterDescriptor.MEDIAN_MASK_SQUARE;
    size = 5;
    medianFilter = new MedianFilter((filters.pmp.interfaces.Readable<PlanarImage>) this, medianFilterShape, size);
    listeners = new Vector();
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
      PlanarImage medianImage = medianFilter.process(image);
      ImageEvent ie2 = new ImageEvent(this, medianImage);

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

  @Override
  public PlanarImage read() throws Exception {
    throw new Exception("not implemented");
  }

  public MedianFilterShape getMedianFilterShape() {
    return medianFilterShape;
  }

  public void setMedianFilterShape(MedianFilterShape medianFilterShape) {
    this.medianFilterShape = medianFilterShape;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public MedianFilter getMedianFilter() {
    return medianFilter;
  }

  public void setMedianFilter(MedianFilter medianFilter) {
    this.medianFilter = medianFilter;
  }

  public PlanarImage getImage() {
    return image;
  }

  public void setImage(PlanarImage image) {
    this.image = image;
  }

  public Vector getListeners() {
    return listeners;
  }

  public void setListeners(Vector listeners) {
    this.listeners = listeners;
  }
}
