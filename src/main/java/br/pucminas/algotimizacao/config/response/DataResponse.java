package br.pucminas.algotimizacao.config.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by anderson on 02/03/16.
 */

public class DataResponse {

    /**
     * Simple Success response which can be used for custom ajax calls
     */
    public final static DataResponse SUCCESS = new DataResponse(true, null);
    public final static DataResponse ERROR = new DataResponse(false, null);
    protected String message = null;
    protected String topic = null;
    protected boolean important = false;
    protected boolean success = false;

    public DataResponse() {
        //no-op constructor
        this.success = true;
    }

    public DataResponse(boolean success) {
        this(success, null);
    }

    public DataResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getMessage() {
        return message;
    }

    /**
     * Topico é utilizado por mensagens asyncronas para identificar o que fazer com ela
     *
     * @return
     */
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getTopic() {
        return topic;
    }

    public DataResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public DataResponse setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public boolean isImportant() {
        return important;
    }

    public DataResponse setImportant(boolean important) {
        this.important = important;
        return this;
    }
}
