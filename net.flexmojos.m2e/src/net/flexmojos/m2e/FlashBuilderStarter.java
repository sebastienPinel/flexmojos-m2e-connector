package net.flexmojos.m2e;

import org.eclipse.ui.IStartup;

import com.adobe.flexbuilder.codemodel.StandardConfiguration;

public class FlashBuilderStarter
    implements IStartup
{

    @Override
    public void earlyStartup()
    {
        new StandardConfiguration().isIndexable( null );
    }

}
