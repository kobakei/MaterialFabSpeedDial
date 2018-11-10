package io.github.kobakei.materialfabspeeddial

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.recyclerview.widget.SortedList
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu

/**
 * Menu class of FAB speed dial.
 * This class implements [Menu] interface but some methods are marked as deprecated and not implemented.
 *
 * Created by keisuke on 2017/06/13.
 */
class FabSpeedDialMenu(private val context: Context) : Menu {

    private val menuItems = SortedList(MenuItem::class.java, object : SortedList.Callback<MenuItem>() {
        override fun compare(o1: MenuItem, o2: MenuItem): Int = o1.order - o2.order

        override fun onChanged(position: Int, count: Int) {

        }

        override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean = false

        override fun areItemsTheSame(item1: MenuItem, item2: MenuItem): Boolean = false

        override fun onInserted(position: Int, count: Int) {

        }

        override fun onRemoved(position: Int, count: Int) {

        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {

        }
    })

    override fun add(title: CharSequence): MenuItem {
        val itemId = menuItems.size() + 1
        return add(0, itemId, itemId, title)
    }

    override fun add(@StringRes titleRes: Int): MenuItem {
        val itemId = menuItems.size() + 1
        return add(0, itemId, itemId, titleRes)
    }

    override fun add(groupId: Int, itemId: Int, order: Int, title: CharSequence): MenuItem {
        val menuItem = FabSpeedDialMenuItem(context, itemId, groupId, order)
        menuItem.title = title
        menuItems.add(menuItem)
        return menuItem
    }

    override fun add(groupId: Int, itemId: Int, order: Int, @StringRes titleRes: Int): MenuItem {
        val menuItem = FabSpeedDialMenuItem(context, itemId, groupId, order)
        menuItem.setTitle(titleRes)
        menuItems.add(menuItem)
        return menuItem
    }

    @Deprecated("")
    override fun addSubMenu(title: CharSequence): SubMenu {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun addSubMenu(@StringRes titleRes: Int): SubMenu {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun addSubMenu(groupId: Int, itemId: Int, order: Int, title: CharSequence): SubMenu {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun addSubMenu(groupId: Int, itemId: Int, order: Int, @StringRes titleRes: Int): SubMenu {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun addIntentOptions(groupId: Int, itemId: Int, order: Int, caller: ComponentName, specifics: Array<Intent>, intent: Intent, flags: Int, outSpecificItems: Array<MenuItem>): Int {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun removeItem(id: Int) {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun removeGroup(groupId: Int) {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun clear() {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun setGroupCheckable(group: Int, checkable: Boolean, exclusive: Boolean) {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun setGroupVisible(group: Int, visible: Boolean) {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun setGroupEnabled(group: Int, enabled: Boolean) {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun hasVisibleItems(): Boolean {
        throw RuntimeException("Not implemented")
    }

    @Deprecated("")
    override fun findItem(id: Int): MenuItem {
        throw RuntimeException("Not implemented")
    }

    override fun size(): Int = menuItems.size()

    override fun getItem(index: Int): MenuItem = menuItems.get(index)

    override fun close() {
        throw RuntimeException("Not implemented")
    }

    override fun performShortcut(keyCode: Int, event: KeyEvent, flags: Int): Boolean {
        throw RuntimeException("Not implemented")
    }

    override fun isShortcutKey(keyCode: Int, event: KeyEvent): Boolean {
        throw RuntimeException("Not implemented")
    }

    override fun performIdentifierAction(id: Int, flags: Int): Boolean {
        throw RuntimeException("Not implemented")
    }

    override fun setQwertyMode(isQwerty: Boolean) {
        throw RuntimeException("Not implemented")
    }
}
