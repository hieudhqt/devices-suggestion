<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                version="1.0">
    <xsl:output method="text" encoding="UTF-8"/>
    <xsl:strip-space elements="*"/>
    <xsl:template match="/">
        <xsl:for-each select=".//xh:div[contains(@id, 'info_detailP')]/descendant::node()[not(@class='text_btn')]">
            <xsl:if test="normalize-space(text()) != ''">
                <xsl:value-of select="normalize-space(text())"/>
                <xsl:text>&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>