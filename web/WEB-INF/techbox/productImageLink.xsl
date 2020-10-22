<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                version="1.0">
    <xsl:output method="text" encoding="UTF-8"/>
    <xsl:variable name="host" select="'https://techbox.vn'"/>
    <xsl:template match="/">
        <xsl:value-of select="$host"/><xsl:value-of select="//xh:div[@class='img_wrapper_inner main_img']//xh:img[@id='image']/@src"/>
    </xsl:template>
</xsl:stylesheet>