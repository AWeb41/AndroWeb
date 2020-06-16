package com.androweb.application.engine.app.download;

import com.androweb.application.R;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

// inspired by https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/recipes/Progress.java

abstract class ResponseBodyWrapper extends ResponseBody
{
	abstract Source wrapSource(Source original);

	private final ResponseBody wrapped;
	private BufferedSource buffer;

	ResponseBodyWrapper(ResponseBody wrapped)
	{
		this.wrapped = wrapped;
	}

	@Override
	public MediaType contentType()
	{
		return(wrapped.contentType());
	}

	@Override
	public long contentLength()
	{
		return(wrapped.contentLength());
	}

	@Override
	public BufferedSource source()
	{
		if (buffer == null)
		{
			buffer = Okio.buffer(wrapSource(wrapped.source()));
		}

		return(buffer);
	}
}
