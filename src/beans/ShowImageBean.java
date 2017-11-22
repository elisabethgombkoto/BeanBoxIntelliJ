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
  }
  @Override
  public PlanarImage read() throws StreamCorruptedException {
    return null;
  }


}
