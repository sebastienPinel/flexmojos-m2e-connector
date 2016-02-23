package net.flexmojos.m2e.flex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlexFrameworkHelper
{

    /**
     * Returns a Flash Builder compatible framework name from flex-sdk-description.xml. If no version is matching,
     * simply returns "Flex X.Y.Z".
     * @param fullVersion
     * @return
     */
    public static String getFlexSDKName( String flexVersion )
    {
        Pattern pattern = Pattern.compile( "(\\d*)\\.(\\d*)\\.(\\d*)" );

        Matcher matcher = pattern.matcher( flexVersion );
        String version = "";
        if ( matcher.find() )
        {
            version = matcher.group( 1 ) + "." + matcher.group( 2 ) + "." + matcher.group( 3 );

            if ( version.equals( "4.5.1" ) )
            {
                version = "4.5.1A";
            }
            else if ( version.equals( "4.5.0" ) )
            {
                version = "4.5A";
            }
            else if ( version.equals( "4.1.0" ) )
            {
                version = "4.1A";
            }
            else if ( version.equals( "4.0.0" ) )
            {
                version = "4.0A";
            }
            else if ( version.equals( "3.6.0" ) )
            {
                version = "3.6A";
            }
            else if ( version.equals( "3.5.0" ) )
            {
                version = "3.5B";
            }
            else if ( version.startsWith( "3.4" ) )
            {
                version = "3.4A";
            }
            else if ( version.equals( "3.3.0" ) )
            {
                version = "3.3A";
            }
            else if ( version.equals( "3.2.0" ) )
            {
                version = "3.2A";
            }
            else if ( version.startsWith( "3.0" ) )
            {
                version = "3A";
            }
        }
        else
        {
            version = flexVersion.substring( 0, 5 );
        }

        return "Flex " + version;
    }

}
