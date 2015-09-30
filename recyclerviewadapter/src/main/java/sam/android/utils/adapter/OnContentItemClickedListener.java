package sam.android.utils.adapter;

import android.view.View;

import sam.android.utils.adapter.bean.Item;

public interface OnContentItemClickedListener
    {
        void onContentClicked(View view, int position , Item item);
    }
    