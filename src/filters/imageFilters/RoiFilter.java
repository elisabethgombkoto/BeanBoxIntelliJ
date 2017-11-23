package filters.imageFilters;

import filters.pmp.filter.DataTransformationFilter2;
import filters.pmp.interfaces.Readable;
import filters.pmp.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.security.InvalidParameterException;

/**
 * Created by Bernd on 06.11.2017.
 *
 * Todo drop down für ein form unddie grösse erst nacher bestimmen
 */
public class RoiFilter extends DataTransformationFilter2<PlanarImage, PlanarImage> {

  private Rectangle rectangle;
  private int x;
  private int y;
  private int width;
  private int height;

  public RoiFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output, int x, int y, int width, int height) throws InvalidParameterException {
    super(input, output);
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public RoiFilter(Readable<PlanarImage> input, int x, int y, int width, int height) throws InvalidParameterException {
    super(input);
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public RoiFilter(Writeable<PlanarImage> output, int x, int y, int width, int height) throws InvalidParameterException {
    super(output);
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

   @Override
  public PlanarImage process(PlanarImage entity) {
    rectangle = new Rectangle(x, y, width, height);
    PlanarImage image = entity;
    image = PlanarImage.wrapRenderedImage(image.getAsBufferedImage(rectangle, image.getColorModel()));
    image.setProperty("offsetX", (int) rectangle.getX());
    image.setProperty("offsetY", (int) rectangle.getY());
    return image;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setWidth(int width) {
    this.width = width;
  }
}
