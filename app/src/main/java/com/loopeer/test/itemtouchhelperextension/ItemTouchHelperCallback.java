package com.loopeer.test.itemtouchhelperextension;

import android.graphics.Canvas;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;
import com.loopeer.test.itemtouchhelperextension.MainRecyclerAdapter.ItemBaseViewHolder;
import com.loopeer.test.itemtouchhelperextension.MainRecyclerAdapter.ItemSwipeWithActionWidthNoSpringViewHolder;

public class ItemTouchHelperCallback extends ItemTouchHelperExtension.Callback {

    @Override
    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
        if (viewHolder instanceof MainRecyclerAdapter.ItemNoSwipeViewHolder) {
            return 0;
        }
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
        MainRecyclerAdapter adapter = (MainRecyclerAdapter) recyclerView.getAdapter();
        adapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dY != 0 && dX == 0) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        ItemBaseViewHolder holder = (ItemBaseViewHolder) viewHolder;
        if (viewHolder instanceof ItemSwipeWithActionWidthNoSpringViewHolder) {
            if (dX < -holder.mActionContainer.getWidth()) {
                dX = -holder.mActionContainer.getWidth();
            }
            holder.mViewContent.setTranslationX(dX);
            return;
        }
        if (viewHolder instanceof ItemBaseViewHolder) {
            holder.mViewContent.setTranslationX(dX);
        }
    }
}
