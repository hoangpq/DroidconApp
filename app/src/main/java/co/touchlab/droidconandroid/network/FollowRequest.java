package co.touchlab.droidconandroid.network;

import co.touchlab.android.superbus.errorcontrol.PermanentException;
import co.touchlab.android.superbus.errorcontrol.TransientException;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by kgalligan on 7/20/14.
 */
public interface FollowRequest
{
    @FormUrlEncoded
    @POST("/dataTest/follow")
    LoginResult follow(@Field("uuid") String uuid, @Field("otherId") Long otherId) throws TransientException, PermanentException;
}
