package beans;

import filters.pmp.interfaces.Readable;
import javax.media.jai.PlanarImage;
import java.io.Serializable;
import java.io.StreamCorruptedException;

/**
 * Created by Elisabeth on 22.11.2017.
 */
public class MedianBean implements Serializable, ImageProcessListener, Readable<PlanarImage> {


  @Override
  public void imageValueChanged(ImageEvent ie2) {

  }

  @Override
  public PlanarImage read() throws StreamCorruptedException, Exception {
    return null;
  }
}
