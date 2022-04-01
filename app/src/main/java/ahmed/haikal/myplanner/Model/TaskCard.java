package ahmed.haikal.myplanner.Model;

public class TaskCard {

    private int id, status;
    private String task, date, reminder;

    public TaskCard(int id, int status, String task, String date, String reminder) {
        this.id = id;
        this.status = status;
        this.task = task;
        this.date = date;
        this.reminder = reminder;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
