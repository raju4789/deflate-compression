package com.raju.medium.deflate_compression.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.zip.DeflaterOutputStream;

public class DeflateCompressionFilter implements Filter {

    private static final int MIN_RESPONSE_SIZE = 1024; // Minimum size to compress (1 KB)

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Check if the client supports Deflate compression
        String acceptEncoding = httpRequest.getHeader("Accept-Encoding");
        if (acceptEncoding == null || !acceptEncoding.toLowerCase().contains("deflate-compression")) {
            // If the client does not support Deflate, proceed without compression
            chain.doFilter(request, response);
            return;
        }

        // Wrap the response to capture the output
        DeflateResponseWrapper responseWrapper = new DeflateResponseWrapper(httpResponse);

        // Proceed with the filter chain
        chain.doFilter(request, responseWrapper);

        // Compress the response if it meets the size threshold
        if (responseWrapper.getContentLength() > MIN_RESPONSE_SIZE) {
            httpResponse.setHeader("Content-Encoding", "deflate");
            try (DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(httpResponse.getOutputStream())) {
                deflaterOutputStream.write(responseWrapper.getCapturedData());
            }
        } else {
            // Write the uncompressed response
            httpResponse.getOutputStream().write(responseWrapper.getCapturedData());
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}