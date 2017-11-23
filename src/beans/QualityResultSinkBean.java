package beans;

import filters.imageFilters.QualityResultSink;
import filters.pmp.interfaces.Readable;
import filters.utils.QualityData;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by Elisabeth on 22.11.2017.
 */
public class QualityResultSinkBean implements Serializable, IDataQualityDatasChangeListener, Readable<ArrayList<QualityData>> {

  private String destinationPath;
  private QualityResultSink qualityResultSink;
  private ArrayList<QualityData> qualityDataArrayList;

  public QualityResultSinkBean() throws IOException {
    destinationPath = "C:\\Users\\Elisabeth\\IdeaProjects\\BeanBoxIntelliJ\\resources\\loetstellenDatenResultBean.txt";
    FileWriter fileWriter = new FileWriter(destinationPath);
    qualityResultSink = new QualityResultSink((filters.pmp.interfaces.Readable<ArrayList<QualityData>>) this, destinationPath, fileWriter);
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

  public String getDestinationPath() {
    return destinationPath;
  }

  public void setDestinationPath(String destinationPath) {
    this.destinationPath = destinationPath;
    try {
      qualityResultSink.setPath(destinationPath);
      if (qualityDataArrayList != null && !qualityDataArrayList.isEmpty()) {
        qualityResultSink.write(qualityDataArrayList);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
