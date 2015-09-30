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
import sam.android.utils.adapter.ann.ConfigHolder;
import sam.android.utils.adapter.ann.ConfigLayout;
import sam.android.utils.adapter.ann.ConfigView;
import sam.android.utils.adapter.bean.ContentItem;
import sam.android.utils.adapter.bean.Item;
import sam.android.utils.adapter.identification.HolderSerialize;
import sam.android.utils.adapter.identification.Null;

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

    private MyViewAdapter adapter;

    private void intiUi()
    {
        LinearLayoutManager lin = new LinearLayoutManager(this);
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lin);
        adapter = new MyViewAdapter(this);
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

    //注入item布局
    @ConfigLayout(R.layout.adapter_layout_recyclerview)
    public static  class ContentHolder implements  HolderSerialize
    {
        //注入View的id ，让框架本身给它提供引用
        @ConfigView(R.id.tv_data)
       public   TextView dataView;
    }

    //注入配置好的holder
    @ConfigHolder(contentHolder = ContentHolder.class)
    private class MyViewAdapter extends BaseRecyclerViewAdapter<ContentHolder,Null, Null, Null>
    {
        public MyViewAdapter(Context context) {
            super(context);
        }

        //列表内容每一项的绘制
        @Override
        protected void onContentUpdate(ContentHolder holder, int position, Item item) {
            TextView dataView = holder.dataView;
            dataView.setText((String) item.data);
        }

        //列表的点击事件
        @Override
        public void onContentClicked(View view, int position, Item item) {
            Toast.makeText(getApplicationContext(), ""+item.data, Toast.LENGTH_SHORT).show();
        }
    }


}
