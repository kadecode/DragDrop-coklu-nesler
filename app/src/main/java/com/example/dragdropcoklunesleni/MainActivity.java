package com.example.dragdropcoklunesleni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {
    private Button button;
    private ImageView imageView;
    private TextView textView;
    private LinearLayout üst_tasarim,sol_tasarim,sag_tasarim;

    private static final String YAZI_ETIKET = "YAZI";
    private static final String BUTTON_ETIKET = "BUTON";
    private static final String RESIM_ETIKET = "RESIM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setTag(BUTTON_ETIKET);
        imageView = findViewById(R.id.imageView);
        imageView.setTag(RESIM_ETIKET);
        textView = findViewById(R.id.textView);
        textView.setTag(YAZI_ETIKET);

        üst_tasarim = findViewById(R.id.üst_tasarim);
        sag_tasarim = findViewById(R.id.sag_tasarim);
        sol_tasarim = findViewById(R.id.sol_tasarim);

        textView.setOnLongClickListener(this);
        imageView.setOnLongClickListener(this);
        button.setOnLongClickListener(this);

        üst_tasarim.setOnDragListener(this);
        sag_tasarim.setOnDragListener(this);
        sol_tasarim.setOnDragListener(this);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()){
            case DragEvent.ACTION_DRAG_STARTED:
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                v.getBackground().clearColorFilter();
                v.invalidate();
                return true;
            case DragEvent.ACTION_DRAG_ENTERED:
                v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_OUT);
                v.invalidate();
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                v.getBackground().clearColorFilter();
                v.invalidate();
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                return true;
            case DragEvent.ACTION_DROP:
                v.getBackground().clearColorFilter();
                v.invalidate();

                View gorselNesne = (View) event.getLocalState();

                ViewGroup eskiTasarimAlani = (ViewGroup) gorselNesne.getParent();

                eskiTasarimAlani.removeView(gorselNesne);

                LinearLayout hedefTasarimAlani = (LinearLayout) v;
                hedefTasarimAlani.addView(gorselNesne);
                gorselNesne.setVisibility(v.VISIBLE);
                return true;
            default:break;
        }
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
        String [] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(v.getTag().toString(),mimeTypes,item);
        View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
        v.startDrag(data,dragShadowBuilder,v,0);
        v.setVisibility(v.INVISIBLE);

        return true;
    }
}