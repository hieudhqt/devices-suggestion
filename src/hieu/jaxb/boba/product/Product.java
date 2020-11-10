package hieu.jaxb.boba.product;

import javax.xml.bind.annotation.*;


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
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="warranty" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="imageUrl" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "product", propOrder = {
        "name",
        "price",
        "warranty",
        "description",
        "imageUrl"
})
@XmlRootElement(name = "product")
public class Product {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String price;
    @XmlElement(required = true)
    protected String warranty;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String imageUrl;

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
     * Gets the value of the description property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the imageUrl property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the value of the imageUrl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setImageUrl(String value) {
        this.imageUrl = value;
    }

}
