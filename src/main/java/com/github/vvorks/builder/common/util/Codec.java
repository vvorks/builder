package com.github.vvorks.builder.common.util;

public interface Codec<D, E> {

	public E encode(D decodedData);

	public D decode(E encodedData);

}
