package com.tous.application.mvc.controller.activity.main;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.moka.framework.widget.calendar.adapter.CalendarViewAdapter;
import com.moka.framework.widget.calendar.model.CalendarCellData;
import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.moka.framework.widget.calendar.util.DateProvider;
import com.squareup.otto.Subscribe;
import com.tous.application.mvc.controller.activity.main.calendar.CalendarAdapter;
import com.tous.application.mvc.controller.activity.main.schedule.ScheduleAdapter;
import com.tous.application.mvc.controller.activity.main.schedule.ScheduleItemFragment;
import com.tous.application.mvc.controller.activity.plandetail.DetailPlanActivity;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.main.MainFragmentLayout;
import com.tous.application.mvc.view.main.MainFragmentLayoutListener;
import com.tous.application.util.DateUtil;

import java.util.Date;


public class MainFragment extends BaseFragment implements MainFragmentLayoutListener, DateProvider {

	private MainFragmentLayout fragmentLayout;

	private CalendarAdapter calendarAdapter;
	private ScheduleAdapter scheduleAdapter;

	private Handler handler;

	private Plan plan;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new MainFragmentLayout( this, this, inflater, container );

		handler = new Handler();
		OttoUtil.getInstance().register( this );

		refreshView();
		fragmentLayout.showCalendar();
		fragmentLayout.setDayIndex( CalendarUtil.getDayIndexFrom( DateUtil.getTodayDate() ) );

		return fragmentLayout.getRootView();
	}

	private void refreshView() {

		String startDate = DateUtil.formatIntDateToString( plan.getStartDate() );
		String endDate = DateUtil.formatIntDateToString( plan.getEndDate() );
		fragmentLayout.setPlanName( plan.getName() ); //+ " " + startDate + " ~ " + endDate );

		calendarAdapter.setPlan( plan );
	}

	@Override
	public PagerAdapter getSchedulePagerAdapter() {

		if ( null == scheduleAdapter )
			scheduleAdapter = new ScheduleAdapter( getActivity().getSupportFragmentManager() );

		scheduleAdapter.setPlanId( plan.getId() );

		return scheduleAdapter;
	}

	@Override
	public CalendarViewAdapter<?, ?> getCalendarViewAdapter() {

		if ( null == calendarAdapter )
			calendarAdapter = new CalendarAdapter( getActivity() );

		return calendarAdapter;
	}

	@Override
	public DateProvider getDateProvider() {

		return this;
	}

	/**
	 * Click Listener
	 */

	@Override
	public void onCalendarItemSelected( final CalendarCellData data ) {

		handler.post( new Runnable() {

			@Override
			public void run() {

				int dayIndex = CalendarUtil.getDayIndexFrom( data.getDate() );
				if ( null != fragmentLayout )
					fragmentLayout.setDayIndex( dayIndex );
			}

		} );
	}

	@Override
	public void onDaySelected( int dayIndex ) {

		Date date = CalendarUtil.getDateFromDayIndex( dayIndex );
		int currentDate = CalendarUtil.getIntDate( date );

		setDayCount( currentDate );
	}

	private void setDayCount( int currentDate ) {

		if ( plan.isPlaningDate( currentDate ) )
			fragmentLayout.setDayCount( plan.getDayCount( currentDate ) + "일째" );
		else
			fragmentLayout.setDayCount( "ToUs 와 함께" );
	}

	@Override
	public void onClickToCalendar() {

		fragmentLayout.showCalendar();
	}

	@Override
	public void onClickToMap() {

		fragmentLayout.showMap();
	}

	@Subscribe
	public void onClickToDetailPlan( ScheduleItemFragment.OnClickToDetailPlan onClickToDetailPlan ) {

		Intent intent = new Intent( getActivity(), DetailPlanActivity.class );
		intent.putExtra( DetailPlanActivity.KEY_PLAN_ID, plan.getId() );
		startActivity( intent );
	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		OttoUtil.getInstance().unregister( this );
	}

	public MainFragment setPlan( Plan plan ) {

		this.plan = plan;
		return this;
	}

	public static MainFragment newInstance() {

		return new MainFragment();
	}

}
