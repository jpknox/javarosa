package org.javarosa.communication.file;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.javarosa.core.JavaRosaServiceProvider;
import org.javarosa.core.services.properties.IPropertyRules;

/**
 * A set of rules for the properties of the Http Transport layer
 *
 * @author ctsims
 *
 */
public class FileTransportProperties implements IPropertyRules {
    Hashtable rules;
    Vector readOnlyProperties;
    
    public final static String SAVE_URI_LIST_PROPERTY = "SaveURIlist";
    public final static String SAVE_URI_PROPERTY = "SaveURI";
    public final static String LOAD_URI_PROPERTY = "LoadURI";

    /**
     * Creates the JavaRosa set of property rules
     */
    public FileTransportProperties() {
        rules = new Hashtable();
        readOnlyProperties = new Vector();


        // PostURL List Property
        rules.put(SAVE_URI_LIST_PROPERTY, new Vector());
        readOnlyProperties.addElement(SAVE_URI_LIST_PROPERTY);
        Vector postList = JavaRosaServiceProvider.instance().getPropertyManager().getProperty(SAVE_URI_LIST_PROPERTY);
        if(postList == null) {
        	JavaRosaServiceProvider.instance().getPropertyManager().setProperty(SAVE_URI_LIST_PROPERTY, new Vector());
        }
        

        // PostURL Property
        Vector postUrls = new Vector();
        postUrls.addElement(SAVE_URI_LIST_PROPERTY);
        rules.put(SAVE_URI_PROPERTY, postUrls);

        // GetURL Property
        Vector getUrls = new Vector();
        //getUrls.addElement(GET_URL_PROPERTY);
        rules.put(LOAD_URI_PROPERTY, getUrls);
    }

    /** (non-Javadoc)
     *  @see org.javarosa.properties.IPropertyRules#allowableValues(String)
     */
    public Vector allowableValues(String propertyName) {
        return (Vector)rules.get(propertyName);
    }

    /** (non-Javadoc)
     *  @see org.javarosa.properties.IPropertyRules#checkValueAllowed(String, String)
     */
    public boolean checkValueAllowed(String propertyName, String potentialValue) {
        Vector prop = ((Vector)rules.get(propertyName));
        if(prop.size() != 0) {
            //Check whether this is a dynamic property
            if(prop.size() == 1 && checkPropertyAllowed((String)prop.elementAt(0))) {
                // If so, get its list of available values, and see whether the potentival value is acceptable.
                return ((Vector)JavaRosaServiceProvider.instance().getPropertyManager().getProperty((String)prop.elementAt(0))).contains(potentialValue);
            }
            else {
                return ((Vector)rules.get(propertyName)).contains(potentialValue);
            }
        }
        else
            return true;
    }

    /** (non-Javadoc)
     *  @see org.javarosa.properties.IPropertyRules#allowableProperties()
     */
    public Vector allowableProperties() {
        Vector propList = new Vector();
        Enumeration iter = rules.keys();
        while (iter.hasMoreElements()) {
            propList.addElement(iter.nextElement());
        }
        return propList;
    }

    /** (non-Javadoc)
     *  @see org.javarosa.properties.IPropertyRules#checkPropertyAllowed)
     */
    public boolean checkPropertyAllowed(String propertyName) {
        Enumeration iter = rules.keys();
        while (iter.hasMoreElements()) {
            if(propertyName.equals(iter.nextElement())) {
                return true;
            }
        }
        return false;
    }
    
    /** (non-Javadoc)
     *  @see org.javarosa.properties.IPropertyRules#checkPropertyUserReadOnly)
     */
    public boolean checkPropertyUserReadOnly(String propertyName){
        return readOnlyProperties.contains(propertyName);
    }

    /*
     * (non-Javadoc)
     * @see org.javarosa.core.services.properties.IPropertyRules#getHumanReadableDescription(java.lang.String)
     */
    public String getHumanReadableDescription(String propertyName) {
    	if(SAVE_URI_LIST_PROPERTY.equals(propertyName)) {
    		return "List of possible file save locations";
    	} else if(SAVE_URI_PROPERTY.equals(propertyName)) {
    		return "Current file system save location";
    	} else if(LOAD_URI_PROPERTY.equals(propertyName)) {
    		return "Current file system load location";
     	}
    	return propertyName;
    }

    /*
     * (non-Javadoc)
     * @see org.javarosa.core.services.properties.IPropertyRules#getHumanReadableValue(java.lang.String, java.lang.String)
     */
    public String getHumanReadableValue(String propertyName, String value) {
    	return value;
    }
    /*
     * (non-Javadoc)
     * @see org.javarosa.core.services.properties.IPropertyRules#handlePropertyChanges(java.lang.String)
     */
    public void handlePropertyChanges(String propertyName) {
    	//Nothing
    }
}
