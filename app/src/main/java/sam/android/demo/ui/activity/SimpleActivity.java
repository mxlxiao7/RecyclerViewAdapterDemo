package sam.android.demo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sam.android.demo.R;
import sam.android.utils.adapter.BaseRecyclerAdapter;
import sam.android.utils.adapter.ann.ConfigHolder;
import sam.android.utils.adapter.ann.ConfigLayout;
import sam.android.utils.adapter.ann.ConfigView;
import sam.android.utils.adapter.bean.ContentItem;
import sam.android.utils.adapter.bean.Item;
import sam.android.utils.adapter.identification.HolderSerialize;

/**
 * Created by Administrator on 2015/9/29.
 */
public class SimpleActivity  extends Activity{
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

    private MyAdapter adapter;

    private void intiUi()
    {
        LinearLayoutManager lin = new LinearLayoutManager(this);
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lin);
        adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);
        for(int i = 0; i < 10; i ++)
        {
            ContentItem item = new ContentItem();
            item.data = ""+i;
            adapter.getItemList().add(item);
        }
        ContentItem item = new ContentItem();
        adapter.getItemList().add(item);
        adapter.notifyDataSetChanged();
    }


    @ConfigLayout(R.layout.adapter_layout_recyclerview)
    public static  class ContentHolder implements  HolderSerialize
    {
        @ConfigView(R.id.tv_data)
       public   TextView dataView;
    }

    @ConfigHolder(contentHolder = ContentHolder.class)
    private class MyAdapter extends BaseRecyclerAdapter<ContentHolder,HolderSerialize, HolderSerialize, HolderSerialize>
    {
        public MyAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onContentUpdate(ContentHolder holder, int position, Item item) {
            TextView dataView = holder.dataView;
            dataView.setText((String) item.data);
        }

        @Override
        public void onContentClicked(View view, int position, Item item) {
            Toast.makeText(getApplicationContext(), ""+item.data, Toast.LENGTH_SHORT).show();
        }
    }


}
