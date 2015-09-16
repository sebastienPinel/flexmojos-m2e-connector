package net.flexmojos.m2e.maven;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public interface ICompilerMojo
{
    /**
     * Gets the target player version.
     */
    @Nullable
    String getTargetPlayerVersion();

    /**
     * Gets the main application path.
     */
    @Nullable
    IPath getMainApplicationPath();

    /**
     * Whether or not an output folder path has been defined.
     */
    boolean hasOutputFolderPath();

    /**
     * Gets the output folder path.
     */
    @NonNull
    IPath getOutputFolderPath();

    /**
     * Gets manifest path.
     */
    @NonNull
    Xpp3Dom getManifests();

    /**
     * @return locales
     */
    @NonNull
    Xpp3Dom getLocales();

    /**
     * @return locales source path
     */
    @NonNull
    String getLocalesSource();

}
