package beans;

import java.util.EventListener;

/**
 * Created by Elisabeth on 23.11.2017.
 */
public interface IDataQualityDatasChangeListener extends EventListener{
  public abstract void dataQualityDatasValueChanged(QualityDatasEvent ie);
}
