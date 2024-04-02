package ch07.irsystem.concurrent;

import java.util.List;

import ch07.irsystem.common.Token;

public class ConcurrentInvertedIndex {

	private List<Token> index;

	public void setIndex(List<Token> index) {
		this.index = index;
	}

	public List<Token> getIndex() {
		return index;
	}

	
	
}
