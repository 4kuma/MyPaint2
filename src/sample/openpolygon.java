package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * class for creating ShapeArray
 */
class openingfile2 {

    /**
     * @param fileforfunction
     * @return
     * @throws IOException
     * getting an string with arguments
     * reading String line by line
     * searching for the shapes and their parameters
     * creating shapes and storing them in the array
     */
    public static Polygon[] open(String fileforfunction) throws IOException {
        {


            BufferedReader br = new BufferedReader(new FileReader((fileforfunction)));
            String st;
            String st2;
            int split=0;
            int lines=0;
            int m=0;
            while ((st = br.readLine()) != null) {
                if(st.contains("polygon")) {
                    lines++;
                }

            }
            br.close();
            Polygon[] polygon = new Polygon[lines];
            BufferedReader br2 = new BufferedReader(new FileReader(fileforfunction));
            while((st2 = br2.readLine()) != null){
                if(st2.contains("circle")){

                }
                else if(st2.contains("rectangle")){
                }
                else if(st2.contains("polygon")){
                    polygon[m] = new Polygon();
                    String[] argumentsAsString1 = st2.split("\\[");
                    String firstsplit = argumentsAsString1[1];
                    String[] argumentsAsString2 = firstsplit.split("\\]");
                    String parametersasstring = argumentsAsString2[0];
                    String[] parameters = parametersasstring.split("\\s+");

                    for(int o=0;o<parameters.length-1;o++) {
                        parameters[o]=parameters[o].substring(0,parameters[o].length()-1);
                    }
                    for(int l=0;l<parameters.length;l=l+2){
                        double parameterx=Double.parseDouble(parameters[l]);
                        double parametery=Double.parseDouble(parameters[l+1]);

                        polygon[m].getPoints().addAll(parameterx, parametery);
                    }
                    String[] argumentsAsString = st2.split("\\s+");
                    int j = argumentsAsString.length;
                    Color color = Color.valueOf(argumentsAsString[1]);
                    double scalex = Double.parseDouble(argumentsAsString[j-2]);
                    double scaley= Double.parseDouble(argumentsAsString[j-1]);
                    polygon[m].setScaleX(scalex);
                    polygon[m].setScaleY(scaley);
                    double layoutx = Double.parseDouble(argumentsAsString[j-4]);
                    double layouty = Double.parseDouble(argumentsAsString[j-3]);
                    polygon[m].setLayoutX(layoutx);
                    polygon[m].setLayoutY(layouty);
                    polygon[m].setFill(color);
                    m++;


                }

            }
            return polygon;
        }
    }
}