package com.example.sportstrackerapp.ui.scores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;

public class SvgDecoder implements ResourceDecoder<InputStream, SVG> {

    @Override
    public boolean handles(@NonNull InputStream source, @NonNull com.bumptech.glide.load.Options options) {
        return true;
    }

    @Override
    public Resource<SVG> decode(@NonNull InputStream source, int width, int height, @NonNull com.bumptech.glide.load.Options options) {
        try {
            SVG svg = SVG.getFromInputStream(source);
            return new SimpleResource<>(svg);
        } catch (Exception e) {
            Log.e("SvgDecoder", "Cannot load SVG", e);
            return null;
        }
    }
}
