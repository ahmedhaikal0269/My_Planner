package ahmed.haikal.myplanner.Controller.Listeners;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface CustomClickListenerInterface {

    public void onClick(View view, int position);

    public void onLongClick(View view, int position);

    public void onItemMove(int fromPos, int toPos);

    public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int direction);

    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive);
}
