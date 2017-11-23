package beans;

import filters.imageFilters.QualityDatasFilter;
import filters.pmp.interfaces.Readable;
import filters.utils.QualityData;

import javax.media.jai.PlanarImage;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Elisabeth on 22.11.2017.
 */
public class QualityDatasBean implements Serializable, IImageProcessListener, Readable<PlanarImage> {

  private QualityDatasFilter qualityDatasFilter;
  private PlanarImage image;
  private Vector listeners;

  public QualityDatasBean() {
    listeners = new Vector();
    qualityDatasFilter = new QualityDatasFilter((filters.pmp.interfaces.Readable<PlanarImage>) this);
  }

  public void addIDataQualityDatasChangeListener (IDataQualityDatasChangeListener il){listeners.addElement(il);}
  public void removeIDataQualityDatasChangeListener (IDataQualityDatasChangeListener il){listeners.removeElement(il);}

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
      ArrayList<QualityData> qualityDataArrayList = qualityDatasFilter.process(image);
      QualityDatasEvent qde= new QualityDatasEvent(this, qualityDataArrayList);

      // Listener benachrichtigen
      Vector v;
      synchronized (this) {
        v = (Vector) listeners.clone();
      }
      for (int i = 0; i < v.size(); i++) {
        IDataQualityDatasChangeListener wl = (IDataQualityDatasChangeListener) v.elementAt(i);
        wl.dataQualityDatasValueChanged(qde);
      }
    } else {
      throw new Exception("image is null");
    }
  }

  @Override
  public PlanarImage read() throws StreamCorruptedException, Exception {
    throw new Exception("method not implemented");
  }
}
