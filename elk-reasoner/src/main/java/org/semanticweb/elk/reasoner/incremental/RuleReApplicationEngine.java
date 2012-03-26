/*
 * #%L
 * ELK Reasoner
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
package org.semanticweb.elk.reasoner.incremental;

import java.util.Map;

import org.semanticweb.elk.reasoner.indexing.OntologyIndex;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpressionChange;
import org.semanticweb.elk.reasoner.rules.RuleApplicationEngine;
import org.semanticweb.elk.reasoner.rules.SaturatedClassExpression;
import org.semanticweb.elk.util.collections.LazySetIntersection;

public class RuleReApplicationEngine extends RuleApplicationEngine {

	final Map<IndexedClassExpression, IndexedClassExpressionChange> indexAdditions;

	public RuleReApplicationEngine(OntologyIndex ontologyIndex,
			ContextModificationListener listener) {
		super(ontologyIndex, listener, false, true);
		indexAdditions = ontologyIndex.getIndexChange().getIndexAdditions();
	}

	void processContextAdditions(SaturatedClassExpression context) {
		final QueueProcessor queueProcessor = new QueueProcessor(context);

		// applying inferences for all changed indexed class expressions of the
		// context
		for (IndexedClassExpression changedIndexedClassExpression : new LazySetIntersection<IndexedClassExpression>(
				indexAdditions.keySet(), context.getSuperClassExpressions())) {
			IndexedClassExpressionChange change = indexAdditions
					.get(changedIndexedClassExpression);
			queueProcessor.processClass(change);
		}
	}

	void processContext(SaturatedClassExpression context) {
		// re-initializing context just in case
		context.clear();
		initContext(context);
	}
}
