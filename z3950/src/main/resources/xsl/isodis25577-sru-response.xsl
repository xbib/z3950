<?xml version="1.0" encoding="UTF-8"?>
<!--
  XSL stylesheet for wrapping MarcXChange into SRU response
  Version 1, Jörg Prante, 14 Feb 2012
-->
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:srw="http://www.loc.gov/zing/srw/"
                xmlns:srw_mx="info:srw/schema/9/marcxchange"
                xmlns:mx="info:lc/xmlns/marcxchange-v1">
    <xsl:output method="xml" omit-xml-declaration="yes"/>
    <xsl:strip-space elements="*"/>
    <xsl:param name="version"/>
    <xsl:param name="numberOfRecords"/>
    <xsl:param name="format"/>
    <xsl:param name="type"/>    
    <xsl:template match="mx:collection">
        <srw:searchRetrieveResponse
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
            xsi:schemaLocation="http://www.loc.gov/zing/srw/ http://www.loc.gov/standards/sru/xml-files/srw-types.xsd" 
            xmlns="info:lc/xmlns/marcxchange-v1" >
            <srw:version>
                <xsl:value-of select="$version"/>
            </srw:version>
            <srw:numberOfRecords>
                <xsl:value-of select="$numberOfRecords"/>
            </srw:numberOfRecords>
            <srw:records>
                <xsl:apply-templates/>
            </srw:records>
        </srw:searchRetrieveResponse>
    </xsl:template>    
    <xsl:template match="mx:record">
        <srw:record>
            <srw:recordSchema>info:srw/schema/9/marcxchange</srw:recordSchema>
            <srw:recordPacking>xml</srw:recordPacking>
            <srw:recordData>
                <srw_mx:mx>
                    <xsl:element name="record" namespace="info:lc/xmlns/marcxchange-v1">
                        <xsl:attribute name="format" >
                            <xsl:value-of select="$format"/>
                        </xsl:attribute>
                        <xsl:attribute name="type" >
                            <xsl:value-of select="$type"/>
                        </xsl:attribute>
                        <xsl:apply-templates/>
                    </xsl:element>
                </srw_mx:mx>
            </srw:recordData>
            <srw:recordPosition>
                <xsl:value-of select="position()"/>
            </srw:recordPosition>
        </srw:record>
    </xsl:template>
    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>    
</xsl:stylesheet>
