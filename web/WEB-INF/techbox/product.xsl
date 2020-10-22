<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                xmlns="https://techbox.vn/product"
                version="1.0">
    <xsl:output method="xml" encoding="UTF-8"/>
    <xsl:template match="/">
        <xsl:element name="product">
            <xsl:element name="name">
                <xsl:value-of select=".//xh:h1[@class='heading_page']/xh:span[@id='ContentPlaceHolder1_ctl00_lbl_Title'][normalize-space(text())]"/>
            </xsl:element>
            <xsl:element name="price">
                <xsl:variable name="price" select=".//xh:div[@class='col_detail']//xh:div[@class='cost']/xh:span[@id='ContentPlaceHolder1_ctl00_lbl_GiaBan'][normalize-space(text())]"/>
                <xsl:value-of select="translate($price, translate($price, '0123456789', ''), '')"/>
            </xsl:element>
            <xsl:element name="warranty">
                <xsl:value-of select=".//xh:div[@class='row_pro']//xh:div[@class='code_pro fr']//xh:span[@id='ContentPlaceHolder1_ctl00_lblBaoHanh'][normalize-space(text())]"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>