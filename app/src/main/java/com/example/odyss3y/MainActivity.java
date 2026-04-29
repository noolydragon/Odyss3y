package com.example.odyss3y;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Use these in the ancient style level
    int[] ancientIcons = {
            R.drawable.cave_dinosaur,
            R.drawable.cave_mammoth,
            R.drawable.caveman,
            R.drawable.caveman_painting
    };
    //Use these in the medieval style level
    int[] medIcons = {
            R.drawable.chariot,
            R.drawable.helmet,
            R.drawable.shield,
            R.drawable.vase
    };
    //Use these in the modern style level
    int[] modernIcons = {
            R.drawable.computer,
            R.drawable.headphones,
            R.drawable.phone,
            R.drawable.calculator
    };
    //Use these in the future style level
    int[] futureIcons = {
            R.drawable.rocket,
            R.drawable.sci_donut,
            R.drawable.sci_eye,
            R.drawable.sci_ficone
    };
    int widthOfTile, numOfTile = 8, widthOfScreen;
    ArrayList<ImageView> icon = new ArrayList<>();
    int iconToBeDragged, iconToBeReplaced;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        widthOfScreen = displayMetrics.widthPixels;
        int heightOfScreen = displayMetrics.heightPixels;
        widthOfTile = widthOfScreen / numOfTile;
        createBoard();
        for (ImageView imageView : icon)
        {
            imageView.setOnTouchListener(new OnSwipeListener(this)
            {
                @Override
                void onSwipeLeft() {
                    super.onSwipeLeft();
                    iconToBeDragged = imageView.getId();
                    iconToBeReplaced = iconToBeDragged - 1;
                    iconSwap();
                }

                @Override
                void onSwipeRight() {
                    super.onSwipeRight();
                    iconToBeDragged = imageView.getId();
                    iconToBeReplaced = iconToBeDragged + 1;
                    iconSwap();
                }

                @Override
                void onSwipeUp() {
                    super.onSwipeUp();
                    iconToBeDragged = imageView.getId();
                    iconToBeReplaced = iconToBeDragged - numOfTile;
                    iconSwap();
                }

                @Override
                void onSwipeDown() {
                    super.onSwipeDown();
                    iconToBeDragged = imageView.getId();
                    iconToBeReplaced = iconToBeDragged + numOfTile;
                    iconSwap();
                }
            });
        }
    }

    private void iconSwap()
    {
        int background = (int) icon.get(iconToBeReplaced).getTag();
        int background1 = (int) icon.get(iconToBeDragged).getTag();
        icon.get(iconToBeDragged).setImageResource(background);
        icon.get(iconToBeReplaced).setImageResource(background1);
        icon.get(iconToBeDragged).setTag(background);
        icon.get(iconToBeReplaced).setTag(background1);
    }
    private void createBoard() {
        GridLayout gridLayout = findViewById(R.id.board);
        gridLayout.setRowCount(numOfTile);
        gridLayout.setColumnCount(numOfTile);
        gridLayout.getLayoutParams().width = widthOfScreen;
        gridLayout.getLayoutParams().height = widthOfScreen;
        for (int i = 0; i < numOfTile * numOfTile ; i++)
        {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new
                    android.view.ViewGroup.LayoutParams(widthOfTile, widthOfTile));
            imageView.setMaxHeight(widthOfTile);
            imageView.setMaxWidth(widthOfTile);
            int randomIcon = (int) Math.floor(Math.random() * ancientIcons.length);// Grabs random icons
            imageView.setImageResource(ancientIcons[randomIcon]);
            imageView.setTag(ancientIcons[randomIcon]);
            icon.add(imageView);
            gridLayout.addView(imageView);
        }
    }
}