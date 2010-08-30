package com.ecommerce.grutils.cl

/**
 * Contains menu items
 * @author daniel
 *
 */
class Menu {

	String intro = "What would you like to do?"
	
	List<MenuItem> menuItems = new ArrayList<MenuItem>()
	
	String toString(){
		menuItems = menuItems.sort()
		
		StringBuilder sb = new StringBuilder()
		
		sb.append intro
		sb.append "\n"
		
		menuItems.each{ MenuItem menuItem ->
			sb.append menuItem.toString()
			sb.append "\n"
		}
		return sb.toString()
	}
	
	String selectorsToString(){
		return menuItems.selector.toString()
	}
	
	boolean performMenuAction(String choice){
		MenuItem selected = null
		
		for(MenuItem menuItem : menuItems){
			if(menuItem.selector==choice){
				selected = menuItem
				break
			}
		}
		
		if(selected){
			selected.action()
			return true
		}else{
			return false
		}
	}
}
