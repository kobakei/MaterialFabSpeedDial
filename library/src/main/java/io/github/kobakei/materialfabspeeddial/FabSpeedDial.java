package io.github.kobakei.materialfabspeeddial;

import android.animation.Animator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Layout class containing {@link FloatingActionButton} with speed dial animation.
 * Created by keisukekobayashi on 2017/06/12.
 */
@CoordinatorLayout.DefaultBehavior(FabSpeedDial.Behavior.class)
public class FabSpeedDial extends FrameLayout {

    private Menu menu;

    private FloatingActionButton fabMain;
    private LinearLayout fabsContainer;
    private LinearLayout menuContainer;
    private View touchGuard;

    private List<OnMenuItemClickListener> menuClickListeners = new ArrayList<>();
    private List<OnStateChangeListener> stateChangeListeners = new ArrayList<>();

    @Nullable
    private ColorStateList miniFabBackgroundColor;
    @Nullable
    private List<ColorStateList> miniFabBackgroundColorList;
    @Nullable
    private ColorStateList miniFabDrawableTint;
    @Nullable
    private List<ColorStateList> miniFabDrawableTintList;
    @ColorInt
    private int miniFabRippleColor;
    @Nullable
    private List<Integer> miniFabRippleColorList;

    @Nullable
    private ColorStateList miniFabTextColor;
    @Nullable
    private List<ColorStateList> miniFabTextColorList;
    @Nullable
    private Drawable miniFabTextBackground;
    @Nullable
    private List<Drawable> miniFabTextBackgroundList;

    private float fabRotationAngle = 45.0f;

    private int extraMarginPixel = 0;

    private boolean isOpened = false;
    private boolean useTouchGuard = true;
    private boolean useRevealEffect = true;
    private boolean useRippleOnPreLollipop = true;

    private boolean isLandscapeLayout = false;

    private static final long MAIN_FAB_ROTATE_DURATION = 200L;
    private static final long MINI_FAB_SHOW_DURATION = 100L;
    private static final long MINI_FAB_SHOW_DELAY = 50L;
    private static final float MINI_FAB_SHOW_TRANSLATION = 24.0f;
    private static final long MINI_FAB_DISMISS_DURATION = 100L;

    public FabSpeedDial(@NonNull Context context) {
        super(context);
        initLayout(context, null, 0);
    }

    public FabSpeedDial(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout(context, attrs, 0);
    }

