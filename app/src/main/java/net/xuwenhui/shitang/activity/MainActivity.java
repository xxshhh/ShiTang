package net.xuwenhui.shitang.activity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import net.xuwenhui.shitang.R;
import net.xuwenhui.shitang.fragment.EmptyFragment;
import net.xuwenhui.shitang.fragment.HomeFragment;
import net.xuwenhui.shitang.fragment.OrderFragment;
import net.xuwenhui.shitang.fragment.PersonFragment;

import butterknife.Bind;

/**
 * 主界面
 * <p/>
 * Created by xwh on 2016/3/15.
 */
public class MainActivity extends BaseActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;

	private Drawer mDrawer;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	protected void initData() {
		// 设置toolbar
		mToolbar.setTitle("首页");
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(false);
		// 设置抽屉导航菜单
		setupDrawer();
		mDrawer.setSelection(1);
	}

	@Override
	protected void initListener() {

	}

	/**
	 * 设置抽屉导航菜单
	 */
	private void setupDrawer() {
		PrimaryDrawerItem item_home = new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(GoogleMaterial.Icon.gmd_home).withIdentifier(1);
		PrimaryDrawerItem item_order = new PrimaryDrawerItem().withName(R.string.drawer_item_order).withIcon(GoogleMaterial.Icon.gmd_assignment).withBadge("99").withIdentifier(2);
		PrimaryDrawerItem item_person = new PrimaryDrawerItem().withName(R.string.drawer_item_person).withIcon(GoogleMaterial.Icon.gmd_person).withIdentifier(3);

		SecondaryDrawerItem item_settings = (SecondaryDrawerItem) new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(4);

		mDrawer = new DrawerBuilder()
				.withActivity(this)
				.withHeader(R.layout.layout_drawer_header)
				.withToolbar(mToolbar)
				.addDrawerItems(
						item_home,
						item_order,
						item_person,
						new SectionDrawerItem().withName(R.string.drawer_item_others),
						item_settings
				)
				.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
					@Override
					public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
						if (drawerItem != null) {
							// 设置气泡
							if (drawerItem instanceof Badgeable) {
								Badgeable badgeable = (Badgeable) drawerItem;
								if (badgeable.getBadge() != null) {
									//note don't do this if your badge contains a "+"
									//only use toString() if you set the test as String
									int badge = Integer.valueOf(badgeable.getBadge().toString());
									if (badge > 0) {
										badgeable.withBadge(String.valueOf(badge - 1));
										mDrawer.updateItem(drawerItem);
									}
								}
							}
							// 跳转页面
							switch ((int) drawerItem.getIdentifier()) {
								case 1:
									getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new HomeFragment()).commit();
									mToolbar.setTitle("首页");
									break;
								case 2:
									getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new OrderFragment()).commit();
									mToolbar.setTitle("订单");
									break;
								case 3:
									getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new PersonFragment()).commit();
									mToolbar.setTitle("我的信息");
									break;
								case 4:
									getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new EmptyFragment()).commit();
									mToolbar.setTitle("设置");
									break;
								default:
							}
						}
						return false;
					}
				})
				.build();
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

	@Override
	public void onBackPressed() {
		//handle the back press :D close the drawer first and if the drawer is closed close the activity
		if (mDrawer != null && mDrawer.isDrawerOpen()) {
			mDrawer.closeDrawer();
		} else {
			super.onBackPressed();
		}
	}
}
