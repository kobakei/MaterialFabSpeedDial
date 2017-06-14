package io.github.kobakei.materialfabspeeddial.internal;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/**
 * Created by keisuke on 2017/06/13.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class FabSpeedDialMenuItem implements MenuItem {

    private final Context context;

    private final int itemId;
    private final int groupId;
    private final int order;

    private CharSequence title = null;
    private CharSequence titleCondensed = null;
    private boolean checked = false;
    private boolean visible = false;
    private boolean enabled = false;
    private boolean checkable = false;
    private Drawable icon = null;
    private char alphaChar;
    private char numericChar;

    // FabSpeedDial fields
    private ColorStateList titleColor;
    @DrawableRes
    private int titleBackgroundDrawableId;
    private ColorStateList drawableTintList;
    private ColorStateList fabBackgroundColor;
    @ColorInt
    private int rippleColor;

    FabSpeedDialMenuItem(Context context, int itemId, int groupId, int order) {
        this.context = context;
        this.itemId = itemId;
        this.groupId = groupId;
        this.order = order;
    }

    @Override
    public int getItemId() {
        return itemId;
    }

    @Override
    public int getGroupId() {
        return groupId;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public MenuItem setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    @Override
    public MenuItem setTitle(@StringRes int title) {
        this.title = context.getString(title);
        return this;
    }

    @Override
    public CharSequence getTitle() {
        return title;
    }

    @Override
    public MenuItem setTitleCondensed(CharSequence title) {
        this.titleCondensed = title;
        return this;
    }

    @Override
    public CharSequence getTitleCondensed() {
        return titleCondensed;
    }

    @Override
    public MenuItem setIcon(Drawable icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public MenuItem setIcon(@DrawableRes int iconRes) {
        this.icon = ContextCompat.getDrawable(context, iconRes);
        return this;
    }

    @Override
    public Drawable getIcon() {
        return icon;
    }

    @Override
    public MenuItem setIntent(Intent intent) {
        throw new RuntimeException("");
    }

    @Override
    public Intent getIntent() {
        throw new RuntimeException("");
    }

    @Override
    public MenuItem setShortcut(char numericChar, char alphaChar) {
        this.numericChar = numericChar;
        this.alphaChar = alphaChar;
        return this;
    }

    @Override
    public MenuItem setNumericShortcut(char numericChar) {
        this.numericChar = numericChar;
        return this;
    }

    @Override
    public char getNumericShortcut() {
        return numericChar;
    }

    @Override
    public MenuItem setAlphabeticShortcut(char alphaChar) {
        this.alphaChar = alphaChar;
        return this;
    }

    @Override
    public char getAlphabeticShortcut() {
        return alphaChar;
    }

    @Override
    public MenuItem setCheckable(boolean checkable) {
        this.checkable = checkable;
        return this;
    }

    @Override
    public boolean isCheckable() {
        return checkable;
    }

    @Override
    public MenuItem setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public MenuItem setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public MenuItem setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean hasSubMenu() {
        throw new RuntimeException("");
    }

    @Override
    public SubMenu getSubMenu() {
        throw new RuntimeException("");
    }

    @Override
    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
        throw new RuntimeException("");
    }

    @Override
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        throw new RuntimeException("");
    }

    @Override
    public void setShowAsAction(int actionEnum) {
        throw new RuntimeException("");
    }

    @Override
    public MenuItem setShowAsActionFlags(int actionEnum) {
        throw new RuntimeException("");
    }

    @Override
    public MenuItem setActionView(View view) {
        throw new RuntimeException("");
    }

    @Override
    public MenuItem setActionView(@LayoutRes int resId) {
        throw new RuntimeException("");
    }

    @Override
    public View getActionView() {
        throw new RuntimeException("");
    }

    @Override
    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new RuntimeException("");
    }

    @Override
    public ActionProvider getActionProvider() {
        throw new RuntimeException("");
    }

    @Override
    public boolean expandActionView() {
        throw new RuntimeException("");
    }

    @Override
    public boolean collapseActionView() {
        throw new RuntimeException("");
    }

    @Override
    public boolean isActionViewExpanded() {
        throw new RuntimeException("");
    }

    @Override
    public MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
        throw new RuntimeException("");
    }
}
