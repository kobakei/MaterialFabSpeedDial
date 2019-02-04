package io.github.kobakei.materialfabspeeddial

import android.animation.Animator
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Build
import android.os.Handler
import android.os.Parcelable
import android.util.AttributeSet
import android.view.*
import android.view.View.OnKeyListener
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.MenuRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

/**
 * Event listener to handle menu item click event
 */
typealias OnMenuItemClick = (miniFab: FloatingActionButton, label: TextView?, itemId: Int) -> Unit

/**
 * Event listener to handle FAB open/close state
 */
typealias OnStateChange = (open: Boolean) -> Unit

/**
 * Layout class containing [FloatingActionButton] with speed dial animation.
 * Created by keisukekobayashi on 2017/06/12.
 */
class FabSpeedDial : FrameLayout, CoordinatorLayout.AttachedBehavior {

    private var menu: Menu? = null

    /**
     * Get main [FloatingActionButton]
     * @return Main FAB
     */
    lateinit var mainFab: FloatingActionButton
        private set
    private lateinit var fabsContainer: LinearLayout
    private lateinit var menuContainer: LinearLayout
    private lateinit var touchGuard: View

    private val menuClickListeners = mutableListOf<OnMenuItemClick>()
    private val stateChangeListeners = mutableListOf<OnStateChange>()

    private var miniFabBackgroundColor: ColorStateList? = null
    private var miniFabBackgroundColorList: MutableList<ColorStateList>? = null
    private var miniFabDrawableTint: ColorStateList? = null
    private var miniFabDrawableTintList: MutableList<ColorStateList>? = null
    @ColorInt
    private var miniFabRippleColor: Int = 0
    private var miniFabRippleColorList: MutableList<Int>? = null

    private var miniFabTextColor: ColorStateList? = null
    private var miniFabTextColorList: MutableList<ColorStateList>? = null
    private var miniFabTextBackground: Drawable? = null
    private var miniFabTextBackgroundList: MutableList<Drawable>? = null
    private var miniFabLabelTextAppearance : Int = 0
    private var miniFabLabelElevation : Int = 0

    private var fabRotationAngle = 45.0f

    private var extraMarginPixel = 0
    private var useCompatPadding = false

    /**
     * Check whether menu is opened or closes
     * @return true if opened, false otherwise
     */
    var isOpeningMenu = false
        private set
    private var useTouchGuard = true
    private var useRevealEffect = true
    private var useRippleOnPreLollipop = true

    private var isLandscapeLayout = false

    @Dimension
    var miniFabSpacing : Int = 0
        set(value)  {
            field = value
            applyMiniFabSpacing(value)
        }

