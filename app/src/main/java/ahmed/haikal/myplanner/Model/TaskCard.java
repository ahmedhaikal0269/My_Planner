package ahmed.haikal.myplanner.Model;

public class TaskCard {

    private int status, checkBoxColor;
    private String task, date, reminder;

    public TaskCard(int status, String task, String date, String reminder, int checkBoxColor) {
        this.status = status;
        this.task = task;
        this.date = date;
        this.reminder = reminder;
        this.checkBoxColor = checkBoxColor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public int getCheckBoxColor() {
        return checkBoxColor;
    }

    public void setCheckBoxColor(int checkBoxColor) {
        this.checkBoxColor = checkBoxColor;
    }
}
