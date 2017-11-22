package beans;

import javax.media.jai.PlanarImage;
import java.util.EventObject;

public class ImageEvent extends EventObject {
    //Todo siehe Folien von Vollbrecht, classe anpassen so ist es kake aber compailert
    private PlanarImage value;
    public ImageEvent(Object object, PlanarImage image) {
        super(object);
        this.value = image;
    }

    public void setValue(PlanarImage value ){
        this.value = value;
    }

    public PlanarImage getValue() {
        return value;
    }
}
