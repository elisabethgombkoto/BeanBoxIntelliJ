import sun.beanbox.BeanBoxFrame;

/**
 * Created by Elisabeth on 13.11.2017.
 */
public class Run {
  public static void main(String[] args) {
    System.setProperty( "com.sun.media.jai.disableMediaLib", "true" );
    BeanBoxFrame.main(new String[]{});
  }
}
