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
 * creating polygons from parameters and storing them in the array
 */
class openingfile {

    /**
     * @param fileforfunction
     * @return
     * @throws IOException
     * searching for polygon's parameters in String
     * creating polygons based on that parameters
     * storing polygons in the array
     */
    public static Shape[] open(String fileforfunction) throws IOException {
        {


            BufferedReader br = new BufferedReader(new FileReader((fileforfunction)));
            String st;
            String st2;
            int split=0;
            int lines=0;
            int m=0;
            while ((st = br.readLine()) != null) {
                if(st.contains("circle") || st.contains("rectangle")) {
                    lines++;
                }

            }
            br.close();
            Shape[] shape = new Shape[lines];
            BufferedReader br2 = new BufferedReader(new FileReader(fileforfunction));
            while((st2 = br2.readLine()) != null){
            if(st2.contains("circle")){
                String[] argumentsAsString =st2.split("\\s+");
                Circle circle = new Circle();
                Color color = Color.valueOf(argumentsAsString[1]);
                double circleX = Double.parseDouble(argumentsAsString[2]);
                double circleY = Double.parseDouble(argumentsAsString[3]);
                double radius = Double.parseDouble(argumentsAsString[4]);
                circle.setFill(color);
                circle.setCenterX(circleX);
                circle.setCenterY(circleY);
                circle.setRadius(radius);
                shape[m]=circle;
                m++;

            }
            else if(st2.contains("rectangle")){
                String[] argumentsAsString =st2.split("\\s+");
                Rectangle rectangle = new Rectangle();
                Color color = Color.valueOf(argumentsAsString[1]);
                double rectangleX = Double.parseDouble(argumentsAsString[2]);
                double rectangleY = Double.parseDouble(argumentsAsString[3]);
                double rectanglewidth = Double.parseDouble(argumentsAsString[4]);
                double rectangleheight = Double.parseDouble(argumentsAsString[5]);
                double rectanglescale = Double.parseDouble(argumentsAsString[6]);
                rectangle.setX(rectangleX);
                rectangle.setY(rectangleY);
                rectangle.setWidth(rectanglewidth);
                rectangle.setHeight(rectangleheight);
                rectangle.setScaleX(rectanglescale);
                rectangle.setScaleY(rectanglescale);
                shape[m]=rectangle;
                shape[m].setFill(color);
                m++;
            }
            else if(st2.contains("polygon")){

            }

            }
            return shape;
        }
    }
}