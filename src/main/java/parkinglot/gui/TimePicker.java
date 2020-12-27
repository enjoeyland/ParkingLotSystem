package parkinglot.gui;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class TimePicker extends JPanel {
    JSpinner spinner;

    public TimePicker() {

        this.spinner = new JSpinner();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
        calendar.set(Calendar.MINUTE, 0);

        SpinnerDateModel model = new SpinnerDateModel();
        model.setValue(calendar.getTime());
        spinner.setModel(model);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); // this makes what you want
        formatter.setOverwriteMode(true);
        formatter.setCommitsOnValidEdit(true);

        spinner.setEditor(editor);

        add(spinner);
    }

    public LocalTime getTime() {
        return LocalDateTime.ofInstant(((Date) this.spinner.getValue()).toInstant(),
                ZoneId.systemDefault()).toLocalTime();
    }


    // https://stackoverflow.com/questions/21960236/jspinner-time-picker-model-editing
}
