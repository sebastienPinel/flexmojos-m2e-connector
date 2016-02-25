package net.flexmojos.m2e.project.internal.fb47;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import net.flexmojos.m2e.maven.IMavenFlexPlugin;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import com.adobe.flexbuilder.project.FlexProjectManager;
import com.adobe.flexbuilder.project.IFlexLibraryProject;
import com.adobe.flexbuilder.project.XMLNamespaceManifestPath;
import com.adobe.flexbuilder.project.internal.FlexLibraryProjectSettings;
import com.google.inject.Inject;

public class FlexLibraryProjectConfigurator
    extends AbstractFlexProjectConfigurator
{

    @Inject
    FlexLibraryProjectConfigurator( final IMavenFlexPlugin plugin, final IProject project,
        final IProgressMonitor monitor )
    {
        super( plugin, project, monitor );
    }

    @Override
    protected void createConfiguration()
    {
        final IFlexLibraryProject flexProject = ( IFlexLibraryProject ) FlexProjectManager.getFlexProject( project );
        // Checks if project already exists.
        if ( flexProject == null )
        {
            // If it does not, create new settings.
            settings =
                FlexProjectManager
                    .createFlexLibraryProjectDescription( project.getName(), project.getLocation(), false /* FIXME: hard-coded ! */);
        }
        else
        {
            // If it does, reuse the settings.
            settings = flexProject.getFlexLibraryProjectSettingsClone();
        }
    }

    @Override
    protected void saveDescription()
    {
        final FlexLibraryProjectSettings flexProjectSettings = ( FlexLibraryProjectSettings ) settings;
        flexProjectSettings.saveDescription( project, monitor );
    }

    @Override
    protected void configureLibraryPath()
    {
        super.configureFlexSDKName();
        super.configureLibraryPath();
    }

    protected void configureManifest()
    {
        final Xpp3Dom namespacesTag = plugin.getManifestPath();
        final Map<String, IPath> namespaces = new LinkedHashMap<String, IPath>();

        if ( namespacesTag != null )
        {
            for ( final Xpp3Dom namespace : namespacesTag.getChildren() )
            {
                final String key = namespace.getChild( "uri" ).getValue();
                final IPath value = plugin.getFullPath( new File( namespace.getChild( "manifest" ).getValue() ) );
                namespaces.put( key, value );
            }
        }

        final XMLNamespaceManifestPath[] paths = new XMLNamespaceManifestPath[namespaces.size()];
        int iterator = 0;

        for ( final Map.Entry<String, IPath> namespace : namespaces.entrySet() )
        {
            // Converts <String, IPath> to XMLNamespaceManifestPath.
            paths[iterator++] = new XMLNamespaceManifestPath( namespace.getKey(), namespace.getValue() );
        }

        ( ( FlexLibraryProjectSettings ) settings ).setManifestPaths( paths );
    }

    @Override
    /**
     * Configures the project.
     */
    public void configure()
    {
        createConfiguration();

        configureMainSourceFolder();
        configureSourcePath();
        configureLibraryPath();
        configureOutputFolderPath();
        configureManifest();
        configureTargetPlayerVersion();
        configureMainApplicationPath();
        configureAdditionalCompilerArgs();

        saveDescription();
    }

}
