package sam.android.demo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import sam.android.demo.R;
import sam.android.utils.adapter.BaseRecyclerViewAdapter;
import sam.android.utils.adapter.ann.ConfigLayout;
import sam.android.utils.adapter.ann.ConfigView;
import sam.android.utils.adapter.bean.ContentItem;
import sam.android.utils.adapter.bean.FooterItem;
import sam.android.utils.adapter.bean.GroupItem;
import sam.android.utils.adapter.bean.HeaderItem;
import sam.android.utils.adapter.bean.Item;
import sam.android.utils.adapter.identification.HolderSerialize;

/**
 * Created by Administrator on 2015/9/29.
 */
public class MutilLayoutActivity extends Activity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recyclerview);
        findViews();
        intiUi();
    }


    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

    }

    private MyViewAdapter adapter;

    private void intiUi() {
        LinearLayoutManager lin = new LinearLayoutManager(this);
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lin);
        adapter = new MyViewAdapter(this);
        recyclerView.setAdapter(adapter);
        HeaderItem headerItem = new HeaderItem();
        headerItem.data = "头部";
        adapter.getItemList().add(headerItem);
        GroupItem groupItem1 = new GroupItem();
        groupItem1.data = "分组一";
        adapter.getItemList().add(groupItem1);
        for (int i = 0; i < 20; i++) {
            ContentItem item = new ContentItem();
            item.data = "" + i;
            adapter.getItemList().add(item);
        }
        GroupItem groupItem2 = new GroupItem();
        groupItem2.data = "分组2";
        adapter.getItemList().add(groupItem2);
        for (int i = 0; i < 50; i++) {
            ContentItem item = new ContentItem();
            item.data = "" + i;
            adapter.getItemList().add(item);
        }

        FooterItem footerItem = new FooterItem();
        footerItem.data = "尾部";
        adapter.getItemList().add(footerItem);
        adapter.notifyDataSetChanged();
    }

    //内容
    @ConfigLayout(R.layout.adapter_layout_recyclerview)
    public static class ContentHolder implements HolderSerialize {
        @ConfigView(R.id.tv_data)
        public TextView dataView;
    }

    //头部
    @ConfigLayout(R.layout.adapter_layout_recyclerview)
    public static class HeaderHolder implements HolderSerialize {
        @ConfigView(R.id.tv_data)
        public TextView dataView;
    }

    //尾部
    @ConfigLayout(R.layout.adapter_layout_recyclerview)
    public static class FooterHolder implements HolderSerialize {
        @ConfigView(R.id.tv_data)
        public TextView dataView;
    }

    //分组
    @ConfigLayout(R.layout.adapter_layout_recyclerview)
    public static class GroupHolder implements HolderSerialize {
        @ConfigView(R.id.tv_data)
        public TextView dataView;
    }


    //泛型类别分别是内容，分组， 头部，尾部。不实现用Null
    private class MyViewAdapter extends BaseRecyclerViewAdapter<ContentHolder, GroupHolder, HeaderHolder, FooterHolder> {
        public MyViewAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onContentUpdate(ContentHolder holder, int position, Item item) {
            TextView dataView = holder.dataView;
            dataView.setText((String) item.data);
        }

        @Override
        protected void onGroupUpdate(GroupHolder holder, int position, Item item) {
            TextView dataView = holder.dataView;
            dataView.setText((String) item.data);
        }


        @Override
        protected void onHeaderUpdate(HeaderHolder holder, int position, Item item) {
            TextView dataView = holder.dataView;
            dataView.setText((String) item.data);
        }

        @Override
        protected void onFooterUpdate(FooterHolder holder, int position, Item item) {
            TextView dataView = holder.dataView;
            dataView.setText((String) item.data);
        }


        @Override
        public void onGroupClicked(View view, int position, Item item) {
            Toast.makeText(getApplicationContext(), "" + item.data, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onContentClicked(View view, int position, Item item) {
            Toast.makeText(getApplicationContext(), "" + item.data, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFooterClicked(View view, int position, Item item) {
            Toast.makeText(getApplicationContext(), "" + item.data, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onHeaderClicked(View view, int position, Item item) {
            Toast.makeText(getApplicationContext(), "" + item.data, Toast.LENGTH_SHORT).show();
        }
    }


}
