package beans;

import filters.imageFilters.ImageToFileFilter;
import filters.imageFilters.RoiFilter;
import filters.pmp.interfaces.Readable;

import javax.media.jai.PlanarImage;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Vector;

/**
 * Created by Elisabeth on 22.11.2017.
 */
public class ImageToFileBean implements Serializable, IImageProcessListener, Readable<PlanarImage> {
  private String destinationPath;
  private ImageToFileFilter imageToFileFilter;
  private PlanarImage image;
  private Vector listeners;

  public ImageToFileBean(){
    listeners = new Vector();
    destinationPath = "C:\\Users\\Elisabeth\\IdeaProjects\\BeanBoxIntelliJ\\resources\\loetstellenPictureResultBean.jpg";
    imageToFileFilter = new ImageToFileFilter(destinationPath, (filters.pmp.interfaces.Readable<PlanarImage>) this);
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
      //Todo Katja frangen es ist void dann gebe ich den image weiter?
      imageToFileFilter.process(image);
      ImageEvent ie2 = new ImageEvent(this, image);

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
  public String getDestinationPath() {
    return destinationPath;
  }

  public void setDestinationPath(String destinationPath) throws Exception {
    this.destinationPath = destinationPath;
    imageToFileFilter.setDestinationPath(destinationPath);
    if(image != null){
      processListeners();
    }
  }

  @Override
  public PlanarImage read() throws StreamCorruptedException, Exception {
    throw new Exception("not implemented method");
  }
}
