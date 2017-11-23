package beans;

import filters.utils.QualityData;

import java.util.ArrayList;
import java.util.EventObject;

/**
 * Created by Elisabeth on 23.11.2017.
 */
public class QualityDatasEvent extends EventObject {

  private ArrayList<QualityData> value;

  public QualityDatasEvent(Object object, ArrayList<QualityData> qualityData) {
    super(object);
    this.value = qualityData;
  }

  public void setValue(ArrayList<QualityData> value) {
    this.value = value;
  }

  public ArrayList<QualityData> getValue() {
    return value;
  }
}
