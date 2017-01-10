package in.evolve.ornateresidency.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import in.evolve.ornateresidency.Activities.GuestHouseBookingActivity;
import in.evolve.ornateresidency.Activities.PgBookingActivity;
import in.evolve.ornateresidency.AppContext;
import in.evolve.ornateresidency.Models.GuestHouse;
import in.evolve.ornateresidency.Models.Pg;
import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.Constants;

/**
 * Created by RAJEEV YADAV on 1/6/2017.
 */
public class GuestHouseAdapter extends RecyclerView.Adapter<GuestHouseAdapter.PgViewHolder> {


    private Context context;
    List<GuestHouse> list;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    public GuestHouseAdapter(Context context, List<GuestHouse> list)
    {
        this.context=context;
        this.list=list;

        imageLoader = AppContext.imageLoader;

        AppContext.getInstance().initImageLoader(context);  //Initialising The ImageLoader

        //just a check we found on stack overflow for adapter problem we were facing...

        this.options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.abc_textfield_activated_mtrl_alpha)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public PgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PgViewHolder(LayoutInflater.from(context).inflate(R.layout.guest_house_custom_row,parent,false));
    }

    @Override
    public void onBindViewHolder(PgViewHolder holder, final int position) {

        holder.ghName.setText(list.get(position).getGhName());
        holder.ghAddress.setText(list.get(position).getGhaddress());
        holder.ghImage.setImageResource(R.drawable.landing_image2);
        holder.ghPrice.setText(list.get(position).getGhRates());

        imageLoader.displayImage(Constants.WEBSITE_URL+list.get(position).getGhImageUrls(), holder.ghImage, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, GuestHouseBookingActivity.class);
                intent.putExtra("guesthouse",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PgViewHolder extends  RecyclerView.ViewHolder {

        TextView ghName;
        TextView ghAddress;
        ImageView ghImage;
        CardView  container;
        TextView ghPrice;

        public PgViewHolder(View itemView) {
            super(itemView);
            ghName= (TextView) itemView.findViewById(R.id.gh_name);
            ghAddress= (TextView) itemView.findViewById(R.id.gh_address);
            ghImage= (ImageView) itemView.findViewById(R.id.gh_image);
            container = (CardView) itemView.findViewById(R.id.ghContainer);
            ghPrice= (TextView) itemView.findViewById(R.id.gh_price);
        }
    }

    public void changeTheList(List<GuestHouse> list){
        this.list = list;
        this.notifyDataSetChanged();
    }
}
