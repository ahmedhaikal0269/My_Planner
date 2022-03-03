package ahmed.haikal.myplanner.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import ahmed.haikal.myplanner.Controller.All_Lists_Adapter.List_View_Holder;
import ahmed.haikal.myplanner.Model.ListCard;
import ahmed.haikal.myplanner.R;

public class All_Lists_Adapter extends RecyclerView.Adapter<List_View_Holder> {

    List<ListCard> all_lists;
    Context context;

    public All_Lists_Adapter(List<ListCard> all_lists, Context context){
        this.all_lists = all_lists;
        context = context;
    }

    @Override
    public List_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cardview, parent, false);
        List_View_Holder listViewHolder =new List_View_Holder(view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(List_View_Holder holder, int position) {
        ListCard list = all_lists.get(position);
        holder.list_title.setText(list.getNewListName());
        holder.numofTasks.setText(String.valueOf(list.getNumberOfTasks()));
        holder.listCardview.setCardBackgroundColor(list.getCardBackground());

    }

    @Override
    public int getItemCount() {
        return all_lists.size();
    }


    //add item to the recyclerview
    public void insert(ListCard list){
        all_lists.add(list);
        notifyItemInserted(all_lists.size() - 1);
    }

    //remove item from the list
    public void remove(ListCard list){
        int position = all_lists.indexOf(list);
        all_lists.remove(position);
        notifyItemRemoved(position);
    }



    // ----------------- View Holder Class ------------------//

    class List_View_Holder extends RecyclerView.ViewHolder {

        CardView listCardview;
        TextView list_title,numofTasks;

        public List_View_Holder(@NonNull View itemView) {
            super(itemView);

            listCardview = itemView.findViewById(R.id.listCardView);
            list_title = itemView.findViewById(R.id.list_title);
            numofTasks = itemView.findViewById(R.id.num_of_tasks);
        }
    }

}
