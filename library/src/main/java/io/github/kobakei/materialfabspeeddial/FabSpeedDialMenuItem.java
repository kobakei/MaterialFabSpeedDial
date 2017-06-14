package io.github.kobakei.materialfabspeeddial;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/**
 * Menu item of FAB speed dial
 * This class implements {@link MenuItem} interface but some methods are marked as deprecated and
 * not implemented.
 *
 * Created by keisuke on 2017/06/13.
 */
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

    /**
     * Constructor for inflater
     * @param context
     * @param itemId
     * @param groupId
     * @param order
     */
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

    @Deprecated
    @Override
    public MenuItem setTitleCondensed(CharSequence title) {
        this.titleCondensed = title;
        return this;
    }

    @Deprecated
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

    @Deprecated
    @Override
    public MenuItem setIntent(Intent intent) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public Intent getIntent() {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public MenuItem setShortcut(char numericChar, char alphaChar) {
        this.numericChar = numericChar;
        this.alphaChar = alphaChar;
        return this;
    }

    @Deprecated
    @Override
    public MenuItem setNumericShortcut(char numericChar) {
        this.numericChar = numericChar;
        return this;
    }

    @Deprecated
    @Override
    public char getNumericShortcut() {
        return numericChar;
    }

    @Deprecated
    @Override
    public MenuItem setAlphabeticShortcut(char alphaChar) {
        this.alphaChar = alphaChar;
        return this;
    }

    @Deprecated
    @Override
    public char getAlphabeticShortcut() {
        return alphaChar;
    }

    @Deprecated
    @Override
    public MenuItem setCheckable(boolean checkable) {
        this.checkable = checkable;
        return this;
    }

    @Deprecated
    @Override
    public boolean isCheckable() {
        return checkable;
    }

    @Deprecated
    @Override
    public MenuItem setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    @Deprecated
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

    @Deprecated
    @Override
    public boolean hasSubMenu() {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public SubMenu getSubMenu() {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public void setShowAsAction(int actionEnum) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public MenuItem setShowAsActionFlags(int actionEnum) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public MenuItem setActionView(View view) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public MenuItem setActionView(@LayoutRes int resId) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public View getActionView() {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public ActionProvider getActionProvider() {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public boolean expandActionView() {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public boolean collapseActionView() {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public boolean isActionViewExpanded() {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
        throw new RuntimeException("Not implemented");
    }
}
