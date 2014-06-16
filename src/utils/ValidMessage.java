package utils;

public class ValidMessage {
	private static  String[] curses =new String[]{ "shit","fuck","asshole","motherfucker","jerk","cunt",
													"idiot","bitch","douchebag","douche","butthead","nigger"};
	public static boolean isValidMessage(String message){
		String[] wordsFromMessage = message.split("[^\\w']+");

		for(String curse : curses){
			for(String word : wordsFromMessage){
				if(curse.equals(word))
					return false;
			}
		}
		return true;
	}
}
