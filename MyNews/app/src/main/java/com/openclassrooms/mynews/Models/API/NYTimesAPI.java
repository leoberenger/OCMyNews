package com.openclassrooms.mynews.Models.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by berenger on 02/03/2018.
 */

public class NYTimesAPI {

    @SerializedName("results")
    @Expose
    private final List<Result> results = null;

    public List<Result> getResults() {
        return results;
    }

    //FOR SEARCH ARTICLES API
    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

}