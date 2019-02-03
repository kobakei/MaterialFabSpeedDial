package io.github.kobakei.materialfabspeeddial

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import android.view.ActionProvider
import android.view.ContextMenu
import android.view.MenuItem
import android.view.SubMenu
import android.view.View

/**
 * Menu item of FAB speed dial
 * This class implements [MenuItem] interface but some methods are marked as deprecated and
 * not implemented.
 *
 * Created by keisuke on 2017/06/13.
 */
class FabSpeedDialMenuItem(
        private val context: Context,
        private val itemId: Int,
        private val groupId: Int,
        private val order: Int
) : MenuItem {

    private var title: CharSequence? = null
    private var titleCondensed: CharSequence? = null
    private var checked = false
    private var visible = true
    private var enabled = true
    private var checkable = false
    private var icon: Drawable? = null
    private var alphaChar: Char = ' '
    private var numericChar: Char = ' '

    override fun getItemId(): Int = itemId

    override fun getGroupId(): Int = groupId

    override fun getOrder(): Int = order

    override fun setTitle(title: CharSequence?): MenuItem {
        this.title = title
        return this
    }

    override fun setTitle(@StringRes title: Int): MenuItem {
        this.title = context.getString(title)
        return this
    }

    override fun getTitle(): CharSequence? = title

    @Deprecated("")
    override fun setTitleCondensed(title: CharSequence?): MenuItem {
        this.titleCondensed = title
        return this
    }

    @Deprecated("")
    override fun getTitleCondensed(): CharSequence? = titleCondensed

    override fun setIcon(icon: Drawable): MenuItem {
        this.icon = icon
        return this
    }

    override fun setIcon(@DrawableRes iconRes: Int): MenuItem {
        this.icon = AppCompatResources.getDrawable(context, iconRes)
        return this
    }

    override fun getIcon(): Drawable? = icon

    @Deprecated("")
    override fun setIntent(intent: Intent): MenuItem {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun getIntent(): Intent {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun setShortcut(numericChar: Char, alphaChar: Char): MenuItem {
        this.numericChar = numericChar
        this.alphaChar = alphaChar
        return this
    }

    @Deprecated("")
    override fun setNumericShortcut(numericChar: Char): MenuItem {
        this.numericChar = numericChar
        return this
    }

    @Deprecated("")
    override fun getNumericShortcut(): Char = numericChar

    @Deprecated("")
    override fun setAlphabeticShortcut(alphaChar: Char): MenuItem {
        this.alphaChar = alphaChar
        return this
    }

    @Deprecated("")
    override fun getAlphabeticShortcut(): Char = alphaChar

    @Deprecated("")
    override fun setCheckable(checkable: Boolean): MenuItem {
        this.checkable = checkable
        return this
    }

    @Deprecated("")
    override fun isCheckable(): Boolean = checkable

    @Deprecated("")
    override fun setChecked(checked: Boolean): MenuItem {
        this.checked = checked
        return this
    }

    @Deprecated("")
    override fun isChecked(): Boolean = checked

    override fun setVisible(visible: Boolean): MenuItem {
        this.visible = visible
        return this
    }

    override fun isVisible(): Boolean = visible

    override fun setEnabled(enabled: Boolean): MenuItem {
        this.enabled = enabled
        return this
    }

    override fun isEnabled(): Boolean = enabled

    @Deprecated("")
    override fun hasSubMenu(): Boolean {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun getSubMenu(): SubMenu {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun setOnMenuItemClickListener(menuItemClickListener: MenuItem.OnMenuItemClickListener): MenuItem {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun getMenuInfo(): ContextMenu.ContextMenuInfo {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun setShowAsAction(actionEnum: Int) {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun setShowAsActionFlags(actionEnum: Int): MenuItem {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun setActionView(view: View): MenuItem {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun setActionView(@LayoutRes resId: Int): MenuItem {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun getActionView(): View {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun setActionProvider(actionProvider: ActionProvider): MenuItem {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun getActionProvider(): ActionProvider {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun expandActionView(): Boolean {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun collapseActionView(): Boolean {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun isActionViewExpanded(): Boolean {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun setOnActionExpandListener(listener: MenuItem.OnActionExpandListener): MenuItem {
        throw RuntimeException("Not implemented")
    }
}
