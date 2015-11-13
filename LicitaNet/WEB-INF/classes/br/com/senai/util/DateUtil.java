package br.com.senai.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getDataFormatada(Date date){
		if(date != null){
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return format.format(date);
		}	
		return "";
	}
	
}
