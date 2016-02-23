package net.flexmojos.m2e.flex;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;

/**
 * Mxmlc compiler specifications.
 * @see http ://help.adobe.com/en_US/flex/using/WS2db454920e96a9e51e63e3d11c0bf69084 -7a92.html
 */
public class FlexCompilerArguments
{
    List<String> arguments = new LinkedList<String>();

    public FlexCompilerArguments()
    {

    }

    /**
     * Adds directories or files to the source path. The Flex compiler searches directories in the source path for MXML,
     * AS, or CSS source files that are used in your applications and includes those that are required at compile time.
     * @param resourceBundlePath
     */
    public void setSourcePath( final List<String> pathElements )
    {
        if ( pathElements.size() != 0 )
        {
            arguments.add( "-source-path=" + StringUtils.join( pathElements.iterator(), " " ) );
        }
    }

    /**
     * Specifies one or more locales to be compiled into the SWF file. If you do not specify a locale, then the compiler
     * uses the default locale from the flex-config.xml file. The default value is en_US.
     * @param localesCompiled
     */
    public void setLocalesCompiled( final List<String> locales )
    {
        if ( locales.size() != 0 )
        {
            arguments.add( "-locale=" + StringUtils.join( locales.iterator(), "," ) );
        }
    }

    /**
     * Specifies the SWF version argument
     * @param swfVersion
     */
    public void setSwfVersion( final String swfVersion )
    {
        if ( swfVersion != null )
        {
            arguments.add( "-swf-version=" + swfVersion );
        }
    }

    /**
     * Specifies if debug
     * @param swfVersion
     */
    public void setDebug( final boolean debug )
    {
        arguments.add( "-debug=" + debug );
    }

    /**
     * Specifies if optimized
     * @param optimize
     */
    public void setOptimize( final boolean optimize )
    {
        arguments.add( "-optimize=" + optimize );
    }

    @Override
    public String toString()
    {
        return arguments.size() == 0 ? "" : StringUtils.join( arguments.iterator(), " " );
    }
}
