package beans;

import filters.imageFilters.MedianFilter;

import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;
import java.beans.PropertyEditorSupport;

/**
 * Created by Elisabeth on 23.11.2017.
 */
public class MedianFilterShapeEditor extends PropertyEditorSupport {

  public String[] getTags() {
    String shapes[] = { "PLUS", "SQUARE",
        "SQUARE SEPARABLE", "X" };
    return shapes;
  }

  public void setAsText(String text){
    if(text.equals("PLUS")){
      setValue(MedianFilterDescriptor.MEDIAN_MASK_PLUS);
    }
    if(text.equals("SQUARE")){
      setValue(MedianFilterDescriptor.MEDIAN_MASK_SQUARE);
    }
    if(text.equals("SQUARE SEPARABLE")){
      setValue(MedianFilterDescriptor.MEDIAN_MASK_SQUARE_SEPARABLE);
    }
    if(text.equals("X")){
      setValue(MedianFilterDescriptor.MEDIAN_MASK_X);
    }
  }
}