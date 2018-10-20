package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.text.html.Option;
import java.awt.event.MouseWheelEvent;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;

import static javafx.scene.input.MouseEvent.*;




/**
 * Main class.
 * Author Marek Smorag
 * Creating application with options: drawing shapes, resizing shapes,moving shapes with mouse,saving shapes do *txt and opening saved shapes.
 */
public class Main extends Application {

    Object anyShape;
    Object anyShape2;
    double previousX, previousY;
    Pane root;
    double layouty;
    double layoutx;

    File selectedfilemain=null;
    String saving = new String("");

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout = new VBox(20);
        HBox hbox = new HBox(50);
        HBox tools = new HBox(50);
        HBox colortools = new HBox(0);
        TextField textfield = new TextField();
        root = new Pane();
        root.setPrefSize(1000, 600);

        MenuBar menubar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem info = new MenuItem("Info");
        menubar.getMenus().addAll(menu);
        MenuItem save = new MenuItem("Save...");
        MenuItem open = new MenuItem("Open...");
        menu.getItems().addAll(save, open,info);


        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(root.widthProperty());
        clip.heightProperty().bind(root.heightProperty());
        root.setClip(clip);

        RadioButton circle = new RadioButton("circle");
        RadioButton rectangle = new RadioButton("rectangle");
        RadioButton polygon = new RadioButton("polygon");
        RadioButton sizechange = new RadioButton("change size");
        RadioButton colorchange = new RadioButton();
        RadioButton moveshape = new RadioButton("move");
        ColorPicker color = new ColorPicker();
        color.setValue(Color.RED);

        ToggleGroup buttongroup = new ToggleGroup();
        circle.setToggleGroup(buttongroup);
        rectangle.setToggleGroup(buttongroup);
        sizechange.setToggleGroup(buttongroup);
        colorchange.setToggleGroup(buttongroup);
        moveshape.setToggleGroup(buttongroup);
        polygon.setToggleGroup(buttongroup);

        tools.getChildren().addAll(sizechange, moveshape, rectangle, circle, polygon);
        colortools.getChildren().addAll(colorchange, color);
        hbox.getChildren().addAll(tools, colortools);
        layout.getChildren().addAll(menubar, hbox, root, textfield);

        ArrayList<Double> parameters = new ArrayList<>();
        ArrayList circles = new ArrayList();
        ArrayList rectangles = new ArrayList();
        ArrayList<Double> parameters2 = new ArrayList<>();
        ArrayList<Double> parameters3 = new ArrayList<>();
        ArrayList<Double> parameters4 = new ArrayList<>();
        ArrayList<Object> polygonarray = new ArrayList<>();

        ArrayList<Object> savingarray = new ArrayList<>();

