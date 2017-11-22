package beans;

import filters.imageFilters.ShowImageFilter;
import filters.pmp.interfaces.Readable;

import javax.media.jai.PlanarImage;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Vector;

/**
 * Created by Elisabeth on 22.11.2017.
 */
public class ShowImageBean implements Serializable, ImageProcessListener, Readable<PlanarImage> {

  private PlanarImage image;
  private Vector listeners;
  private String title;
  private ShowImageFilter imageFilter;

  public ShowImageBean(){
    listeners = new Vector();
    title = "image";
    imageFilter = new ShowImageFilter((filters.pmp.interfaces.Readable<PlanarImage>) this, title);
  }

  public void addImageProcessListener(ImageProcessListener il) {
    listeners.addElement(il);
  }

  public void removeImageProcessListener(ImageProcessListener il) {
    listeners.removeElement(il);
  }
  @Override
  public void imageValueChanged(ImageEvent imageEvent) {
    //TODO Katja fragen
    try {
      image = imageEvent.getValue();
      processListeners();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void processListeners() throws Exception {
    if (image != null) {
      PlanarImage imageToShow = imageFilter.process(image);
      ImageEvent ie2 = new ImageEvent(this, imageToShow);

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ShowImageFilter getImageFilter() {
    return imageFilter;
  }

  public void setImageFilter(ShowImageFilter imageFilter) {
    this.imageFilter = imageFilter;
  }
}
