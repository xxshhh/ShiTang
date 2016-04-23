package net.xuwenhui.shitang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * 公共适配器
 * <p/>
 * Created by xwh on 2016/4/15.
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter {

	protected List<T> mDataList;//数据源
	protected Context mContext;

	public CommonAdapter(Context context, List<T> dataList) {
		this.mContext = context;
		this.mDataList = dataList;
	}

	/**
	 * 获取数据源
	 *
	 * @return
	 */
	public List<T> getDataList() {
		return mDataList;
	}

	/**
	 * 返回数据总数
	 *
	 * @return
	 */
	@Override
	public int getItemCount() {
		return mDataList == null ? 0 : mDataList.size();
	}

	/**
	 * 给ViewHolder设置布局文件
	 *
	 * @param parent
	 * @param viewType
	 * @return
	 */
	@Override
	public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

	/**
	 * 给ViewHolder设置元素
	 *
	 * @param holder
	 * @param position
	 */
	@Override
	public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);
}
