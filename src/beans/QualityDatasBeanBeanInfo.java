package beans;

import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;

/**
 * Created by Elisabeth on 23.11.2017.
 */
public class QualityDatasBeanBeanInfo extends SimpleBeanInfo {

  public MethodDescriptor[] getMethodDescriptors() {
    try {
      Class c = QualityDatasBean.class;
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