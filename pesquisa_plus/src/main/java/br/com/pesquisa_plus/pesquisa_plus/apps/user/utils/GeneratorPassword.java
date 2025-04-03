package br.com.pesquisa_plus.pesquisa_plus.apps.user.utils;

public class GeneratorPassword {

    public static String generate(int qtdeMaximaCaracteres){
		
	    String[] caracteres = { "0", "1", "b", "2", "4", "5", "6", "7", "8",
	                "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
	                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
	                "x", "y", "z"};
	    
		StringBuilder password = new StringBuilder();

        for (int i = 0; i < qtdeMaximaCaracteres; i++) {
            int position = (int) (Math.random() * caracteres.length);
            password.append(caracteres[position]);
        }
        return password.toString();
	}
    
}
