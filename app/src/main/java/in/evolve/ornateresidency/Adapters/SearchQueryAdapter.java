package in.evolve.ornateresidency.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

import in.evolve.ornateresidency.Models.City;
import in.evolve.ornateresidency.Models.Locality;
import in.evolve.ornateresidency.R;

/**
 * Created by Brekkishhh on 06-01-2017.
 */
public class SearchQueryAdapter extends ExpandableRecyclerViewAdapter<SearchQueryAdapter.CityViewHolder,SearchQueryAdapter.LocalityViewHolder> {


    public SearchQueryAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
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
    public void onBindChildViewHolder(LocalityViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        Locality l = ((City) group).getItems().get(childIndex);
      holder.setLocalitiesNames(l.getLocalityName());
    }

    @Override
    public void onBindGroupViewHolder(CityViewHolder holder, int flatPosition, ExpandableGroup group) {
       holder.setCityTitle(group);
    }

    protected class CityViewHolder extends GroupViewHolder {

        private TextView value;

        public CityViewHolder(View itemView) {
            super(itemView);

            value = (TextView) itemView.findViewById(R.id.list_city_name);
        }

        public void setCityTitle(ExpandableGroup genre) {

                value.setText(genre.getTitle());
                //icon.setBackgroundResource(((Genre) genre).getIconResId());
        }
    }

    protected class LocalityViewHolder extends ChildViewHolder{

        private TextView value;

        public LocalityViewHolder(View itemView) {
            super(itemView);

            value = (TextView) itemView.findViewById(R.id.list_item_locality_name);
        }

        public void setLocalitiesNames(String name){
            value.setText(name);
        }
    }
}
