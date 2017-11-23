package beans;

import filters.imageFilters.ShowImageFilter;
import filters.pmp.interfaces.Readable;

import javax.media.jai.PlanarImage;
import java.io.Serializable;
import java.util.Vector;

/**
 * Created by Elisabeth on 22.11.2017.
 */
public class ShowImageBean implements Serializable, IImageProcessListener, Readable<PlanarImage> {

  private PlanarImage image;
  private Vector listeners;
  private String title;
  private ShowImageFilter imageFilter;

  public ShowImageBean() {
    listeners = new Vector();
    title = "image";
    imageFilter = new ShowImageFilter((filters.pmp.interfaces.Readable<PlanarImage>) this, title);
  }

  public void addIImageProcessListener(IImageProcessListener il) {
    listeners.addElement(il);
  }

  public void removeIImageProcessListener(IImageProcessListener il) {
    listeners.removeElement(il);
  }

  @Override
  public void imageValueChanged(ImageEvent imageEvent) {
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) throws Exception {
    this.title = title;
    imageFilter.setTitle(title);
    if (image != null) {
      processListeners();
    }
  }
}
