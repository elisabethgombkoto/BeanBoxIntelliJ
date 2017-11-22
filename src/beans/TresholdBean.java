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
public class TresholdBean implements Serializable, ImageProcessListener, Readable<PlanarImage> {

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

  public void addImageProcessListener(ImageProcessListener il) {
    listeners.addElement(il);
  }

  public void removeImageProcessListener(ImageProcessListener il) {
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
        ImageProcessListener w1 = (ImageProcessListener)v.elementAt(i);
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

  public TresholdFilter getTresholdFilter() {
    return tresholdFilter;
  }

  public void setTresholdFilter(TresholdFilter tresholdFilter) {
    this.tresholdFilter = tresholdFilter;
  }

  public double getLow() {
    return low;
  }

  public void setLow(double low) {
    this.low = low;
  }

  public double getHigh() {
    return high;
  }

  public void setHigh(double high) {
    this.high = high;
  }

  public double getMap() {
    return map;
  }

  public void setMap(double map) {
    this.map = map;
  }
}
