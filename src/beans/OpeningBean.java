package beans;

import javax.media.jai.PlanarImage;
import java.io.IOException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.nio.CharBuffer;
import filters.pmp.interfaces.Readable;

/**
 * Created by Elisabeth on 22.11.2017.
 */
public class OpeningBean implements Serializable, ImageProcessListener, Readable<PlanarImage>  {
  @Override
  public void imageValueChanged(ImageEvent ie2) {

  }

  @Override
  public PlanarImage read() throws StreamCorruptedException, Exception {
    return null;
  }
}
