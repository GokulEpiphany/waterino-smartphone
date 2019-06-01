package ai.eloop.waterino.interfaces;

import ai.eloop.waterino.model.Depth;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gokulakrishnanm on 07/05/19.
 */

public interface DepthInterface {

    @GET("/depth")
    public Call<Depth> getDistance();
}
