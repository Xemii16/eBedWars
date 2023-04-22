package com.yecraft.engine;


import java.io.Serializable;

public enum GameStatus implements Serializable {
	WAIT,
	START,
	ACTIVE,
	WIN,
	RESTART,
	DRAW
}