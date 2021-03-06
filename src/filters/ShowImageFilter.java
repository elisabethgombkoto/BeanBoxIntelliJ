package filters;


import com.sun.media.jai.widget.DisplayJAI;
import filters.pmp.filter.DataTransformationFilter2;
import filters.pmp.interfaces.Writeable;
import filters.pmp.interfaces.Readable;

import javax.media.jai.PlanarImage;
import javax.swing.*;
import java.awt.*;
import java.security.InvalidParameterException;

/**
 * Created by Elisabeth on 31.10.2017.
 */
public class ShowImageFilter extends DataTransformationFilter2<PlanarImage, PlanarImage> {

    private String _title;

    public ShowImageFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output, String title) throws InvalidParameterException {
        super( input, output );
        _title = title;
    }

    public ShowImageFilter(Readable<PlanarImage> input, String title) throws InvalidParameterException {
        super( input );
        _title = title;
    }

    public ShowImageFilter(Writeable<PlanarImage> output, String title) throws InvalidParameterException {
        super( output );
        _title = title;
    }

    protected PlanarImage process(PlanarImage image) {
        // Create a frame for display.
        JFrame frame = new JFrame();
        frame.setTitle( "DisplayJAI: " + _title );

// Get the JFrame� ContentPane.
        Container contentPane = frame.getContentPane();
        contentPane.setLayout( new BorderLayout() );

// Create an instance of DisplayJAI.
        DisplayJAI dj = new DisplayJAI( image );

// Add to the JFrame� ContentPane an instance of JScrollPane
// containing the DisplayJAI instance.
        contentPane.add( new JScrollPane( dj ), BorderLayout.CENTER );

// Set the closing operation so the application is finished.
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( 500, 600 ); // adjust the frame size.
        frame.setVisible( true ); // show the frame.
        return image;
    }
}
