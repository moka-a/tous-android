package com.tous.application.database;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.database.table.transport.TransportTable;
import com.tous.application.database.table.user.UserTable;
import com.tous.application.mvc.model.spot.Transport;


public class TousProvider extends ContentProvider {

	private UriMatcher uriMatcher = buildUriMatcher();

	private TousDBHelper tousDBHelper;

	private UriMatcher buildUriMatcher() {

		UriMatcher uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );

		// user
		uriMatcher.addURI( TousDB.AUTHORITY, UserTable.TABLE_NAME, UserTable.CODE_USER );
		uriMatcher.addURI( TousDB.AUTHORITY, UserTable.TABLE_NAME + "/#", UserTable.CODE_USER_ID );

		// plan
		uriMatcher.addURI( TousDB.AUTHORITY, PlanTable.TABLE_NAME, PlanTable.CODE_PLAN );
		uriMatcher.addURI( TousDB.AUTHORITY, PlanTable.TABLE_NAME + "/#", PlanTable.CODE_PLAN_ID );

		// spot
		uriMatcher.addURI( TousDB.AUTHORITY, SpotTable.TABLE_NAME, SpotTable.CODE_SPOT );
		uriMatcher.addURI( TousDB.AUTHORITY, SpotTable.TABLE_NAME + "/#", SpotTable.CODE_SPOT_ID );

		// transport
		uriMatcher.addURI( TousDB.AUTHORITY, TransportTable.TABLE_NAME, TransportTable.CODE_TRANSPORT );
		uriMatcher.addURI( TousDB.AUTHORITY, TransportTable.TABLE_NAME + "/#", TransportTable.CODE_TRANSPORT_ID );

		return uriMatcher;
	}

	@Override
	public boolean onCreate() {

		tousDBHelper = new TousDBHelper( getContext() );
		return true;
	}

	@Override
	public String getType( Uri uri ) {

		return null;
	}

	@Override
	public Uri insert( Uri uri, ContentValues values ) {

		SQLiteDatabase database = tousDBHelper.getWritableDatabase();
		int match = uriMatcher.match( uri );
		Uri resultUri;

		switch ( match ) {

			case UserTable.CODE_USER:

				resultUri = UserTable.from( getContext() )
						.insert( database, uri, values );
				break;

			case PlanTable.CODE_PLAN:

				resultUri = PlanTable.from( getContext() )
						.insert( database, uri, values );
				break;

			case SpotTable.CODE_SPOT:

				resultUri = SpotTable.from( getContext() )
						.insert( database, uri, values );
				break;

			case TransportTable.CODE_TRANSPORT:

				resultUri = TransportTable.from( getContext() )
						.insert( database, uri, values );
				break;

			default:
				throw new UnsupportedOperationException( "Unknown uri: " + uri );
		}

		return resultUri;
	}

	@Override
	public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {

		SQLiteDatabase database = tousDBHelper.getWritableDatabase();
		int match = uriMatcher.match( uri );
		Cursor resultCursor;

		switch ( match ) {

			// user

			case UserTable.CODE_USER:

				resultCursor = UserTable.from( getContext() )
						.findAll( database, uri, projection, selection, selectionArgs, sortOrder );
				break;

			case UserTable.CODE_USER_ID:

				resultCursor = UserTable.from( getContext() )
						.find( database, uri, projection, selection, selectionArgs, sortOrder );
				break;

			// plan

			case PlanTable.CODE_PLAN:

				resultCursor = PlanTable.from( getContext() )
						.findAll( database, uri, projection, selection, selectionArgs, sortOrder );
				break;

			case PlanTable.CODE_PLAN_ID:

				resultCursor = PlanTable.from( getContext() )
						.find( database, uri, projection, selection, selectionArgs, sortOrder );
				break;

			// spot

			case SpotTable.CODE_SPOT:

				resultCursor = SpotTable.from( getContext() )
						.findAll( database, uri, projection, selection, selectionArgs, sortOrder );
				break;

			case SpotTable.CODE_SPOT_ID:

				resultCursor = SpotTable.from( getContext() )
						.find( database, uri, projection, selection, selectionArgs, sortOrder );
				break;

			// transport

			case TransportTable.CODE_TRANSPORT:

				resultCursor = TransportTable.from( getContext() )
						.findAll( database, uri, projection, selection, selectionArgs, sortOrder );
				break;

			case TransportTable.CODE_TRANSPORT_ID:

				resultCursor = TransportTable.from( getContext() )
						.find( database, uri, projection, selection, selectionArgs, sortOrder );
				break;

			default:
				throw new UnsupportedOperationException( "Unknown uri: " + uri );
		}

		return resultCursor;
	}

	@Override
	public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs ) {

		SQLiteDatabase database = tousDBHelper.getWritableDatabase();
		int match = uriMatcher.match( uri );
		int updateRow;

		switch ( match ) {

			// user

			case UserTable.CODE_USER:

				updateRow = UserTable.from( getContext() )
						.updateAll( database, uri, values, selection, selectionArgs );
				break;

			case UserTable.CODE_USER_ID:

				updateRow = UserTable.from( getContext() )
						.update( database, uri, values, selection, selectionArgs );
				break;

			// plan

			case PlanTable.CODE_PLAN:

				updateRow = PlanTable.from( getContext() )
						.updateAll( database, uri, values, selection, selectionArgs );
				break;

			case PlanTable.CODE_PLAN_ID:

				updateRow = PlanTable.from( getContext() )
						.update( database, uri, values, selection, selectionArgs );
				break;

			// spot

			case SpotTable.CODE_SPOT:

				updateRow = SpotTable.from( getContext() )
						.updateAll( database, uri, values, selection, selectionArgs );
				break;

			case SpotTable.CODE_SPOT_ID:

				updateRow = SpotTable.from( getContext() )
						.update( database, uri, values, selection, selectionArgs );
				break;

			// transport

			case TransportTable.CODE_TRANSPORT:

				updateRow = TransportTable.from( getContext() )
						.updateAll( database, uri, values, selection, selectionArgs );
				break;

			case TransportTable.CODE_TRANSPORT_ID:

				updateRow = TransportTable.from( getContext() )
						.update( database, uri, values, selection, selectionArgs );
				break;


			default:
				throw new UnsupportedOperationException( "Unknown uri: " + uri );
		}

		return updateRow;
	}

	@Override
	public int delete( Uri uri, String selection, String[] selectionArgs ) {

		return 0;
	}

}
