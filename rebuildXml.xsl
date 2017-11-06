<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" omit-xml-declaration="no"/>

    <xsl:template match="/">
        <!-- add new line,
        //TODO: Заменить костыль вставки новой строки, после ифну XML
        <xsl:text>&#13;&#10;</xsl:text>-->
        <xsl:text>&#13;</xsl:text>
        <entries>
            <xsl:apply-templates />
        </entries>
    </xsl:template>

    <xsl:template match="entry">
        <entry>
            <xsl:attribute name="field">
                    <xsl:value-of select="field"/>
            </xsl:attribute>
        </entry>
    </xsl:template>

</xsl:stylesheet>
