<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                version="1.0">
    <xsl:output method="text" encoding="UTF-8"/>
    <xsl:template match="/">
        <xsl:for-each select="//xh:div[@class='prlist wrapper prlist_container']/xh:div[@class='row' and @id='productlist']/xh:div[@class='pit col-xs-6 col-sm-3 col-md-2 col-lg-2 gap']">
            <xsl:value-of select="./xh:a/@href"/>
            <xsl:text>}</xsl:text>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>