package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;

import javafx.scene.paint.Paint;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MouseDraw extends Application {
	//instanciated variables
	private Color paintColor;
	private Group group;
	private ArrayList<Polyline> lines;
	private Scene scene;
	private ColorPicker colorpicker;
	private Button button;
	
	//processes the color change
	private void processColor(ActionEvent event) {
		paintColor = colorpicker.getValue();
	}
	//processes the clear button
	private void processClear(ActionEvent event) {
		lines.clear();
		group.getChildren().clear();
		group.getChildren().add(colorpicker);
		group.getChildren().add(button);
		button.setLayoutX(150);
	}
	
	public void start(Stage primaryStage) {
		
		//initialize polyline
		lines = new ArrayList<Polyline>();
		
		//sets up handler for the mouse
		paintColor = Color.BLACK;
		colorpicker = new ColorPicker(Color.BLACK);
		colorpicker.setOnAction(this::processColor);
		
		//sets up button
		button = new Button("Clear Lines");
		button.setLayoutX(150);
		button.setOnAction(this::processClear);
		
		//sets up the handler for clicks
		EventHandler<MouseEvent> clicker = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//tracks the point of the mouse
				double [] point = {e.getX(), e.getY()};
				Polyline line = new Polyline(point);
				line.setStroke(paintColor);
				lines.add(line);
			}

	
		};
		//handles mouse drag
		EventHandler<MouseEvent> drag = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//gets coords of the mouse
				lines.get(lines.size()-1).getPoints().addAll(e.getX(), e.getY());
			}
		};
		
		//handles mouse release
		EventHandler<MouseEvent> release = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//draws a line
				group.getChildren().add(lines.get(lines.size()-1));
			}
		};
		
		//ties scene and buttons together in a group
		group = new Group(colorpicker, button);
		
		scene = new Scene(group, 500, 500);
		
		scene.addEventFilter(MouseEvent.MOUSE_PRESSED, clicker);
		scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, drag);
		scene.addEventFilter(MouseEvent.MOUSE_RELEASED, release);
		
		primaryStage.setTitle("MouseDraw");
		primaryStage.setScene(scene);
		primaryStage.show();
		

		
	}
	
	//main
	public static void main(String[] args) {
		launch(args);
	}

	
    } 