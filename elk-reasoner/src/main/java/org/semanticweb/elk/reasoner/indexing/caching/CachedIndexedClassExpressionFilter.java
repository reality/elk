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
 * A filter method for types of {@link CachedIndexedClassExpression} that
 * produces elements of the same type.
 * 
 * @author "Yevgeny Kazakov"
 *
 */
public interface CachedIndexedClassExpressionFilter {

	public CachedIndexedClass filter(CachedIndexedClass element);

	public CachedIndexedIndividual filter(CachedIndexedIndividual element);

	public CachedIndexedObjectComplementOf filter(
			CachedIndexedObjectComplementOf element);

	public CachedIndexedObjectIntersectionOf filter(
			CachedIndexedObjectIntersectionOf element);

	public CachedIndexedObjectSomeValuesFrom filter(
			CachedIndexedObjectSomeValuesFrom element);

	public CachedIndexedObjectUnionOf filter(CachedIndexedObjectUnionOf element);

	public CachedIndexedDataHasValue filter(CachedIndexedDataHasValue element);
}
