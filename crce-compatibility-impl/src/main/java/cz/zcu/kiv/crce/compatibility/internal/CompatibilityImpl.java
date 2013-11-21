package cz.zcu.kiv.crce.compatibility.internal;

import org.osgi.framework.Version;

import cz.zcu.kiv.typescmp.Difference;

import cz.zcu.kiv.crce.compatibility.Compatibility;
import cz.zcu.kiv.crce.compatibility.Diff;

/**
 * Default implementation of Compatibility interface.
 *
 * Date: 16.11.13
 *
 * @author Jakub Danek
 */
public class CompatibilityImpl implements Compatibility {

    private String id;
    private String resourceName;
    private Version resourceVersion;
    private String baseResourceName;
    private Version baseResourceVersion;
    private Difference diffValue;
    private Diff diffDetails;

    /**
     * Empty constructor, creates uninitialized instance.
     */
    CompatibilityImpl() {

    }

    /**
     * Initializes only ID of the instance
     * @param id
     */
    CompatibilityImpl(String id) {
        this.id = id;
    }

    /**
     * Fully initialized instance. See {@link Compatibility} for parameter documentation.
     *
     * @see Compatibility
     */
    CompatibilityImpl(String id, String resourceName, Version resourceVersion,
                             String baseResourceName, Version baseResourceVersion,
                             Difference diffValue, Diff diffDetails) {
        this.id = id;
        this.resourceName = resourceName;
        this.resourceVersion = resourceVersion;
        this.baseResourceName = baseResourceName;
        this.baseResourceVersion = baseResourceVersion;
        this.diffValue = diffValue;
        this.diffDetails = diffDetails;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public Version getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(Version resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    @Override
    public String getBaseResourceName() {
        if(baseResourceName == null || baseResourceName.isEmpty()) {
            return getResourceName();
        }
        return baseResourceName;
    }

    public void setBaseResourceName(String baseResourceName) {
        this.baseResourceName = baseResourceName;
    }

    @Override
    public Version getBaseResourceVersion() {
        return baseResourceVersion;
    }

    public void setBaseResourceVersion(Version baseResourceVersion) {
        this.baseResourceVersion = baseResourceVersion;
    }

    @Override
    public Difference getDiffValue() {
        return diffValue;
    }

    public void setDiffValue(Difference diffValue) {
        this.diffValue = diffValue;
    }

    @Override
    public Diff getDiffDetails() {
        return diffDetails;
    }

    public void setDiffDetails(Diff diffDetails) {
        this.diffDetails = diffDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompatibilityImpl that = (CompatibilityImpl) o;

        if (baseResourceName != null ? !baseResourceName.equals(that.baseResourceName) : that.baseResourceName != null)
            return false;
        if (baseResourceVersion != null ? !baseResourceVersion.equals(that.baseResourceVersion) : that.baseResourceVersion != null)
            return false;
        if (diffDetails != null ? !diffDetails.equals(that.diffDetails) : that.diffDetails != null) return false;
        if (diffValue != that.diffValue) return false;
        if (resourceName != null ? !resourceName.equals(that.resourceName) : that.resourceName != null) return false;
        if (resourceVersion != null ? !resourceVersion.equals(that.resourceVersion) : that.resourceVersion != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = resourceName != null ? resourceName.hashCode() : 0;
        result = 31 * result + (resourceVersion != null ? resourceVersion.hashCode() : 0);
        result = 31 * result + (baseResourceName != null ? baseResourceName.hashCode() : 0);
        result = 31 * result + (baseResourceVersion != null ? baseResourceVersion.hashCode() : 0);
        result = 31 * result + (diffValue != null ? diffValue.hashCode() : 0);
        result = 31 * result + (diffDetails != null ? diffDetails.hashCode() : 0);
        return result;
    }
}