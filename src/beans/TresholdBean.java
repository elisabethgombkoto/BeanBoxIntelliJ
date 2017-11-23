package beans;

import filters.imageFilters.TresholdFilter;
import filters.pmp.interfaces.Readable;

import javax.media.jai.PlanarImage;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Vector;

/**
 * Created by Elisabeth on 22.11.2017.
 */
public class TresholdBean implements Serializable, IImageProcessListener, Readable<PlanarImage> {

  private PlanarImage image;
  private Vector listeners;
  private TresholdFilter tresholdFilter;
  private double low;
  private double high;
  private double map;

  public TresholdBean(){
    low = 0;
    high = 35;
    map = 255;
    listeners = new Vector();
    tresholdFilter = new TresholdFilter((filters.pmp.interfaces.Readable<PlanarImage>) this, low, high, map);
  }

  public void addIImageProcessListener(IImageProcessListener il) {
    listeners.addElement(il);
  }

  public void removeIImageProcessListener(IImageProcessListener il) {
    listeners.removeElement(il);
  }

  @Override
  public void imageValueChanged(ImageEvent ie2) {
    image = ie2.getValue();
    try {
      processListener();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void processListener() throws Exception {
    if(image != null){
      PlanarImage tresholdImage = tresholdFilter.process(image);
      ImageEvent imageEvent = new ImageEvent(this, tresholdImage);
      Vector v;
      synchronized (this){
        v = (Vector)listeners.clone();
      }
      for(int i = 0; i<v.size(); i++){
        IImageProcessListener w1 = (IImageProcessListener)v.elementAt(i);
        w1.imageValueChanged(imageEvent);
      }
    }else {
      throw  new Exception("image is null");
    }
  }

  @Override
  public PlanarImage read() throws StreamCorruptedException, Exception {
    throw  new Exception("image is null");
  }
  public double getLow() {
    return low;
  }

  public void setLow(double low) throws Exception {
    this.low = low;
    tresholdFilter.setLow(low);
    if (image != null) {
      processListener();
    }
  }

  public double getHigh() {
    return high;
  }

  public void setHigh(double high) throws Exception {
    this.high = high;
    tresholdFilter.setHigh(high);
    if (image != null) {
      processListener();
    }
  }

  public double getMap() {
    return map;
  }

  public void setMap(double map) throws Exception {
    this.map = map;
    this.high = high;
    tresholdFilter.setMap(map);
    if (image != null) {
      processListener();
    }
  }
}
