package sam.android.utils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import sam.android.utils.adapter.ann.ConfigView;
import sam.android.utils.adapter.ann.ConfigLayout;
import sam.android.utils.adapter.bean.Item;
import sam.android.utils.adapter.identification.Null;
import sam.android.utils.adapter.identification.HolderSerialize;

/**
 * RecyclerView.Adapter 基类
 * Created by Sam on 2015/9/23.
 */
public abstract class BaseRecyclerViewAdapter<C extends  HolderSerialize,G extends  HolderSerialize,H extends  HolderSerialize,F extends  HolderSerialize> extends RecyclerView.Adapter
    implements OnContentItemClickedListener, OnGroupItemClickedListener, OnFooterItemClickedListener, OnHeaderItemClickedListener
{
    protected final int POSITION_CONTENT = 0;
    protected final int POSITION_GROUP = 1;
    protected final int POSITION_HEADER = 2;
    protected final int POSITION_FOOTER= 3;
    private List<Item> itemList = new ArrayList<Item>();

    private Context context;

    public BaseRecyclerViewAdapter(Context context) {
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
        protected abstract Class supportViewHolder();

        public BaseViewHolder(View itemView) throws IllegalAccessException {
            super(itemView);
            Class<HolderSerialize> holderClass = supportViewHolder();
            if (!holderClass.isAssignableFrom(Null.class)) {
                Constructor<?> constructor = holderClass.getConstructors()[0];
                constructor.setAccessible(true);
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
                        field.setAccessible(true);
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
            }


        }




    }

    /**
     * 获取对应位置泛型的class
     * @param postion
     * @return
     */
    protected    Class getSupportClass(int postion)
    {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Null.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (!(params[postion] instanceof Class)) {
            return Null.class;
        }
        return (Class) params[postion];
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
        protected Class supportViewHolder() {


            return getSupportClass(POSITION_HEADER);
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
        protected Class supportViewHolder() {
            return getSupportClass(POSITION_FOOTER);
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
        protected Class supportViewHolder() {
            return getSupportClass(POSITION_CONTENT);
        }
    }


    private class GroupHolder extends BaseViewHolder implements View.OnClickListener {

        public GroupHolder(View itemView) throws IllegalAccessException, InstantiationException {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected Class supportViewHolder() {
            return getSupportClass(POSITION_GROUP);
        }

        @Override
        public void onClick(View v) {
            onGroupClicked(v, getPosition(), itemList.get(getPosition()));
        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {

        try {
            switch (type) {
                case Item.TYPE_CONTENT: {
                    Class holderClass = getSupportClass(POSITION_CONTENT);
                    ConfigLayout configHolderLayout = (ConfigLayout) holderClass.getAnnotation(ConfigLayout.class);
                    int layout = configHolderLayout.value();
                     View convertView =  LayoutInflater.from(context).inflate(layout, viewGroup, false);

                    return new ContentViewHolder(convertView);
                }
                case Item.TYPE_HEADER: {
                    Class holderClass = getSupportClass(POSITION_HEADER);
                    ConfigLayout configHolderLayout = (ConfigLayout) holderClass.getAnnotation(ConfigLayout.class);
                    return new HeaderViewHolder(LayoutInflater.from(context).inflate(configHolderLayout.value(), viewGroup, false));

                }

                case Item.TYPE_GROUP: {
                    Class holderClass = getSupportClass(POSITION_GROUP);
                    ConfigLayout configHolderLayout = (ConfigLayout) holderClass.getAnnotation(ConfigLayout.class);
                    return new GroupHolder(LayoutInflater.from(context).inflate(configHolderLayout.value(), viewGroup, false));
                }

                case Item.TYPE_FOOTER: {
                    Class holderClass = getSupportClass(POSITION_FOOTER);
                    ConfigLayout configHolderLayout = (ConfigLayout) holderClass.getAnnotation(ConfigLayout.class);
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
