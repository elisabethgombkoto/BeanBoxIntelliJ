package beans;

import filters.imageFilters.MedianFilter;
import filters.pmp.interfaces.Readable;

import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;
import java.io.Serializable;
import java.util.Vector;

/**
 * Created by Elisabeth on 22.11.2017.
 * <p>
 * Todo beanInfo siehe osborn flavor
 * p1 filterschape
 * p2 size
 * Todo property descriptor p1 und p2 hinfügen
 * Todo editor class getTags()--> wie soll in drop down angezeigt werden
 * setAsText() --> immmer erst setValue() aufrufen wenn plus ausgewählt dann soll richtige enum
 * <p>
 * <p>
 * Todo alle anderen classe  ee clampBeanInfo getmethodDescriptor()
 */
public class MedianBean implements Serializable, IImageProcessListener, Readable<PlanarImage> {

  private MedianFilterShape medianFilterShape;
  private int size;
  private MedianFilter medianFilter;
  private PlanarImage image;
  private Vector listeners;

  public MedianBean() {
    medianFilterShape = MedianFilterDescriptor.MEDIAN_MASK_SQUARE;
    size = 5;
    medianFilter = new MedianFilter((filters.pmp.interfaces.Readable<PlanarImage>) this, medianFilterShape, size);
    listeners = new Vector();
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
      PlanarImage medianImage = medianFilter.process(image);
      ImageEvent ie2 = new ImageEvent(this, medianImage);

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

  @Override
  public PlanarImage read() throws Exception {
    throw new Exception("not implemented");
  }

  public MedianFilterShape getMedianFilterShape() {
    return medianFilterShape;
  }

  public void setMedianFilterShape(MedianFilterShape medianFilterShape) {
    this.medianFilterShape = medianFilterShape;
    medianFilter.setMedianFilterShape(medianFilterShape);
    if(image!=null){
      medianFilter.process(image);
    }
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) throws Exception {
    this.size = size;
    medianFilter.setSize(size);
    if (image != null) {
      processListeners();
    }
  }
}
