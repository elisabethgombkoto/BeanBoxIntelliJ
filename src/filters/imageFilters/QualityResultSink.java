package filters.imageFilters;

import filters.pmp.filter.Sink;
import filters.pmp.interfaces.Readable;
import filters.utils.Coordinate;
import filters.utils.QualityData;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Created by Elisabeth on 11.11.2017.
 */
public class QualityResultSink extends Sink<ArrayList<QualityData>> {
  //expected_coordinates=[(73,77), (110,80), (202,80), (265,79), (330,81), (396,81)]

  private FileWriter _fileWriter;
  private String _exceptedMiddelCoordinatePath;
  private  int _tolerance = 5;
  private String _destinationPath;

  public QualityResultSink(Readable<ArrayList<QualityData>> input, String path, String destinationPath) throws InvalidParameterException, IOException {
    super(input);
    _exceptedMiddelCoordinatePath = path;
    _destinationPath = destinationPath;

  }

  public QualityResultSink( FileWriter fileWriter, String path, String destinationPath)  {
    _exceptedMiddelCoordinatePath = path;
    _destinationPath = destinationPath;

  }

  @Override
  public void write(ArrayList<QualityData> actualValues) throws StreamCorruptedException {
    try {
      if(_fileWriter == null){
        _fileWriter = new FileWriter(new File(_destinationPath));
      }


    ArrayList<Coordinate> expectedValues = createExpectedCordinateList();
    StringBuilder stringBuilder = new StringBuilder();

    int i = 1;

    for ( QualityData qualityData : actualValues) {
      String s = i++ + ".Point: Centre -> x = " + qualityData.get_centroid()._x + " y = " + qualityData.get_centroid()._y +
          ", Diameter-> " + qualityData.get_diameter() + ", Tolerance -> ±  " + _tolerance + " in the tolerance range ->  " +
          isInTolerance(qualityData.get_centroid(), expectedValues.get(i-2)) + "\n";
      stringBuilder.append(s);
    }

      _fileWriter.write(stringBuilder.toString());
      _fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private boolean isInTolerance(Coordinate actuelCordinate, Coordinate expectedCoordinate) {
    int differencX = actuelCordinate._x-expectedCoordinate._x;
    int differencY = actuelCordinate._y-expectedCoordinate._y;

    if((_tolerance> Math.abs(differencX)) &&(_tolerance>Math.abs(differencY))){
      return true;
    }
    return false;
  }

  private ArrayList<Coordinate> createExpectedCordinateList(){
    ArrayList<Coordinate> expected = new ArrayList<Coordinate>();
    StringBuilder sb = new StringBuilder();
    ArrayList<String> coordinateStrings = new ArrayList<>();

    BufferedReader br = null;
    try {
      br = new BufferedReader( new FileReader( _exceptedMiddelCoordinatePath) );
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    int c;
    try {

      while ((c = br.read()) != -1) {

        while ((!Character.isDigit( (char) c )) && c != -1) {
          c = br.read();
        }

        while ((Character.isDigit( (char) c )) && c != -1) {
          // System.out.println((char) c + " " );
          sb.append( (char) c );
          c = br.read();
        }

        if (!sb.toString().isEmpty()) {
          coordinateStrings.add( sb.toString() );
          sb = new StringBuilder();
        }

      }
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    int x = 0;
    int y = 0;
    for (int i = 0; i < coordinateStrings.size(); i++) {
      if (i % 2 == 0) {
        x = Integer.parseInt( coordinateStrings.get( i ) );
      } else {
        y = Integer.parseInt( coordinateStrings.get( i ) );
        expected.add( new Coordinate( x, y ) );
      }
    }


    return expected;
  }

  @Override
  public void run() {
    ArrayList<QualityData> input;
    try {
      input = m_Input.read();

      if (input != null) {
        write(input);
      }
    } catch (StreamCorruptedException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String get_exceptedMiddelCoordinatePath() {
    return _exceptedMiddelCoordinatePath;
  }

  public void set_exceptedMiddelCoordinatePath(String _exceptedMiddelCoordinatePath) {
    this._exceptedMiddelCoordinatePath = _exceptedMiddelCoordinatePath;
  }

  public String get_destinationPath() {
    return _destinationPath;
  }

  public void set_destinationPath(String _destinationPath) {
    this._destinationPath = _destinationPath;
  }

}