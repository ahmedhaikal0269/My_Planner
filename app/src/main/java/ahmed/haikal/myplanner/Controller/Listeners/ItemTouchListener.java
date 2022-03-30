package ahmed.haikal.myplanner.Controller.Listeners;

public interface ItemTouchListener {

    public void onItemMove(int fromPos, int toPos);

    public void onItemSwiped(int position, int direction);

}
