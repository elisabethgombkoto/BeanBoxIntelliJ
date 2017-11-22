package beans;

import javax.media.jai.PlanarImage;
import java.io.IOException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.nio.CharBuffer;
import java.util.Vector;

import filters.imageFilters.OpeningFilter;
import filters.pmp.interfaces.Readable;

/**
 * Created by Elisabeth on 22.11.2017.
 */
public class OpeningBean implements Serializable, ImageProcessListener, Readable<PlanarImage>  {

  private OpeningFilter openingFilter;
  private PlanarImage image;
  private Vector listeners;

  public OpeningBean(){
    listeners = new Vector();
    openingFilter = new OpeningFilter((filters.pmp.interfaces.Readable<PlanarImage>) this);
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
      PlanarImage openingImage = openingFilter.process(image);
      ImageEvent ie2 = new ImageEvent(this, openingImage);

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
  public PlanarImage read() throws StreamCorruptedException, Exception {
    throw new Exception("not implemented");
  }

  public OpeningFilter getOpeningFilter() {
    return openingFilter;
  }

  public void setOpeningFilter(OpeningFilter openingFilter) {
    this.openingFilter = openingFilter;
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
