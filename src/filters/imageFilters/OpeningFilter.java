package filters.imageFilters;

import filters.pmp.filter.DataTransformationFilter2;
import filters.pmp.interfaces.Readable;
import filters.pmp.interfaces.Writeable;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;

/**
 * Created by Bernd on 08.11.2017.
 * https://findusages.com/search/javax.media.jai.KernelJAI/KernelJAI$3?offset=0
 */
public class OpeningFilter extends DataTransformationFilter2<PlanarImage, PlanarImage> {

    public OpeningFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output) throws InvalidParameterException {
        super( input, output );
    }

    public OpeningFilter(Readable<PlanarImage> input) throws InvalidParameterException {
        super( input );
    }

    public OpeningFilter(Writeable<PlanarImage> output) throws InvalidParameterException {
        super( output );
    }

    private float[] kernelData = {
            0, 0, 0, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 0, 0, 0,
            0, 0, 1, 1, 1, 1, 1, 0, 0,
            0, 1, 0, 1, 1, 1, 0, 1, 0,
            1, 1, 1, 1, 1, 1, 1, 1, 1,
            0, 1, 0, 1, 1, 1, 0, 1, 0,
            0, 0, 1, 1, 1, 1, 1, 0, 0,
            0, 0, 0, 1, 1, 1, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0, 0};

    KernelJAI kernel = new KernelJAI( 9, 9, kernelData );

    public PlanarImage process(PlanarImage entity) {
        PlanarImage image = erodeProcess( entity );
        return dilateProcess( image );
    }

    private PlanarImage dilateProcess(PlanarImage image) {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource( image );
        pb.add( kernel );
        return JAI.create( "dilate", pb );
    }

    private PlanarImage erodeProcess(PlanarImage image) {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource( image );
        pb.add( kernel );
        return JAI.create( "erode", pb );
    }
}

