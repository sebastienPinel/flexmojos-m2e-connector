package net.flexmojos.m2e.maven.internal.fm6.adapters;

import static net.flexmojos.oss.plugin.common.FlexExtension.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.flexmojos.m2e.maven.IMavenFlexPlugin;
import net.flexmojos.m2e.maven.internal.MavenFlexPlugin;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Dependency;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.project.IMavenProjectFacade;

import com.google.inject.Inject;

/**
 * Implementation of the Flexmojos 6.x plugin.
 * @author Sylvain Lecoy (sylvain.lecoy@gmail.com)
 * @author Sebastien Pinel
 */
public class Flexmojos6Plugin
    extends MavenFlexPlugin
    implements IMavenFlexPlugin
{

    @Inject
    Flexmojos6Plugin( final IMavenProjectFacade facade, final IProgressMonitor monitor )
    {
        super( facade, monitor );
    }

    @Override
    public IPath[] getSourcePath()
    {
        IPath[] sourcePath = super.getSourcePath();

        if ( generator != null && !generator.getOutputDirectory().toFile().toString().contains( "src\\main\\flex" ) )
        {
            final List<IPath> classPath = new ArrayList<IPath>( Arrays.asList( sourcePath ) );

            // Directories from generator mojo are treated as supplementary source path.
            classPath.add( generator.getOutputDirectory() );
            classPath.add( generator.getBaseOutputDirectory() );

            sourcePath = classPath.toArray( new IPath[classPath.size()] );
        }

        return sourcePath;
    }

    @Override
    public Map<String, Artifact> getDependencies()
    {
        final Map<String, Artifact> dependencies = new LinkedHashMap<String, Artifact>();

        for ( final Artifact artifact : facade.getMavenProject().getArtifacts() )
        {
            // Only manage SWC type dependencies.
            if ( SWC.equals( artifact.getType() ) && !isAirFramework( artifact ) && !isFlashFramework( artifact )
                && !isFlexFramework( artifact ) )
            {
                dependencies.put( artifact.getFile().getAbsolutePath(), artifact );
            }
        }

        return dependencies;
    }

    /* (non-Javadoc)
     * @see net.flexmojos.m2e.maven.IMavenFlexPlugin#getFrameworkDependencies()
     */
    @Override
    public Map<String, Artifact> getFrameworkDependencies()
    {
        final Map<String, Artifact> dependencies = new LinkedHashMap<String, Artifact>();

        for ( final Artifact artifact : facade.getMavenProject().getArtifacts() )
        {
            // Only manage SWC type dependencies.
            if ( SWC.equals( artifact.getType() ) && isFlexFramework( artifact ) )
            {
                dependencies.put( artifact.getArtifactId(), artifact );
            }
        }

        return dependencies;
    }

    @Override
    public boolean isRSLDependency( String artifact )
    {
        for ( final Dependency artifactRef : facade.getMavenProject().getDependencies() )
        {
            if ( artifactRef.getArtifactId().equals( artifact ) )
            {
                return artifactRef.getType().equalsIgnoreCase( "rsl" );
            }
        }
        return false;
    }

    @Override
    public IPath getLocalesSourcePath()
    {
        // final String localesSourcePath = configuration.evaluate( "localesSourcePath" );
        // final IPath path = facade.getProjectRelativePath( localesSourcePath );
        // // Checks the base path (without the placeholder {locale} exists).
        // return facade.getProject().exists( path.removeLastSegments( 1 ) ) ? path : null;
        return null;
    }

    @Override
    public String[] getLocalesCompiled()
    {
        return compiler.getLocalesCompiled();
    }

    @Override
    public Xpp3Dom getManifestPath()
    {
        return compiler.getManifestPath();
    }

    @Override
    public IPath getCertificatePath()
    {
        final Xpp3Dom airConfig =
            facade.getMavenProject().getGoalConfiguration( "net.flexmojos.oss", "flexmojos-maven-plugin",
                "default-sign-air", "sign-air" );

        final Xpp3Dom keystoreTag = airConfig.getChild( "keystore" );

        if ( keystoreTag != null )
        {
            return facade.getProjectRelativePath( null /* evaluate( keystoreTag ) */);
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns <tt>true</tt> if this artifact belongs to Air Framework.
     * @param artifact The artifact to test.
     * @return <tt>true</tt> if this artifact belongs to Air Framework.
     */
    protected boolean isAirFramework( final Artifact artifact )
    {
        return artifact.getGroupId().startsWith( "com.adobe.air.framework" );
    }

    /**
     * Returns <tt>true</tt> if this artifact belongs to Flash Framework.
     * @param artifact The artifact to test.
     * @return <tt>true</tt> if this artifact belongs to Flash Framework.
     */
    protected boolean isFlashFramework( final Artifact artifact )
    {
        return artifact.getGroupId().startsWith( "com.adobe.flash.framework" );
    }

    /**
     * Returns <tt>true</tt> if this artifact belongs to Flex Framework.
     * @param artifact The artifact to test.
     * @return <tt>true</tt> if this artifact belongs to Flex Framework.
     */
    protected boolean isFlexFramework( final Artifact artifact )
    {
        return artifact.getGroupId().startsWith( "com.adobe.flex.framework" )
            || artifact.getGroupId().startsWith( "org.apache.flex.framework" );
    }

    @Override
    public String getRslURL()
    {
        return compiler.getRslURL();
    }

    @Override
    public String getSwfVersion()
    {
        return compiler.getSwfVersion();
    }

    @Override
    public boolean isDebug()
    {
        return compiler.isDebug();
    }

    @Override
    public boolean isOptimize()
    {
        return compiler.isOptimize();
    }

    @Override
    public boolean isVerifyDigests()
    {
        return compiler.isVerifyDigests();
    }

}
