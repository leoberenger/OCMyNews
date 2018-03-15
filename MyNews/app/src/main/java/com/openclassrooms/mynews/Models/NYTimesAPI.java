package com.openclassrooms.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by berenger on 02/03/2018.
 */

public class NYTimesAPI {

    //FOR TOP STORIES AND MOST POPULAR APIs
    @SerializedName("num_results")
    @Expose
    private Integer numResults;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public Integer getNumResults() {
        return numResults;
    }

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