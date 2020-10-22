<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                version="1.0">
    <xsl:output method="text" encoding="UTF-8"/>
    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="boolean(//xh:div[@class='pagination-container']//xh:a[contains(@href, 'page')][last()])">
                <xsl:value-of select="//xh:div[@class='pagination-container']//xh:a[last()][normalize-space(text())]"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="number(1)"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>