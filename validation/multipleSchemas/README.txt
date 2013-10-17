streamSourceArrayApproach
    - uses the javax.xml.validation API of JAXP to validate XML
    - using this approach, the following is ignored:
        1)  values of xsi:schemaLocation in the xml instance
        2)  values of schemaLocation attribute of xs:import in XSD
    - in a multi schema situation, appears that need to explicity set StreamSource[] array with streams of each schema


saxParserApproach
    - use Xerces SAXParser to validate XML
    - using this approach, the following are used:
        1)  values of xsi:schemaLocation in the xml instance
        2)  values of schemaLocation attribute of xs:import in XSD
