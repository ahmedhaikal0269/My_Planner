package ahmed.haikal.myplanner.Model;

public class ListCard {

    String listName;
    int  numberOfTasks, cardBackground;

    public ListCard(String listName, int numberOfTasks, int cardBackground) {
        this.listName = listName;
        this.numberOfTasks = numberOfTasks;
        this.cardBackground = cardBackground;
    }

    public int getCardBackground() {
        return cardBackground;
    }

    public void setCardBackground(int cardBackground) {
        this.cardBackground = cardBackground;
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
