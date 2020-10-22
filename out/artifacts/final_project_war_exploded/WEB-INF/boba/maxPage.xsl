<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                version="1.0">
    <xsl:output method="text" encoding="UTF-8"/>
    <xsl:variable name="totalItem" select="number(//xh:span[@class='total']/@data-total)"/>
    <xsl:variable name="pageSize" select="number(//xh:span[@class='pagesize']/@pagesize)"/>
    <xsl:template match="/">
        <xsl:value-of select="ceiling($totalItem div $pageSize)"/>
    </xsl:template>
</xsl:stylesheet>