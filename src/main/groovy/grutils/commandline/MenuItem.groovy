package grutils.commandline

/**
 * MenuItems for a Menu
 * @author daniel
 *
 */
class MenuItem implements Comparable{

	MenuItem(String selector, String description, int sortOrder, Closure action){
		this.selector = selector
		this.description = description
		this.sortOrder = sortOrder
		this.action = action
	}
	
	int sortOrder
	String selector
	String description
	
	String toString(){
		return "$selector - $description"
	}
	
	Closure action
	
	int compareTo(Object other){
		if(other && other instanceof MenuItem){
			MenuItem b = (MenuItem) other
			if(this.sortOrder==b.sortOrder){
				return this.selector.compareTo(b.selector)
			}else{
				return this.sortOrder.compareTo(b.sortOrder)
			}
		}else{
			throw new Exception("Cannot compare to null or non menu item")
		}
	}
	
}
