<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">
    <xsl:output method="xml" encoding="UTF-8"/>
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4" page-height="594mm" page-width="280mm" margin-top="10mm">
                    <fo:region-body margin-top="0.5in"/>
                    <fo:region-before extent="1in"/>
                    <fo:region-after extent=".75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="A4">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block font-size="20pt" font-family="Arial Unicode MS" line-height="24pt" background-color="cyan"
                              space-after.optimum="15pt" text-align="center" padding-top="3pt">Devices Suggestion
                    </fo:block>
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block font-size="18pt" font-family="Arial Unicode MS" line-height="24pt"
                              space-after.optimum="15pt" text-align="center" padding-top="3pt">
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-family="Arial Unicode MS">
                        <xsl:text>Tên người dùng: </xsl:text><xsl:value-of select="products/name"/>
                        <fo:block/>
                        <xsl:text>Thời gian: </xsl:text><xsl:value-of select="products/time"/>
                        <fo:block/>
                    </fo:block>
                    <fo:block font-size="14pt" font-family="Arial Unicode MS" line-height="24pt"
                              space-after.optimum="15pt" text-align="center" padding-top="3pt">Danh sách sản phẩm yêu
                        thích
                    </fo:block>
                    <fo:block font-family="Arial Unicode MS">
                        <fo:table border-collapse="separate" table-layout="fixed">
                            <fo:table-column column-width="3cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">STT</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">Tên sản phẩm</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">Danh mục</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">Giá</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">Bảo hành</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">Vị trí</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:for-each select="products/product">
                                    <fo:table-row>
                                        <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="center" font-family="Arial Unicode MS">
                                                <xsl:number level="single" count="product"/>
                                                .
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="center" font-family="Arial Unicode MS">
                                                <xsl:value-of select="name"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="center" font-family="Arial Unicode MS">
                                                <xsl:value-of select="category"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="center" font-family="Arial Unicode MS">
                                                <xsl:value-of select="format-number(price, '###,###')"/> đ
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="center" font-family="Arial Unicode MS">
                                                <xsl:value-of select="warranty"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="center" font-family="Arial Unicode MS">
                                                <xsl:for-each select="room">
                                                    <xsl:value-of select="text()"/>
                                                    <xsl:if test="position() != last()">
                                                        <xsl:text>,</xsl:text>
                                                    </xsl:if>
                                                    <fo:block/>
                                                </xsl:for-each>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
