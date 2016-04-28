package net.xuwenhui.shitang.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mikepenz.iconics.view.IconicsImageView;

import net.xuwenhui.model.Address;
import net.xuwenhui.shitang.R;

import butterknife.Bind;

/**
 * 地址详情(修改或新增)
 * <p/>
 * Created by xwh on 2016/4/28.
 */
public class AddressDetailActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.edt_name)
	EditText mEdtName;
	@Bind(R.id.icon_man)
	IconicsImageView mIconMan;
	@Bind(R.id.icon_woman)
	IconicsImageView mIconWoman;
	@Bind(R.id.edt_phone_num)
	EditText mEdtPhoneNum;
	@Bind(R.id.edt_address_desc)
	EditText mEdtAddressDesc;
	@Bind(R.id.edt_note)
	EditText mEdtNote;
	@Bind(R.id.tv_ok)
	TextView mTvOk;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_address_detail;
	}

	@Override
	protected void initData() {
		// 获取Intent
		Intent intent = getIntent();
		Address address = (Address) intent.getSerializableExtra("Address");
		if (address != null) {
			mToolbar.setTitle("修改地址");
			mEdtName.setText(address.getName());
			if (address.getSex().equals("男士")) {
				mIconMan.performClick();
			} else {
				mIconWoman.performClick();
			}
			mEdtPhoneNum.setText(address.getPhone_num());
			mEdtAddressDesc.setText(address.getAddress_desc());
			mEdtNote.setText(address.getNote());
		} else {
			mToolbar.setTitle("新增地址");
		}

		// 设置toolbar
		setSupportActionBar(mToolbar);
		// 设置返回键<-
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}

	@Override
	protected void initListener() {
		// 性别点击
		mIconMan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mIconMan.setColor(getResources().getColor(R.color.colorAccent));
				mIconWoman.setColor(getResources().getColor(R.color.third_text));
			}
		});
		mIconWoman.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mIconMan.setColor(getResources().getColor(R.color.third_text));
				mIconWoman.setColor(getResources().getColor(R.color.colorAccent));
			}
		});
	}

}
