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
package org.apache.polaris.service.catalog.response;

import jakarta.annotation.Nonnull;

/**
 * Allows Iceberg response types to be wrapped alongside an ETag that can be unwrapped into the HTTP
 * ETag header.
 *
 * <p>TODO: Remove this if ever the ETag change to the REST spec is propagated to the iceberg rest
 * library representation of {@link org.apache.iceberg.rest.responses.LoadTableResponse}
 *
 * @param response the iceberg response to encapsulate
 * @param eTag the eTag value
 * @param <T> The type of the encapsulated response object
 */
public record ETaggedResponse<T>(@Nonnull T response, @Nonnull String eTag) {}
