package org.semanticweb.elk.reasoner.indexing.caching;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2014 Department of Computer Science, University of Oxford
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
 * A {@link CachedIndexedObjectFactory} which uses a given
 * {@link ModifiableIndexedObjectCache} to reuse the objects previously created
 * by this factory. An object in the {@link ModifiableIndexedObjectCache} is
 * reused (returned by the factory) if it is structurally equivalent to the one
 * being constructed.
 * 
 * @author "Yevgeny Kazakov"
 *
 */
public class ResolvingCachedIndexedObjectFactory extends
		DelegatingCachedIndexedObjectFactory {

	private final ModifiableIndexedObjectCache cache_;

	public ResolvingCachedIndexedObjectFactory(
			CachedIndexedObjectFactory baseFactory,
			ModifiableIndexedObjectCache cache) {
		super(baseFactory);
		this.cache_ = cache;
	}

	@Override
	<T extends CachedIndexedObject<T>> T filter(T input) {
		return cache_.resolve(input);
	}

}
