package hieu.jaxb.report;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for product complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="product">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="category" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="warranty" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="room" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "product", propOrder = {
        "name",
        "category",
        "price",
        "warranty",
        "room"
})
public class Product {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String category;
    @XmlElement(required = true)
    protected String price;
    @XmlElement(required = true)
    protected String warranty;
    @XmlElement(required = true)
    protected List<String> room;

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the category property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCategory(String value) {
        this.category = value;
    }

    /**
     * Gets the value of the price property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPrice(String value) {
        this.price = value;
    }

    /**
     * Gets the value of the warranty property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getWarranty() {
        return warranty;
    }

    /**
     * Sets the value of the warranty property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setWarranty(String value) {
        this.warranty = value;
    }

    /**
     * Gets the value of the room property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the room property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoom().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getRoom() {
        if (room == null) {
            room = new ArrayList<String>();
        }
        return this.room;
    }

}
