package Medium.DeFam.app.common.base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Administrator on 2017/2/21 0021.
 */
public abstract class RecyclerBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> list;// 存放实体类的集合。即加载在view的对象
    protected LayoutInflater inflater;//布局加载器
    protected Context mContext;
    private static final int TYPE_NORMAL = 1000001;
    private static final int TYPE_FOOT = 1000002;
    private static final String TAG = RecyclerBaseAdapter.class.getSimpleName();

    private int load_status = STATUS_GONE;

    public static final int STATUS_LOADING = 0;//该状态加载不可用
    public static final int STATUS_OVER = 1;//该状态加载不可用
    public static final int STATUS_REFRESH = 5;//该状态加载不可用
    public static final int STATUS_GONE = 2;
    public static final int STATUS_INVISIBLE = 3;
    public static final int STATUS_ERROR = 4;
    //类型分离器
    private static final int TYPE_ITEM = 6;
    private static final int TYPE_SEPARATOR = 7;
    private TreeSet mSeparatorsSet;//分离器类型。因为类型不可重复
    //    private FootHolder footHolder;
    protected OnItemClickListener onItemClickListener;//单击事件
    protected OnItemLongClickListener onItemLongClickListener;//长按单击事件
    protected boolean clickFlag = true;//单击事件和长单击事件的屏蔽标识

    /**
     * 基础类 构造器
     *
     * @param context
     */
    public RecyclerBaseAdapter(Context context) {
        this.mContext = context;
        list = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        mSeparatorsSet = new TreeSet();
    }

    public RecyclerBaseAdapter(Context context, List<T> list) {
        this.list = list;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        mSeparatorsSet = new TreeSet();
    }

    /**
     * 增加适配器数据
     *
     * @param t 对象
     * @return
     */
    public boolean add(T t) {
        boolean flag = false;
        flag = list.add(t);
        return flag;
    }

    /**
     * 增加适配器数据顶部
     *
     * @param t 对象
     * @return
     */
    public boolean add(int inderx, T t) {
        boolean flag = false;
        try {
            list.add(inderx, t);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }


    /**
     * 增加适配器集合数据
     *
     * @param lists 集合
     * @return
     */
    public boolean addList(List<T> lists) {
        boolean flag = false;
        flag = list.addAll(lists);
        return flag;
    }

    /**
     * 删除适配器数据
     *
     * @param t
     * @return
     */
    public boolean remove(T t) {
        boolean flag = false;
        flag = list.remove(t);
        return flag;
    }

    /**
     * 删除适配器数据
     *
     * @param t
     * @return
     */
    public void remove(int t) {
//        boolean flag = false;
        list.remove(t);
    }

    /**
     * 删除适配器大量数据
     *
     * @param lists
     * @return
     */
    public boolean removeAll(List<T> lists) {
        boolean flag = false;
        flag = list.removeAll(lists);
        return flag;
    }

    /**
     * 清除适配器
     *
     * @return
     */
    public boolean clear() {
        boolean flag = false;
        try {
            list.clear();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 获取适配器集合
     *
     * @return
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 获取适配器的长度
     * 此处长度加一的原因：因为整个item视图比集合多一，因为底端有上拉加载的item
     *
     * @return
     */
    @Override
    public int getItemCount() {
        Log.e("TTTT", "----list.size----------->" + list.size());
        return list == null ? 0 : list.size();
    }


    /**
     * 获得相应数据集合中特定位置的数据项
     *
     * @return
     */
    public T getItem(int position) {

        return list.size() > 0 ? list.get(position) : null;
    }

    /**
     * 由position返回view type id
     * 返回的是有参数position所决定的的view的id
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOT;
        }
        return TYPE_NORMAL;
    }

    /**
     * 返回的是该postion对应item的id
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return list == null ? 0 : list.size() - 1;
    }


    /**
     * 返回在view上的items对象
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return loadOnCreateViewHolder(parent, viewType);
    }

    /**
     * 抽象方法 让继承本类，实现该方法，即可在items上创建view
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract RecyclerView.ViewHolder loadOnCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        loadOnBindViewHolder(holder, position);
    }

    /**
     * 为item设置数据
     *
     * @param holder
     * @param position
     */
    protected abstract void loadOnBindViewHolder(RecyclerView.ViewHolder holder, int position);


    public void setOnItemClickListener(OnItemClickListener onItemClickListner) {
        this.onItemClickListener = onItemClickListner;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListner) {
        this.onItemLongClickListener = onItemLongClickListner;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View v, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClickListener(View v, int position);
    }

}
