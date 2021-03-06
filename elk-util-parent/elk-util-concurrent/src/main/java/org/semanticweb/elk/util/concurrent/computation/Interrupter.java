/*
 * #%L
 * ELK Utilities for Concurrency
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 - 2012 Department of Computer Science, University of Oxford
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.semanticweb.elk.util.concurrent.computation;

/**
 * An abstract interface for interrupting computations.
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public interface Interrupter {

	/**
	 * Set interrupt flag to the given value. After calling this method
	 * {@link #isInterrupted()} should return this value
	 * 
	 * @param flag
	 *            {@code true} if interrupt is requested {@code false} if not
	 */
	public void setInterrupt(boolean flag);

//	/**
//	 * Request interrupt of the computation. After calling this method,
//	 * {@link #isInterrupted()} should return {@code true}
//	 */
//	public void setInterrupt();
//
//	/**
//	 * Clears the interrupt status of the computation. After calling this
//	 * method, {@link #isInterrupted()} should return {@code false}
//	 */
//	public void clearInterrupt();

	/**
	 * @return {@code true} if interrupt was requested and {@code false}
	 *         otherwise
	 */
	public boolean isInterrupted();

}
