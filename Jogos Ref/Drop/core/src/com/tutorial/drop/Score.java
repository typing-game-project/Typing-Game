package com.tutorial.drop;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Score implements Json.Serializable {
	public String name;
	public String points;

	@Override
	public void write(Json json) {
		json.writeValue("name",name);
		json.writeValue("points",points);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		name = jsonData.child().name();
	    points = jsonData.child().asString();
	}
}
