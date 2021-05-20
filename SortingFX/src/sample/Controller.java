package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Controller {

    @FXML
    private HBox lineBox;

    @FXML
    private ComboBox sortingBox;

    private static int numOfLines = 35;
    private static double[] lineLengths = new double[numOfLines];

    @FXML
    public void generateLines(){
        lineBox.getChildren().clear();
        for (int i = 0; i < numOfLines; i++) {
            Line line = new Line();
            line.setStartY(0);
            line.setEndY(Math.random() * 300);
            line.setStrokeWidth(3);
            lineBox.getChildren().add(line);
            double length = line.getEndY();
            lineLengths[i] = length;
        }
        sortingBox.setValue("");

    }

    @FXML
    public void onSortingBoxChange() {

        if (sortingBox.getValue().equals("Bubble Sort")){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    bubbleSort();
                    for (int i = 0; i < numOfLines; i++){
                        ((Line)lineBox.getChildren().get(i)).setStroke(Color.LIGHTGREEN);
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
        if (sortingBox.getValue().equals("Insertion Sort")){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    insertionSort();
                    for (int i = 0; i < numOfLines; i++){
                        ((Line)lineBox.getChildren().get(i)).setStroke(Color.LIGHTGREEN);
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void bubbleSort(){

        for(int i = 0; i < numOfLines; i++){

            if(i + 2  > numOfLines){
                break;
            }
            for (int j = 0; j < numOfLines - 1; j++){
                int finalJ = j;
                Line firstLine = (Line)lineBox.getChildren().get(finalJ);
                Line secondLine = (Line)lineBox.getChildren().get(finalJ +1);

                Platform.runLater(() -> {
                   firstLine.setStroke(Color.LIGHTGREEN);

                    if (firstLine.getEndY() > secondLine.getEndY()) {

                        lineBox.getChildren().set(finalJ, new Line());
                        lineBox.getChildren().set(finalJ + 1, new Line());

                        firstLine.setStroke(Color.LIGHTBLUE);
                        lineBox.getChildren().set(finalJ, secondLine);
                        lineBox.getChildren().set(finalJ + 1, firstLine);

                    }
                });
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                }
                firstLine.setStroke(Color.BLACK);

            }
        }
    }

    public void insertionSort(){
        for (int i = 1; i < numOfLines; i++){
            int finalI = i;
            Line lineA = (Line)lineBox.getChildren().get(finalI);


            Platform.runLater(() -> {
                int j = finalI - 1;
                ((Line)lineBox.getChildren().get(j+1)).setStroke(Color.LIGHTGREEN);
                while(j >= 0 && ((Line)lineBox.getChildren().get(j)).getEndY() > lineA.getEndY()){
                    Line lineB = (Line)lineBox.getChildren().get(j);
                    lineBox.getChildren().set(j, new Line());
                    lineBox.getChildren().set(j+ 1, new Line());


                    lineA.setStroke(Color.BLUE);
                    lineBox.getChildren().set(j+1, lineB);

                    j--;
                }
                lineBox.getChildren().set(j + 1, lineA);
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
            lineA.setStroke(Color.BLACK);
        }
    }
}
