package com.brunomarcos.typinggame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class LevelJSONData implements Json.Serializable {
	public ArrayList<String> frase;
	public ArrayList<String> password;
	public ArrayList<Integer> timer;
	public ArrayList<Integer> maxVida;
	public ArrayList<String> bg;
	public Map<String,Texture> bgTex;
	public ArrayList<String> bgm;
	public Map<String,Music> bgmFile;
	public ArrayList<String> arteCantos;
	public Map<String,Texture> arteCantosTex;

	@Override
	public void write(Json json) {}

	@Override
	public void read(Json json, JsonValue jsonData) {
		frase = new ArrayList<String>();
		password = new ArrayList<String>();
		timer = new ArrayList<Integer>();
		maxVida = new ArrayList<Integer>();
		bg = new ArrayList<String>();
		bgTex = new HashMap<String,Texture>();
		bgm = new ArrayList<String>();
		bgmFile = new HashMap<String,Music>();
		arteCantos = new ArrayList<String>();
		arteCantosTex = new HashMap<String,Texture>();
		
		for (int i = 0; i < jsonData.size; i++) {
			for (int j = 0; j < jsonData.get(i).size; j++) {
				JsonValue jv = jsonData.get(i).get(j);
				if (jv.name.equals("frase"))
					frase.add(i, jv.asString());
				else if (jv.name.equals("password"))
					password.add(i, jv.asString().toUpperCase());
				else if (jv.name.equals("timer"))
					timer.add(i, jv.asInt());
				else if (jv.name.equals("maxVida"))
					maxVida.add(i, jv.asInt());
				else if (jv.name.equals("background"))
					bg.add(i, jv.asString());
				else if (jv.name.equals("bgm"))
					bgm.add(i, jv.asString());
				else if (jv.name.equals("arteCantos"))
					arteCantos.add(i, jv.asString());
			}
			
			this.popularMapTex(bg, bgTex);
			this.popularMapTex(arteCantos, arteCantosTex);
			this.popularMapMusic(bgm, bgmFile);
		}
	}
	
	public void dispose() {
		this.disposeMapTex(bg, bgTex);
		this.disposeMapTex(arteCantos, arteCantosTex);
		this.disposeMapMusic(bgm, bgmFile);
	}
	
	// Método para carregar as texturas
	public void popularMapTex(ArrayList<String> name, Map<String,Texture> map) {
		for (int i = 0; i < name.size();i++) {
			if (i == 0) {
				map.put(name.get(i), new Texture(Gdx.files.internal(name.get(i))));
			}
			else {
				for (int j = 0; j < map.size(); j++) {
					if (map.containsKey(name.get(i)))
						break;
					else {
						map.put(name.get(i), new Texture(Gdx.files.internal(name.get(i))));
					}
				}
			}
		}
	}
	
	// Método para carregar as músicas dos levels
	public void popularMapMusic(ArrayList<String> name, Map<String,Music> map) {
		for (int i = 0; i < name.size();i++) {
			if (i == 0) {
				map.put(name.get(i), Gdx.audio.newMusic(Gdx.files.internal(name.get(i))));
			}
			else {
				for (int j = 0; j < map.size(); j++) {
					if (map.containsKey(name.get(i)))
						break;
					else {
						map.put(name.get(i), Gdx.audio.newMusic(Gdx.files.internal(name.get(i))));
					}
				}
			}
		}
	}
	
	// Método para apagar as texturas
	public void disposeMapTex(ArrayList<String> name, Map<String,Texture> map) {
		for(String i:name) {
			try {
				map.get(i).dispose();
			} catch(Exception e) {
				continue;
			}
		}
	}
	
	// Método para apagar as músicas dos levels
	public void disposeMapMusic(ArrayList<String> name, Map<String,Music> map) {
		for(String i:name) {
			try {
				map.get(i).dispose();
			} catch(Exception e) {
				continue;
			}
		}
	}
}
