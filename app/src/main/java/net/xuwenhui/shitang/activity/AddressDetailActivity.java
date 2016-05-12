package net.xuwenhui.shitang.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mikepenz.iconics.view.IconicsImageView;

import net.xuwenhui.core.ActionCallbackListener;
import net.xuwenhui.model.Address;
import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.util.T;

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
	@Bind(R.id.btn_ok)
	Button mBtnOk;
	@Bind(R.id.btn_delete)
	Button mBtnDelete;

	Address address;

	String sex;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_address_detail;
	}

	@Override
	protected void initData() {
		// 获取Intent
		Intent intent = getIntent();
		address = (Address) intent.getSerializableExtra("Address");
		if (address != null) {
			mToolbar.setTitle("修改地址");
			mEdtName.setText(address.getName());
			if (address.getSex().equals("先生")) {
				mIconMan.setColor(getResources().getColor(R.color.colorAccent));
				mIconWoman.setColor(getResources().getColor(R.color.third_text));
				sex = "先生";
			} else {
				mIconMan.setColor(getResources().getColor(R.color.third_text));
				mIconWoman.setColor(getResources().getColor(R.color.colorAccent));
				sex = "女士";
			}
			mEdtPhoneNum.setText(address.getPhone_num());
			mEdtAddressDesc.setText(address.getAddress_desc());
			mEdtNote.setText(address.getNote());
		} else {
			mToolbar.setTitle("新增地址");
			mBtnDelete.setVisibility(View.INVISIBLE);
			sex = "先生";
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
				sex = "先生";
			}
		});
		mIconWoman.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mIconMan.setColor(getResources().getColor(R.color.third_text));
				mIconWoman.setColor(getResources().getColor(R.color.colorAccent));
				sex = "女士";
			}
		});

		// 确认
		mBtnOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (address != null) {
					mAppAction.address_update(address.getAddress_id(), mEdtName.getText().toString(), sex, mEdtPhoneNum.getText().toString(), mEdtAddressDesc.getText().toString(), mEdtNote.getText().toString(), new ActionCallbackListener<Address>() {
						@Override
						public void onSuccess(Address data) {
							T.show(mContext, "修改地址成功");
							finish();
						}

						@Override
						public void onFailure(String errorCode, String errorMessage) {
							T.show(mContext, errorMessage);
						}
					});
				} else {
					mAppAction.address_create(mApplication.getUser().getUser_id(), mEdtName.getText().toString(), sex, mEdtPhoneNum.getText().toString(), mEdtAddressDesc.getText().toString(), mEdtNote.getText().toString(), new ActionCallbackListener<Address>() {
						@Override
						public void onSuccess(Address data) {
							T.show(mContext, "新增地址成功");
							finish();
						}

						@Override
						public void onFailure(String errorCode, String errorMessage) {
							T.show(mContext, errorMessage);
						}
					});
				}
			}
		});

		// 删除
		mBtnDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mAppAction.address_delete(address.getAddress_id(), new ActionCallbackListener<Void>() {
					@Override
					public void onSuccess(Void data) {
						T.show(mContext, "删除地址成功");
						finish();
					}

					@Override
					public void onFailure(String errorCode, String errorMessage) {
						T.show(mContext, errorMessage);
					}
				});
			}
		});
	}

}