    public FabSpeedDial(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context, attrs, defStyleAttr);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        SavedState savedState = new SavedState(parcelable);
        savedState.isOpened = isOpened;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            if (ss.isOpened) {
                openMenu();
            } else {
                closeMenu();
            }
            super.onRestoreInstanceState(ss.getSuperState());
        } else {
            super.onRestoreInstanceState(BaseSavedState.EMPTY_STATE);
        }
    }

    private void initLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        isLandscapeLayout = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fab_speed_dial, this, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.BOTTOM | Gravity.END;
        addView(view, params);

        fabMain = findViewById(R.id.fab_main);
        fabMain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpened) {
                    closeMenu();
                } else {
                    openMenu();
                }
            }
        });

        fabsContainer = findViewById(R.id.fabs_container);
        menuContainer = findViewById(R.id.menu_container);

        touchGuard = findViewById(R.id.touch_guard);
        touchGuard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FabSpeedDial, defStyleAttr, 0);

        // Key listener to handle BACK key
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (isOpened) {
                        closeMenu();
                        return true;
                    }
                }
                return false;
            }
        });

        // Read attrs

        // Extra margin
        extraMarginPixel = ta.getDimensionPixelSize(R.styleable.FabSpeedDial_fab_extraMargin, 0);

        // Ripple on pre Lollipop
        useRippleOnPreLollipop = ta.getBoolean(R.styleable.FabSpeedDial_fab_useRippleOnPreLollipop, true);

        // Main FAB
        int drawableId = ta.getResourceId(R.styleable.FabSpeedDial_fab_fabDrawable, 0);
        if (drawableId != 0) {
            Drawable drawable = AppCompatResources.getDrawable(context, drawableId);
            fabMain.setImageDrawable(drawable);
        }

        ColorStateList fabBackgroundColor = ta.getColorStateList(R.styleable.FabSpeedDial_fab_fabBackgroundColor);
        if (fabBackgroundColor != null) {
            fabMain.setBackgroundTintList(fabBackgroundColor);
        }

        ColorStateList fabDrawableTint = ta.getColorStateList(R.styleable.FabSpeedDial_fab_fabDrawableTint);
        if (fabDrawableTint != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fabMain.setImageTintList(fabDrawableTint);
            } else {
                fabMain.setColorFilter(fabDrawableTint.getDefaultColor());
            }
        }

        int rippleColor = ta.getColor(R.styleable.FabSpeedDial_fab_fabRippleColor, Color.WHITE);
        if (shouldUseRipple()) {
            fabMain.setRippleColor(rippleColor);
        }

        MarginLayoutParams mainParams = (MarginLayoutParams) fabMain.getLayoutParams();
        mainParams.setMargins(mainParams.leftMargin, mainParams.topMargin + extraMarginPixel, mainParams.rightMargin, mainParams.bottomMargin);
        fabMain.setLayoutParams(mainParams);

        fabRotationAngle = ta.getFloat(R.styleable.FabSpeedDial_fab_fabRotationAngle, 45.0f);


        // Mini FAB
        miniFabBackgroundColor = ta.getColorStateList(R.styleable.FabSpeedDial_fab_miniFabBackgroundColor);
        int miniFabBackgroundColorListId = ta.getResourceId(R.styleable.FabSpeedDial_fab_miniFabBackgroundColorList, 0);
        if (miniFabBackgroundColorListId != 0) {
            TypedArray colorArray = getResources().obtainTypedArray(miniFabBackgroundColorListId);
            miniFabBackgroundColorList = new ArrayList<>();
            for (int i = 0; i < colorArray.length(); i++) {
                miniFabBackgroundColorList.add(colorArray.getColorStateList(i));
            }
            colorArray.recycle();
        }

        miniFabDrawableTint = ta.getColorStateList(R.styleable.FabSpeedDial_fab_miniFabDrawableTint);
        int miniFabDrawableTintListId = ta.getResourceId(R.styleable.FabSpeedDial_fab_miniFabDrawableTintList, 0);
        if (miniFabDrawableTintListId != 0) {
            TypedArray colorArray = getResources().obtainTypedArray(miniFabDrawableTintListId);
            miniFabDrawableTintList = new ArrayList<>();
            for (int i = 0; i < colorArray.length(); i++) {
                miniFabDrawableTintList.add(colorArray.getColorStateList(i));
            }
            colorArray.recycle();
        }

        miniFabRippleColor = ta.getColor(R.styleable.FabSpeedDial_fab_miniFabRippleColor, Color.TRANSPARENT);
        int miniFabRippleColorListId = ta.getResourceId(R.styleable.FabSpeedDial_fab_miniFabRippleColorList, 0);
        if (miniFabRippleColorListId != 0) {
            TypedArray colorArray = getResources().obtainTypedArray(miniFabRippleColorListId);
            miniFabRippleColorList = new ArrayList<>();
            for (int i = 0; i < colorArray.length(); i++) {
                miniFabRippleColorList.add(colorArray.getColor(i, Color.TRANSPARENT));
            }
            colorArray.recycle();
        }

        // Mini FAB text
        miniFabTextColor = ta.getColorStateList(R.styleable.FabSpeedDial_fab_miniFabTextColor);
        int miniFabTextColorListId = ta.getResourceId(R.styleable.FabSpeedDial_fab_miniFabTextColorList, 0);
        if (miniFabTextColorListId != 0) {
            TypedArray colorArray = getResources().obtainTypedArray(miniFabTextColorListId);
            miniFabTextColorList = new ArrayList<>();
            for (int i = 0; i < colorArray.length(); i++) {
                miniFabTextColorList.add(colorArray.getColorStateList(i));
            }
            colorArray.recycle();
        }

        miniFabTextBackground = ta.getDrawable(R.styleable.FabSpeedDial_fab_miniFabTextBackground);
        int miniFabTextBackgroundListId = ta.getResourceId(R.styleable.FabSpeedDial_fab_miniFabTextBackgroundList, 0);
        if (miniFabTextBackgroundListId != 0) {
            TypedArray drawableArray = getResources().obtainTypedArray(miniFabTextBackgroundListId);
            miniFabTextBackgroundList = new ArrayList<>();
            for (int i = 0; i < drawableArray.length(); i++) {
                miniFabTextBackgroundList.add(drawableArray.getDrawable(i));
            }
            drawableArray.recycle();
        }

        // Touch guard
        useTouchGuard = ta.getBoolean(R.styleable.FabSpeedDial_fab_useTouchGuard, true);
        useRevealEffect = ta.getBoolean(R.styleable.FabSpeedDial_fab_useRevealEffect, true);

        int touchGuardColor = ta.getColor(R.styleable.FabSpeedDial_fab_touchGuardColor, Color.argb(128, 0, 0, 0));
        touchGuard.setBackgroundColor(touchGuardColor);

        // Menu
        menu = new FabSpeedDialMenu(context);
        int menuId = ta.getResourceId(R.styleable.FabSpeedDial_fab_menu, 0);
        if (menuId > 0) {
            new MenuInflater(context).inflate(menuId, menu);
        }

        refreshMenus();

        ta.recycle();
    }

    private void refreshMenus() {
        menuContainer.removeAllViews();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.isVisible()) {
                View itemView = createItemView(i, menuItem);
                menuContainer.addView(itemView);
            }
        }
    }

    @NonNull
    private View createItemView(int index, final MenuItem menuItem) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View itemView = inflater.inflate(R.layout.fab_speed_dial_item, menuContainer, false);

        // Mini FAB
        final FloatingActionButton miniFab = itemView.findViewById(R.id.fab_mini);
        if (menuItem.getIcon() != null) {
            miniFab.setVisibility(VISIBLE);
            miniFab.setImageDrawable(menuItem.getIcon());
            miniFab.setEnabled(menuItem.isEnabled());
        } else {
            miniFab.setVisibility(GONE);
        }

        if (miniFabBackgroundColor != null) {
            miniFab.setBackgroundTintList(miniFabBackgroundColor);
        }
        if (miniFabBackgroundColorList != null) {
            miniFab.setBackgroundTintList(miniFabBackgroundColorList.get(index));
        }

        if (miniFabDrawableTint != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                miniFab.setImageTintList(miniFabDrawableTint);
            } else {
                miniFab.setColorFilter(miniFabDrawableTint.getDefaultColor());
            }
        }
        if (miniFabDrawableTintList != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                miniFab.setImageTintList(miniFabDrawableTintList.get(index));
            } else {
                miniFab.setColorFilter(miniFabDrawableTintList.get(index).getDefaultColor());
            }
        }

        if (shouldUseRipple()) {
            if (miniFabRippleColor != 0) {
                miniFab.setRippleColor(miniFabRippleColor);
            }
            if (miniFabRippleColorList != null) {
                miniFab.setRippleColor(miniFabRippleColorList.get(index));
            }
        }

        MarginLayoutParams params = (MarginLayoutParams) miniFab.getLayoutParams();
        params.setMargins(params.leftMargin, params.topMargin + extraMarginPixel, params.rightMargin, params.bottomMargin + extraMarginPixel);
        miniFab.setLayoutParams(params);

        // TextView
        final TextView label = itemView.findViewById(R.id.text);
        label.setText(menuItem.getTitle());
        label.setEnabled(menuItem.isEnabled());

        if (miniFabTextColor != null) {
            label.setTextColor(miniFabTextColor);
        }
        if (miniFabTextColorList != null) {
            label.setTextColor(miniFabTextColorList.get(index));
        }

        if (miniFabTextBackground != null) {
            Drawable.ConstantState cs = miniFabTextBackground.mutate().getConstantState();
            if (cs != null) {
                label.setBackground(cs.newDrawable());
            }
        }
        if (miniFabTextBackgroundList != null) {
            label.setBackground(miniFabTextBackgroundList.get(index));
        }

        label.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (OnMenuItemClickListener listener : menuClickListeners) {
                    listener.onMenuItemClick(miniFab, label, menuItem.getItemId());
                }
                closeMenu();
            }
        });

        if (miniFab.getVisibility() == GONE) {
            label.setVisibility(VISIBLE);
            float scale = getResources().getDisplayMetrics().density;
            int padding = (int) (28 * scale + 0.5f);
            MarginLayoutParams labelParams = (MarginLayoutParams) miniFab.getLayoutParams();
            params.setMargins(labelParams.leftMargin, labelParams.topMargin + padding + extraMarginPixel, labelParams.rightMargin, labelParams.bottomMargin + padding + extraMarginPixel);
            label.setLayoutParams(labelParams);
        }

        // Listener
        miniFab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (OnMenuItemClickListener listener : menuClickListeners) {
                    listener.onMenuItemClick(miniFab, label, menuItem.getItemId());
                }
                closeMenu();
            }
        });

        return itemView;
    }

    private boolean shouldUseRipple() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP || useRippleOnPreLollipop;
    }

    /**
     * Inflate menu items from menu resource.
     * @param menuId Menu resource ID
     */
    @SuppressWarnings("unused")
    public void inflateMenu(@MenuRes int menuId) {
        new MenuInflater(getContext()).inflate(menuId, menu);
        refreshMenus();
    }

    /**
     * Set FAB speed dial menu.
     * This method recreates mini FABs and labels
     * @param menu Menu
     */
    @SuppressWarnings("unused")
    public void setMenu(FabSpeedDialMenu menu) {
        this.menu = menu;
        refreshMenus();
    }

    /**
     * Open menu
     */
    @SuppressWarnings("unused")
    public void openMenu() {
        if (isOpened) {
            return;
        }
        fabMain.setSelected(true);
        fabMain.animate().rotation(fabRotationAngle)
                .setDuration(MAIN_FAB_ROTATE_DURATION)
                .start();

        for (int i = 0; i < menuContainer.getChildCount(); i++) {
            View itemView = menuContainer.getChildAt(i);

            itemView.setAlpha(0.0f);

            ViewPropertyAnimator animator = itemView.animate();
            if (isLandscapeLayout) {
                itemView.setTranslationX(MINI_FAB_SHOW_TRANSLATION);
                animator.translationX(0.0f);
            } else {
                itemView.setTranslationY(MINI_FAB_SHOW_TRANSLATION);
                animator.translationY(0.0f);
            }
            animator.alpha(1.0f)
                    .setDuration(MINI_FAB_SHOW_DURATION)
                    .setStartDelay((menu.size() - 1 - i) * MINI_FAB_SHOW_DELAY)
                    .start();
        }

        menuContainer.setVisibility(View.VISIBLE);
        if (useTouchGuard) {
            touchGuard.setVisibility(View.VISIBLE);
            if (useRevealEffect) {
                touchGuard.setAlpha(0.0f);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        touchGuard.setAlpha(1.0f);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            int cx = fabsContainer.getLeft() + (fabMain.getLeft() + fabMain.getRight()) / 2;
                            int cy = fabsContainer.getTop() + (fabMain.getTop() + fabMain.getBottom()) / 2;
                            float radius = Math.max(touchGuard.getWidth(), touchGuard.getHeight()) * 2.0f;
                            ViewAnimationUtils.createCircularReveal(touchGuard, cx, cy, 0, radius).start();
                        }
                    }
                });
            }
        }
        isOpened = true;
        for (OnStateChangeListener listener : stateChangeListeners) {
            listener.onStateChange(isOpened);
        }
    }

    /**
     * Close menu
     */
    @SuppressWarnings("unused")
    public void closeMenu() {
        if (!isOpened) {
            return;
        }
        fabMain.setSelected(false);
        fabMain.animate().rotation(0.0f)
                .setDuration(MAIN_FAB_ROTATE_DURATION)
                .start();

        for (int i = 0; i < menuContainer.getChildCount(); i++) {
            final View itemView = menuContainer.getChildAt(i);
            itemView.animate()
                    .alpha(0.0f)
                    .setDuration(MINI_FAB_DISMISS_DURATION)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            menuContainer.setVisibility(View.GONE);
                            itemView.animate().setListener(null).start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .start();
        }

        if (useTouchGuard) {
            touchGuard.animate()
                    .alpha(0.0f)
                    .setDuration(MINI_FAB_DISMISS_DURATION)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            touchGuard.setAlpha(1.0f);
                            touchGuard.setVisibility(View.GONE);
                            touchGuard.animate().setListener(null).start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .start();
        }

        isOpened = false;
        for (OnStateChangeListener listener : stateChangeListeners) {
            listener.onStateChange(isOpened);
        }
    }

    /**
     * Check whether menu is opened or closes
     * @return true if opened, false otherwise
     */
    @SuppressWarnings("unused")
    public boolean isOpeningMenu() {
        return isOpened;
    }

    /**
     * Show main {@link FloatingActionButton}
     */
    @SuppressWarnings("unused")
    public void show() {
        fabMain.show();
    }

    /**
     * Hide main {@link FloatingActionButton}
     */
    @SuppressWarnings("unused")
    public void hide() {
        if (isOpened) {
            closeMenu();
            fabMain.setRotation(0.0f);
        }
        fabMain.hide();
    }

    /**
     * Check whether main {@link FloatingActionButton} is shown or not
     * @return true if main FAB is shown, false otherwise
     */
    @SuppressWarnings("unused")
    public boolean isShown() {
        return fabMain.isShown();
    }

    /**
     * Add event listener
     * @param listener event listener
     */
    @SuppressWarnings("unused")
    public void addOnMenuItemClickListener(OnMenuItemClickListener listener) {
        menuClickListeners.add(listener);
    }

    /**
     * Remove event listener
     * @param listener event listener
     */
    @SuppressWarnings("unused")
    public void removeOnMenuItemClickListener(OnMenuItemClickListener listener) {
        menuClickListeners.remove(listener);
    }

    /**
     * Remove all event listeners
     */
    @SuppressWarnings("unused")
    public void removeAllOnMenuItemClickListeners() {
        menuClickListeners.clear();
    }

    /**
     * Add event listener
     * @param listener event listener
     */
    @SuppressWarnings("unused")
    public void addOnStateChangeListener(OnStateChangeListener listener) {
        stateChangeListeners.add(listener);
    }

    /**
     * Remove event listener
     * @param listener event listener
     */
    @SuppressWarnings("unused")
    public void removeOnStateChangeListener(OnStateChangeListener listener) {
        stateChangeListeners.remove(listener);
    }

    /**
     * Remove all event listeners
     */
    @SuppressWarnings("unused")
    public void removeAllOnStateChangeListeners() {
        stateChangeListeners.clear();
    }

    /**
     * Get main {@link FloatingActionButton}
     * @return Main FAB
     */
    @SuppressWarnings("unused")
    public FloatingActionButton getMainFab() {
        return fabMain;
    }

    /**
     * Get mini {@link FloatingActionButton} at index
     * @param index Index of mini FAB
     * @return Mini FAB
     */
    @SuppressWarnings("unused")
    public FloatingActionButton getMiniFab(int index) {
        View view = menuContainer.getChildAt(index);
        return (FloatingActionButton) view.findViewById(R.id.fab_mini);
    }

    /**
     * Get mini {@link TextView} at index
     * @param index Index of mini FAB
     * @return TextView
     */
    @SuppressWarnings("unused")
    public TextView getMiniFabTextView(int index) {
        View view = menuContainer.getChildAt(index);
        return (TextView) view.findViewById(R.id.text);
    }

    /**
     * Event listener to handle menu item click event
     */
    public interface OnMenuItemClickListener {
        /**
         * Invoked when mini FAB or label is clicked
         * @param miniFab Mini FAB of the item
         * @param label Label of the item. May be null on landscape mode.
         * @param itemId Item ID
         */
        void onMenuItemClick(FloatingActionButton miniFab, @Nullable TextView label, int itemId);
    }

    /**
     * Event listener to handle FAB open/close state
     */
    public interface OnStateChangeListener {
        /**
         * Invoked when FAB is opened or closed
         * @param open True if opened.
         */
        void onStateChange(boolean open);
    }

    /**
     * Default behavior of {@link FabSpeedDial}.
     * It works as same as {@link android.support.design.widget.FloatingActionButton.Behavior}.
     */
    public static class Behavior extends CoordinatorLayout.Behavior<FabSpeedDial> {

        public Behavior() {
            super();
        }

        public Behavior(Context context, AttributeSet attrs) {
            super();
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, FabSpeedDial child, View dependency) {
            return dependency instanceof Snackbar.SnackbarLayout;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, FabSpeedDial child, View dependency) {
            float diff = dependency.getTranslationY() - dependency.getHeight();
            child.setTranslationY(diff);
            return false;
        }
    }

    private static class SavedState extends BaseSavedState {

        boolean isOpened = false;

        SavedState(Parcelable source) {
            super(source);
        }
    }
}
