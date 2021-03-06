package cz.zcu.kiv.crce.test.plugin2.search.impl;


import cz.zcu.kiv.crce.test.plugin2.search.FoundArtifact;
import org.eclipse.aether.artifact.Artifact;

/**
 * A simple implementation of FoundArtifact.
 *
 * Messenger design pattern.
 *
 * Created by Zdenek Vales on 1.2.2017.
 */
public class SimpleFoundArtifact implements FoundArtifact {

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String jarDownload;
    private final String pomDownload;

    /**
     * Creates a new artifact from aether artifact.
     * @param artifact
     */
    public SimpleFoundArtifact(Artifact artifact) {
        this.groupId = artifact.getGroupId();
        this.artifactId = artifact.getArtifactId();
        this.version = artifact.getVersion();
        this.jarDownload = "";
        this.pomDownload = "";
    }

    public SimpleFoundArtifact(String groupId, String artifactId, String version, String jarDownload, String pomDownload) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.jarDownload = jarDownload;
        this.pomDownload = pomDownload;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public String getArtifactId() {
        return artifactId;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getPomDownloadLink() {
        return pomDownload;
    }

    @Override
    public String getJarDownloadLink() {
        return jarDownload;
    }
}
