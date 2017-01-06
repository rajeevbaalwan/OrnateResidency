package in.evolve.ornateresidency.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

import in.evolve.ornateresidency.Activities.PgListActivity;
import in.evolve.ornateresidency.Models.City;
import in.evolve.ornateresidency.Models.Locality;
import in.evolve.ornateresidency.R;
import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by Brekkishhh on 06-01-2017.
 */
public class SearchQueryAdapter extends ExpandableRecyclerViewAdapter<SearchQueryAdapter.CityViewHolder,SearchQueryAdapter.LocalityViewHolder> {


    private Context context ;
    public SearchQueryAdapter(Context context ,List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
    }

    @Override
    public CityViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        return new CityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_city_row,parent,false));
    }

    @Override
    public LocalityViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        return new LocalityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_locality_row,parent,false));
    }

    @Override
    public void onBindChildViewHolder(LocalityViewHolder holder, int flatPosition, final ExpandableGroup group, int childIndex) {
        Locality l = ((City) group).getItems().get(childIndex);
      holder.setLocalitiesNames(l.getLocalityName());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PgListActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onBindGroupViewHolder(CityViewHolder holder, int flatPosition, ExpandableGroup group) {
       holder.setCityTitle(group);
    }

    protected class CityViewHolder extends GroupViewHolder {

        private TextView value;
        private ImageView arrow;

        public CityViewHolder(View itemView) {
            super(itemView);

            value = (TextView) itemView.findViewById(R.id.list_city_name);
            arrow = (ImageView) itemView.findViewById(R.id.list_item_genre_arrow);
        }

        public void setCityTitle(ExpandableGroup genre) {

                value.setText(genre.getTitle());
                //icon.setBackgroundResource(((Genre) genre).getIconResId());
        }

        @Override
        public void expand() {
            animateExpand();
        }

        @Override
        public void collapse() {
            animateCollapse();
        }

        private void animateExpand() {
            RotateAnimation rotate = new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.setAnimation(rotate);
        }

        private void animateCollapse() {
            RotateAnimation rotate = new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.setAnimation(rotate);
        }
    }

    protected class LocalityViewHolder extends ChildViewHolder{

        private TextView value;
        private FrameLayout mainLayout;

        public LocalityViewHolder(View itemView) {
            super(itemView);

            value = (TextView) itemView.findViewById(R.id.list_item_locality_name);
            mainLayout = (FrameLayout) itemView.findViewById(R.id.mainContainer);


        }

        public void setLocalitiesNames(String name){
            value.setText(name);
        }
    }
}
