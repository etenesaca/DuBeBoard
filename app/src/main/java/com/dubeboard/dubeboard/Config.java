package com.dubeboard.dubeboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.Locale;

public class Config {
	// Definimos el nombre del archivo de configuracion
	private final String SHARED_PREFS_FILE = "DuBeBoard_Config";

	// Datos que se van a manejar
	private final String KEY_LANG = "lang";
	private final String KEY_TEXT_SIZE = "text_size";

	private Context mContext;

	public Config(Context context) {
		mContext = context;
	}

	// Obtenemos el archivo donde se guardan las preferencias para poder
	// modificarlas o leerlas
	private SharedPreferences getSettings() {
		return mContext.getSharedPreferences(SHARED_PREFS_FILE, 0);
	}

	/*
	 * IDIOMA DE PRONUNCIACIÖN
	 */
	public String getLang() {
		return getSettings().getString(KEY_LANG, "Español - spa_ES");
	}

	public Locale getLangLocale() {
		String[] LangArray = this.getLang().split(" - ")[1].split("_");
		Locale Lang = new Locale(LangArray[0], LangArray[1]);
		return Lang;
	}

	public void setLang(String Lang) {
		SharedPreferences.Editor editor = getSettings().edit();
		editor.putString(KEY_LANG, Lang);
		editor.commit();
	}
	/*
	 * TAMAÑO DE TEXTO
	 */
	public int getTextSize() {
		return getSettings().getInt(KEY_TEXT_SIZE, 18);
	}

	public void setTextSize(String TextSize) {
		setTextSize(Integer.parseInt(TextSize));
	}
	public void setTextSize(int TextSize) {
		SharedPreferences.Editor editor = getSettings().edit();
		editor.putInt(KEY_TEXT_SIZE, TextSize);
		editor.commit();
	}
}
