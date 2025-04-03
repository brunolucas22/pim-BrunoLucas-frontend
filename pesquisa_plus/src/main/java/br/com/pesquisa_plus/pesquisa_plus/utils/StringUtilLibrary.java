package br.com.pesquisa_plus.pesquisa_plus.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

import javax.swing.text.MaskFormatter;

import org.springframework.stereotype.Component;



@Component
public class StringUtilLibrary {

	public static String removerAcentos(String texto) {
	    return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	public static String removerCaracteres(String texto) {
	    return texto.replaceAll("\\D", "");
	}
	
	public static String removerCaracteresEspeciais(String texto) {
	    return texto.replaceAll("[^\\p{L}\\p{N}\\s]", "");
	}
	public static String aplicarMascara(String pattern, String value) {
        try {
        	
        	MaskFormatter mask = new MaskFormatter(pattern);
            mask.setValueContainsLiteralCharacters(false);
            
            return mask.valueToString(value);
            
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
	
	
	public static String extrairPrimeiroUltimoNome(String nomeCompleto) {
		
		if (Objects.nonNull(nomeCompleto) && !nomeCompleto.isBlank()) {
			String[] partesDoNome = nomeCompleto.trim().split("\\s+");
			if (partesDoNome.length == 1)
				return partesDoNome[0];
			if (partesDoNome.length >= 2)
				return partesDoNome[0] + " " + partesDoNome[partesDoNome.length - 1];
		}
		return null;
	}
	
	
	
	
	
	public static String formatartValorMonetarioComoString(BigDecimal valor) {
		
		DecimalFormat df = new DecimalFormat("#,###.0000", new DecimalFormatSymbols(new Locale ("pt", "BR")));
		
		return "R$ " + df.format(valor);
	}
	
	public static String formatartValorQuantidadesComoString(BigDecimal valor) {
		
		DecimalFormat df = new DecimalFormat("#,###.00", new DecimalFormatSymbols(new Locale ("pt", "BR")));
		
		return df.format(valor);
	}
	
	public static String formatarTempoNanos(long  tempoExecucaoNanos) {
		
		long tempoExecucaoSegundos = tempoExecucaoNanos / 1_000_000_000;

		// Calcular horas, minutos e segundos
		long horas = tempoExecucaoSegundos / 3600;
		long minutos = (tempoExecucaoSegundos % 3600) / 60;
		long segundos = tempoExecucaoSegundos % 60;

		// Formatar a saída conforme o tempo de execução
		String tempoFormatado;
		if (horas > 0) {
		    tempoFormatado = String.format("%dh %dm %ds", horas, minutos, segundos);
		} else if (minutos > 0) {
		    tempoFormatado = String.format("%dm %ds", minutos, segundos);
		} else {
		    tempoFormatado = String.format("%ds", segundos);
		}

		return tempoFormatado;
	}
}
