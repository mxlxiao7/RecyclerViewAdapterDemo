package sam.android.utils.adapter;

import android.view.View;

import sam.android.utils.adapter.bean.Item;

public interface OnGroupItemClickedListener
    {
        void onGroupClicked(View view, int position , Item item);
    }