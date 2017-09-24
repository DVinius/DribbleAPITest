package com.vsgdev.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vsgdev.models.DribbleAPI;
import com.vsgdev.models.Shoot;
import com.vsgdev.utils.AppConstants;
import com.wang.avi.AVLoadingIndicatorView;

public class ShootDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_layout);

        final int shootIndex = getIntent().getIntExtra(AppConstants.SHOOT_POSITION, -1);
        final Shoot detailedShoot = DribbleAPI.getInstance(this).getShoots().get(shootIndex);

        setTitle(detailedShoot.getTitle());

        final String description = detailedShoot.getDescription();
        if (!"null".equalsIgnoreCase(description)){
            final TextView tvShootDescription = findViewById(R.id.tvShootDetDescription);
            tvShootDescription.setText(Html.fromHtml(description));
        }

        final Integer viewsCount = detailedShoot.getViewCount();
        final TextView tvShootViewCount = findViewById(R.id.tvShootDetViewsCount);
        tvShootViewCount.setText(String.valueOf(viewsCount));

        final Integer commentsCount = detailedShoot.getCommentsCount();
        final TextView tvShootCommentsCount = findViewById(R.id.tvShootDetCommentsCount);
        tvShootCommentsCount.setText(String.valueOf(commentsCount));

        final String createdAt = detailedShoot.getCreatedAt();
        final TextView tvShootViewCreatedAt = findViewById(R.id.tvShootDetCreatedAt);
        tvShootViewCreatedAt.setText(String.valueOf(createdAt));

        final AVLoadingIndicatorView indicatorView = findViewById(R.id.aviNiceLoadingAnim);

        final ImageView ivShootImg = findViewById(R.id.detailShootImg);
        Picasso.with(this)
                .load(detailedShoot.getNormalImageUrl())
                .placeholder(R.mipmap.static_progress_img)
                .into(ivShootImg, new Callback() {
                    @Override
                    public void onSuccess() {
                        indicatorView.hide();
                        ivShootImg.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {

                    }
                });

    }
}
