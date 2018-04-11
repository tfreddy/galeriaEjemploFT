package cr.ac.unadeca.galeriaejemploft.subclases;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.flexbox.FlexboxLayoutManager;

import cr.ac.unadeca.galeriaejemploft.R;


/**
 * Created by Freddy
 */

public class ImageViewHolder extends RecyclerView.ViewHolder {//funcion que se agragara cuando se este ejecutando
    public ImageView image;
    public ImageViewHolder(View itemView) {
        super(itemView);
        image =  itemView.findViewById(R.id.image);
    }

    public void bindTo() {
        ViewGroup.LayoutParams lp = image.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams)lp;
            flexboxLp.setFlexGrow(1.0f);
        }
    }
}