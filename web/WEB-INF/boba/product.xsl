<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                xmlns="https://boba.vn/product"
                version="1.0">
    <xsl:output method="xml" encoding="UTF-8"/>
    <xsl:variable name="header" select="//xh:div[@class='blk']//xh:div[@class='row']//xh:div[@class='col-md-8']"/>
    <xsl:template match="/">
        <xsl:element name="product">
            <xsl:element name="name">
                <xsl:value-of select="$header//xh:div[@class='clearfix' and @id='product-header']/xh:h1[normalize-space(text())]"/>
            </xsl:element>
            <xsl:element name="price">
                <xsl:variable name="price" select="$header//xh:div[@class='price']/child::text()"/>
                <xsl:value-of select="translate($price, translate($price, '0123456789', ''), '')"/>
            </xsl:element>
            <xsl:element name="warranty">
                <xsl:for-each select="$header//xh:div[@class='row']//xh:div[@class='policies']/xh:div">
                    <xsl:choose>
                        <xsl:when test="@id='policy-warranty'">
                            <xsl:value-of select="./xh:span[@class='glyphicon glyphicon-certificate']/following-sibling::node()"/>
                        </xsl:when>
                        <xsl:when test="@id='policy-warranty-time'">
                            <xsl:value-of select="normalize-space(text())"/>
                        </xsl:when>
                        <xsl:when test="@id='policy-note'">
                            <xsl:value-of select="normalize-space(text())"/>
                        </xsl:when>
                    </xsl:choose>
                    <xsl:if test="position() != last()">
                        <xsl:text>&#10;</xsl:text>
                    </xsl:if>
                </xsl:for-each>
            </xsl:element>
            <xsl:element name="description">
                <xsl:variable name="desAncestor" select=".//xh:div[@class='blk']//xh:aside[@id='long-description']"/>
                <xsl:for-each select="$desAncestor/descendant::node()">
                    <xsl:value-of select="normalize-space(text())"/>
                    <xsl:if test="position() != last()">
                        <xsl:text>&#10;</xsl:text>
                    </xsl:if>
                </xsl:for-each>
            </xsl:element>
            <xsl:element name="imageUrl">
                <xsl:value-of select="//xh:div[@class='blk']//xh:div[@class='row']//xh:div[@class='owl-carousel owl-theme']//xh:img[@class='owl-lazy']/@src"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>