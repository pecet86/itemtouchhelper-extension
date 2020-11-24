# itemtouchhelper-extension

_A fork of [itemtouchhelper-extension](https://github.com/loopeer/itemtouchhelper-extension)_

[![](https://jitpack.io/v/pecet86/itemtouchhelper-extension.svg)](https://jitpack.io/#pecet86/itemtouchhelper-extension)
![License](https://img.shields.io/github/license/pecet86/historian.svg)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-orange.svg)](http://makeapullrequest.com)

This library add something for swipe item settling, recover base on the [Itemtouchhelper](https://developer.android.com/reference/androidx/recyclerview/widget/ItemTouchHelper).

This used in the repo [code-reader](https://github.com/loopeer/code-reader)

[Download Sample](https://github.com/pecet86/itemtouchhelper-extension/releases/tag/2.0.0)

Screenshot
====
### item swipe with spring  

![](/screenshot/itemtouch_spring.gif)   

### item swipe with no spring  

![](/screenshot/itemtouch_nospring.gif)  
### item swipe with recycler view width  

![](/screenshot/itemtouch_recycler_width.gif)   

Installation
====
```gradle
repositories {
    maven { url "https://jitpack.io" }
}
dependencies {
  def itemtouchhelper_version = '<version>'
  implementation "com.github.pecet86:itemtouchhelper-extension:itemtouchhelper_version"
}
```


Usages
====
```java
mCallback = new ItemTouchHelperCallback();
mItemTouchHelper = new ItemTouchHelperExtension(mCallback);
mItemTouchHelper.attachToRecyclerView(mRecyclerView);
```

ItemTouchHelperCallback just set as Itemtouchhelper. Set your swipe content view **translationX** by moving in method **onChildDraw**
```java
public class ItemTouchHelperCallback extends ItemTouchHelperExtension.Callback {

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.START);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }
    
    ...

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        MainRecyclerAdapter.ItemBaseViewHolder holder = (MainRecyclerAdapter.ItemBaseViewHolder) viewHolder;
        holder.mViewContent.setTranslationX(dX);
    }
}
```
At last you can let your **viewholder implements Extension** to set swipe settling width
```java
@Override
public float getActionWidth() {
    return mActionContainer.getWidth();
}
```
This will use whole recyclerview width when you do not add the width

Additional Drag Feature
====
If you want to add drag item feature.You just do **super.onChildDraw** in method onChildDraw of the class **ItemTouchHelperCallback**. Just like bottom:
```java
  @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dY != 0 && dX == 0) super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
       ...
    }
```

Click Action Button To Close Opened
====
If you want to close item after click action btn in the swipe item. You show add
```java
    mItemTouchHelperExtension.closeOpened();
```

License
====
<pre>
Copyright 2016 Loopeer
Copyright 2020 Paweł Cal (pecet86)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>
