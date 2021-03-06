package net.flexmojos.m2e.maven.internal;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.PluginParameterExpressionEvaluator;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Simple configuration wrapper class. Maven plug-ins (build and reporting) are configured by specifying a
 * <code><configuration></code> element where the child elements of the <code><configuration></code> element are mapped
 * to fields inside the Mojo. Plug-in consists of one or more Mojos where a Mojo maps to a goal.
 * @author Sylvain Lecoy (sylvain.lecoy@gmail.com)
 */
public class Configuration
{
    private final Xpp3Dom configuration;

    private final Xpp3Dom originalConfiguration;

    private final ExpressionEvaluator evaluator;

    Configuration( final MavenSession session, final MojoExecution mojoExecution )
    {
        configuration = mojoExecution.getConfiguration();
        originalConfiguration = ( Xpp3Dom ) mojoExecution.getPlugin().getConfiguration();
        evaluator = new PluginParameterExpressionEvaluator( session, mojoExecution );
    }

    /**
     * Short-hand method for evaluating a configuration value.
     * @return the configuration value if defined, or the default value if not.
     */
    public @Nullable String evaluate( final String name )
    {
        try
        {
            final Xpp3Dom child = configuration.getChild( name );
            if ( child.getValue() != null )
            {
                return ( String ) evaluator.evaluate( child.getValue() );
            }
            else
            {
                return ( String ) evaluator.evaluate( child.getAttribute( "default-value" ) );
            }
        }
        catch ( final Exception e )
        {
            return null;
        }
    }

    /**
     * Short-hand method for evaluating a configuration value.
     * @return the configuration value if defined, or the default value if not.
     */
    public @Nullable Xpp3Dom getManifestPath()
    {
        try
        {
            return configuration.getChild( "namespaces" );
        }
        catch ( final Exception e )
        {
            return null;
        }
    }

    /**
     * Returns whether or not a configuration value is equal to a given string.
     * @param name
     * @param needle
     * @return
     */
    public boolean exists( final String name )
    {
        return originalConfiguration.getChild( name ) != null;
    }

    /**
     * <rslUrls> <url>{artifactId}.{extension}</url> </rslUrls>
     * @return
     */
    public String getRslURL()
    {
        try
        {
            final Xpp3Dom child = configuration.getChild( "rslUrls" ).getChild( 0 );
            if ( child.getValue() != null )
            {
                return ( String ) evaluator.evaluate( child.getValue() );
            }
            else
            {
                return ( String ) evaluator.evaluate( child.getAttribute( "default-value" ) );
            }
        }
        catch ( final Exception e )
        {
            return null;
        }
    }

    /**
     * <rslUrls> <url>{artifactId}.{extension}</url> </rslUrls>
     * @return
     */
    public String[] getLocalesCompiled()
    {
        try
        {
            final Xpp3Dom[] children = configuration.getChild( "localesCompiled" ).getChildren();
            String[] localesCompiled = new String[children.length];
            for ( int i = 0; i < children.length; i++ )
            {
                if ( children[i].getValue() != null )
                {
                    localesCompiled[i] = ( String ) evaluator.evaluate( children[i].getValue() );
                }
                else
                {
                    localesCompiled[i] = ( String ) evaluator.evaluate( children[i].getAttribute( "default-value" ) );
                }
            }
            return localesCompiled;
        }
        catch ( final Exception e )
        {
            return null;
        }
    }
}
