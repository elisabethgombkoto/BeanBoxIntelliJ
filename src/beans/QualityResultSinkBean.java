package beans;

import filters.imageFilters.QualityResultSink;
import filters.pmp.interfaces.Readable;
import filters.utils.QualityData;

import java.io.IOException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by Elisabeth on 22.11.2017.
 */
public class QualityResultSinkBean implements Serializable, IDataQualityDatasChangeListener, Readable<ArrayList<QualityData>> {

  private String exceptedMiddelCoordinatePath;
  private String destinationPath;
  private QualityResultSink qualityResultSink;
  private ArrayList<QualityData> qualityDataArrayList;

  public QualityResultSinkBean() throws IOException {
    exceptedMiddelCoordinatePath = "C:\\Users\\Elisabeth\\IdeaProjects\\BeanBoxIntelliJ\\resources\\expectedCentroids.txt";
    destinationPath = "C:\\Users\\Elisabeth\\IdeaProjects\\BeanBoxIntelliJ\\resources\\resultDatas.txt";
    qualityResultSink = new QualityResultSink((filters.pmp.interfaces.Readable<ArrayList<QualityData>>) this, exceptedMiddelCoordinatePath, destinationPath);
  }

  @Override
  public void dataQualityDatasValueChanged(QualityDatasEvent ie) {
    try {
      qualityDataArrayList = ie.getValue();
      qualityResultSink.write(qualityDataArrayList);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public ArrayList<QualityData> read() throws StreamCorruptedException, Exception {
    throw new Exception("not implemented method");
  }

  public String getExceptedMiddelCoordinatePath() {
    return exceptedMiddelCoordinatePath;
  }

  public void setExceptedMiddelCoordinatePath(String exceptedMiddelCoordinatePath) {
    this.exceptedMiddelCoordinatePath = exceptedMiddelCoordinatePath;
    try {
      qualityResultSink.set_exceptedMiddelCoordinatePath(exceptedMiddelCoordinatePath);
      if (qualityDataArrayList != null && !qualityDataArrayList.isEmpty()) {
        qualityResultSink.write(qualityDataArrayList);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getDestinationPath() {
    return destinationPath;
  }

  public void setDestinationPath(String destinationPath) {
    this.destinationPath = destinationPath;
    try {
      qualityResultSink.set_destinationPath(destinationPath);
      if (qualityDataArrayList != null && !qualityDataArrayList.isEmpty()) {
        qualityResultSink.write(qualityDataArrayList);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
