package com.tous.application.database.table.transport;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.moka.framework.database.BaseTable;
import com.moka.framework.util.UriBuilder;
import com.squareup.phrase.Phrase;
import com.tous.application.database.TousDB;
import com.tous.application.mvc.model.transport.Transport;
import com.tous.application.util.DateUtil;

import java.util.ArrayList;
import java.util.List;


public class TransportTable extends BaseTable<Transport> {

	public static final String TABLE_NAME = "transport";
	public static final Uri CONTENT_URI = TousDB.BASE_CONTENT_URI.buildUpon().appendPath( TABLE_NAME ).build();

	public static final int CODE_TRANSPORT = TousDB.BASE_CODE_TRANSPORT + 1;
	public static final int CODE_TRANSPORT_ID = TousDB.BASE_CODE_TRANSPORT + 2;

	public static final String ID = "id";
	public static final String PLAN_ID = "plan_id";
	public static final String SERVER_ID = "server_id";
	public static final String NAME = "name";
	public static final String CONTENT = "content";
	public static final String TIME_AT = "time_at";
	public static final String WAY = "way";
	public static final String DIRTY_FLAG = "dirty_flag";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";

	private static final String[] PROJECTION = { ID, PLAN_ID, SERVER_ID, NAME, CONTENT, TIME_AT, WAY, DIRTY_FLAG, CREATED_AT, UPDATED_AT };

	private Context context;

	private TransportTable( Context context ) {

		super( TABLE_NAME, CONTENT_URI );
		this.context = context;
	}

	@Override
	public void createTable( SQLiteDatabase database ) {

		final String queryStringFormat = "CREATE TABLE {table_name} ( " +
				"{id} INTEGER PRIMARY KEY AUTOINCREMENT, {plan_id} TEXT, {server_id} INTEGER, " +
				"{name} TEXT, {content} TEXT, {time_at} TEXT, {way} TEXT, " +
				"{dirty_flag} INTEGER DEFAULT 1, {created_at} INTEGER, {updated_at} INTEGER);";

		final String queryString = Phrase.from( queryStringFormat )
				.put( "table_name", TABLE_NAME )
				.put( "id", ID ).put( "plan_id", PLAN_ID ).put( "server_id", SERVER_ID )
				.put( "name", NAME ).put( "content", CONTENT ).put( "time_at", TIME_AT ).put( "way", WAY )
				.put( "dirty_flag", DIRTY_FLAG ).put( "created_at", CREATED_AT ).put( "updated_at", UPDATED_AT )
				.format().toString();

		database.execSQL( queryString );
	}

	@Override
	public Uri insert( Transport transport ) {

		transport.setCreatedAt( DateUtil.getTimestamp() );
		transport.setUpdatedAt( DateUtil.getTimestamp() );

		ContentValues values = new ContentValues();
		values.put( PLAN_ID, transport.getPlanId() );
		values.put( SERVER_ID, transport.getServerId() );
		values.put( NAME, transport.getName() );
		values.put( CONTENT, transport.getContent() );
		values.put( TIME_AT, transport.getTimeAt() );
		values.put( WAY, transport.getWay() );
		values.put( DIRTY_FLAG, transport.getDirtyFlag() );
		values.put( CREATED_AT, transport.getCreatedAt() );
		values.put( UPDATED_AT, transport.getUpdatedAt() );

		return context.getContentResolver().insert( CONTENT_URI, values );
	}

	@Override
	public Transport find( long id ) {

		String[] projection = PROJECTION;
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = null;

		Cursor cursor = context.getContentResolver()
				.query( UriBuilder.buildUriWithId( CONTENT_URI, id ), projection, selection, selectionArgs, sortOrder );

		Transport transport = null;
		if ( null != cursor ) {

			if ( 0 < cursor.getCount() && cursor.moveToFirst() )
				transport = parseFrom( cursor );

			cursor.close();
		}

		return transport;
	}

	public List<Transport> findAll() {

		String[] projection = PROJECTION;
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = null;

		ArrayList<Transport> spots = new ArrayList<>();
		Cursor cursor = context.getContentResolver()
				.query( CONTENT_URI, projection, selection, selectionArgs, sortOrder );

		if ( null != cursor ) {

			if ( 0 < cursor.getCount() ) {

				while ( cursor.moveToNext() )
					spots.add( parseFrom( cursor ) );
			}

			cursor.close();
		}

		return spots;
	}

	private static Transport parseFrom( Cursor cursor ) {

		Transport transport = new Transport();
		transport.setId( cursor.getLong( cursor.getColumnIndex( ID ) ) );
		transport.setPlanId( cursor.getLong( cursor.getColumnIndex( PLAN_ID ) ) );
		transport.setServerId( cursor.getLong( cursor.getColumnIndex( SERVER_ID ) ) );
		transport.setName( cursor.getString( cursor.getColumnIndex( NAME ) ) );
		transport.setContent( cursor.getString( cursor.getColumnIndex( CONTENT ) ) );
		transport.setTimeAt( cursor.getInt( cursor.getColumnIndex( TIME_AT ) ) );
		transport.setWay( cursor.getString( cursor.getColumnIndex( WAY ) ) );
		transport.setDirtyFlag( cursor.getInt( cursor.getColumnIndex( DIRTY_FLAG ) ) );
		transport.setCreatedAt( cursor.getLong( cursor.getColumnIndex( CREATED_AT ) ) );
		transport.setUpdatedAt( cursor.getLong( cursor.getColumnIndex( UPDATED_AT ) ) );

		return transport;
	}

	@Override
	public int update( Transport transport ) {

		transport.setUpdatedAt( DateUtil.getTimestamp() );

		ContentValues values = new ContentValues();
		values.put( PLAN_ID, transport.getPlanId() );
		values.put( SERVER_ID, transport.getServerId() );
		values.put( NAME, transport.getName() );
		values.put( CONTENT, transport.getContent() );
		values.put( TIME_AT, transport.getTimeAt() );
		values.put( WAY, transport.getWay() );
		values.put( DIRTY_FLAG, transport.getDirtyFlag() );
		values.put( UPDATED_AT, transport.getUpdatedAt() );

		String where = null;
		String[] selectionArgs = null;

		return context.getContentResolver()
				.update( UriBuilder.buildUriWithId( CONTENT_URI, transport.getId() ), values, where, selectionArgs );
	}

	@Override
	public int delete( Transport transport ) {

		return context.getContentResolver().delete( UriBuilder.buildUriWithId( CONTENT_URI, transport.getId() ), null, null );
	}

	public static TransportTable from( Context context ) {

		return new TransportTable( context );
	}

}
