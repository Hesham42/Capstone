
package com.example.hesham.moves.model.modelVedio;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trial {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<ResultTrial> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ResultTrial> getResults() {
        return results;
    }

    public void setResults(List<ResultTrial> resultTrials) {
        this.results = resultTrials;
    }

}
