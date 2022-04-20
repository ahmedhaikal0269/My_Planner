package ahmed.haikal.myplanner.Controller.Listeners;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CustomItemTouchListener extends ItemTouchHelper.Callback implements RecyclerView.OnItemTouchListener{

    private final CustomClickListenerInterface customClickListenerInterface;
    private static GestureDetector gestureDetector;
    //private RecyclerView.Adapter adapter;

    public CustomItemTouchListener(Context context, final RecyclerView recyclerView,
                                   RecyclerView.Adapter adapter, CustomClickListenerInterface customClickListenerInterface) {

        this.customClickListenerInterface = customClickListenerInterface;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if(child != null && customClickListenerInterface != null)
                    customClickListenerInterface.onLongClick(child, recyclerView.getChildAdapterPosition(child));
            }

        });
    }

    @Override
    public boolean isLongPressDragEnabled(){
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        customClickListenerInterface.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        customClickListenerInterface.onItemSwiped(viewHolder, direction);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if( child != null && customClickListenerInterface != null && gestureDetector.onTouchEvent(e))
            customClickListenerInterface.onClick(child, rv.getChildAdapterPosition(child));
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
