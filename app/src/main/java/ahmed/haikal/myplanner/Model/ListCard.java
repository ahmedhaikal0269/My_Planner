package ahmed.haikal.myplanner.Model;

public class ListCard {

    String listName;
    int id, numberOfTasks, cardBackground;

    public ListCard(String listName, int id, int numberOfTasks, int cardBackground) {
        this.listName = listName;
        this.id = id;
        this.numberOfTasks = numberOfTasks;
        this.cardBackground = cardBackground;
    }

    public int getCardBackground() {
        return cardBackground;
    }

    public void setCardBackground(int cardBackground) {
        this.cardBackground = cardBackground;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNewListName() {
        return listName;
    }

    public void setNewListName(String newListName) {
        this.listName = newListName;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public void setNumberOfTasks(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }


}
