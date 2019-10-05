package hellofx;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.curiousworks.BlueMarble;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class BlueMarbleController {

	@FXML
	private ImageView image;

	@FXML
	private RadioButton natural;

	@FXML
	private ToggleGroup color;

	@FXML
	private RadioButton blackAndWhite;

	@FXML
	private Label colorLabel;

	@FXML
	private CheckBox enhanced;

	@FXML
	private DatePicker datePicker;

	@FXML
	private Text text;

	@FXML
	private URL location;

	@FXML
	private ResourceBundle resources;

	public void initialize() {
		enhanced.setVisible(false);
		colorLabel.setVisible(false);
		natural.setVisible(false);
		blackAndWhite.setVisible(false);
		text.setVisible(false);
		Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);

				if (item.isBefore(LocalDate.of(2015, 7, 1))) {
					setStyle("-fx-background-color: #ffc0cb;");
					setDisable(true);
					;
				}
			}
		};
		datePicker.setDayCellFactory(dayCellFactory);
	}

	private String selectedDate() {

		String date;
		if (datePicker.getValue().getMonthValue() < 10) {
			if (datePicker.getValue().getDayOfMonth() < 10) {
				date = datePicker.getValue().getYear() + "-0" + datePicker.getValue().getMonthValue() + "-0"
						+ datePicker.getValue().getDayOfMonth();
			} else {
				date = datePicker.getValue().getYear() + "-0" + datePicker.getValue().getMonthValue() + "-"
						+ datePicker.getValue().getDayOfMonth();
			}
		} else {
			if (datePicker.getValue().getDayOfMonth() < 10) {
				date = datePicker.getValue().getYear() + "-" + datePicker.getValue().getMonthValue() + "-0"
						+ datePicker.getValue().getDayOfMonth();
			} else {
				date = datePicker.getValue().getYear() + "-" + datePicker.getValue().getMonthValue() + "-"
						+ datePicker.getValue().getDayOfMonth();
			}
		}
		return date + "";
	}

	@FXML
	void updateDate(ActionEvent event) {

		enhanced.setSelected(false);

		BlueMarble blueMarble = new BlueMarble();
		blueMarble.setDate(selectedDate());

		try {
			if (datePicker.getValue().isAfter(LocalDate.now())) {
				throw new Exception("You selected future date!");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			text.setVisible(true);
		}

		if (datePicker.getValue().isBefore(LocalDate.of(2018, 6, 30))) {
			enhanced.setVisible(true);
		} else {
			enhanced.setVisible(false);
		}

		colorLabel.setVisible(true);
		natural.setVisible(true);
		blackAndWhite.setVisible(true);

		image.setImage(new Image(blueMarble.getImage()));
	}

	@FXML
	void enhancedSelected(ActionEvent event) {

		BlueMarble blueMarble = new BlueMarble();
		blueMarble.setDate(selectedDate());

		if (enhanced.isSelected()) {
			blueMarble.setEnhanced(true);
			image.setImage(new Image(blueMarble.getImage()));
		} else {
			updateDate(event);
		}
	}

	@FXML
	void naturalOrBlackAndWhite(ActionEvent event) {

		if (color.getSelectedToggle().equals(blackAndWhite)) {
			ColorAdjust colorAdjust = new ColorAdjust();
			colorAdjust.setSaturation(-1);
			image.setEffect(colorAdjust);
		} else {
			image.setEffect(null);
		}
	}

}
