package sam.android.utils.adapter.bean;

/**
 * 保存列数据
 */
public class Item<T> {

    /**
     * 保存的数据
     */
    public T data;

    /**
     * 头部
     */
    public static final int TYPE_HEADER = 0x01;

    /**
     * 尾部
     */
    public static final int TYPE_FOOTER = 0x02;


    /**
     * 内容详情
     */
    public static final int TYPE_CONTENT = 0x03;


    /**
     * 分组
     */
    public static final int TYPE_GROUP = 0x04;

    public int type=TYPE_CONTENT;

    public Item(int type) {
        this.type = type;
    }

    public Item(int type, T data) {
        this.type = type;
        this.data = data;
    }

}
