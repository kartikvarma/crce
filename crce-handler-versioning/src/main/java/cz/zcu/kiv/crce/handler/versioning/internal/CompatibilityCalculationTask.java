package cz.zcu.kiv.crce.handler.versioning.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zcu.kiv.crce.compatibility.service.CompatibilityService;
import cz.zcu.kiv.crce.concurrency.model.Task;
import cz.zcu.kiv.crce.metadata.Resource;

/**
 * Background task for calculation of compatibility data for a Resource.
 *
 * Creates compatibility data for all resources with the same crce.identity name and lower version.
 *
 * Date: 19.11.13
 *
 * @author Jakub Danek
 */
public class CompatibilityCalculationTask extends Task {
    private static final Logger logger = LoggerFactory.getLogger(CompatibilityCalculationTask.class);

    private CompatibilityService m_compatibilityService;      //injected by dependency manager

    private Resource resource;

    /**
     *
     * @param id ID of the task, for tracking
     * @param resource resource for which the compatibility data shall be computed
     */
    public CompatibilityCalculationTask(String id, Resource resource) {
        super(id, "Calculates compatibility data for a provided resource.", "crce-handler-versioning");
        this.resource = resource;
    }

    @Override
    protected Object run() throws Exception {
        logger.debug("Started calculation of Compatibility data for resource {}", resource.getSymbolicName());
        //Object o =  m_compatibilityService.calculateCompatibilities(resource);
        //TODO m_compatibilityService not implemented yet
        logger.debug("Finished calculation of Compatibility data for resource {}", resource.getSymbolicName());
        return null;
    }
}