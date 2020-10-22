<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                version="1.0">
    <xsl:output method="text" encoding="UTF-8"/>
    <xsl:template match="/">
        <xsl:for-each select="//xh:div[@class='list_products_page column_items']//xh:ul[@id='products']//xh:li[@class='columns product']">
            <xsl:value-of select=".//xh:h2[@class='pro_name']/xh:a/@href"/>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>