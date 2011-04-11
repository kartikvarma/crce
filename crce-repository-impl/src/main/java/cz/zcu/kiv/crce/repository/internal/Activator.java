package cz.zcu.kiv.crce.repository.internal;

import cz.zcu.kiv.crce.plugin.Plugin;
import cz.zcu.kiv.crce.plugin.PluginManager;
import cz.zcu.kiv.crce.repository.Store;
import cz.zcu.kiv.crce.repository.SessionFactory;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Dictionary;
import org.apache.felix.dm.Component;
import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.log.LogService;

/**
 * 
 * @author Jiri Kucera (kalwi@students.zcu.cz, kalwi@kalwi.eu)
 */
public class Activator extends DependencyActivatorBase implements ManagedService {
    
    public static final String PID = "cz.zcu.kiv.crce.repository";
    
    public static final String STORE_URI = "store.uri";

    private volatile LogService m_log;              /* injected by dependency manager */
    private volatile DependencyManager m_manager;   /* injected by dependency manager */
    private volatile BundleContext m_context;       /* injected by dependency manager */
    
    @Override
    public void init(BundleContext bc, DependencyManager dm) throws Exception {
        
        dm.add(createComponent()
                .setImplementation(this)
                .add(createServiceDependency().setRequired(false).setService(LogService.class))
                .add(createConfigurationDependency().setPid(PID))
                );

        dm.add(createComponent()
                .setInterface(SessionFactory.class.getName(), null)
                .setImplementation(SessionFactoryImpl.class)
                );
        
        dm.add(createComponent()
                .setInterface(Plugin.class.getName(), null)
                .setImplementation(PriorityActionHandler.class)
                .add(createServiceDependency().setRequired(true).setService(PluginManager.class))
                .add(createServiceDependency().setRequired(false).setService(LogService.class))
                );

        dm.add(createComponent()
                .setInterface(Plugin.class.getName(), null)
                .setImplementation(DefaultResourceDAOFactory.class)
                .add(createServiceDependency().setRequired(true).setService(PluginManager.class))
                );
        
    }

    @Override
    public void updated(Dictionary dict) throws ConfigurationException {
        if (dict == null) {
            return;
        }
        
        if (m_context.getServiceReference(Store.class.getName()) != null) {
            m_log.log(LogService.LOG_WARNING, "Store URI reconfiguration on runtime is not supported");
            return;
        }
        
        URI uri;
        try {
            uri = new URI((String) dict.get(STORE_URI));
        } catch (URISyntaxException ex) {
            throw new ConfigurationException(STORE_URI, "Invalid URI: " + dict.get("uri"), ex);
        }

        Component store;
        
        if ("file".equals(uri.getScheme())) {
            try {
                store = createComponent()
                        .setInterface(Store.class.getName(), null)
                        .setImplementation(new FilebasedStoreImpl(new File(uri)))
                        .add(createServiceDependency().setRequired(true).setService(PluginManager.class))
                        .add(createServiceDependency().setRequired(false).setService(LogService.class));
            } catch (IOException e) {
                throw new ConfigurationException(STORE_URI, "Can not create store on given base directory: " + uri, e);
            }
        } else if ("http".equals(uri.getScheme())) {
            try {
                store = createComponent()
                        .setInterface(Store.class.getName(), null)
                        .setImplementation(new ObrStoreImpl(uri.toURL()));
            } catch (MalformedURLException ex) {
                throw new ConfigurationException(STORE_URI, "OBR Store URI is not an URL: " + uri, ex);
            }
        } else {
            throw new ConfigurationException(STORE_URI, "No Store implementation for given URI scheme: " + uri.getScheme());
        }
        
        m_manager.add(store);
        
        // for more instances of Store (e.g. user would like to choose a store for uploading resources)
        // th ManagedServiceFactory can be used, see:
        // http://www.osgilook.com/2009/08/04/factory-pattern-on-steroids-the-managedservicefactory/
        // http://changelos.com/2010/12/19/using-a-managedservicefactory/
        
    }

    @Override
    public void destroy(BundleContext bc, DependencyManager dm) throws Exception {
    }
}