package br.pucminas.algotimizacao.config.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by anderson on 02/03/16.
 */
public class DataData extends DataResponse {
    @JsonProperty("data")
    private final List<Object> data = new ArrayList<Object>();

    @JsonProperty("total")
    private int total = 0;
    public DataData(boolean success, String message){
        super(success, message);
    }
    @Override
    public DataData setMessage(String message){
        this.message = message;
        return this;
    }
    public DataData(){
        setSuccess(true);
    }
    public DataData(boolean success){
        super(success);
    }

    /**
     * Add a single Object to the data array
     * @param item the Object to add to the array
     */
    public Object add(Object item) {

        if(item == null) return null;

        if(item instanceof Collection) {

            for(Object object : (Collection) item) {
                data.add(object);
                total++;
            }
        } else {
            data.add(item);
            total++;
        }
        return item;
    }

    public Object add(Object item[]){
        for(Object o : item){
            data.add(o);
            total++;
        }
        return item;
    }

    public List<Object> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "DataData{" +
            "data=" + data +
            ", total=" + total +
            '}';
    }
}
