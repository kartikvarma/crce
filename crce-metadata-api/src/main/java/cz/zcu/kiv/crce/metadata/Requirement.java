package cz.zcu.kiv.crce.metadata;

/**
 * 
 * @author Jiri Kucera (kalwi@students.zcu.cz, kalwi@kalwi.eu)
 */
public interface Requirement {
    
    String getName();

    String getFilter();

    boolean isMultiple();

    boolean isOptional();

    boolean isExtend();

    String getComment();
    
    boolean isWritable();
    
    boolean isSatisfied(Capability capability);
    
    Requirement setFilter(String filter);
    
    Requirement setMultiple(boolean multiple);
    
    Requirement setOptional(boolean optional);
    
    Requirement setExtend(boolean extend);
    
    Requirement setComment(String comment);

}
