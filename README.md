# MaterialFabSpeedDial

[![](https://jitpack.io/v/kobakei/MaterialFabSpeedDial.svg)](https://jitpack.io/#kobakei/MaterialFabSpeedDial)

**NOTICE: This project is still experimental. APIs may be changed without compatibility.**

This project aims to provide [FloatingActionButton's speed dial pattern in material design guideline](https://material.io/guidelines/components/buttons-floating-action-button.html#buttons-floating-action-button-transitions).

This project is inspired by [fab-speed-dial](https://github.com/yavski/fab-speed-dial) and overcomes some issues of it (ex. using private APIs of support library).

## Download

Project build.gradle

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

App build.gradle

```groovy
dependencies {
    compile 'com.github.kobakei:MaterialFabSpeedDial:LATEST_VERSION'
}
```

`LATEST_VERSION` is [![](https://jitpack.io/v/kobakei/MaterialFabSpeedDial.svg)](https://jitpack.io/#kobakei/MaterialFabSpeedDial)

## Getting Started

### Add to layout file

You can put `FabSpeedDial` in your layout XML.

```xml
<io.github.kobakei.materialfabspeeddial.FabSpeedDial
    android:id="@+id/fab"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:menu="@menu/fab"/>
```

Menu file is as below.

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/one"
        android:title="Cut"
        android:icon="@drawable/ic_action_cut"
        android:orderInCategory="1"/>
    <item
        android:id="@+id/two"
        android:title="Copy"
        android:icon="@drawable/ic_action_copy"
        android:orderInCategory="2"/>
    <item
        android:id="@+id/three"
        android:title="Paste"
        android:icon="@drawable/ic_action_paste"
        android:orderInCategory="3"/>
</menu>
```

Or you can set up menu items in Java.

```java
FabSpeedDialMenu menu = new FabSpeedDialMenu(this);
menu.add("One").setIcon(R.drawable.ic_action_cut);
menu.add("Two").setIcon(R.drawable.ic_action_copy);
menu.add("Three").setIcon(R.drawable.ic_action_paste);
fab.setMenu(menu);
```

### Attributes

|Attribute|Type|Description|
|---|---|---|
|menu|Menu|Menu|

### Event listener

```java
fab.addOnMenuItemClickListener(new FabSpeedDial.OnMenuItemClickListener() {
    @Override
    public void onMenuItemClick(FloatingActionButton fab, TextView textView, int itemId) {
        // do something
    }
});
```

## TODO

- Set up menus from XML
- Support CoordinatorLayout

## License

```
Copyright (c) 2017 Keisuke Kobayashi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
