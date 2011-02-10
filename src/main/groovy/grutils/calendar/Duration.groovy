package grutils.calendar

class Duration {
    def seconds = 0
	def minutes = 0
	def hours = 0
	def days = 0
	def weeks = 0
	def years = 0
	
	/**
	* Format a duration that is in seconds into a hash 
	* 
	* @param seconds
	* @return [years:years, weeks:weeks, days:days, hours:hours, minutes:minutes, seconds:seconds]
	*/
   Duration(num){
	   if(!num) return
	   
	   if(num instanceof BigDecimal){
		   	seconds = num.toBigInteger()
	   }else if(num instanceof Double){
	   		seconds = new BigInteger(num.intValue())
	   }else{
	   		seconds = new BigInteger(num)
	   }
	   
	   
	   if(seconds > 60){
		   def divideResults = divide(seconds, 60)
		   minutes = divideResults[0]
		   seconds = divideResults[1]
	   }
	   
	   if(minutes > 60){
		   def divideResults = divide(minutes, 60)
		   hours = divideResults[0]
		   minutes = divideResults[1]
	   }
	   
	   if(hours > 24){
		   def divideResults = divide(hours, 24)
		   days = divideResults[0]
		   hours = divideResults[1]
	   }
	   
	   if(days > 365){
		   def divideResults = divide(days, 365)
		   years = divideResults[0]
		   days = divideResults[1]
	   }
	   
	   if(days > 7){
		   def divideResults = divide(days, 7)
		   weeks = divideResults[0]
		   days = divideResults[1]
	   }
	   
	   [years:years, weeks:weeks, days:days, hours:hours, minutes:minutes, seconds:seconds]
   }
   
   private divide(num, divisor){
	   num.divideAndRemainder(divisor)
   }
   
   String toString(){
	   StringBuilder sb = new StringBuilder()
	   if(years) sb.append("${years}y ")
	   if(weeks) sb.append("${weeks}w ")
	   if(days) sb.append("${days}d ")
	   if(hours) sb.append("${hours}h ")
	   if(minutes) sb.append("${minutes}m ")
	   if(seconds) sb.append("${seconds}s")
   }
}