        info.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informations");
            alert.setHeaderText(null);
            alert.setContentText("Application:MyPaint\n"+"Author:Marek Smorag\n"+"Purpose of the program: drawing shapes and editing them\n");
            alert.showAndWait();
        });

        save.setOnAction(event -> {
            ObservableList allshapes=root.getChildren();

            for (int m = 0; m < allshapes.size(); m++) {
                if (allshapes.get(m) instanceof Circle) {
                    Circle somecircle = (Circle) allshapes.get(m);
                    saving = saving.concat("circle " + somecircle.getFill() + " " + somecircle.getCenterX() + " " + somecircle.getCenterY() + " " + (somecircle.getBoundsInParent().getWidth())/2 + "\n");
                }
                else if (allshapes.get(m) instanceof Rectangle) {
                    Rectangle somerectangle = (Rectangle)allshapes.get(m);
                    saving = saving.concat("rectangle " + somerectangle.getFill() + " " + somerectangle.getX() + " " + somerectangle.getY() + " " + somerectangle.getWidth() + " " + somerectangle.getHeight() + " "+ somerectangle.getScaleX()+ "\n");
                }
                else if (allshapes.get(m) instanceof Polygon) {
                    Polygon somepolygon = (Polygon) allshapes.get(m);
                    saving = saving.concat("polygon " + somepolygon.getFill() + " " + somepolygon.getPoints() + " " +somepolygon.getLayoutX()+" "+somepolygon.getLayoutY()+" "+ somepolygon.getScaleX() + " " + somepolygon.getScaleY() + "\n");

                }
            }
            System.out.println(saving);
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Saving");
            dialog.setHeaderText("Saving");
            dialog.setContentText("Enter a file name:");
            Optional<String> result = dialog.showAndWait();


            result.ifPresent(e -> {
                        try {
                            FileWriter fw = new FileWriter("C:\\Users\\Marek\\IdeaProjects\\MyPaint2\\" + result.get() + ".txt");
                            fw.write(saving);
                            fw.close();


                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }


                    }

            );


        });
        open.setOnAction(event -> {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("C:\\Users\\Marek\\IdeaProjects\\MyPaint2\\"));
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*txt")
            );
            File selectedfile = fc.showOpenDialog(null);
            if (selectedfile != null) {
                layout.getChildren().removeAll(root, textfield);
                root = null;
                root = new Pane();
                root.setPrefSize(1000, 680);
                Rectangle clip1 = new Rectangle();
                clip1.widthProperty().bind(root.widthProperty());
                clip1.heightProperty().bind(root.heightProperty());
                root.setClip(clip1);
                layout.getChildren().addAll(root, textfield);
                saving="";
                String fileforfunction = String.valueOf(selectedfile);
                try {
                    Shape[] k = openingfile.open(fileforfunction);
                    for(int p=0;p<k.length;p++){
                            root.getChildren().add(k[p]);

                    }
                    Polygon[] l = openingfile2.open(fileforfunction);
                    for(int u=0;u<l.length;u++){
                        root.getChildren().add(l[u]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        });

        EventHandler<MouseEvent> sizechangeevent1 = new EventHandler<MouseEvent>() {
            /**
             * @param event getting shape focused
             */
            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    anyShape2 = event.getTarget();
                }
            }
        };
        EventHandler<ScrollEvent> sizechangeevent2 = new EventHandler<ScrollEvent>() {
            /**
             * @param event setting scaling for focused shape by using scroll
             *              if we are scrolling down, shape size is decreasing
             *              if we are scrolling up, shape size is increasing
             */
            @Override
            public void handle(ScrollEvent event) {
                if (event.getEventType().equals(ScrollEvent.SCROLL)) {
                    if (anyShape2 instanceof Shape) {
                        Shape shapeforhandler = (Shape) anyShape2;
                        if (event.getDeltaY() > 0) {
                            double deltax = shapeforhandler.getScaleX() * 1.1;
                            double deltay = shapeforhandler.getScaleY() * 1.1;
                            shapeforhandler.setScaleX(deltax);
                            shapeforhandler.setScaleY(deltay);

                        }
                        if (event.getDeltaY() < 0) {
                            double deltax = shapeforhandler.getScaleX() * 0.9;
                            double deltay = shapeforhandler.getScaleY() * 0.9;
                            shapeforhandler.setScaleX(deltax);
                            shapeforhandler.setScaleY(deltay);
                        }
                    }
                }

            }
        };

        EventHandler<MouseEvent> circleevent = new EventHandler<MouseEvent>() {
            /**
             * @param event creating circle by getting center x and y  when mouse is clicked
             *              calculating circle's radius by getting  parameters when mouse is released
             *              creating circle with taken radius and center x and y
             */
            @Override
            public void handle(MouseEvent event) {
                double firstX = 0;
                double firstY = 0;
                double secondX = 0;
                double secondY = 0;
                double delta = 0;
                Circle circle1 = new Circle();

                if (event.getEventType().equals(MOUSE_PRESSED)) {
                    firstX = event.getX();
                    firstY = event.getY();
                    parameters.add(firstX);
                    parameters.add(firstY);

                }

                if (event.getEventType().equals(MOUSE_RELEASED)) {
                    secondX = event.getX();
                    secondY = event.getY();
                    delta = Math.sqrt(((secondX - parameters.get(0)) * (secondX - parameters.get(0))) + (secondY - parameters.get(1)) * (secondY - parameters.get(1)));
                    circle1.setRadius(delta);
                    circle1.setCenterX(parameters.get(0));
                    circle1.setCenterY(parameters.get(1));

                    circles.add(circle1);

                    savingarray.add(circle1);

                    root.getChildren().add(circle1);
                    textfield.setText("Parameters: " + parameters.get(0) + ", " + parameters.get(1) + "  Radius: " + delta);
                    parameters.clear();

                }
            }
        };

        EventHandler<MouseEvent> rectangleevent = new EventHandler<MouseEvent>() {
            /**
             * @param event
             * getting first x and first y parameters when mouse is clicked
             * getting second x and second y when mouse is released
             * calculating minimum of first x and second x
             * calculating minimum of first y and second y
             * calculating the difference between parameters x-setting width
             * calculating the difference between parameters y-setting height
             * creating rectangle
             * setting his x and y (minimum of x's and minimum of y's)
             * setting his width and height
             *
             */
            @Override
            public void handle(MouseEvent event) {
                double firstX = 0;
                double firstY = 0;
                double secondX = 0;
                double secondY = 0;
                double width = 0;
                double height = 0;
                double minimumx = 0;
                double minimumy = 0;
                Rectangle rectangle1 = new Rectangle();
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    firstX = event.getX();
                    firstY = event.getY();
                    parameters.add(firstX);
                    parameters.add(firstY);

                }
                if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                    secondX = event.getX();
                    secondY = event.getY();

                    minimumx = Math.min(secondX, parameters.get(0));
                    minimumy = Math.min(secondY, parameters.get(1));

                    width = Math.abs(secondX - parameters.get(0));
                    height = Math.abs(secondY - parameters.get(1));

                    rectangle1.setX(minimumx);
                    rectangle1.setY(minimumy);
                    rectangle1.setWidth(width);
                    rectangle1.setHeight(height);

                    rectangles.add(rectangle1);
                    savingarray.add(rectangle1);

                    root.getChildren().add(rectangle1);
                    textfield.setText("Height: " + height + ", " + "  Width:  " + width);
                    parameters.clear();
                }
            }
        };
        EventHandler<MouseEvent> moveshapeevent = new EventHandler<MouseEvent>() {
            /**
             * @param event moving shapes
             * taking parameters when mouse is clicked(first x first y)
             *  taking other parameters when mouse is dragged
             *              calculating the difference between parameters
             *              adding the difference to the previous parameters of circle
             *              adding the difference to the previous parameters of rectangle
             *              adding the difference to polygon's layout
             *
             */
            @Override
            public void handle(MouseEvent event) {
                double firstX1;
                double firstY1;

                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    parameters2.clear();
                    anyShape = event.getTarget();
                    firstX1 = event.getX();
                    firstY1 = event.getY();
                    parameters2.add(firstX1);
                    parameters2.add(firstY1);
                    if (anyShape instanceof Circle) {
                        Circle circle = (Circle) anyShape;
                        previousX = circle.getCenterX();
                        previousY = circle.getCenterY();

                    } else if (anyShape instanceof Rectangle) {
                        Rectangle rectangle = (Rectangle) anyShape;
                        previousX = rectangle.getX();
                        previousY = rectangle.getY();
                    } else if (anyShape instanceof Polygon) {
                        Polygon polygon = (Polygon) anyShape;
                        layoutx = polygon.getLayoutX();
                        layouty = polygon.getLayoutY();

                    }
                } else if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
                    if (anyShape instanceof Circle) {
                        Circle anyshape1 = (Circle) anyShape;
                        anyshape1.setCenterX(previousX + (event.getX() - parameters2.get(0)));
                        anyshape1.setCenterY(previousY + (event.getY() - parameters2.get(1)));
                        textfield.setText("Parameters:X: " + anyshape1.getCenterX() + ", Y:" + anyshape1.getCenterY() + "  Radius: " + (anyshape1.getBoundsInParent().getWidth()) / 2);
                    } else if (anyShape instanceof Rectangle) {
                        Rectangle anyshape2 = (Rectangle) anyShape;
                        anyshape2.setX(previousX + (event.getX() - parameters2.get(0)));
                        anyshape2.setY(previousY + (event.getY() - parameters2.get(1)));
                        textfield.setText("Parameters: X:" + anyshape2.getX() + ",Y: " + anyshape2.getY() + " Height: " + anyshape2.getBoundsInParent().getHeight() + "  Width: " + anyshape2.getBoundsInParent().getWidth());
                    } else if (anyShape instanceof Polygon) {
                        Polygon anyshape3 = (Polygon) anyShape;
                        anyshape3.setLayoutX(layoutx + (event.getX() - parameters2.get(0)));
                        anyshape3.setLayoutY(layouty + (event.getY() - parameters2.get(1)));
                    }
                }

            }
        };
        EventHandler<MouseEvent> changecolorevent = new EventHandler<MouseEvent>() {
            /**
             * @param event changing colors
             *              taking color code from colorpicker
             *              getting shape as an target
             *              changing shape fill with color code from colorpicker
             */
            @Override
            public void handle(MouseEvent event) {
                Color whatcolor = color.getValue();
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    if (event.getTarget() instanceof Shape) {


                        Shape anyshape3 = (Shape) event.getTarget();
                        whatcolor = color.getValue();
                        anyshape3.setFill(whatcolor);
                    }

                }
            }
        };
        EventHandler<MouseEvent> polygonevent = new EventHandler<MouseEvent>() {
            /**
             * @param event creating polygon
             *              taking parameters when mouse is clicked
             *              storing them in the arraylist
             *              creating a polygon when parameters is clicked nearby first parameter
             *              adding stored parameters as points x and y to the polygon
             *
             */
            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    if (parameters3.size() == 0) {
                        double clickedX = event.getX();
                        double clickedY = event.getY();
                        parameters3.add(clickedX);
                        parameters4.add(clickedY);
                        Circle tempcircle = new Circle(clickedX, clickedY, 5);
                        tempcircle.setFill(Color.RED);
                        polygonarray.add(tempcircle);
                        root.getChildren().add(tempcircle);
                    } else if (parameters3.size() > 2 && (Math.abs((event.getX() - parameters3.get(0))) < 5) && (Math.abs((event.getY() - parameters4.get(0))) < 5)) {
                        Polygon polygon = new Polygon();
                        for (int k = 0; k < parameters3.size(); k++) {
                            if (k == parameters3.size() - 1) {
                                double polygonX = parameters3.get(k);
                                double polygonY = parameters4.get(k);
                                polygon.getPoints().addAll(parameters3.get(k), parameters4.get(k));
                                root.getChildren().add(polygon);
                                savingarray.add(polygon);

                            } else {
                                polygon.getPoints().addAll(parameters3.get(k), parameters4.get(k));
                            }
                        }
                        parameters3.clear();
                        parameters4.clear();
                        for (int i = 0; i < polygonarray.size(); i++) {
                            root.getChildren().remove(polygonarray.get(i));
                        }
                        polygonarray.clear();
                    } else {
                        double clickedX = event.getX();
                        double clickedY = event.getY();
                        parameters3.add(clickedX);
                        parameters4.add(clickedY);
                        Circle tempcircle = new Circle(clickedX, clickedY, 5);
                        polygonarray.add(tempcircle);
                        root.getChildren().add(tempcircle);
                    }
                }
            }
        };

        buttongroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            /**
             *
             * @param ov
             * @param old_toggle
             * @param new_toggle
             * adding events to the toggles and removing them
             */
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {

                if (circle.isSelected()) {
                    root.removeEventFilter(ANY, rectangleevent);
                    root.removeEventFilter(ANY, moveshapeevent);
                    root.removeEventFilter(ANY, changecolorevent);
                    root.removeEventFilter(ANY, polygonevent);
                    root.removeEventFilter(ANY, sizechangeevent1);
                    root.removeEventFilter(ScrollEvent.SCROLL, sizechangeevent2);
                    root.addEventFilter(ANY, circleevent);

                }
                if (rectangle.isSelected()) {
                    root.removeEventFilter(ANY, circleevent);
                    root.removeEventFilter(ANY, moveshapeevent);
                    root.removeEventFilter(ANY, changecolorevent);
                    root.removeEventFilter(ANY, polygonevent);
                    root.removeEventFilter(ANY, sizechangeevent1);
                    root.removeEventFilter(ScrollEvent.SCROLL, sizechangeevent2);
                    root.addEventFilter(ANY, rectangleevent);
                }
                if (colorchange.isSelected()) {
                    root.removeEventFilter(ANY, circleevent);
                    root.removeEventFilter(ANY, moveshapeevent);
                    root.removeEventFilter(ANY, rectangleevent);
                    root.removeEventFilter(ANY, polygonevent);
                    root.removeEventFilter(ANY, sizechangeevent1);
                    root.removeEventFilter(ScrollEvent.SCROLL, sizechangeevent2);
                    root.addEventFilter(ANY, changecolorevent);
                }
                if (moveshape.isSelected()) {
                    root.removeEventFilter(ANY, circleevent);
                    root.removeEventFilter(ANY, rectangleevent);
                    root.removeEventFilter(ANY, changecolorevent);
                    root.removeEventFilter(ANY, polygonevent);
                    root.removeEventFilter(ANY, sizechangeevent1);
                    root.removeEventFilter(ScrollEvent.SCROLL, sizechangeevent2);
                    root.addEventFilter(ANY, moveshapeevent);

                }
                if (sizechange.isSelected()) {
                    root.removeEventFilter(ANY, circleevent);
                    root.removeEventFilter(ANY, rectangleevent);
                    root.removeEventFilter(ANY, changecolorevent);
                    root.removeEventFilter(ANY, moveshapeevent);
                    root.removeEventFilter(ANY, polygonevent);
                    root.addEventFilter(ANY, sizechangeevent1);
                    root.addEventFilter(ScrollEvent.SCROLL, sizechangeevent2);
                }
                if (polygon.isSelected()) {
                    root.removeEventFilter(ANY, circleevent);
                    root.removeEventFilter(ANY, rectangleevent);
                    root.removeEventFilter(ANY, changecolorevent);
                    root.removeEventFilter(ANY, moveshapeevent);
                    root.removeEventFilter(ANY, sizechangeevent1);
                    root.removeEventFilter(ScrollEvent.SCROLL, sizechangeevent2);
                    root.addEventFilter(ANY, polygonevent);
                }
            }
        });

        primaryStage.setTitle("MyPaint");
        primaryStage.setScene(new Scene(layout, 800, 690));
        primaryStage.show();
    }


    /**
     * Main method.
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
