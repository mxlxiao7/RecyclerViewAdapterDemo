package sam.android.utils.adapter.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import sam.android.utils.adapter.identification.Null;
import sam.android.utils.adapter.identification.HolderSerialize;

/**
 * 配置列表头部， 尾部，内容，分组
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigHolder {
    public Class<? extends HolderSerialize> headerHolder() default Null.class;

    public Class<? extends HolderSerialize> footerHolder() default Null.class;

    public Class<? extends HolderSerialize> contentHolder();

    public Class<? extends HolderSerialize> groupHolder() default Null.class;

}