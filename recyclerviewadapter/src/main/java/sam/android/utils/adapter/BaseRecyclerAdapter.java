package sam.android.utils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import sam.android.utils.adapter.ann.ConfigHolder;
import sam.android.utils.adapter.ann.ConfigView;
import sam.android.utils.adapter.ann.ConfigLayout;
import sam.android.utils.adapter.bean.Item;
import sam.android.utils.adapter.identification.Null;
import sam.android.utils.adapter.identification.HolderSerialize;

/**
 * RecyclerView.Adapter 基类
 * Created by Sam on 2015/9/23.
 */
public abstract class BaseRecyclerAdapter<C extends  HolderSerialize,G extends  HolderSerialize,H extends  HolderSerialize,F extends  HolderSerialize> extends RecyclerView.Adapter
    implements OnContentItemClickedListener, OnGroupItemClickedListener, OnFooterItemClickedListener, OnHeaderItemClickedListener
{

    private List<Item> itemList = new ArrayList<Item>();

    private Context context;

    public BaseRecyclerAdapter(Context context) {
        this.context = context;

    }

    /**
     * 设置列表item
     * @param itemList
     */
    public void setItemList(List<Item> itemList)
    {
        if(null == itemList)
            return;
        this.itemList = itemList;
    }

    /**
     * 获取列表item
     * @return
     */
    public List<Item> getItemList()
    {
        return  itemList;
    }

    private abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        private HolderSerialize viewContent;
        public HolderSerialize holderInstance;

        protected abstract Class supportViewHolder(ConfigHolder adapter);

        public BaseViewHolder(View itemView) throws IllegalAccessException {
            super(itemView);
            Class baseAdapterClassType = BaseRecyclerAdapter.this.getClass();
            if (baseAdapterClassType.isAnnotationPresent(ConfigHolder.class)) {
                ConfigHolder configAdapter = (ConfigHolder) baseAdapterClassType.getAnnotation(ConfigHolder.class);
                Class<HolderSerialize> holderClass = supportViewHolder(configAdapter);
                if (!holderClass.isAssignableFrom(Null.class)) {
                    Constructor<?> constructor = holderClass.getConstructors()[0];
                    try {
                        holderInstance = (HolderSerialize) constructor.newInstance();
                    } catch (InstantiationException e) {
                        throw new RuntimeException("cant not new instance for "+getClass().getSimpleName());
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException("cant not new instance for "+getClass().getSimpleName());
                    }
                    Field[] fields = holderClass.getFields();
                    if (null != fields) {
                        for (Field field : fields) {
                            if (field.isAnnotationPresent(ConfigView.class)) {
                                Class classtype = field.getType();
                                if(!classtype.isPrimitive())
                                {
                                    ConfigView configHolder = field.getAnnotation(ConfigView.class);
                                    if(View.class.isAssignableFrom(classtype) || ViewGroup.class.isAssignableFrom(classtype))
                                    {
                                        int resourceId = configHolder.value();
                                        field.set(holderInstance, itemView.findViewById(resourceId));
                                    }
                                }

                            }
                        }
                    }

                } else {
                    throw new RuntimeException("you must support a ConfigHodler annimotion for your holder that name is " + getClass().getSimpleName());
                }

            }
        }
    }


    private class HeaderViewHolder extends BaseViewHolder implements View.OnClickListener {

        public HeaderViewHolder(View itemView) throws IllegalAccessException, InstantiationException {
            super(itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onHeaderClicked(v, getPosition(), itemList.get(getPosition()));
        }
        @Override
        protected Class supportViewHolder(ConfigHolder adapter) {
            return adapter.headerHolder();
        }
    }


    private class FooterViewHolder extends BaseViewHolder implements View.OnClickListener {

        public FooterViewHolder(View itemView) throws IllegalAccessException, InstantiationException {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onFooterClicked(v, getPosition(), itemList.get(getPosition()));
        }



        @Override
        protected Class supportViewHolder(ConfigHolder adapter) {
            return adapter.footerHolder();
        }
    }

    private class ContentViewHolder extends BaseViewHolder  implements View.OnClickListener{

        public ContentViewHolder(View itemView) throws IllegalAccessException, InstantiationException {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onContentClicked(v, getPosition(), itemList.get(getPosition()));
        }

        @Override
        protected Class supportViewHolder(ConfigHolder adapter) {
            return adapter.contentHolder();
        }
    }


    private class GroupHolder extends BaseViewHolder implements View.OnClickListener {

        public GroupHolder(View itemView) throws IllegalAccessException, InstantiationException {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected Class supportViewHolder(ConfigHolder adapter) {
            return adapter.groupHolder();
        }

        @Override
        public void onClick(View v) {
            onGroupClicked(v, getPosition(), itemList.get(getPosition()));
        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        Class baseAdapterClassType = BaseRecyclerAdapter.this.getClass();
        ConfigHolder configAdapter = (ConfigHolder) baseAdapterClassType.getAnnotation(ConfigHolder.class);
        try {
            switch (type) {
                case Item.TYPE_CONTENT: {
                    Class headerHolderClass = configAdapter.contentHolder();
                    ConfigLayout configHolderLayout = (ConfigLayout) headerHolderClass.getAnnotation(ConfigLayout.class);
                    int layout = configHolderLayout.value();
                     View convertView =  LayoutInflater.from(context).inflate(layout, viewGroup, false);

                    return new ContentViewHolder(convertView);
                }
                case Item.TYPE_HEADER: {
                    Class headerHolderClass = configAdapter.headerHolder();
                    ConfigLayout configHolderLayout = (ConfigLayout) headerHolderClass.getAnnotation(ConfigLayout.class);
                    return new HeaderViewHolder(LayoutInflater.from(context).inflate(configHolderLayout.value(), viewGroup, false));

                }

                case Item.TYPE_GROUP: {
                    Class headerHolderClass = configAdapter.groupHolder();
                    ConfigLayout configHolderLayout = (ConfigLayout) headerHolderClass.getAnnotation(ConfigLayout.class);
                    return new GroupHolder(LayoutInflater.from(context).inflate(configHolderLayout.value(), viewGroup, false));
                }

                case Item.TYPE_FOOTER: {
                    Class headerHolderClass = configAdapter.footerHolder();
                    ConfigLayout configHolderLayout = (ConfigLayout) headerHolderClass.getAnnotation(ConfigLayout.class);
                    return new FooterViewHolder(LayoutInflater.from(context).inflate(configHolderLayout.value(), viewGroup, false));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    protected abstract  void onContentUpdate( C holder, int position, Item item);


    protected   void onHeaderUpdate(H holder, int position, Item item) {

    }


    protected    void onFooterUpdate(F holder, int position, Item item) {

    }


    protected  void onGroupUpdate(G holder, int position, Item item) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Item item = itemList.get(i);
        BaseViewHolder holder = (BaseViewHolder) viewHolder;
        switch (item.type) {
            case Item.TYPE_CONTENT: {

                onContentUpdate((C) holder.holderInstance,  i, item);
            }
            break;
            case Item.TYPE_GROUP: {
                onGroupUpdate((G) holder.holderInstance, i, item);
            }
            break;
            case Item.TYPE_HEADER: {
                onHeaderUpdate((H) holder.holderInstance, i, item);
            }
            break;
            case Item.TYPE_FOOTER: {
                onFooterUpdate((F) holder.holderInstance, i, item);
            }
            break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @Override
    public void onContentClicked(View view, int position, Item item) {

    }

    @Override
    public void onGroupClicked(View view, int position, Item item) {

    }

    @Override
    public void onHeaderClicked(View view, int position, Item item) {

    }

    @Override
    public void onFooterClicked(View view, int position, Item item) {

    }
}
