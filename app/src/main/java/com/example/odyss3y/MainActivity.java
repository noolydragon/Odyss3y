package com.example.odyss3y;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    int notIcon = R.drawable.transparent;
    Handler mHandler;
    int interval = 100;
    TextView scoreRes;
    int score = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreRes = findViewById(R.id.score);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        widthOfScreen = displayMetrics.widthPixels;
        int heightOfScreen = displayMetrics.heightPixels;
        widthOfTile = widthOfScreen / numOfTile;
        createBoard();
        for (final ImageView imageView : icon)
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
        mHandler = new Handler();
        startRepeat();
    }
    private void rowMatch() {
        for (int i = 0; i < 62; i++) {
            int chosenIcon = (int) icon.get(i).getTag();
            boolean isBlank = (int) icon.get(i).getTag() == notIcon;
            Integer[] notValid = {6,7,14,15,22,23,30,31,38,39,46,47,54,55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i)) {
                int x = i;
                if ((int) icon.get(x++).getTag() == chosenIcon && !isBlank &&
                        (int) icon.get(x++).getTag() == chosenIcon &&
                        (int) icon.get(x).getTag() == chosenIcon) {
                    score = score + 3;
                    scoreRes.setText(String.valueOf(score));
                    icon.get(x).setImageResource(notIcon);
                    icon.get(x).setTag(notIcon);
                    x--;
                    icon.get(x).setImageResource(notIcon);
                    icon.get(x).setTag(notIcon);
                    x--;
                    icon.get(x).setImageResource(notIcon);
                    icon.get(x).setTag(notIcon);
                }
            }
        }
        moveDownIcon();
    }
    private void columnMatch()
    {
        for (int i = 0; i < 47; i++)
        {
            int chosenIcon = (int) icon.get(i).getTag();
            boolean isBlank = (int) icon.get(i).getTag() == notIcon;
            int x = i;
            if ((int) icon.get(x).getTag() == chosenIcon && !isBlank &&
                    (int) icon.get(x+numOfTile).getTag() == chosenIcon &&
                    (int) icon.get(x+2*numOfTile).getTag() == chosenIcon) {
                score = score + 3;
                scoreRes.setText(String.valueOf(score));
                icon.get(x).setImageResource(notIcon);
                icon.get(x).setTag(notIcon);
                x = x + numOfTile;
                icon.get(x).setImageResource(notIcon);
                icon.get(x).setTag(notIcon);
                x = x + numOfTile;
                icon.get(x).setImageResource(notIcon);
                icon.get(x).setTag(notIcon);
            }
        }
        moveDownIcon();
    }

    private void moveDownIcon()
    {
        Integer[] firstRow = {0, 1, 2, 3, 4, 5, 6, 7};
        List<Integer> list = Arrays.asList(firstRow);
        for (int i = 55; i >= 0; i--)
        {
            if ((int) icon.get(i + numOfTile).getTag() == notIcon)
            {
                icon.get(i + numOfTile).setImageResource((int) icon.get(i).getTag());
                icon.get(i + numOfTile).setTag(icon.get(i).getTag());
                icon.get(i).setImageResource(notIcon);
                icon.get(i).setTag(notIcon);

                if (list.contains(i) && (int) icon.get(i).getTag() == notIcon)
                {
                    int randomColor = (int) Math.floor(Math.random() * ancientIcons.length);
                    icon.get(i).setImageResource(ancientIcons[randomColor]);
                    icon.get(i).setTag(ancientIcons[randomColor]);
                }
            }
        }
        for (int i = 0; i < 0 ; i++)
        {
            if ((int) icon.get(i).getTag() == notIcon)
            {
                int randomColor = (int) Math.floor(Math.random() * ancientIcons.length);
                icon.get(i).setImageResource(ancientIcons[randomColor]);
                icon.get(i).setTag(ancientIcons[randomColor]);
            }
        }
    }
    Runnable repeatChecker = new Runnable() {
        @Override
        public void run() {
            try {
                rowMatch();
                columnMatch();
                moveDownIcon();
            }
            finally {
                mHandler.postDelayed(repeatChecker, interval);
            }
        }
    };
    void startRepeat()
    {
        repeatChecker.run();
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