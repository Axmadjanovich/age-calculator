package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {
    public Label lived;
    public Label age1;
    public Label age2;
    public AnchorPane pane, top;
    public ToolBar move;
    @FXML
    private AnchorPane anchor;
    @FXML
    private DatePicker picker;

    @FXML
    private Label year;

    @FXML
    private Label month;

    @FXML
    private Label day;

    @FXML
    private Label months;

    @FXML
    private Label weeks;

    @FXML
    private Label days;

    @FXML
    private Label hours;

    @FXML
    private Label minutes;

    @FXML
    private Label seconds;

    @FXML
    public void exit(ActionEvent event){
        System.exit(0);
        Platform.exit();
    }
    @FXML
    public void go(ActionEvent event){

        LocalDate ld = LocalDate.now();


        LocalDateTime ldt =  LocalDateTime.now();
        Period period = Period.between(picker.getValue(), ld);

        day.setText(String.valueOf(period.getDays()));
        year.setText(String.valueOf(period.getYears()));
        month.setText(String.valueOf(period.getMonths()));
        months.setText(String.valueOf(period.toTotalMonths()));

        LocalDateTime end = ldt.plus(period);
        String wiks = String.valueOf(ldt.until(end, ChronoUnit.WEEKS));
        String sekonds = String.valueOf(ldt.until(end, ChronoUnit.SECONDS));
        String deys = String.valueOf(ldt.until(end, ChronoUnit.DAYS));
        String minuts = String.valueOf(ldt.until(end, ChronoUnit.MINUTES));
        String ours = String.valueOf(ldt.until(end, ChronoUnit.HOURS));
        seconds.setText(sekonds);
        days.setText(deys);
        weeks.setText(wiks);
        minutes.setText(minuts);
        hours.setText(ours);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double [] xOffset = {0}, yOffset = {0};
                move.setOnMousePressed(pressEvent -> {
                    xOffset[0] = Main.ss.getX() - pressEvent.getScreenX();
                    yOffset[0] = Main.ss.getY() - pressEvent.getScreenY();
        });
        move.setOnMouseDragged(dragEvent -> {
            Main.ss.setX(dragEvent.getScreenX()+xOffset[0]);
            Main.ss.setY(dragEvent.getScreenY()+yOffset[0]);
        });

        LocalDate date = LocalDate.now();
        picker.setValue(date.minusYears(20));
        String format = "dd-MM-yyyy";
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        StringConverter stc = new StringConverter<LocalDate>() {

            @Override
            public String toString(LocalDate object) {
                if (object!=null)
                    return df.format(object);
                else return "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string!=null && !string.isEmpty())
                    return LocalDate.parse(string, df);
                else return null;
            }
        };
        picker.setConverter(stc);
        final Callback<DatePicker, DateCell> dateCellCallback = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty){
                        super.updateItem(item, empty);
                        setStyle("-fx-background-radius:20");
                        if (!item.isBefore(LocalDate.now())){
                            setDisable(true);
                            setStyle("-fx-background-color: pink");
                        }
                        else setStyle("-fx-background-color: lightGray");
                    }
                };
            }
        };
        picker.setDayCellFactory(dateCellCallback);
    }


    public void clear(ActionEvent actionEvent) {
        pane.getChildren().clear();
    }
}
