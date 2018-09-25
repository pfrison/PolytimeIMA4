package me.pfrison.polytimeima4.graphics.style;

import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.pfrison.polytimeima4.R;

public class StyleVersionArrayAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater;
    private List<StyleVersion> styleVersions;
    private int resource;
    private Context context;

    public StyleVersionArrayAdapter(Context context, @LayoutRes int resource, List styleVersions) {
        super(context, resource, styleVersions);

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.resource = resource;
        this.styleVersions = styleVersions;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return createView(position, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return createView(position, parent);
    }

    private View createView(int position, ViewGroup parent){
        View view = inflater.inflate(resource, parent, false);

        TextView color = view.findViewById(R.id.spinner_adapt_color);
        TextView text = view.findViewById(R.id.spinner_adapt_text);

        StyleVersion styleVersion = styleVersions.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color.setTextColor(context.getResources().getColor(styleVersion.versionTextColorResId, null));
            color.setBackgroundColor(context.getResources().getColor(styleVersion.versionBackgroundResId, null));
        }else {
            color.setTextColor(context.getResources().getColor(styleVersion.versionTextColorResId));
            color.setBackgroundColor(context.getResources().getColor(styleVersion.versionBackgroundResId));
        }
        text.setText(styleVersion.versionNameResId);

        return view;
    }
}