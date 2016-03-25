package net.xuwenhui.shitang.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.fragment.EmptyFragment;

import butterknife.Bind;

/**
 * 主界面
 * <p/>
 * Created by xwh on 2016/3/15.
 */
public class MainActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.frame_content)
	FrameLayout mFrameContent;
	@Bind(R.id.layout_main)
	CoordinatorLayout mLayoutMain;
	@Bind(R.id.navigation_view)
	NavigationView mNavigationView;
	@Bind(R.id.layout_drawer)
	DrawerLayout mLayoutDrawer;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	protected void initData() {
		mToolbar.setTitle("首页");
		// toolbar替换actionbar
		setSupportActionBar(mToolbar);
		// 设置抽屉开关
		ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mLayoutDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
		drawerToggle.syncState();
		mLayoutDrawer.setDrawerListener(drawerToggle);
		// 默认跳转到首页
		switchToHome();
	}

	@Override
	protected void initListener() {
		// 设置抽屉导航菜单
		mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.navigation_home:
						Toast.makeText(mContext, "首页", Toast.LENGTH_SHORT).show();
						switchToHome();
						break;
					case R.id.navigation_order:
						Toast.makeText(mContext, "订单", Toast.LENGTH_SHORT).show();
						break;
					case R.id.navigation_person:
						Toast.makeText(mContext, "个人信息", Toast.LENGTH_SHORT).show();
						break;
				}
				item.setCheckable(true);
				mLayoutDrawer.closeDrawers();
				return true;
			}
		});
	}

	/**
	 * 跳转到首页
	 */
	private void switchToHome() {
		getFragmentManager().beginTransaction().replace(R.id.frame_content, new EmptyFragment()).commit();
		mToolbar.setTitle("首页");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
