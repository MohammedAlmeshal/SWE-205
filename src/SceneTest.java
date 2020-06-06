import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.sun.javafx.geom.Point2D;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SceneTest extends Application implements Initializable {

	@FXML
	private Button lineBtn, rectBtn, deleteButton, selectBtn, moveBtn, fillColorBtn, triangleBtn, rhombusBtn, pentaBtn,
			ellipseBtn, saveBtn, openBtn, helpBtn;
	@FXML
	private ColorPicker colorPicker = new ColorPicker();
	private javafx.geometry.Point2D origin;
	private ArrayList<Shape> shapesArray = new ArrayList<Shape>();
	private Parent root;
	@FXML
	private Pane canvas = new Pane();;
	private Line line;
	private Rectangle rect;
	private Ellipse elps;
	private Polygon trngl, pntgon, rmbs;
	private Color color = Color.WHITE;
	@FXML
	private TextField widthTxt = new TextField();;
	private double orgSceneX, orgSceneY;
	private double orgTranslateX, orgTranslateY;
	private ArrayList<Shape> SelectedShapes = new ArrayList<Shape>();
	@FXML
	TextField setWidth;
	@FXML
	TextField setHeight;
	@FXML
	Button resize;
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		root = FXMLLoader.load(getClass().getResource("tst.fxml"));
		primaryStage.setTitle("MyPaintShop");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		root.requestFocus();

	}
	
	// picks color from color palet
	public void setColor() {
		canvas.setOnMouseMoved(event -> {
			event.consume();
			color = colorPicker.getValue();
		});
	}
  // the fill function which aplly on selected shapes 
	public void fillColorBtnAction() {
		System.out.println(color);

		// to stop any drawing action when filling the shapes
		canvas.setOnMouseDragged(null);
		canvas.setOnMousePressed(null);
		// disable move function for all shapes in the array
		for (int i = 0; i < shapesArray.size(); i++) {

			shapesArray.get(i).setOnMouseDragged(null);
			shapesArray.get(i).setOnMousePressed(null);
		}
		// the filling action 
		canvas.setOnMouseClicked((event) -> {
			event.consume();
			for (int i = 0; i < shapesArray.size(); i++) {
				if (shapesArray.get(i).contains(event.getX(), event.getY())
						&& SelectedShapes.contains(shapesArray.get(i))) {
					shapesArray.get(i).setFill(color);
					i = shapesArray.size() + 1;
				}
			}
		});
	}
		// Changing stroke color
	public void changeStroke() {
		// to stop any drawing action when filling the shapes
		canvas.setOnMouseDragged(null);
		canvas.setOnMousePressed(null);
		// disable move function for all shapes in the array
		for (int i = 0; i < shapesArray.size(); i++) {

			shapesArray.get(i).setOnMouseDragged(null);
			shapesArray.get(i).setOnMousePressed(null);
		}
		// change stroke on mouse clicked & shape selected
		canvas.setOnMouseClicked((event) -> {
			event.consume();
			for (int i = 0; i < shapesArray.size(); i++) {
				if (shapesArray.get(i).contains(event.getX(), event.getY())
						&& SelectedShapes.contains(shapesArray.get(i))) {
					shapesArray.get(i).setStroke(color);
					i = shapesArray.size() + 1;
				}
			}
		});
	}

	// to draw triangles
	public void trinangleBtnAction() {

		// to disable delete, move and select while drawing
		canvas.setOnMouseClicked(null);
		// disable move function for all shapes in the array
		for (int i = 0; i < shapesArray.size(); i++) {

			shapesArray.get(i).setOnMouseDragged(null);
		}
		 // get initial coordinates 
		canvas.setOnMousePressed((event) -> {
			event.consume();

			origin = new javafx.geometry.Point2D(event.getX(), event.getY());
			trngl = new Polygon();
			trngl.setFill(Color.TRANSPARENT);
			trngl.setStroke(Color.BLACK);
			trngl.setStrokeWidth(10);
			canvas.getChildren().add(trngl);
			shapesArray.add(0, trngl);// 0 to be in the top
		});

		canvas.setOnMouseDragged((event) -> {
			event.consume();
			trngl.getPoints().setAll(origin.getX(), event.getY(), // (x,y) of
																	// lower
																	// left
																	// point
					origin.getX() + ((event.getX() - origin.getX()) / 2), origin.getY(), // (x,y)
																							// of
																							// top
																							// of
																							// triangle
					event.getX(), event.getY() // (x,y) of lower right point
			);
		});

		canvas.setOnMouseReleased((event) -> {
			event.consume();
			trngl = null;
			origin = null;
		});
	}

	// to draw rhombuses(diamonds)
	public void rhombusBtnAction() {
		// to disable delete, move and select while drawing
		canvas.setOnMouseClicked(null);
		// disable move function for all shapes in the array
		for (int i = 0; i < shapesArray.size(); i++) {

			shapesArray.get(i).setOnMouseDragged(null);
		}
		canvas.setOnMousePressed((event) -> {
			event.consume();

			origin = new javafx.geometry.Point2D(event.getX(), event.getY());
			rmbs = new Polygon();
			rmbs.setFill(Color.TRANSPARENT);
			rmbs.setStroke(Color.BLACK);
			rmbs.setStrokeWidth(10);

			canvas.getChildren().add(rmbs);
			shapesArray.add(0, rmbs);// 0 to be in the top
		});

		canvas.setOnMouseDragged((event) -> {
			event.consume();

			rmbs.getPoints()
					.setAll(new Double[] { origin.getX(), origin.getY() + ((event.getY() - origin.getY()) / 2), // (x,y)
																												// of
																												// top
																												// of
																												// triangle
							origin.getX() + ((event.getX() - origin.getX()) / 2), origin.getY(), // (x,y)
																									// of
																									// upper
																									// right
																									// point
							event.getX(), origin.getY() + ((event.getY() - origin.getY()) / 2), // (x,y)
																								// of
																								// top
																								// of
																								// triangle
							origin.getX() + ((event.getX() - origin.getX()) / 2), event.getY(), // (x,y)
																								// of
																								// upper
																								// right
																								// point
			});

		});

		canvas.setOnMouseReleased((event) -> {
			event.consume();
			rmbs = null;
			origin = null;
		});
	}

	// to draw pentagons
	public void pentaBtnAction() {

		// to disable delete, move and select while drawing
		canvas.setOnMouseClicked(null);
		// disable move function for all shapes in the array
		for (int i = 0; i < shapesArray.size(); i++) {

			shapesArray.get(i).setOnMouseDragged(null);
		}

		canvas.setOnMousePressed((event) -> {
			event.consume();

			origin = new javafx.geometry.Point2D(event.getX(), event.getY());
			pntgon = new Polygon();
			pntgon.setFill(Color.TRANSPARENT);
			pntgon.setStroke(Color.BLACK);
			pntgon.setStrokeWidth(10);

			canvas.getChildren().add(pntgon);
			shapesArray.add(0, pntgon);// 0 to be in the top
		});

		canvas.setOnMouseDragged((event) -> {
			event.consume();

			pntgon.getPoints()
					.setAll(new Double[] { origin.getX() + ((event.getX() - origin.getX()) / 2), origin.getY(), // (x,y)
																												// of
																												// top
																												// point
							event.getX(), origin.getY() + (0.4 * (event.getY() - origin.getY())), // (x,y)
																									// of
																									// middle
																									// right
																									// point
							origin.getX() + (0.80 * (event.getX() - origin.getX())), event.getY(), // (x,y)
																									// of
																									// lower
																									// right
																									// point
							origin.getX() + (0.20 * (event.getX() - origin.getX())), event.getY(), // (x,y)
																									// of
																									// lower
																									// left
																									// point
							origin.getX(), origin.getY() + (0.4 * (event.getY() - origin.getY())) // (x,y)
																									// of
																									// middle
																									// left
																									// point
			});

		});

		canvas.setOnMouseReleased((event) -> {
			event.consume();
			pntgon = null;
			origin = null;
		});
	}

	// to draw ellipses
	public void ellipseBtnAction() {
		// to disable delete, move and select while drawing
		canvas.setOnMouseClicked(null);
		// disable move function for all shapes in the array
		for (int i = 0; i < shapesArray.size(); i++) {

			shapesArray.get(i).setOnMouseDragged(null);
		}

		canvas.setOnMousePressed((event) -> {
			event.consume();

			origin = new javafx.geometry.Point2D(event.getX(), event.getY());
			elps = new Ellipse();
			elps.setStrokeWidth(5.5);
			elps.setFill(Color.TRANSPARENT);
			elps.setStroke(Color.BLACK);
			elps.setStrokeWidth(10);

			canvas.getChildren().add(elps);
			shapesArray.add(0, elps);// 0 to be in the top
		});

		canvas.setOnMouseDragged((event) -> {
			event.consume();

			elps.setCenterX((origin.getX() + event.getX()) / 2.0);
			elps.setCenterY((origin.getY() + event.getY()) / 2.0);
			elps.setRadiusX(Math.abs(event.getX() - origin.getX()) / 2.0);
			elps.setRadiusY(Math.abs(event.getY() - origin.getY()) / 2.0);

		});

		canvas.setOnMouseReleased((event) -> {
			event.consume();
			elps = null;
			origin = null;
		});
	}

	// to draw rectangles
	public void rectBtnAction() {
		// to disable delete, move and select while drawing
		canvas.setOnMouseClicked(null);
		// disable move function for all shapes in the array
		for (int i = 0; i < shapesArray.size(); i++) {

			shapesArray.get(i).setOnMouseDragged(null);
			shapesArray.get(i).setOnMousePressed(null);
		}

		canvas.setOnMousePressed((event) -> {
			event.consume();
			origin = new javafx.geometry.Point2D(event.getX(), event.getY());

			rect = new Rectangle(origin.getX(), origin.getY(), 0.0, 0.0);
			rect.setFill(Color.TRANSPARENT);
			rect.setStroke(Color.BLACK);
			rect.setStrokeWidth(10);

			canvas.getChildren().add(rect);
			shapesArray.add(0, rect);// 0 to be in the top

		});

		canvas.setOnMouseDragged((event) -> {

			event.consume();
			if (event.getX() > origin.getX()) {
				rect.setWidth(event.getX() - origin.getX());
			} else {
				rect.setX((event.getX()));
				rect.setWidth(Math.abs(event.getX() - origin.getX()));
			}

			if (event.getY() > origin.getY()) {
				rect.setHeight(event.getY() - origin.getY());
			} else {
				rect.setY((event.getY()));
				rect.setHeight(origin.getY() - event.getY());
			}
		});

		canvas.setOnMouseReleased((event) -> {
			event.consume();
			rect = null;
			origin = null;
		});
	}

	// to draw lines
	public void lineBtnAction() {
		// to disable delete, move and select while drawing
		canvas.setOnMouseClicked(null);
		// disable move function for all shapes in the array
		for (int i = 0; i < shapesArray.size(); i++) {

			shapesArray.get(i).setOnMouseDragged(null);
		}

		canvas.setOnMousePressed((event) -> {
			event.consume();
			origin = new javafx.geometry.Point2D(event.getX(), event.getY());
			line = new Line();
			line.setStrokeWidth(8);
			line.setStroke(Color.BLACK);
			canvas.getChildren().add(line);
			shapesArray.add(0, line);// 0 to be in the top
		});

		canvas.setOnMouseDragged((event) -> {
			event.consume();

			line.setStartX(origin.getX());
			line.setStartY(origin.getY());
			line.setEndX(event.getX());
			line.setEndY(event.getY());

		});

		canvas.setOnMouseReleased((event) -> {
			event.consume();
			line = null;
			origin = null;
		});

	}
	// remove the shape selected
	public void removeBtnAction() {
		

		// to stop any drawing action when removing shapes
		canvas.setOnMouseDragged(null);
		canvas.setOnMousePressed(null);
		// disable move function for all shapes in the array
		for (int i = 0; i < shapesArray.size(); i++) {

			shapesArray.get(i).setOnMouseDragged(null);
			shapesArray.get(i).setOnMousePressed(null);
		}
		// remove shape on click
		canvas.setOnMouseClicked(event -> {
			event.consume();

			for (int i = 0; i < shapesArray.size(); i++) {
				if (shapesArray.get(i).contains(event.getX(), event.getY())
						&& SelectedShapes.contains(shapesArray.get(i))) {
					canvas.getChildren().remove(shapesArray.get(i));
					shapesArray.remove(shapesArray.get(i));
					i = shapesArray.size() + 1;
				}
			}
		});
	}
	// saves drawing as a photo
	public void saveDrawing(ActionEvent event) {

	
		try {
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
			fileChooser.getExtensionFilters().addAll(extFilterPNG);

			// Show open file dialog
			File file = fileChooser.showSaveDialog(null);
			WritableImage snapshot = canvas.snapshot(new SnapshotParameters(), null);

			ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
		} catch (IOException e) {
			System.out.println(e);
		}

	}
	// opens a photo into the canvas
	public void openDrawing(ActionEvent event) {

		
		FileChooser fileChooser = new FileChooser();

		// Set extension filter

		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
		fileChooser.getExtensionFilters().addAll(extFilterPNG);

		// Show open file dialog
		File file = fileChooser.showOpenDialog(null);
		Image img = null;
		try {
			img = new Image("file:///" + file.getAbsolutePath());
		} catch (Exception exception) {
			// handle exception here
		}

		ImageView imgView = new ImageView(img);
		canvas.getChildren().add(imgView);

	}
		// change shape dimensions
	public void resize(ActionEvent event) {
		// get wanted dimensions from text fields 
		double newWidth = Double.parseDouble(setWidth.getText());
		double newHeight = Double.parseDouble(setHeight.getText());
		for (int i = 0; i < shapesArray.size(); i++) {
			if (SelectedShapes.contains(shapesArray.get(i))) {
				
				// get current dimensions of selected shape
			Double oldWidth = shapesArray.get(i).getLayoutBounds().getWidth();
			Double oldHeight = shapesArray.get(i).getLayoutBounds().getHeight();
			
			// set new dementions
			shapesArray.get(i).setScaleX(newWidth / oldWidth);
			shapesArray.get(i).setScaleY(newHeight / oldHeight);
			}

		}

	}

	public void moveAction(ActionEvent event) {
		
		canvas.setOnMouseDragged(null);
		canvas.setOnMousePressed(null);
		canvas.setOnMouseClicked(null);

		for (int j = 0; j < shapesArray.size(); j++) {
			final int i = j;
			shapesArray.get(i).setOnMousePressed(e -> { // placing a listeners
														// for all the shapes
				e.consume();
				orgSceneX = e.getSceneX();
				orgSceneY = e.getSceneY();
				orgTranslateX = shapesArray.get(i).getTranslateX();
				orgTranslateY = shapesArray.get(i).getTranslateY();
				SelectedShapes.clear(); // clears selected shapes
				for (int h = 0; h < shapesArray.size(); h++) // clears style
																// from all
																// shapes
					shapesArray.get(h).setStyle("-fx-effect: null");

				SelectedShapes.add((shapesArray.get(i))); // selects a shape
				deleteButton.setDisable(false);
				fillColorBtn.setDisable(false);
				selectBtn.setDisable(false);
				setWidth.setDisable(false);
				setHeight.setDisable(false);
				resize.setDisable(false);

				// adds a style to the selected shape
				shapesArray.get(i).setStyle("-fx-effect: innershadow(one-pass-box, blue, 5, 10, 0, 0)");

			});

			shapesArray.get(i).setOnMouseEntered(e -> { // changes the mouse
														// when hovering over a
														// shape
				e.consume();
				shapesArray.get(i).setCursor(Cursor.HAND);
			});

			shapesArray.get(i).setOnMouseExited(e -> { // bring it back to
														// normal
				e.consume();
				shapesArray.get(i).setCursor(Cursor.DEFAULT);
			});

			shapesArray.get(i).setOnMouseDragged(e -> {
				e.consume();
				double offsetX = e.getSceneX() - orgSceneX;
				double offsetY = e.getSceneY() - orgSceneY;
				double newTranslateX = orgTranslateX + offsetX;
				double newTranslateY = orgTranslateY + offsetY;

				shapesArray.get(i).setTranslateX(newTranslateX);
				shapesArray.get(i).setTranslateY(newTranslateY);
			});
		}

		canvas.setOnMousePressed(e -> {
			e.consume();

			for (int i = 0; i < shapesArray.size(); i++) {
				if (!shapesArray.get(i).contains(e.getX(), e.getY())) { // clears
																		// selected
																		// shapes
					// when you press on the canvas
					shapesArray.get(i).setStyle("-fx-effect: null");
					SelectedShapes.clear();

				}
			}

		});

	}

	public void help(ActionEvent event) {

		Alert alert = new Alert(AlertType.INFORMATION);

		alert.setTitle("Help");
		alert.setHeaderText("MyPaintShop is a paint application programmed using Java programming language\n");
		alert.setContentText("MyPaintShop was designed and developed by: Majid, Ali, Muhammad, Turki and Mwafaq");
		alert.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		deleteButton.setDisable(true);
		fillColorBtn.setDisable(true);
		selectBtn.setDisable(true);
		setWidth.setDisable(true);
		setHeight.setDisable(true);
		resize.setDisable(true);
	}
}
