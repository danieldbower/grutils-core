package com.ecommerce.grutils.dependencyinjection

/**
 * Build off of here to get simple Dependency Injection
 * @author daniel
 *
 */
abstract class AbstractDependencyBuilder implements DependencyBuilder{

	private final Config config
	
	/**
	 * If you create a new Constructor for your dependency builder, remember to call super()
	 * @param config
	 */
	AbstractDependencyBuilder(final Config config){
		this.config = config
	}
	
	public abstract void initialize()
	
	public abstract void closeResources()
	
	public Config getConfig(){
		return config
	}
}
