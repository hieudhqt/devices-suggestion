<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                xmlns="https://techbox.vn/category" version="1.0">
    <xsl:output encoding="UTF-8" method="xml" omit-xml-declaration="yes" indent="yes"/>

    <xsl:variable name="host" select="'https://techbox.vn'"/>

    <xsl:template match="/">
        <xsl:element name="categories">
            <xsl:for-each select="//xh:div[@class='show_menu']//xh:ul[@class='nav_menu']//xh:li[@id='menu2']//xh:a[@class='t_menu']">
                <xsl:variable name="pName" select="normalize-space(text())"/>
                <xsl:element name="category">
                    <xsl:element name="name">
                        <xsl:value-of select="$pName"/>
                    </xsl:element>
                    <xsl:element name="url">
                        <xsl:value-of select="$host"/><xsl:value-of select="@href"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>