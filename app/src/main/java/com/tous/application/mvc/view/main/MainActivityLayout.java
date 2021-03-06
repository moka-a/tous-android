package com.tous.application.mvc.view.main;


import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.moka.framework.support.toolbar.ToolbarLayout;
import com.moka.framework.view.SupportActivityLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.main.MainActivity;
import com.tous.application.mvc.controller.activity.main.drawer.NavigationDrawerFragment;
import com.tous.application.mvc.model.plan.Plan;


public class MainActivityLayout extends SupportActivityLayout<MainActivity, MainActivityLayoutListener> implements MainActivityListener {

	private DrawerLayout drawerLayout;
	private ToolbarLayout toolbarLayout;

	private ActionBar actionBar;

	private ContentDrawerToggle drawerToggle;

	private NavigationDrawerFragment navigationDrawerFragment;

	public MainActivityLayout( MainActivity activity, MainActivityLayoutListener layoutListener ) {

		super( activity, layoutListener );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.activity_main;
	}

	@Override
	protected void onLayoutInflated() {

		actionBar = getActivity().getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled( true );
		actionBar.setHomeButtonEnabled( true );

		toolbarLayout = (ToolbarLayout) findViewById( R.id.toolbarLayout );
		toolbarLayout.setAlpha( 1 );
		getActivity().setTitle( "Tous" );

		drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
		drawerToggle = new ContentDrawerToggle( getActivity(), drawerLayout, toolbarLayout.getToolbar(), 0, 0 );

		drawerLayout.setDrawerListener( drawerToggle );
		drawerLayout.post( new Runnable() {

			@Override
			public void run() {

				drawerToggle.syncState();
			}

		} );

		navigationDrawerFragment = (NavigationDrawerFragment) getActivity().getSupportFragmentManager()
				.findFragmentById( R.id.navigation_drawer );
		navigationDrawerFragment.setDrawerLayout( drawerLayout );
	}

	private int getStatusBarHeight() {

		int statusBarHeight = 0;
		int resourceId = getActivity().getResources().getIdentifier( "status_bar_height", "dimen", "android" );
		if ( resourceId > 0 )
			statusBarHeight = getActivity().getResources().getDimensionPixelSize( resourceId );

		return statusBarHeight;
	}

	@Override
	public void setExpandToolbar( boolean expandToolbar ) {

		toolbarLayout.setExpand( expandToolbar );
	}

	@Override
	public void setAlpha( float alpha ) {

		toolbarLayout.setAlpha( alpha );
	}

	public void setTitle( String name ) {

		actionBar.setTitle( name );
	}

	public int getFragmentContainerID() {

		return R.id.frameLayout_container_main;
	}

	public void setPlanToNavigationDrawerFragment( Plan plan ) {

		navigationDrawerFragment.setPlan( plan );
	}

	private static class ContentDrawerToggle extends ActionBarDrawerToggle {

		private View contentView;

		public ContentDrawerToggle( Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
									@StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes ) {

			super( activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes );
			contentView = drawerLayout.findViewById( R.id.toolbarLayout );
		}

		@SuppressWarnings( "Annotator" )
		@Override
		public void onDrawerSlide( View drawerView, float slideOffset ) {

			int gravity = ( (DrawerLayout.LayoutParams) drawerView.getLayoutParams() ).gravity;
			if ( Gravity.LEFT == gravity ) {

//				TODO: 왼쪽 드로워 애니메이션
//				ViewHelper.setPivotY( drawerView, drawerView.getHeight() / 2 );
//				ViewHelper.setPivotX( drawerView, drawerView.getWidth() );
//				ViewHelper.setRotationY( drawerView, -67 * ( 1 - slideOffset ) );

//				ViewHelper.setTranslationX( contentView, drawerView.getWidth() * slideOffset );
			}

			super.onDrawerSlide( drawerView, slideOffset );
		}

	}

}
