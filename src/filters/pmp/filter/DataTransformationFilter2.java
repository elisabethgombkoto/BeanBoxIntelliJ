package filters.pmp.filter;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import filters.pmp.interfaces.Readable;
import filters.pmp.interfaces.Writeable;

/**
* same as DataTransformationFilter1, but with the possibility to output a new object of the same or another type
* as that of the incoming object
* 
* hook method: process(T):S which creates an S from a T
* 
* contract: a null entity signals end of stream
*/

public abstract class DataTransformationFilter2<T,S> extends AbstractFilter<T,S> {

    public DataTransformationFilter2(Readable<T> input, Writeable<S> output) throws InvalidParameterException {
        super(input, output);

    }

    public DataTransformationFilter2(Readable<T> input) throws InvalidParameterException {
        super(input);

    }

    public DataTransformationFilter2(Writeable<S> output) throws InvalidParameterException {
        super(output);

    }

    public S read() throws Exception {
        T entity = readInput();
        S result = null;
        if (entity != null) 
        	result = process(entity);
        if (result == ENDING_SIGNAL)
        	beforeSendingEndingSignal();

        return result;
    }

    public void write(T value) throws StreamCorruptedException {
        S result = null;
        if (value != null) 
        	result = process(value);
        if (result == ENDING_SIGNAL)
        	beforeSendingEndingSignal();
       writeOutput(result);
    }
    
    /**
     * does the transformation on entity
     * @param entity
     */
    protected abstract S process(T entity);

    

}
