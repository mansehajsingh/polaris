/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.polaris.service.ratelimiter;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import org.apache.polaris.service.config.PolarisFilterPriorities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Request filter that returns a 429 Too Many Requests if the rate limiter says so */
@Provider
@PreMatching
@Priority(PolarisFilterPriorities.RATE_LIMITER_FILTER)
@ApplicationScoped
public class RateLimiterFilter implements ContainerRequestFilter {
  private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterFilter.class);

  private final RateLimiter rateLimiter;

  @Inject
  public RateLimiterFilter(RateLimiter rateLimiter) {
    this.rateLimiter = rateLimiter;
  }

  /** Returns a 429 if the rate limiter says so. Otherwise, forwards the request along. */
  @Override
  public void filter(ContainerRequestContext ctx) throws IOException {
    if (!rateLimiter.canProceed()) {
      ctx.abortWith(Response.status(Response.Status.TOO_MANY_REQUESTS).build());
      LOGGER.atDebug().log("Rate limiting request");
    }
  }
}
