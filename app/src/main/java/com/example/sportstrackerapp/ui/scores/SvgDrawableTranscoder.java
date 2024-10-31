package com.example.sportstrackerapp.ui.scores;

import android.graphics.drawable.PictureDrawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.caverock.androidsvg.SVG;

public class SvgDrawableTranscoder implements ResourceTranscoder<SVG, PictureDrawable> {

    @Override
    public Resource<PictureDrawable> transcode(Resource<SVG> toTranscode, @NonNull com.bumptech.glide.load.Options options) {
        SVG svg = toTranscode.get();
        PictureDrawable drawable = new PictureDrawable(svg.renderToPicture());
        return new SimpleResource<>(drawable);
    }
}
