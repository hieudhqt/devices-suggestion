<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                xmlns="https://boba.vn/category"
                version="1.0">
    <xsl:output method="xml" encoding="UTF-8"/>
    <xsl:variable name="host" select="'https://boba.vn'"/>
    <xsl:template match="/">
        <xsl:element name="categories">
            <xsl:for-each select=".//xh:ul[@role='menu']/xh:li/xh:a[@class='dditem']">
                <xsl:element name="category">
                    <xsl:element name="name">
                        <xsl:value-of select=".//xh:span[@class='glyphicon glyphicon-fire']/following-sibling::node()"/>
                    </xsl:element>
                    <xsl:element name="url">
                        <xsl:value-of select="$host"/><xsl:value-of select="@href"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>