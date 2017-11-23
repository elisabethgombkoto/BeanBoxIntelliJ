package filters.imageFilters;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.io.FileNotFoundException;
import java.io.StreamCorruptedException;
import filters.pmp.filter.Source;
import filters.pmp.interfaces.Writeable;

/**
 * Created by Elisabeth on 30.10.2017.
 */
public class ReadSourcePicture extends Source<PlanarImage> {

    private String _path;

    public ReadSourcePicture(String path, Writeable<PlanarImage> output) throws FileNotFoundException {
        super( output );
        _path = path;
    }

    public ReadSourcePicture(String path) {
        _path = path;
    }

    public PlanarImage read() throws StreamCorruptedException {
        PlanarImage image = JAI.create( "fileload", _path );
        return image;
    }

    @Override
    public void run() {
        PlanarImage output = null;

        try {
            output = read();
            m_Output.write( output );
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

  public void setImagePath(String imagePath) {
    _path = imagePath;
  }
}
