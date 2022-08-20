package com.example.essence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity {
    String TAG = "Debug";
    BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout bottomSheetLayout;
    private ViewPager2 pa;
    ViewPageAdapter vpAdaptor;
    
    LinearLayout hardwareLayout, lockscreenLayout, qsLayout, statusbarLayout, systemLayout, themesLayout;
    TextView harwdareTextView, lockscreenTextView, qsTextView, statusbarTextView, systemTextView, themesTextView;
    private LinearLayout[] layoutArray;
    private TextView[] textViewsArr;
    //used to check if page changed before closing the bottom page
    private boolean pageChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize all views
        initViews();

        //swiping to switch pages is disabled
        pa.setUserInputEnabled(false);
        //how many pages to buffer so on loading the clicked pages nearby pages are also buffered
        pa.setOffscreenPageLimit(1);
        //Fragment manager & bottom sheet setup
        FragmentManager fm = getSupportFragmentManager();
        vpAdaptor = new ViewPageAdapter(fm, getLifecycle());
        pa.setAdapter(vpAdaptor);
        //pass bottom sheet view to manipulate bottom sheet programmatically
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        //set how much the bottomsheet should be visible when collapsed
        bottomSheetBehavior.setPeekHeight(178);

        //callback method for bottom sheet state change
        setBottomSheetCallBack();
        //page closes/opens on touching menu
        setMenuButtonListener();
        //page changes onclick listeners for each menu item
        setBottomPageListeners();
        //indicate that page 0 or first page is selected when bottom page is opened
        setUi(0);
        //set first fragment as default fragment to load when app loaded
        pa.setCurrentItem(0, false);
    }

    private void initViews()
    {
        hardwareLayout = findViewById(R.id.layoutHardware);
        lockscreenLayout = findViewById(R.id.layoutLockScreen);
        qsLayout = findViewById(R.id.layoutQs);
        statusbarLayout = findViewById(R.id.layoutStatusBar);
        systemLayout = findViewById(R.id.layoutSystem);
        themesLayout = findViewById(R.id.layoutTheme);
        layoutArray = new LinearLayout[]{ hardwareLayout, lockscreenLayout,
            qsLayout, statusbarLayout, systemLayout, themesLayout};

        harwdareTextView = findViewById(R.id.textHardware);
        lockscreenTextView = findViewById(R.id.textViewLockscreen);
        qsTextView = findViewById(R.id.textViewQs);
        statusbarTextView = findViewById(R.id.textViewStatusBar);
        systemTextView = findViewById(R.id.textViewSystem);
        themesTextView = findViewById(R.id.textViewTheme);
        textViewsArr = new TextView[]{harwdareTextView, lockscreenTextView, qsTextView,
                statusbarTextView,systemTextView,themesTextView};

        bottomSheetLayout = findViewById(R.id.bottom_sheet);
        pa = findViewById(R.id.viewPager);
    }

    //This method collapses opened bottomsheet when touched outside the bottomsheet area
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED) {
                Rect outRect = new Rect();
                bottomSheetLayout.getGlobalVisibleRect(outRect);
                if(!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void setBottomSheetCallBack(){
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    if(pageChanged) {
                            pa.setCurrentItem(vpAdaptor.curTab, false);
                            pageChanged = false;
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    private void setUi(int index){
        final String PURPLE = "#FFA175FF";
        final String WHITE = "#FFFFFFFF";
        final String GREY = "#494949";
        final String PURPLE_LIGHT = "#48D28BFF";

        for(int i = 0;i < vpAdaptor.totalTabs; i++){
            Log.d(TAG, "idx:" + index);
            if(i == index){
                textViewsArr[i].setTextColor(Color.parseColor(PURPLE));
                layoutArray[index].getBackground().setTint(Color.parseColor(PURPLE_LIGHT));
                continue;
            }
            textViewsArr[i].setTextColor(Color.parseColor(WHITE));
            layoutArray[i].getBackground().setTint(Color.parseColor(GREY));
        }
    }

    private void setBottomPageListeners() {
        for(int i = 0; i < vpAdaptor.totalTabs; i++){
            int finalI = i;
            Log.d(TAG, "Idx set:" + i);
            layoutArray[i].setOnClickListener(v -> {
                vpAdaptor.curTab = finalI;
                //sets fancy colours indicating which item is currently selected in bottompage
                setUi(finalI);
                pageChanged = true;
                Log.d(TAG, "page change" + finalI);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            });
        }
    }

    public void setMenuButtonListener(){
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> {
            if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }else{
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }
}
