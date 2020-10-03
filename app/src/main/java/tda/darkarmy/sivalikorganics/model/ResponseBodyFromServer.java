package tda.darkarmy.sivalikorganics.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResponseBodyFromServer implements Serializable {
    private String msg;

    public ResponseBodyFromServer(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseBody{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
