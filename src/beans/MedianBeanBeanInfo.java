package beans;

import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;

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
  public MethodDescriptor[] getMethodDescriptors() {
    try {
      Class c = MedianBean.class;
      Class parameterTypes[] = new Class[1];
      parameterTypes[0] = ImageEvent.class;
      String name = "imageValueChanged";
      Method method1 = c.getMethod(name, parameterTypes);
      ParameterDescriptor pds[] = new ParameterDescriptor[1];
      pds[0] = new ParameterDescriptor();
      MethodDescriptor md1 = new MethodDescriptor(method1, pds);
      MethodDescriptor mds[] = { md1 };
      return mds;
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }
}