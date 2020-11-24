/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loopeer.itemtouchhelperextension;

import android.graphics.Canvas;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchUIUtil;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG;

/**
 * Package private class to keep implementations. Putting them inside ItemTouchUIUtil makes them
 * public API, which is not desired in this case.
 */
class ItemTouchUIUtilImpl {
    static class Lollipop extends Honeycomb {
        @Override
        public void onDraw(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (isCurrentlyActive) {
                Object originalElevation = view.getTag(R.id.item_touch_helper_previous_elevation);
                if (originalElevation == null) {
                    originalElevation = view.getElevation();
                    float newElevation = 1f + findMaxElevation(recyclerView, view);
                    view.setElevation(newElevation);
                    view.setTag(R.id.item_touch_helper_previous_elevation, originalElevation);
                }
            }
            super.onDraw(c, recyclerView, view, dX, dY, actionState, isCurrentlyActive);
        }

        private float findMaxElevation(RecyclerView recyclerView, View itemView) {
            int childCount = recyclerView.getChildCount();
            float max = 0;
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (child == itemView) {
                    continue;
                }
                float elevation = child.getElevation();
                if (elevation > max) {
                    max = elevation;
                }
            }
            return max;
        }

        @Override
        public void clearView(View view) {
            Object tag = view.getTag(R.id.item_touch_helper_previous_elevation);
            if (tag instanceof Float) {
                view.setElevation((Float) tag);
            }
            view.setTag(R.id.item_touch_helper_previous_elevation, null);
            super.clearView(view);
        }
    }

    static class Honeycomb implements ItemTouchUIUtil {

        @Override
        public void clearView(View view) {
            view.setTranslationX(0f);
            view.setTranslationY(0f);
        }

        @Override
        public void onSelected(View view) {

        }

        @Override
        public void onDraw(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            view.setTranslationX(dX);
            view.setTranslationY(dY);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        }
    }

    static class Gingerbread implements ItemTouchUIUtil {

        private void draw(Canvas c, RecyclerView parent, View view, float dX, float dY) {
            c.save();
            c.translate(dX, dY);
            parent.drawChild(c, view, 0);
            c.restore();
        }

        @Override
        public void clearView(View view) {
            view.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSelected(View view) {
            view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState != ACTION_STATE_DRAG) {
                draw(c, recyclerView, view, dX, dY);
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState == ACTION_STATE_DRAG) {
                draw(c, recyclerView, view, dX, dY);
            }
        }
    }
}
