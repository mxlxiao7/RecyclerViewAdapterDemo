package sam.android.utils.adapter;

import android.view.View;

import sam.android.utils.adapter.bean.Item;

public interface OnHeaderItemClickedListener
    {
        void onHeaderClicked(View view, int position , Item item);
    }
