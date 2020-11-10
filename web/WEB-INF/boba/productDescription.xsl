<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                version="1.0">
    <xsl:output method="text" encoding="UTF-8"/>
    <xsl:template match="/">
        <xsl:variable name="header" select=".//xh:div[@class='blk']//xh:article/xh:div[@id='article-content']"/>
        <xsl:for-each select="$header/descendant::node()">
            <xsl:value-of select="normalize-space(text())"/>
            <xsl:if test="position() != last()">
                <xsl:text>&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>