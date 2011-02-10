package grutils.dependencyinjection

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration object.  Pulls external config file from an environment variable.
 * @author daniel
 *
 */
class Config {
    
	/**
	 * Requires name of the environment variable to look for the external config file
	 * @param confEnvironmentVariable
	 */
	Config(String confEnvironmentVariable){
		this.confEnvironmentVariable = confEnvironmentVariable
        loadProperties()
    }
    
	private String confEnvironmentVariable
    private Properties props
    
    private void loadProperties(){
        props = new Properties()
        if(System.getenv(confEnvironmentVariable)){
			String fileLocation = System.getenv(confEnvironmentVariable)
            File file = new File(fileLocation)
            if(!(file.exists() && file.canRead())){
                getLogger().error ("Unable to load config.properties from {}", fileLocation)
            }else{
                props.load (file.newInputStream())
                getLogger().info("Read Configuration file from {}", fileLocation)
            }
        }else{
            throw new RuntimeException("You must set the path to the Configuration file in an environment variable named ${confEnvironmentVariable}")
        }
    }
    
    private Logger logger
    
    private Logger getLogger(){
        if(!logger){
            logger = LoggerFactory.getLogger("com.ecommerce.grutil.dependencyinjection.Config")
        }
        return logger
    }
    
	/**
	 * Access properties through this interface.
	 * @param propertyName
	 * @return
	 */
    String getProperty(String propertyName){
        if(props){
            return props[(propertyName)]
        }
    }
}
