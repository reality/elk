/*
 * #%L
 * ELK Utilities for Testing
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
/**
 * 
 */
package org.semanticweb.elk.testing;

/**
 * @author Pavel Klinov
 *
 * pavel.klinov@uni-ulm.de
 *
 */
public class HashTestOutput implements TestOutput {

	private final int hash;
	
	public HashTestOutput(final int hash) {
		this.hash = hash;
	}
	
	public HashTestOutput(final String hashHex, final int radix) {
		this.hash = Integer.parseInt(hashHex, radix);
	}
	
	public int getHash() {
		return hash;
	}

	@Override
	public String toString() {
		return "HashTestOutput [hash=" + hash + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hash;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashTestOutput other = (HashTestOutput) obj;
		if (hash != other.hash)
			return false;
		return true;
	}


}