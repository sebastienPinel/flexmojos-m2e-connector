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
     * Gets the manifest paths.
     */
    @NonNull
    Xpp3Dom getManifestPath();

    /**
     * Get the RSL Url.
     */
    String getRslURL();

    /**
     * Get Locales for compilation.
     */
    String[] getLocalesCompiled();

    /**
     * Get SWF version.
     */
    String getSwfVersion();

    /**
     * Get debug flag.
     */
    boolean isDebug();

    /**
     * Get optimize flag.
     */
    boolean isOptimize();

    /**
     * Get verify digests flag.
     */
    boolean isVerifyDigests();
}
