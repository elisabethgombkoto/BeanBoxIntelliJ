package beans;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Created by Elisabeth on 23.11.2017.
 */
public class MedianBeanBeanInfo extends SimpleBeanInfo {

  public PropertyDescriptor[] getPropertyDescriptors() {
    try {
      PropertyDescriptor p1;
      PropertyDescriptor p2;
      p2 = new PropertyDescriptor("medianFilterShape", MedianBean.class);
      p2.setPropertyEditorClass( MedianFilterShapeEditor.class);
      p1 = new PropertyDescriptor("size", MedianBean.class);
      PropertyDescriptor pds[] = { p1, p2 };
      return pds;
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }
}