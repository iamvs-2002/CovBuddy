package com.vabrodex.covbuddy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.vabrodex.covbuddy.R;

import java.util.Objects;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class ViewPagerAdapter extends PagerAdapter {

    // Context object
    Context context;

    // Array of anims
    String[] anims;
    String[] texts;

    // Layout Inflater
    LayoutInflater mLayoutInflater;



    // Viewpager Constructor
    public ViewPagerAdapter(Context context, String[] anims, String[] texts) {
        this.context = context;
        this.anims = anims;
        this.texts = texts;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // return the number of images
        return anims.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.awareitem, container, false);

        LottieAnimationView animationView = (LottieAnimationView) itemView.findViewById(R.id.animview);
        TextView animtext = itemView.findViewById(R.id.animtext);
        //dotsLayout = itemView.findViewById(R.id.layoutDotsforaware);


        animationView.setAnimation(anims[position]);
        animtext.setText(texts[position]);

        // Adding the View
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout) object);
    }
}