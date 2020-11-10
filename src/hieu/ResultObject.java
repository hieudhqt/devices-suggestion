package hieu;

import java.io.Serializable;

public class ResultObject implements Serializable {

    private String xmlResult;
    private float fromPrice, toPrice;

    public ResultObject(String xmlResult, float fromPrice, float toPrice) {
        this.xmlResult = xmlResult;
        this.fromPrice = fromPrice;
        this.toPrice = toPrice;
    }

    public String getXmlResult() {
        return xmlResult;
    }

    public void setXmlResult(String xmlResult) {
        this.xmlResult = xmlResult;
    }

    public float getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(float fromPrice) {
        this.fromPrice = fromPrice;
    }

    public float getToPrice() {
        return toPrice;
    }

    public void setToPrice(float toPrice) {
        this.toPrice = toPrice;
    }
}
