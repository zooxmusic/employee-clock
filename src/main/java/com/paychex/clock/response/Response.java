package com.paychex.clock.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Response<T> {

	private T data = null;
	private List<String> errors = new ArrayList<String>();

	private Response() {}

	public static <T> Response<T> create() {
		return new Response<T>();
	}
}
