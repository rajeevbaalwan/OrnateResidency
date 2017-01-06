package in.evolve.ornateresidency.Adapters;

import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

/**
 * Created by Brekkishhh on 06-01-2017.
 */
public class SearchQueryAdapter extends ExpandableRecyclerViewAdapter<SearchQueryAdapter.CityViewHolder,SearchQueryAdapter.LocalityViewHolder> {


    public SearchQueryAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public CityViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public LocalityViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindChildViewHolder(LocalityViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

    }

    @Override
    public void onBindGroupViewHolder(CityViewHolder holder, int flatPosition, ExpandableGroup group) {

    }

    protected class CityViewHolder extends GroupViewHolder {

        public CityViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected class LocalityViewHolder extends ChildViewHolder{

        public LocalityViewHolder(View itemView) {
            super(itemView);
        }
    }
}
