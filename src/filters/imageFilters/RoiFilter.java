package filters.imageFilters;

import filters.pmp.filter.DataTransformationFilter2;
import filters.pmp.interfaces.Readable;
import filters.pmp.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.security.InvalidParameterException;

/**
 * Created by Bernd on 06.11.2017.
 */
public class RoiFilter extends DataTransformationFilter2<PlanarImage, PlanarImage> {

    private Rectangle _rectangle;

    public RoiFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output, int x, int y,  int width, int height) throws InvalidParameterException {
        super( input, output );
        _rectangle = createRectangle(x, y, width, height);
    }



    public RoiFilter(Readable<PlanarImage> input, int x, int y,  int width, int height) throws InvalidParameterException {
        super( input );
        _rectangle = createRectangle(x, y, width, height);
    }

    public RoiFilter(Writeable<PlanarImage> output,int x, int y,  int width, int height) throws InvalidParameterException {
        super( output );
        _rectangle = createRectangle(x, y, width, height);
    }
    private Rectangle createRectangle(int x, int y, int width, int height) {
        return new Rectangle(x, y, width, height);
    }
    @Override
    public PlanarImage process(PlanarImage entity) {
        PlanarImage image = entity;
        image = PlanarImage.wrapRenderedImage( image.getAsBufferedImage( _rectangle, image.getColorModel() ) );
        image.setProperty( "offsetX", (int) _rectangle.getX() );
        image.setProperty( "offsetY", (int) _rectangle.getY() );
        return image;
    }
}
