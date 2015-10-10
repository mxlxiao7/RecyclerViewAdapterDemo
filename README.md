# RecyclerViewAdapterDemo
这是一个快速构建RecyclerView的Adapter的框架，可以快速构建，内容，头部，尾部，分组。还有其点击事件。

如下，实现一个适配器，选项为一个TextView，测试为10项，文本值为 0~9. 


        MyViewAdapter  adapter = new MyViewAdapter(this);
        recyclerView.setAdapter(adapter);
        for(int i = 0; i < 10; i ++)
        {
            ContentItem item = new ContentItem();
            item.data = ""+i;
            adapter.getItemList().add(item);
        }
        adapter.notifyDataSetChanged();



   
    //注入item布局
    @ConfigLayout(R.layout.adapter_layout_recyclerview)
    public static  class ContentHolder implements  HolderSerialize
    {
        //注入View的id ，让框架本身给它提供引用
        @ConfigView(R.id.tv_data)
       public   TextView dataView;
    }

    //泛型类别分别是内容，分组， 头部，尾部。部分不实现用Null代替
    
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
    多个类型布局，请download
    
