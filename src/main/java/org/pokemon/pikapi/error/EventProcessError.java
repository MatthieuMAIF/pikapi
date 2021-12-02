package org.pokemon.pikapi.error;


import java.util.Collections;
import java.util.List;

public class EventProcessError {

    public final IError error;
    public final List<String> params;

    public EventProcessError(IError error, List<String> params) {
        this.error = error;
        this.params = params;
    }


    public static EventProcessError create(IError error) {
        return new EventProcessError(error, Collections.emptyList());
    }

    public static EventProcessError create(IError error, List<String> params) {
        return new EventProcessError(error, params);
    }

    /**
     * @return
     */
    public String toString(){
        return String.format(this.error.getMsg(),this.params.toArray());
    }

}
