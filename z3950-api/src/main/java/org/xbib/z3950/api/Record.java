package org.xbib.z3950.api;

import java.io.InputStream;
import java.nio.charset.Charset;

public interface Record {

    int getNumber();

    InputStream asStream();

    String toString(Charset charset);
}
