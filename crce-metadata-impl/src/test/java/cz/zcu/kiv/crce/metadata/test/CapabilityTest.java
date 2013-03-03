package cz.zcu.kiv.crce.metadata.test;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import cz.zcu.kiv.crce.metadata.Capability;
import cz.zcu.kiv.crce.metadata.ResourceFactory;
import cz.zcu.kiv.crce.metadata.internal.ResourceFactoryImpl;

import cz.zcu.kiv.crce.metadata.Attribute;
import cz.zcu.kiv.crce.metadata.AttributeType;
import cz.zcu.kiv.crce.metadata.SimpleAttributeType;

/**
 *
 * @author Jiri Kucera (kalwi@students.zcu.cz, jiri.kucera@kalwi.eu)
 */
public class CapabilityTest {
    
    private ResourceFactory factory;
    
    private final AttributeType<String> ATTR_P1 = new SimpleAttributeType<>("p1", String.class);
    private final AttributeType<Long> ATTR_P2 = new SimpleAttributeType<>("p2", Long.class);
    private final AttributeType<String> ATTR_P3 = new SimpleAttributeType<>("p3", String.class);
    
    @Before
    public void before(){
        factory = new ResourceFactoryImpl();
    }
    
    @Test
    public void testSetAttribute() throws Exception {
        Capability c = factory.createCapability("a");
        c.setAttribute(ATTR_P1, "1");
        c.setAttribute(ATTR_P2, 2L);
        
        assertEquals("a", c.getNamespace());
        assertEquals("1", c.getAttributeValue(ATTR_P1));
        assertEquals(2L, (long) c.getAttributeValue(ATTR_P2));
    }

    @Test
    public void testUnsetAttribute() throws Exception {
        Capability c = factory.createCapability("a");
        c.setAttribute(ATTR_P1, "1");
        c.setAttribute(ATTR_P2, 2L);
        
        assertNotNull(c.getAttribute(ATTR_P1));
        assertNotNull(c.getAttribute(ATTR_P2));
        
        c.unsetAttribute(ATTR_P1);
        
        assertNull(c.getAttribute(ATTR_P1));
        assertNotNull(c.getAttribute(ATTR_P2));
        
        Attribute<Long> attribute = c.getAttribute(ATTR_P2);
        
        assertNotNull(attribute);
        
        c.unsetAttribute(attribute);
        
        assertNull(c.getAttribute(ATTR_P1));
        assertNull(c.getAttribute(ATTR_P2));
    }
    
    @Test
    public void testUniqueAttributes() throws Exception {
        Capability c1 = factory.createCapability("a");
        
        c1.setAttribute(ATTR_P1, "a");
        assertEquals(1, c1.getAttributes().size());
        
        c1.setAttribute(ATTR_P1, "a");
        assertEquals(1, c1.getAttributes().size());
        
        c1.setAttribute(ATTR_P1, "b");
        assertEquals(1, c1.getAttributes().size());

        c1.setAttribute(ATTR_P3, "a");
        assertEquals(2, c1.getAttributes().size());
    }
    
    @Test
    public void equals() throws Exception {
        Capability c1 = factory.createCapability("a");
        Capability c2 = factory.createCapability("a");
        
        assertEquals(c1, c2);
        
        c1.setAttribute(ATTR_P1, "v1");
        assertNotEquals(c1, c2);
        
        
        c2.setAttribute(ATTR_P1, "v1");
        assertEquals(c1, c2);
    }
    
    
    @Test
    public void testEqualNamespaces() {
        Capability c1 = factory.createCapability("cap");
        Capability c2 = factory.createCapability("cap");
        Capability c3 = factory.createCapability("cap3");
        
        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
        assertNotEquals(c2, c3);
    }
    
    @Test
    public void testHashSetContains() throws Exception {
        Capability c1 = factory.createCapability("a");
        Capability c2 = factory.createCapability("a");
        
        assertNotNull(c1);
        assertNotNull(c2);
        
        assertEquals(c1.hashCode(), c2.hashCode());
        
        c1.setAttribute(ATTR_P1, "p1");
        
        assertNotEquals(c1.hashCode(), c2.hashCode());

        c2.setAttribute(ATTR_P1, "p1");
        
        assertEquals(c1.hashCode(), c2.hashCode());
    }
}
