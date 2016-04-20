package com.brunomarcos.typinggame;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Resources implements Json.Serializable {
	ArrayList<String> frase;
	ArrayList<String> password;
	ArrayList<Integer> timer;
	ArrayList<Integer> maxVida;

	@Override
	public void write(Json json) {}

	@Override
	public void read(Json json, JsonValue jsonData) {
		frase = new ArrayList<String>();
		password = new ArrayList<String>();
		timer = new ArrayList<Integer>();
		maxVida = new ArrayList<Integer>();
		
		for (int i = 0; i < jsonData.size; i++) {
			for (int j = 0; j < jsonData.get(i).size; j++) {
				JsonValue jv = jsonData.get(i).get(j);
				if (jv.name.equals("fraseTemp"))
					frase.add(i, jv.asString());
				else if (jv.name.equals("password"))
					password.add(i, jv.asString());
				else if (jv.name.equals("timer"))
					timer.add(i, jv.asInt());
				else if (jv.name.equals("maxVida"))
					maxVida.add(i, jv.asInt());
			}
		}
	}
}
