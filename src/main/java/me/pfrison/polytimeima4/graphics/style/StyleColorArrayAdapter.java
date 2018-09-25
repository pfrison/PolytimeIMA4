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

public class StyleColorArrayAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater;
    private List<StyleColor> styleColors;
    private int resource;
    private Context context;

    public StyleColorArrayAdapter(Context context, @LayoutRes int resource, List styleColors) {
        super(context, resource, styleColors);

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.resource = resource;
        this.styleColors = styleColors;
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

        View colorPrimary = view.findViewById(R.id.spinner_adapt_colorPrimary);
        View colorPrimaryDark = view.findViewById(R.id.spinner_adapt_colorPrimaryDark);
        View colorAccent = view.findViewById(R.id.spinner_adapt_colorAccent);
        TextView text = view.findViewById(R.id.spinner_adapt_text);

        StyleColor styleColor = styleColors.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            colorPrimary.setBackgroundColor(context.getResources().getColor(styleColor.colorPrimaryResId, null));
            colorPrimaryDark.setBackgroundColor(context.getResources().getColor(styleColor.colorPrimaryDarkResId, null));
            colorAccent.setBackgroundColor(context.getResources().getColor(styleColor.colorAccentResId, null));
        }else {
            colorPrimary.setBackgroundColor(context.getResources().getColor(styleColor.colorPrimaryResId));
            colorPrimaryDark.setBackgroundColor(context.getResources().getColor(styleColor.colorPrimaryDarkResId));
            colorAccent.setBackgroundColor(context.getResources().getColor(styleColor.colorAccentResId));
        }
        text.setText(styleColor.colorNameResId);

        return view;
    }
}