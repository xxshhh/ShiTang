package net.xuwenhui.shitang.fragment;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import net.xuwenhui.model.Evaluation;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.adapter.EvaluationAdapter;
import net.xuwenhui.shitang.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 评价界面
 * <p>
 * Created by xwh on 2016/4/27.
 */
public class EvaluationFragment extends BaseFragment {

	@Bind(R.id.ratingBar)
	RatingBar mRatingBar;
	@Bind(R.id.tv_star)
	TextView mTvStar;
	@Bind(R.id.list_evaluation)
	RecyclerView mListEvaluation;

	EvaluationAdapter mEvaluationAdapter;

	@Override
	protected int getContentLayoutId() {
		return R.layout.fragment_evaluation;
	}

	@Override
	protected void initData() {
		// 初始化评分条及分数
		initRatingBar();

		// 初始化评价列表
		mListEvaluation.setLayoutManager(new LinearLayoutManager(mContext));
		mListEvaluation.setItemAnimator(new DefaultItemAnimator());
		mListEvaluation.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
		List<Evaluation> data = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			Evaluation evaluation = new Evaluation(1, "", "18995629148", "2016-04-27 16:07:56", i, i + "这是一条评价！这是一条评价！这是一条评价！这是一条评价！这是一条评价！这是一条评价！这是一条评价！");
			data.add(evaluation);
		}
		mEvaluationAdapter = new EvaluationAdapter(mContext, data);
		mListEvaluation.setAdapter(mEvaluationAdapter);
	}

	@Override
	protected void initListener() {

	}

	/**
	 * 初始化评分条及分数
	 */
	private void initRatingBar() {
		Drawable fill = new IconicsDrawable(mContext)
				.icon(GoogleMaterial.Icon.gmd_star)
				.color(mContext.getResources().getColor(R.color.colorPrimary))
				.sizeDp(30);
		Drawable empty = new IconicsDrawable(mContext)
				.icon(GoogleMaterial.Icon.gmd_star_border)
				.color(mContext.getResources().getColor(R.color.colorPrimary))
				.sizeDp(30);
		Drawable half = new IconicsDrawable(mContext)
				.icon(GoogleMaterial.Icon.gmd_star_half)
				.color(mContext.getResources().getColor(R.color.colorPrimary))
				.sizeDp(30);
		mRatingBar.setStarFillDrawable(fill);
		mRatingBar.setStarEmptyDrawable(empty);
		mRatingBar.setStarHalfDrawable(half);
		mRatingBar.setStar(4.3f);
		mTvStar.setText(String.valueOf(4.3));
	}

}