    constructor(context: Context) : super(context) {
        initLayout(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initLayout(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initLayout(context, attrs, defStyleAttr)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val parcelable = super.onSaveInstanceState()
        val savedState = SavedState(parcelable)
        savedState.isOpened = isOpeningMenu
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            if (state.isOpened) {
                openMenu()
            } else {
                closeMenu()
            }
            super.onRestoreInstanceState(state.superState)
        } else {
            super.onRestoreInstanceState(View.BaseSavedState.EMPTY_STATE)
        }
    }

    private fun initLayout(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.FabSpeedDial, defStyleAttr, 0)

        // landscape
        val canShowHorizontally = ta.getBoolean(R.styleable.FabSpeedDial_fab_showHorizontallyOnLandscape, true)
        isLandscapeLayout = canShowHorizontally && context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        val inflater = LayoutInflater.from(context)
        if (isLandscapeLayout) {
            inflater.inflate(R.layout.fab_speed_dial_land, this, true)
        } else {
            inflater.inflate(R.layout.fab_speed_dial, this, true)
        }

        mainFab = findViewById(R.id.fab_main)
        mainFab.setOnClickListener {
            if (isOpeningMenu) {
                closeMenu()
            } else {
                openMenu()
            }
        }

        fabsContainer = findViewById(R.id.fabs_container)
        menuContainer = findViewById(R.id.menu_container)

        touchGuard = findViewById(R.id.touch_guard)
        touchGuard.setOnClickListener { closeMenu() }

        // Key listener to handle BACK key
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
        setOnKeyListener(OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (isOpeningMenu) {
                    closeMenu()
                    return@OnKeyListener true
                }
            }
            false
        })

        // Read attrs
        useCompatPadding = ta.getBoolean(R.styleable.FabSpeedDial_fab_useCompatPadding, true)
        mainFab.useCompatPadding = useCompatPadding

        // Extra margin
        extraMarginPixel = ta.getDimensionPixelSize(R.styleable.FabSpeedDial_fab_extraMargin, 0)

        // Ripple on pre Lollipop
        useRippleOnPreLollipop = ta.getBoolean(R.styleable.FabSpeedDial_fab_useRippleOnPreLollipop, true)

        // Main FAB
        val drawableId = ta.getResourceId(R.styleable.FabSpeedDial_fab_fabDrawable, 0)
        if (drawableId != 0) {
            val drawable = AppCompatResources.getDrawable(context, drawableId)
            mainFab.setImageDrawable(drawable)
        }

        val fabBackgroundColor = ta.getColorStateList(R.styleable.FabSpeedDial_fab_fabBackgroundColor)
        if (fabBackgroundColor != null) {
            mainFab.backgroundTintList = fabBackgroundColor
        }

        val fabDrawableTint = ta.getColorStateList(R.styleable.FabSpeedDial_fab_fabDrawableTint)
        if (fabDrawableTint != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mainFab.imageTintList = fabDrawableTint
            } else {
                mainFab.setColorFilter(fabDrawableTint.defaultColor)
            }
        }

        val rippleColor = ta.getColor(R.styleable.FabSpeedDial_fab_fabRippleColor, Color.WHITE)
        if (shouldUseRipple()) {
            mainFab.rippleColor = rippleColor
        }

        val mainParams = mainFab.layoutParams as ViewGroup.MarginLayoutParams
        mainParams.setMargins(mainParams.leftMargin, mainParams.topMargin + extraMarginPixel, mainParams.rightMargin, mainParams.bottomMargin)
        mainFab.layoutParams = mainParams

        fabRotationAngle = ta.getFloat(R.styleable.FabSpeedDial_fab_fabRotationAngle, 45.0f)

        miniFabSpacing = ta.getDimensionPixelSize(R.styleable.FabSpeedDial_fab_miniFabSpacing, 0)

        // Mini FAB
        miniFabBackgroundColor = ta.getColorStateList(R.styleable.FabSpeedDial_fab_miniFabBackgroundColor)
        val miniFabBackgroundColorListId = ta.getResourceId(R.styleable.FabSpeedDial_fab_miniFabBackgroundColorList, 0)
        if (miniFabBackgroundColorListId != 0) {
            miniFabBackgroundColorList = mutableListOf<ColorStateList>().apply {
                val colorArray = resources.obtainTypedArray(miniFabBackgroundColorListId)
                for (i in 0 until colorArray.length()) {
                    colorArray.getColorStateList(i)?.let {
                        add(it)
                    }
                }
                colorArray.recycle()
            }
        }

        miniFabDrawableTint = ta.getColorStateList(R.styleable.FabSpeedDial_fab_miniFabDrawableTint)
        val miniFabDrawableTintListId = ta.getResourceId(R.styleable.FabSpeedDial_fab_miniFabDrawableTintList, 0)
        if (miniFabDrawableTintListId != 0) {
            miniFabDrawableTintList = mutableListOf<ColorStateList>().apply {
                val colorArray = resources.obtainTypedArray(miniFabDrawableTintListId)
                for (i in 0 until colorArray.length()) {
                    colorArray.getColorStateList(i)?.let {
                        add(it)
                    }
                }
                colorArray.recycle()
            }
        }

        miniFabRippleColor = ta.getColor(R.styleable.FabSpeedDial_fab_miniFabRippleColor, Color.TRANSPARENT)
        val miniFabRippleColorListId = ta.getResourceId(R.styleable.FabSpeedDial_fab_miniFabRippleColorList, 0)
        if (miniFabRippleColorListId != 0) {
            miniFabRippleColorList = mutableListOf<Int>().apply {
                val colorArray = resources.obtainTypedArray(miniFabRippleColorListId)
                for (i in 0 until colorArray.length()) {
                    add(colorArray.getColor(i, Color.TRANSPARENT))
                }
                colorArray.recycle()
            }
        }

        // Mini FAB text
        miniFabLabelTextAppearance = ta.getResourceId(R.styleable.FabSpeedDial_fab_miniFabLabelTextAppearance, -1)
        miniFabLabelElevation = ta.getDimensionPixelSize(R.styleable.FabSpeedDial_fab_miniFabLabelElevation, 0)

        miniFabTextColor = ta.getColorStateList(R.styleable.FabSpeedDial_fab_miniFabTextColor)
        val miniFabTextColorListId = ta.getResourceId(R.styleable.FabSpeedDial_fab_miniFabTextColorList, 0)
        if (miniFabTextColorListId != 0) {
            miniFabTextColorList = mutableListOf<ColorStateList>().apply {
                val colorArray = resources.obtainTypedArray(miniFabTextColorListId)
                for (i in 0 until colorArray.length()) {
                    colorArray.getColorStateList(i)?.let {
                        add(it)
                    }
                }
                colorArray.recycle()
            }
        }

        miniFabTextBackground = ta.getDrawable(R.styleable.FabSpeedDial_fab_miniFabTextBackground)
        val miniFabTextBackgroundListId = ta.getResourceId(R.styleable.FabSpeedDial_fab_miniFabTextBackgroundList, 0)
        if (miniFabTextBackgroundListId != 0) {
            miniFabTextBackgroundList = mutableListOf<Drawable>().apply {
                val drawableArray = resources.obtainTypedArray(miniFabTextBackgroundListId)
                for (i in 0 until drawableArray.length()) {
                    drawableArray.getDrawable(i)?.let {
                        add(it)
                    }
                }
                drawableArray.recycle()
            }
        }

        // Touch guard
        useTouchGuard = ta.getBoolean(R.styleable.FabSpeedDial_fab_useTouchGuard, true)
        useRevealEffect = ta.getBoolean(R.styleable.FabSpeedDial_fab_useRevealEffect, true)

        val touchGuardColor = ta.getColor(R.styleable.FabSpeedDial_fab_touchGuardColor, Color.argb(128, 0, 0, 0))
        touchGuard.setBackgroundColor(touchGuardColor)

        // Menu
        menu = FabSpeedDialMenu(context)
        val menuId = ta.getResourceId(R.styleable.FabSpeedDial_fab_menu, 0)
        if (menuId > 0) {
            MenuInflater(context).inflate(menuId, menu)
        }

        refreshMenus()

        ta.recycle()
        clipChildren = false
        clipToPadding = false
    }

    private fun refreshMenus() {
        menuContainer.removeAllViews()
        for (i in 0 until menu!!.size()) {
            val menuItem = menu!!.getItem(i)
            if (menuItem.isVisible) {
                val itemView = createItemView(i, menuItem)
                menuContainer.addView(itemView)
            }
        }
    }

    private fun createItemView(index: Int, menuItem: MenuItem): View {
        val inflater = LayoutInflater.from(context)
        val itemView = if (isLandscapeLayout) {
            inflater.inflate(R.layout.fab_speed_dial_item_land, menuContainer, false)
        } else {
            inflater.inflate(R.layout.fab_speed_dial_item, menuContainer, false)
        }

        // Mini FAB
        val miniFab = itemView.findViewById<FloatingActionButton>(R.id.fab_mini)
        if (menuItem.icon != null) {
            miniFab.setImageDrawable(menuItem.icon)
        }
        miniFab.isEnabled = menuItem.isEnabled

        if (miniFabBackgroundColor != null) {
            miniFab.backgroundTintList = miniFabBackgroundColor
        }
        if (miniFabBackgroundColorList != null) {
            miniFab.backgroundTintList = miniFabBackgroundColorList!![index]
        }

        if (miniFabDrawableTint != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                miniFab.imageTintList = miniFabDrawableTint
            } else {
                miniFab.setColorFilter(miniFabDrawableTint!!.defaultColor)
            }
        }
        if (miniFabDrawableTintList != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                miniFab.imageTintList = miniFabDrawableTintList!![index]
            } else {
                miniFab.setColorFilter(miniFabDrawableTintList!![index].defaultColor)
            }
        }

        if (shouldUseRipple()) {
            if (miniFabRippleColor != 0) {
                miniFab.rippleColor = miniFabRippleColor
            }
            if (miniFabRippleColorList != null) {
                miniFab.rippleColor = miniFabRippleColorList!![index]
            }
        }

        val params = miniFab.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(params.leftMargin, params.topMargin + extraMarginPixel, params.rightMargin, params.bottomMargin + extraMarginPixel)
        miniFab.layoutParams = params
        miniFab.useCompatPadding = useCompatPadding

        // TextView
        val label = itemView.findViewById<TextView>(R.id.text)
        if (label != null) {
            label.text = menuItem.title
            label.isEnabled = menuItem.isEnabled
            if (miniFabLabelTextAppearance != -1) {
                TextViewCompat.setTextAppearance(label, miniFabLabelTextAppearance)
            }
            ViewCompat.setElevation(label, miniFabLabelElevation.toFloat())

            if (miniFabTextColor != null) {
                label.setTextColor(miniFabTextColor)
            }
            if (miniFabTextColorList != null) {
                label.setTextColor(miniFabTextColorList!![index])
            }

            if (miniFabTextBackground != null) {
                val cs = miniFabTextBackground!!.mutate().constantState
                if (cs != null) {
                    ViewCompat.setBackground(label, cs.newDrawable())
                }
            }
            if (miniFabTextBackgroundList != null) {
                ViewCompat.setBackground(label, miniFabTextBackgroundList!![index])
            }

            label.setOnClickListener {
                for (listener in menuClickListeners) {
                    listener.invoke(miniFab, label, menuItem.itemId)
                }
                closeMenu()
            }
        }

        // Listener
        miniFab.setOnClickListener {
            for (listener in menuClickListeners) {
                listener.invoke(miniFab, label, menuItem.itemId)
            }
            closeMenu()
        }

        return itemView
    }

    private fun shouldUseRipple(): Boolean =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP || useRippleOnPreLollipop

    private fun applyMiniFabSpacing(@Dimension sizePixels: Int) {
        if (sizePixels > 0) {
            val drawable = ShapeDrawable(RectShape())
            if (isLandscapeLayout) {
                drawable.intrinsicWidth = sizePixels
                drawable.intrinsicHeight = 1
            } else {
                drawable.intrinsicWidth = 1
                drawable.intrinsicHeight = sizePixels
            }
            drawable.paint.color = Color.TRANSPARENT
            menuContainer.dividerDrawable = drawable
            menuContainer.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE or LinearLayout.SHOW_DIVIDER_END
        } else {
            menuContainer.dividerDrawable = null
            menuContainer.showDividers = LinearLayout.SHOW_DIVIDER_NONE
        }
    }

    /**
     * Inflate menu items from menu resource.
     * @param menuId Menu resource ID
     */
    @Suppress("unused")
    fun inflateMenu(@MenuRes menuId: Int) {
        MenuInflater(context).inflate(menuId, menu)
        refreshMenus()
    }

    /**
     * Set FAB speed dial menu.
     * This method recreates mini FABs and labels
     * @param menu Menu
     */
    fun setMenu(menu: FabSpeedDialMenu) {
        this.menu = menu
        refreshMenus()
    }

    /**
     * Open menu
     */
    fun openMenu() {
        if (isOpeningMenu) {
            return
        }
        mainFab.isSelected = true
        mainFab.animate().rotation(fabRotationAngle)
                .setDuration(MAIN_FAB_ROTATE_DURATION)
                .start()

        for (i in 0 until menuContainer.childCount) {
            val itemView = menuContainer.getChildAt(i)

            itemView.alpha = 0.0f

            val animator = itemView.animate()
            if (isLandscapeLayout) {
                itemView.translationX = MINI_FAB_SHOW_TRANSLATION
                animator.translationX(0.0f)
            } else {
                itemView.translationY = MINI_FAB_SHOW_TRANSLATION
                animator.translationY(0.0f)
            }
            animator.alpha(1.0f)
                    .setDuration(MINI_FAB_SHOW_DURATION)
                    .setStartDelay((menu!!.size() - 1 - i) * MINI_FAB_SHOW_DELAY)
                    .start()
        }

        menuContainer.visibility = View.VISIBLE
        if (useTouchGuard) {
            touchGuard.visibility = View.VISIBLE
            if (useRevealEffect) {
                touchGuard.alpha = 0.0f
                Handler().post {
                    touchGuard.alpha = 1.0f
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val cx = fabsContainer.left + (mainFab.left + mainFab.right) / 2
                        val cy = fabsContainer.top + (mainFab.top + mainFab.bottom) / 2
                        val radius = Math.max(touchGuard.width, touchGuard.height) * 2.0f
                        ViewAnimationUtils.createCircularReveal(touchGuard, cx, cy, 0f, radius).start()
                    }
                }
            }
        }
        isOpeningMenu = true
        for (listener in stateChangeListeners) {
            listener.invoke(isOpeningMenu)
        }
    }

    /**
     * Close menu
     */
    fun closeMenu() {
        if (!isOpeningMenu) {
            return
        }
        mainFab.isSelected = false
        mainFab.animate().rotation(0.0f)
                .setDuration(MAIN_FAB_ROTATE_DURATION)
                .start()

        for (i in 0 until menuContainer.childCount) {
            val itemView = menuContainer.getChildAt(i)
            itemView.animate()
                    .alpha(0.0f)
                    .setDuration(MINI_FAB_DISMISS_DURATION)
                    .setListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {

                        }

                        override fun onAnimationEnd(animation: Animator) {
                            menuContainer.visibility = View.GONE
                            itemView.animate().setListener(null).start()
                        }

                        override fun onAnimationCancel(animation: Animator) {

                        }

                        override fun onAnimationRepeat(animation: Animator) {

                        }
                    })
                    .start()
        }

        if (useTouchGuard) {
            touchGuard.animate()
                    .alpha(0.0f)
                    .setDuration(MINI_FAB_DISMISS_DURATION)
                    .setListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {

                        }

                        override fun onAnimationEnd(animation: Animator) {
                            touchGuard.alpha = 1.0f
                            touchGuard.visibility = View.GONE
                            touchGuard.animate().setListener(null).start()
                        }

                        override fun onAnimationCancel(animation: Animator) {

                        }

                        override fun onAnimationRepeat(animation: Animator) {

                        }
                    })
                    .start()
        }

        isOpeningMenu = false
        for (listener in stateChangeListeners) {
            listener.invoke(isOpeningMenu)
        }
    }

    /**
     * Show main [FloatingActionButton]
     */
    @Suppress("unused")
    fun show() {
        mainFab.show()
    }

    /**
     * Hide main [FloatingActionButton]
     */
    @Suppress("unused")
    fun hide() {
        if (isOpeningMenu) {
            closeMenu()
            mainFab.rotation = 0.0f
        }
        mainFab.hide()
    }

    /**
     * Check whether main [FloatingActionButton] is shown or not
     * @return true if main FAB is shown, false otherwise
     */
    override fun isShown(): Boolean = mainFab.isShown

    /**
     * Add event listener
     * @param listener event listener
     */
    fun addOnMenuItemClickListener(listener: OnMenuItemClick) {
        menuClickListeners.add(listener)
    }

    /**
     * Remove event listener
     * @param listener event listener
     */
    @Suppress("unused")
    fun removeOnMenuItemClickListener(listener: OnMenuItemClick) {
        menuClickListeners.remove(listener)
    }

    /**
     * Remove all event listeners
     */
    @Suppress("unused")
    fun removeAllOnMenuItemClickListeners() {
        menuClickListeners.clear()
    }

    /**
     * Add event listener
     * @param listener event listener
     */
    fun addOnStateChangeListener(listener: OnStateChange) {
        stateChangeListeners.add(listener)
    }

    /**
     * Remove event listener
     * @param listener event listener
     */
    @Suppress("unused")
    fun removeOnStateChangeListener(listener: OnStateChange) {
        stateChangeListeners.remove(listener)
    }

    /**
     * Remove all event listeners
     */
    @Suppress("unused")
    fun removeAllOnStateChangeListeners() {
        stateChangeListeners.clear()
    }

    /**
     * Get mini [FloatingActionButton] at index
     * @param index Index of mini FAB
     * @return Mini FAB
     */
    @Suppress("unused")
    fun getMiniFab(index: Int): FloatingActionButton {
        val view = menuContainer.getChildAt(index)
        return view.findViewById<View>(R.id.fab_mini) as FloatingActionButton
    }

    /**
     * Get mini [TextView] at index
     * @param index Index of mini FAB
     * @return TextView
     */
    @Suppress("unused")
    fun getMiniFabTextView(index: Int): TextView {
        val view = menuContainer.getChildAt(index)
        return view.findViewById<View>(R.id.text) as TextView
    }

    override fun getBehavior(): CoordinatorLayout.Behavior<*> = Behavior()

    /**
     * Default behavior of [FabSpeedDial].
     * It works as same as [FloatingActionButton.Behavior].
     */
    @Suppress("unused")
    class Behavior : CoordinatorLayout.Behavior<FabSpeedDial> {

        constructor() : super()

        constructor(@Suppress("UNUSED_PARAMETER") context: Context,
                    @Suppress("UNUSED_PARAMETER") attrs: AttributeSet) : super()

        override fun layoutDependsOn(parent: CoordinatorLayout, child: FabSpeedDial, dependency: View): Boolean =
                dependency is Snackbar.SnackbarLayout

        override fun onDependentViewChanged(parent: CoordinatorLayout, child: FabSpeedDial, dependency: View): Boolean {
            val diff = dependency.translationY - dependency.height
            child.translationY = diff
            return false
        }
    }

    private class SavedState(source: Parcelable?) : View.BaseSavedState(source) {

        internal var isOpened = false
    }

    companion object {

        private const val MAIN_FAB_ROTATE_DURATION = 200L
        private const val MINI_FAB_SHOW_DURATION = 100L
        private const val MINI_FAB_SHOW_DELAY = 50L
        private const val MINI_FAB_SHOW_TRANSLATION = 24.0f
        private const val MINI_FAB_DISMISS_DURATION = 100L
    }
}
